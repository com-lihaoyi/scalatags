package scalatags

import acyclic.file
import scala.collection.SortedMap

object all extends StringTags with Attrs with Styles with Tags with DataConverters
object short extends StringTags{
  object * extends StringTags with Attrs with Styles
}
/**
 * Convenience object to help import all [[Tags]], [[Attrs]], [[Styles]] and
 * [[Datatypes]] into the global namespace via `import scalatags.all._`
 */
trait StringTags extends Util{ self =>

  type Target = StringBuilder
  implicit class StringAttr(s: String) extends AttrVal[Target]{
    override def applyTo(strb: StringBuilder, k: Attr): Unit = {
      strb ++= " " ++= k.name ++= "=\""
      Escaping.escape(s, strb)
      strb ++= "\""
    }
  }
  implicit class StringStyle(s: String) extends StyleVal[Target]{
    override def applyTo(strb: StringBuilder, k: Style): Unit = {
      strb ++= " " ++= k.cssName ++= "=\""
      Escaping.escape(s, strb)
      strb ++= "\""
    }
  }

  def raw(s: String) = new RawNode(s)

  /**
   * A [[scalatags.Node]] which contains a String.
   */
  case class StringNode(v: String) extends Node[Target] {
    def writeTo(strb: StringBuilder): Unit = Escaping.escape(v, strb)
  }

  /**
   * A [[scalatags.Node]] which contains a String which will not be escaped.
   */
  case class RawNode(v: String) extends Node[Target] {
    def writeTo(strb: StringBuilder): Unit = strb ++= v
  }

  /**
   * Allows you to modify a HtmlTag by adding a String to its list of children
   */
  implicit def stringNode(v: String) = new StringNode(v)

  /**
   * Lets you put numbers into a scalatags tree, as a no-op.
   */
  implicit def NumericModifier[T: Numeric](u: T) = new StringNode(u.toString)
  type HtmlTag = TypedHtmlTag[Platform.Base]
  val HtmlTag = TypedHtmlTag
  def makeAbstractTypedHtmlTag[T <: Platform.Base](tag: String, void: Boolean) = {
    TypedHtmlTag(tag, Nil, SortedMap.empty, Nil, SortedMap.empty, void)
  }
  case class TypedHtmlTag[T <: Platform.Base](tag: String = "",
                                         children: List[Node[Target]],
                                         attrs: SortedMap[String, AttrVal[Target]],
                                         classes: List[Any],
                                         styles: SortedMap[Style, StyleVal[Target]],
                                         void: Boolean = false)
                                         extends AbstractTypedHtmlTag[T, Target]{
    type Self = TypedHtmlTag[T]
    def collapsedAttrs = {
      var moddedAttrs = attrs
      if (!classes.isEmpty) {
        moddedAttrs = moddedAttrs.updated(
          "class",
          (moddedAttrs.get("class") ++ classes.reverseIterator).mkString(" ")
        )
      }
      if (!styles.isEmpty) {
        val styleStrings = styles.map { case (k, v) => s"${k.cssName}: $v;"}.toList
        moddedAttrs = moddedAttrs.updated(
          "style",
          (moddedAttrs.get("style") ++ styleStrings).mkString(" ")
        )
      }
      moddedAttrs
    }

    /**
     * Serialize this [[HtmlTag]] and all its children out to the given StringBuilder.
     */
    def writeTo(strb: Target): Unit = {
      // tag
      strb ++= "<" ++= tag

      // attributes
      for ((attr, value) <- collapsedAttrs) {
        strb ++= " " ++= attr ++= "=\""
        Escaping.escape(value.toString, strb)
        strb ++= "\""
      }
      for ((k, v) <- styles) {
        v.applyTo(strb, k)
      }
      for (c <- children) {
        c.writeTo(strb)
      }
      if (children.isEmpty && void)
      // No children - close tag
        strb ++= " />"
      else {
        strb ++= ">"
        // Childrens
        var x = children.reverse
        while (!x.isEmpty) {
          val child :: newX = x
          x = newX
          child.writeTo(strb)
        }

        // Closing tag
        strb ++= "</" ++= tag ++= ">"
      }
    }
    /**
     * Converts an ScalaTag fragment into an html string
     */
    override def toString = {
      val strb = new StringBuilder
      writeTo(strb)
      strb.toString()
    }

    override def copy(children: List[Node[Target]],
                      attrs: SortedMap[String, AttrVal[Target]],
                      classes: List[Any],
                      styles: SortedMap[Style, StyleVal[Target]]): Self = {
      this.copy(children, attrs, classes, styles)
    }
  }
}
