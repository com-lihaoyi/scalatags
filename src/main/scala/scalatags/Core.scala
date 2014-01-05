package scalatags
import scala.collection.{SortedMap, mutable}
trait Core{



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
  trait Mod{
    def modify(children: mutable.Buffer[Node],
               attrs: mutable.Map[String, String],
               classes: mutable.Buffer[String],
               styles: mutable.Map[String, String]): Unit
  }
  type ModifyFunction = (mutable.Buffer[Node], mutable.Map[String, String], mutable.Buffer[String], mutable.Map[String, String]) => Unit
  class QuickMod(val f: ModifyFunction) extends Mod{
    def modify(children: mutable.Buffer[Core.this.type#Node],
               attrs: mutable.Map[String, String],
               classes: mutable.Buffer[String],
               styles: mutable.Map[String, String]): Unit = {
      f(children, attrs, classes, styles)
    }

  }
  /**
   * A single HTML tag.
   *
   * Note that the various properties of interest (e.g. children, attrs,
   * classes, styles) are all computed lazily in order to speed up the
   *
   * @param tag The name of the tag
   */
  class HtmlTag(val tag: String = "", mods: Seq[Mod] = Nil) extends Node{
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
     * The CSS classes belonging to this HTML tag.
     */
    lazy val classes = _classes
    /**
     * The inline CSS Styles belonging to this HTML tag.
     */
    lazy val styles = _styles

    // Render all these things lazily using mutable state; the bulk of HtmlTags
    // ever created will never be rendered, and it is faster to quickly build up
    // large fragments using mutability rather than trying to use persistent
    // collections all the way.
    private[this] lazy val (
      _children: Seq[Node],
      _attrs: Map[String, String],
      _classes: Seq[String],
      _styles: Map[String, String]
    ) = {
      val _children = mutable.Buffer.empty[Node]
      val _attrs = mutable.Map.empty[String, String]
      val _styles = mutable.Map.empty[String, String]
      val _classes = mutable.Buffer.empty[String]
      for (mod <- mods){
        mod.modify(_children, _attrs, _classes, _styles)
      }
      (_children.toSeq, SortedMap.empty[String, String] ++ _attrs, _classes.toSeq, SortedMap.empty[String, String] ++ _styles)
    }

    /**
     * Add the given modifications (e.g. additional children, or new attributes)
     * to the HtmlTag. Note that any these modifications are queued up and only
     * evaluated when (if!) the HtmlTag is rendered.
     */
    def apply(xs: Mod*) = new HtmlTag(tag, mods ++ xs)

    private[this] def modify(f: ModifyFunction) = new HtmlTag(tag, mods :+ new QuickMod(f))

    /**
     * Add additional classes to this HtmlTag
     */
    def cls(newClasses: String*) = modify((children, attrs, classes, styles) => classes.appendAll(newClasses))
    /**
     * Add additional attribtues to this HtmlTag
     */
    def attr(newAttrs: (String, String)*) = modify((children, attrs, classes, styles) =>
      for ((k, v) <- newAttrs) attrs(k) = v
    )
    /**
     * Add additional inline styles to this HtmlTag
     */
    def style(newStyles: (String, String)*) = modify((children, attrs, classes, styles) =>
      for ((k, v) <- newStyles) styles(k) = v
    )

    /**
     * Serialize this HtmlTag and all its children out to the given StringBuilder.
     */
    def writeTo(strb: StringBuilder): Unit = {
      // tag
      strb ++= "<" ++= tag
      // classes
      if(!classes.isEmpty) strb ++= " class=\"" ++= classes.mkString(" ") ++= "\""

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