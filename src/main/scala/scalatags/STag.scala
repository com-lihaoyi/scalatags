package scalatags

import scala.collection.mutable.StringBuilder

/**
 * A general interface for all types which can appear in a ScalaTags fragment.
 */
trait STag{
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
  def children: Seq[STag]
}

/**
 * An algebraic data type representing a HTML tag
 *
 * @param tag The name of the tag
 * @param children The children of the tag
 * @param attrMap The tag's attributes
 * @param classes The tag's classes
 * @param styles The tag's CSS styles
 */
case class HtmlTag(tag: String = "",
                   children: Seq[STag] = Nil,
                   attrMap: Map[String, Any] = Map.empty,
                   classes: Seq[Any] = Nil,
                   styles: Map[String, Any] = Map.empty) extends STag with HtmlTrait{

  def apply(x1: STag*) = this.copy(children = children ++ x1)

  def attr(t: (String, Any)*) = {
    copy(attrMap = t.foldLeft(attrMap)(_+_))
  }

  def writeTo(strb: StringBuilder): Unit = {
    // tag
    strb ++= "<" ++= tag
    // classes
    if(classes != Nil)
      strb ++= " class=\"" ++= classes.mkString(" ") ++= "\""
    // attributes
    attrMap.foreach(a => strb ++= " " ++= a._1 ++= "=\"" ++= a._2.toString ++= "\"")
    // styles
    if(!styles.isEmpty) {
      strb ++= " style=\""
      styles.foreach(s => strb ++= s._1 ++= ":" ++= s._2.toString ++= ";")
      strb ++= "\""
    }
    if(children == Nil)
      // No children - close tag
      strb ++= "/>"
    else {
      strb ++= ">"
      // Childrens
      children.foreach(_.writeTo(strb))
      // Closing tag
      strb ++= "</" ++= tag ++= ">"
    }
  }

}
