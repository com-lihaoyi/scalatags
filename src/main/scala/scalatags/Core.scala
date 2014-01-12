package scalatags
import scala.collection.{SortedMap, mutable}

/**
 * Represents a single CSS class.
 */
class Cls(val name: String) extends Nested{
  def transform(tag: HtmlTag) = {
    tag.copy(attrs =
      tag.attrs.updated(
        "class",
        tag.attrs.get("class").fold(name)(_ + " " + name)
      )
    )
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
  override def toString = {
    val strb = new StringBuilder
    writeTo(strb)
    strb.toString()
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

  def transform(tag: HtmlTag) = {
    tag.copy(children = tag.children :+ this)
  }
}
/**
 * Represents a value that can be nested within a Node. This can be another
 * Node, but can also be a CSS style or HTML attribute binding, which will
 * add itself to the node's attributes but not appear in the final `children`
 * list.
 */
trait Nested{
  def transform(tag: HtmlTag): HtmlTag
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
 * @param children Child nodes
 * @param attrs A sorted map of attributes
 * @param void Whether or not the tag can be self-closing
 */
case class HtmlTag(tag: String = "",
                   children: Seq[Node],
                   attrs: SortedMap[String, String],
                   void: Boolean = false) extends Node{

  /**
   * Represents the list of CSS classes this HtmlTag contains; lazily derived
   * from `attrs`.
   */
  def classes = attrs("class").split(" ").toSeq

  /**
   * Represents the list of CSS styles this HtmlTag contains; lazily derived
   * from `attrs`.
   */
  def styles = {
    SortedMap.empty[String, String] ++
      attrs("style").split(";|:")
        .iterator
        .map(_.trim)
        .filter(_ != "")
        .grouped(2)
        .map(pair => pair(0) -> pair(1))
  }

  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the HtmlTag. Note that any these modifications are queued up and only
   * evaluated when (if!) the HtmlTag is rendered.
   */
  def apply(xs: Nested*) = {
    var newTag = this

    var i = 0
    while(i < xs.length){
      newTag = xs(i).transform(newTag)
      i += 1
    }
    newTag
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
    if(children.isEmpty && void)
      // No children - close tag
      strb ++= " />"
    else {
      strb ++= ">"
      // Childrens
      var i = 0
      val l = children.length
      while(i < l){
        children(i).writeTo(strb)
        i += 1
      }
      // Closing tag
      strb ++= "</" ++= tag ++= ">"
    }
  }
}

/**
 * A key value pair representing the assignment of an attribute to a value.
 */
case class AttrPair(attr: Attr, value: String) extends Nested{
  def transform(tag: HtmlTag) = {
    tag.copy(attrs = tag.attrs.updated(attr.name, value))
  }
}

/**
 * Wraps up a HTML attribute in an untyped value with an associated
 * type; the := operator takes Strings.
 */
class Attr(val name: String){
  if (!Escaping.validAttrName(name))
    throw new IllegalArgumentException(
      s"Illegal attribute name: $name is not a valid XML attribute name"
    )

  override def toString = name
  /**
   * Force-assigns a typed attribute to a string even if its type would not
   * normally allow it.
   */
  def :=(v: String) = AttrPair(this, v)

}

/**
 * Wraps up a HTML attribute in a statically-typed value with an associated
 * type; overloads the := operator to also accept values of that type to convert
 * to strings, allowing more concise and pseudo-typesafe use of that attribute.
 */
class TypedAttr[T](name: String) extends Attr(name){
  override def toString = name

  /**
   * Assign an attribute to some value of the correct type.
   */
  def :=(v: T): AttrPair = AttrPair(this, v.toString)
}

/**
 * A Style that only has a fixed set of possible values, provided by its members.
 */
class Style(val jsName: String, val cssName: String) {
  /**
   * Force-assigns a typed style to a string even if its type would not normally
   * .allow it
   */
  def :=(value: String) = StylePair(this, value)
  override def toString = cssName
}

/**
 * A key value pair representing the assignment of a style to a value.
 */
case class StylePair(style: Style, value: String) extends Nested{
  def transform(tag: HtmlTag) = {
    val str = style.cssName + ": " + value + ";"
    tag.copy(attrs =
      tag.attrs.updated(
        "style",
        tag.attrs.get("style").fold(str)(_ + " " + str)
      )
    )
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

