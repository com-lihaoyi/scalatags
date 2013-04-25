import scala.xml._

package object scalatags
extends Tags
with Misc{

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
   * A STag node which contains a sequence of STags.
   */
  implicit class SeqXNode[A <% STag](x: Seq[A]) extends STag{
    def toXML() = x.flatMap(n => n.toXML())
    def children = x.map(x => x: STag)
    def searchable = Nil
    def thisTag = x
  }


  implicit def Tuple2XNode[A <% STag, B <% STag](x: (A, B)) = SeqXNode(Seq[STag](x._1, x._2))
  implicit def Tuple3XNode[A <% STag, B <% STag, C <% STag](x: (A, B, C)) = SeqXNode(Seq(x._1: STag, x._2: STag, x._3: STag))
  implicit def Tuple4XNode[A <% STag, B <% STag, C <% STag, D <% STag](x: (A, B, C, D)) = SeqXNode(Seq[STag](x._1, x._2, x._3, x._4))
  implicit def Tuple5XNode[A <% STag, B <% STag, C <% STag, D <% STag, E <% STag](x: (A, B, C, D, E)) = SeqXNode(Seq[STag](x._1, x._2, x._3, x._4, x._5))

  /**
   * A STag node which contains an XML fragment.
   */
  implicit class XmlXNode(x: NodeSeq) extends STag{
    def toXML() = x
    def children = Nil
    def searchable = Nil
    def thisTag = x
  }

  /**
   * A STag node which contains a String.
   */
  implicit class StringXNode(x: String) extends STag{
    def toXML() = scala.xml.Text(x)
    def children = Nil
    def searchable = Seq(x)
    def thisTag = x
  }

  /**
   * Extension class to easily convert `Any`s to STags
   */
  implicit class objXNodeable(x: Any){
    def toSTag = ObjectXNode(x)
  }
  /**
   * A STag node which contains an arbitrary Object.
   */
  case class ObjectXNode(x: Any) extends STag{
    def toXML() = scala.xml.Text(x.toString)
    def children = Nil
    def searchable = Seq(x)
    def thisTag = x
  }
}
