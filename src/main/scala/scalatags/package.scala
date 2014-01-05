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

  implicit class NodeTagger(v: Node) extends Mods{
    def modify(tag: HtmlTag): HtmlTag = tag.copy(children = tag.children :+ v)
  }

  implicit class StringTagger(v: String) extends Mods{
    def modify(tag: HtmlTag): HtmlTag = tag.copy(children = tag.children :+ new StringNode(v))
  }

  /**
   * A STag node which contains a sequence of STags.
   */
  implicit class SeqSTag[A <% Mods](val xs: Seq[A]) extends Mods{
    def modify(tag: HtmlTag): HtmlTag = {
      xs.foldLeft(tag)((tag, tagger) => tagger.modify(tag))
    }
  }


}
