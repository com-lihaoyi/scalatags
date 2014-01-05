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

  implicit class NodeTagger(v: Node) extends Mod{
    def modify(children: mutable.Buffer[Node],
               attrs: mutable.Map[String, String],
               classes: mutable.Buffer[String],
               styles: mutable.Map[String, String]) = {
      children.append(v)
    }
  }

  implicit class StringTagger(v: String) extends Mod{
    def modify(children: mutable.Buffer[Node],
               attrs: mutable.Map[String, String],
               classes: mutable.Buffer[String],
               styles: mutable.Map[String, String]) = {
      children.append(new StringNode(v))
    }
  }

  /**
   * A STag node which contains a sequence of STags.
   */
  implicit class SeqTagger[A <% Mod](xs: Seq[A]) extends Mod{
    def modify(children: mutable.Buffer[Node],
               attrs: mutable.Map[String, String],
               classes: mutable.Buffer[String],
               styles: mutable.Map[String, String]) = {
      for(x <- xs) x.modify(children, attrs, classes, styles)
    }
  }
}
