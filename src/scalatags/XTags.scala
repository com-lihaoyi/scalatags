package scalatags



import scala.xml._
object XTags extends HtmlAttributes with HtmlTags{

  trait XNode{
    def toXML(): NodeSeq
  }

  trait XmlNode extends XNode{
    type T <: XNode

    def tag: String
    def children: Seq[XNode]
    def attrs: Map[String, String]

    def attr(t: (String, String)*) = {
      new HtmlNode(tag, children, t.foldLeft(attrs)(_ + _))
    }
  }
  def flattenChildren(c: Seq[XNode]) = c.flatMap(_.toXML()).foldLeft(Seq[Node]()){ (l, r) =>
    (l, r) match {
      case (rest :+ (lt: Text), rt: Text) =>
        println("COMBINE")
        rest :+ Text(lt.text + rt.text)
      case _ => l :+ r
    }
  }
  case class HtmlNode(tag: String = "",
                      children: Seq[XNode] = Seq(),
                      attrs: Map[String, String] = Map().withDefaultValue(""))
                      extends XmlNode{

    override type T = HtmlNode

    def apply(x1: XNode*) = new HtmlNode(tag, children ++  x1, attrs)

    def toXML() = {
      val c = flattenChildren(children)

      attrs.foldLeft(new Elem(null, tag, Null, TopScope, c: _*))(
        (e, k) => e % new UnprefixedAttribute(k._1, k._2, Null)
      )
    }
  }




  implicit class SymbolToNode(S: Symbol){
    def x = new HtmlNode(S.name)
  }
  implicit class StringToNode(S: String){
    def x = new HtmlNode(S)
  }

  implicit class SeqXNode[A <% XNode](x: Seq[A]) extends XNode{
    def toXML() = flattenChildren(x.map(n => n: XNode))
  }
  implicit class XmlXNode(x: NodeSeq) extends XNode{
    def toXML() = x
  }
  implicit class StringXNode(x: String) extends XNode{
    def toXML() = scala.xml.Text(x)
  }
  implicit class StringFuncXNode[A <% XNode](x: () => A) extends XNode{
    def toXML() = (x(): XNode).toXML()
  }


  def raw(s: String) = scala.xml.Unparsed(s)

  def javascript(origin: String = "") =
    script.attr("type" -> "text/javascript").src(origin)

  def js(jcontents: String, args: Any*): XNode ={
    script.attr("type" -> "text/javascript")(
      raw(
        "$(function(){" +
          jcontents.format(
            args.map( x =>
              x
            ):_*
          ) +
          "});"
      )
    )
  }

  def jsFor(jcontents: String, elemCls: String, args: Any*): XNode =
    js("(function(elem, elemCls){" + jcontents + "})($('.%s'), '%s')".format(elemCls, elemCls), args: _*)


  def stylesheet(origin: String = "") = link.rel("stylesheet").href(origin)


}
