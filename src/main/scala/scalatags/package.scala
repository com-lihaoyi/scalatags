import scala.collection.mutable

/**
 * ScalaTags is a small XML/HTML construction library for Scala. See
 * [[https://github.com/lihaoyi/scalatags the Github page]] for an introduction
 * and documentation.
 */
package object scalatags
extends Core
with Tags
with Styles
with Attributes
with Misc{
  type Color = String
  type Length = String
  type Number = String

  /**
   * Allows you to modify a HtmlNode by adding a Node to its list of children
   */
  implicit class NodeMod(v: Node) extends QuickMod(
    (children, attrs, classes, styles) => children.append(v)
  )

  /**
   * Allows you to modify a HtmlNode by adding a String to its list of children
   */
  implicit class StringMod(v: String) extends QuickMod(
    (children, attrs, classes, styles) => children.append(new StringNode(v))
  )

  /**
   * Allows you to modify a HtmlNode by adding a Seq containing other mod-able
   * objects to its list of children.
   */
  implicit class SeqMod[A <% Mod](xs: Seq[A])extends QuickMod(
    (children, attrs, classes, styles) => for(x <- xs) x.modify(children, attrs, classes, styles)
  )

  implicit class NodeableString(s: String){
    def x = new HtmlTag(s)
    def attr = new Attr(s)
    def attrTyped[T] = new TypedAttr[T](s)
    def style = new Style(s, s)
  }
}
