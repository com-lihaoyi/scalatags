package scalatags

trait Core{


  implicit class NodeableString(s: String){
    def x = HtmlTag(s)
  }

  /**
   * A STag node which contains a String.
   */
  class StringNode(val v: String) extends Node{
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
  trait Mods{
    def modify(tag: HtmlTag): HtmlTag
  }

  /**
   * An algebraic data type representing a HTML tag
   *
   * @param tag The name of the tag
   * @param children The children of the tag
   * @param attrs The tag's attributes
   * @param classes The tag's classes
   * @param styles The tag's CSS styles
   */
  case class HtmlTag(tag: String = "",
                     children: Seq[Node] = Nil,
                     attrs: Map[String, String] = Map.empty,
                     classes: Seq[String] = Nil,
                     styles: Map[String, String] = Map.empty) extends Node{

    def apply(xs: Mods*) = xs.foldLeft(this)((tag, taglike) => taglike.modify(tag))


    def cls(newClasses: String*) = this.copy(classes = classes ++ newClasses)
    def attr(newAttrs: (String, String)*) = this.copy(attrs = attrs ++ newAttrs)
    def style(newStyles: (String, String)*) = this.copy(styles = styles ++ newStyles)

    def writeTo(strb: StringBuilder): Unit = {
      // tag
      strb ++= "<" ++= tag
      // classes
      if(!classes.isEmpty)
        strb ++= " class=\"" ++= classes.mkString(" ") ++= "\""
      // attributes

      attrs.foreach(a => strb ++= " " ++= a._1 ++= "=\"" ++= a._2.toString ++= "\"")

      // styles
      if(!styles.isEmpty) {
        strb ++= " style=\""
        styles.foreach(s => strb ++= s._1 ++= ": " ++= s._2.toString ++= "; ")
        strb.setLength(strb.length - 1) // delete last character
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
}