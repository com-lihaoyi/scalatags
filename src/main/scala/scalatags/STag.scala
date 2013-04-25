package scalatags
import scala.xml._

/**
 * A general interfact for all types which can appear in a ScalaTags fragment.
 */
trait STag{
  def toXML(): NodeSeq
  def children: Seq[STag]
  def searchable: Seq[Any]
  def thisTag: Any

  def findSTag[T](filter: PartialFunction[Any, T]): Seq[T] =
    this.children.flatMap(_.findSTag(filter)) ++ filter.lift(this.thisTag)
  def findAttached[T](filter: PartialFunction[Any, T]): Seq[T] =
    this.children.flatMap(x => x.findAttached(filter)) ++ this.searchable.flatMap(x => filter.lift(x))
}

/**
 * An algebraic data type representing a HTML tag
 *
 * @param tag The name of the tag
 * @param children The children of the tag
 * @param attrMap The tag's attributes
 * @param classes The tag's classes
 * @param styles The tag's CSS styles
 */
case class HtmlTag(tag: String = "",
                   children: Seq[STag] = Nil,
                   attrMap: Map[String, Any] = Map.empty,
                   classes: Seq[Any] = Nil,
                   styles: Map[String, Any] = Map.empty) extends STag with HtmlTrait{

  def apply(x1: STag*) = this.copy(children = children ++ x1)

  def attr(t: (String, Any)*) = {
    copy(attrMap = t.foldLeft(attrMap)(_+_))
  }

  def toXML(): Elem = {
    val c = flattenChildren(children)
    var newAttrMap = attrMap
    if (classes != Nil) newAttrMap = newAttrMap.updated("class", attrMap.getOrElse("class", "") + classes.map(_.toString + " ").mkString)
    if (styles != Map.empty) newAttrMap = newAttrMap.updated("style", attrMap.getOrElse("style", "") + styles.map{case (k, v) => k + ": " + v + "; "}.mkString)
    newAttrMap.foldLeft(new Elem(null, tag, Null, TopScope, false, c: _*))(
      (e, k) => e % new UnprefixedAttribute(k._1, k._2.toString, Null)
    )
  }

  def searchable = (attrMap.values ++ classes ++ styles.values).toList
  def thisTag = this
  def flattenChildren(c: Seq[STag]) =
    c.flatMap(_.toXML())

}
