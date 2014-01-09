package scalatags
import scala.collection.{SortedMap, mutable}

/**
 * Represents a single CSS class.
 */
class Cls(val name: String) extends Nested{
  def build(children: mutable.Buffer[Node], attrs: mutable.Map[String, String]) = {
    attrs("class") = attrs.get("class").fold(name)(_ + " " + name)
  }
}

/**
 * A Node which contains a String.
 */
class StringNode(val v: String) extends Node{
  def writeTo(strb: StringBuilder): Unit = Escaping.escape(v, strb)
  def children = Nil
}

/**
 * A Node which contains a String which will not be escaped.
 */
class RawNode(val v: String) extends Node{
  def writeTo(strb: StringBuilder): Unit = strb ++= v
  def children = Nil
}

/**
 * A general interface for all XML types which can appear in a ScalaTags fragment.
 */
trait Node extends Nested{
  /**
   * Converts an ScalaTag fragment into an html string
   */
  override def toString() = {
    val strb = new StringBuilder
    writeTo(strb)
    strb.toString
  }

  /**
   * Appends the textual representation of the ScalaTag fragment to the
   * 'strb' StringBuilder, so StringBuilder can be passed to childrens.
   * Used to optimize the toString() operation.
   */
  def writeTo(strb: StringBuilder): Unit

  /**
   * The children of a ScalaTag node
   */
  def children: Seq[Node]

  def build(children: mutable.Buffer[Node], attrs: mutable.Map[String, String]) = {
    children.append(this)
  }
}
/**
 * Represents a value that can be nested within a Node. This can be another
 * Node, but can also be a CSS style or HTML attribute binding, which will
 * add itself to the node's attributes but not appear in the final `children`
 * list.
 */
trait Nested{
  def build(children: mutable.Buffer[Node], attrs: mutable.Map[String, String]): Unit
}

/**
 * A single HTML tag.
 *
 * Note that the various properties of interest (e.g. children, attrs,
 * classes, styles) are all computed mutably/lazily in order to speed up the
 * serialization of the node while still keeping it immutable from the
 * outside.
 *
 * @param tag The name of the tag
 */
class HtmlTag(val tag: String = "", nested: List[Nested] = Nil) extends Node{

  if (!Escaping.validTag(tag))
    throw new IllegalArgumentException(
      s"Illegal tag name: $tag is not a valid XML tag name"
    )
  /**
   * The children of this HtmlTag.
   */
  lazy val children = _children
  /**
   * The HTML attributes, as a map. This does not include the styles of classes
   * defined in the HtmlTag's `styles` or `classes` members, which will be
   * concatenated to any `styles` or `classes` in the `attrs` when the tag
   * is finally rendered to HTML.
   */
  lazy val attrs = _attrs

  /**
   * Represents the list of CSS classes this HtmlTag contains; lazily derived
   * from `attrs`.
   */
  lazy val classes = attrs("class").split(" ").toSeq
  /**
   * Represents the list of CSS styles this HtmlTag contains; lazily derived
   * from `attrs`.
   */
  lazy val styles = attrs("style").split(";|:")
                                  .map(_.trim)
                                  .filter(_ != "")
                                  .grouped(2)
                                  .map(pair => pair(0) -> pair(1))
                                  .toSeq

  // Render all these things lazily using mutable state; the bulk of HtmlTags
  // ever created will never be rendered, and it is faster to quickly build up
  // large fragments using mutability rather than trying to use persistent
  // collections all the way.
  private[this] lazy val (
    _children: Seq[Node],
    _attrs: Map[String, String]
  ) = {
    val _children = mutable.Buffer.empty[Node]
    val _attrs = mutable.Map.empty[String, String]
    for (n <- nested.reverseIterator){
      n.build(_children, _attrs)
    }

    (_children.toSeq, SortedMap.empty[String, String] ++ _attrs)
  }

  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the HtmlTag. Note that any these modifications are queued up and only
   * evaluated when (if!) the HtmlTag is rendered.
   */
  def apply(xs: Nested*) = {
    var newNested = nested
    for (x <- xs){
      newNested = x :: newNested
    }
    new HtmlTag(tag, newNested)
  }

  /**
   * Serialize this HtmlTag and all its children out to the given StringBuilder.
   */
  def writeTo(strb: StringBuilder): Unit = {
    // tag
    strb ++= "<" ++= tag

    // attributes
    for((attr, value) <- attrs){
      strb ++= " " ++= attr ++= "=\""
      Escaping.escape(value, strb)
      strb ++= "\""
    }
    if(children == Nil)
      // No children - close tag
      strb ++= " />"
    else {
      strb ++= ">"
      // Childrens
      children.foreach(_.writeTo(strb))
      // Closing tag
      strb ++= "</" ++= tag ++= ">"
    }
  }
}

/**
 * A key value pair representing the assignment of an attribute to a value.
 */
case class AttrPair(attr: Attr[_], value: String) extends Nested{
  def build(children: mutable.Buffer[Node], attrs: mutable.Map[String, String]) = {
    attrs(attr.name) = value
  }
}

/**
 * Wraps up a HTML attribute in a statically-typed value with an associated
 * type; overloads the ~= operator to also accept values of that type to convert
 * to strings, allowing more concise and pseudo-typesafe use of that attribute.
 */
class Attr[T](val name: String){
  if (!Escaping.validAttrName(name))
    throw new IllegalArgumentException(
      s"Illegal attribute name: $name is not a valid XML attribute name"
    )

  override def toString = name
  /**
   * Force-assigns a typed attribute to a string even if its type would not
   * normally allow it.
   */
  def ~=(v: String) = AttrPair(this, v)

  /**
   * Assign an attribute to some value of the correct type.
   */
  def :=(v: T) = AttrPair(this, v.toString)
}

/**
 * A Style that only has a fixed set of possible values, provided by its members.
 */
class Style(val jsName: String, val cssName: String) {
  /**
   * Force-assigns a typed style to a string even if its type would not normally
   * .allow it
   */
  def ~=(value: String) = StylePair(this, value)
  override def toString = cssName
}

/**
 * A key value pair representing the assignment of a style to a value.
 */
case class StylePair(style: Style, value: String) extends Nested{
  def build(children: mutable.Buffer[Node], attrs: mutable.Map[String, String]) = {
    val str = style.cssName + ": " + value + ";"
    attrs("style") = attrs.get("style").fold(str)(_ + " " + str)
  }
}
/**
 * A Style that takes any value of type T as a parameter; overloads the :=
 * operator to also accept values of that type to convert to strings, allowing
 * more concise and pseudo-typesafe use of that style.
 */
class TypedStyle[T](jsName: String, cssName: String) extends Style(jsName, cssName) {
  /**
   * Assign this style to some value of the correct type.
   */
  def :=(value: T) = StylePair(this, value.toString)
}

