import scala.xml._

/**
 * ScalaTags is a small XML/HTML construction library for Scala. See
 * [[https://github.com/lihaoyi/scalatags the Github page]] for an introduction
 * and documentation.
 */
package object scalatags
extends Tags
with Misc{
  object Implicits{
    /**
     * Extension class to provide a nice syntax to convert a String into an
     * STags tag: `"tag-name".x`.
     */
    implicit class StringToNodeable(S: String){ def x = new HtmlTag(S) }

    /**
     * Extension class to provide a nice syntax to convert a Symbolinto an
     * STags tag: `'tagname.x`.
     */
    implicit class SymbolToNodeable(S: Symbol){ def x = new HtmlTag(S.name) }

    /**
     * Extension class to easily convert `Any`s to STags
     */
    implicit class objXNodeable(x: Any){
      def toSTag = ObjectSTag(x)
    }
  }
  implicit lazy val stringToNodeable = Implicits.StringToNodeable _
  implicit lazy val symbolToNodeable = Implicits.SymbolToNodeable _
  implicit lazy val objectToNodeable = Implicits.objXNodeable _
  /**
   * A STag node which contains a sequence of STags.
   */
  implicit class SeqSTag[A <% STag](x: Seq[A]) extends STag{
    def toXML() = x.flatMap(n => n.toXML())
    def children = x.map(x => x: STag)
  }


  implicit def Tuple2XNode[A <% STag, B <% STag](x: (A, B)) = SeqSTag(Seq[STag](x._1, x._2))
  implicit def Tuple3XNode[A <% STag, B <% STag, C <% STag](x: (A, B, C)) = SeqSTag(Seq(x._1: STag, x._2: STag, x._3: STag))
  implicit def Tuple4XNode[A <% STag, B <% STag, C <% STag, D <% STag](x: (A, B, C, D)) = SeqSTag(Seq[STag](x._1, x._2, x._3, x._4))
  implicit def Tuple5XNode[A <% STag, B <% STag, C <% STag, D <% STag, E <% STag](x: (A, B, C, D, E)) = SeqSTag(Seq[STag](x._1, x._2, x._3, x._4, x._5))

  /**
   * A STag node which contains an XML fragment.
   */
  implicit class XmlSTag(x: NodeSeq) extends STag{
    def toXML() = x
    def children = Nil
  }

  /**
   * A STag node which contains a String.
   */
  implicit class StringSTag(x: String) extends STag{
    def toXML() = scala.xml.Text(x)
    def children = Nil
  }


  /**
   * A STag node which contains an arbitrary Object.
   *
   * [[ObjectSTag]]s are generally equivalent to [[StringSTag]]s, except that
   * they maintain a reference to the original object and prevent it from being
   * garbage collected.
   */
  case class ObjectSTag(obj: Any) extends STag{
    def toXML() = scala.xml.Text(x.toString)
    def children = Nil
  }
}