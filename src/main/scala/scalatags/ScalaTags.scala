package scalatags



import scala.xml._
object ScalaTags extends Attributes with Tags with Misc{


  def flattenChildren(c: Seq[STag]) = c.flatMap(_.toXML()).foldLeft(Seq[Node]()){ (l, r) =>
    (l, r) match {
      case (rest :+ (lt: Text), rt: Text) =>
        rest :+ Text(lt.text + rt.text)
      case _ => l :+ r
    }
  }
  case class STag(tag: String = "",
                      children: Seq[STag] = Seq(),
                      attrMap: Map[String, Any] = Map().withDefaultValue(""),
                      classes: Seq[Any] = Nil,
                      styles: Map[String, Any] = Map.empty){



    def apply(x1: STag*) = this.copy(children = children ++ x1)

    def attr(t: (String, Any)*) = {
      copy(attrMap = t.foldLeft(attrMap)(_+_))
    }

    def toXML(): NodeSeq = {
      val c = flattenChildren(children)
      var newAttrMap = attrMap
      if (classes != Nil) newAttrMap = newAttrMap.updated("class", attrMap("class") + classes.map(_.toString + " ").mkString)
      if (styles != Map.empty) newAttrMap = newAttrMap.updated("style", attrMap("style") + styles.map{case (k, v) => k + ": " + v + "; "}.mkString)
      newAttrMap.foldLeft(new Elem(null, tag, Null, TopScope, c: _*))(
        (e, k) => e % new UnprefixedAttribute(k._1, k._2.toString, Null)
      )
    }
  }

  implicit class SymbolToNode(S: Symbol){
    def x = new STag(S.name)
  }
  implicit class StringToNode(S: String){
    def x = new STag(S)
  }

  implicit class SeqXNode[A <% STag](x: Seq[A]) extends STag{
    override def toXML() = flattenChildren(x.map(n => n: STag))
  }
  implicit class XmlXNode(x: NodeSeq) extends STag{
    override def toXML() = x
  }
  implicit class StringXNode(x: String) extends STag{
    override def toXML() = scala.xml.Text(x)
  }

}
