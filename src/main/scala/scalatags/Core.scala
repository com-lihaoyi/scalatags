package scalatags
import scala.collection.{SortedMap, mutable}

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
 * A general interface for all types which can appear in a ScalaTags fragment.
 */
trait Node{
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
}

/**
 * Represents a value that can be nested within a Node. This can be another
 * node, but can also be a CSS style or HTML attribute binding, which will
 * add itself to the node's attributes.
 */
class Nested(val build: (mutable.Buffer[Node], mutable.Map[String, String]) => Unit)

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
