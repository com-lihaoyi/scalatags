package scalatags

import scala.xml._
class ScalaTags(genUUIDx: => String = scala.util.Random.alphanumeric.take(6).mkString)
extends Attributes
with Tags
with Misc{

  implicit class SymbolToNode(S: Symbol){ def x = new HtmlTag(S.name) }
  implicit class StringToNode(S: String){ def x = new HtmlTag(S) }
  implicit class SeqXNode[A <% STag](x: Seq[A]) extends STag{
    def toXML(path: List[Int] = Nil) = x.zipWithIndex.flatMap{case (n, i) => n.toXML(i::path)}
    def children = x.map(x => x: STag)
    override def thisTag: Any = x
    def searchable = Nil
  }

  implicit def Tuple2XNode[A <% STag, B <% STag](x: (A, B)) = SeqXNode(Seq[STag](x._1, x._2))
  implicit def Tuple3XNode[A <% STag, B <% STag, C <% STag](x: (A, B, C)) = SeqXNode(Seq(x._1: STag, x._2: STag, x._3: STag))
  implicit def Tuple4XNode[A <% STag, B <% STag, C <% STag, D <% STag](x: (A, B, C, D)) = SeqXNode(Seq[STag](x._1, x._2, x._3, x._4))
  implicit def Tuple5XNode[A <% STag, B <% STag, C <% STag, D <% STag, E <% STag](x: (A, B, C, D, E)) = SeqXNode(Seq[STag](x._1, x._2, x._3, x._4, x._5))

  implicit class XmlXNode(x: NodeSeq) extends STag{
    def toXML(path: List[Int] = Nil) = x
    def children = Nil
    override def thisTag: Any = x
    def searchable = Nil
  }
  implicit class StringXNode(x: Any) extends STag{
    def toXML(path: List[Int] = Nil) = scala.xml.Text(x.toString)
    def children = Nil
    override def thisTag: Any = x
    def searchable = Nil
  }
}
trait STag{
  def toXML(path: List[Int] = Nil): NodeSeq
  def children: Seq[STag]
  def thisTag: Any = this
  def searchable: Seq[Any]

  def findSTag[T](filter: PartialFunction[Any, T]): Seq[T] =
    this.children.flatMap(_.findSTag(filter)) ++ filter.lift(thisTag)

  def findAttached[T](filter: PartialFunction[Any, T]): Seq[T] =
    this.children.flatMap(x => x.findAttached(filter)) ++ this.searchable.flatMap(x => filter.lift(x))
}

case class HtmlTag(tag: String = "",
                   children: Seq[STag] = Seq(),
                   attrMap: Map[String, Any] = Map().withDefaultValue(""),
                   classes: Seq[Any] = Nil,
                   styles: Map[String, Any] = Map.empty) extends STag{

  def apply(x1: STag*) = this.copy(children = children ++ x1)

  def attr(t: (String, Any)*) = {
    copy(attrMap = t.foldLeft(attrMap)(_+_))
  }

  def toXML(path: List[Int] = Nil): NodeSeq = {
    val c = children.zipWithIndex.flatMap{case (n, i) => n.toXML(i::path)}
    var newAttrMap = attrMap
    if (classes != Nil) newAttrMap = newAttrMap.updated("class", attrMap("class") + classes.map(_.toString + " ").mkString)
    if (styles != Map.empty) newAttrMap = newAttrMap.updated("style", attrMap("style") + styles.map{case (k, v) => k + ": " + v + "; "}.mkString)
    newAttrMap.foldLeft(new Elem(null, tag, Null, TopScope, false, c: _*))(
      (e, k) => e % new UnprefixedAttribute(k._1, k._2.toString, Null)
    )
  }

  def searchable = (attrMap.values ++ classes ++ styles.values).toList

}
case class Raw(content: Any, processor: String => String = x => x) extends STag{
  def toXML(path: List[Int] = Nil) = Unparsed(processor(content.toString))
  def children = Nil
  def searchable = List(content)
}
