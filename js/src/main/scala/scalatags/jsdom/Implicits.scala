package scalatags
package jsdom
import scalatags.generic._
import org.scalajs.dom
import scala.collection.SortedMap
import scalatags.generic.Style
import scalatags.generic.Attr

object Implicits extends generic.AbstractPackage[dom.Element]{

  /**
   * Lets you put numbers into a scalatags tree, as a no-op.
   */
  implicit def NumericModifier[T: Numeric](u: T) = new StringNode(u.toString)

  /**
   * Allows you to modify a HtmlTag by adding a String to its list of children
   */
  implicit def stringNode(v: String) = new StringNode(v)
  /**
   * A [[Node]] which contains a String.
   */
  object StringNode extends Companion[StringNode]
  case class StringNode(v: String) extends Node {
    def writeTo(elem: dom.Element) = elem.appendChild(dom.document.createTextNode(v))
  }

  def raw(s: String) = new RawNode(s)
  /**
   * A [[Node]] which contains a String which will not be escaped.
   */
  object RawNode extends Companion[RawNode]
  case class RawNode(v: String) extends Node {
    def writeTo(elem: dom.Element): Unit = elem.insertAdjacentHTML("beforeend", v)
  }

  class GenericAttr[T](val t: T) extends AttrVal[dom.Element]{
    override def applyTo(elem: dom.Element, k: Attr): Unit = {
      elem.setAttribute(k.name, t.toString)
    }

    override def merge(o: AttrVal[dom.Element]): AttrVal[dom.Element] = ???
    override def applyPartial(t: dom.Element): Unit = ???
  }
  case class StringAttr(s: String) extends GenericAttr(s)
  case class BooleanAttr(b: Boolean) extends GenericAttr(b)
  case class NumericAttr[T: Numeric](n: T) extends GenericAttr(n)
  implicit def stringAttr(s: String) = new StringAttr(s)
  implicit def booleanAttr(b: Boolean) = new BooleanAttr(b)
  implicit def numericAttr[T: Numeric](n: T) = new NumericAttr(n)


  class GenericStyle[T](t: T) extends StyleVal[dom.Element]{
    override def applyTo(elem: dom.Element, k: Style): Unit = {
      elem.asInstanceOf[dom.HTMLElement]
          .style
          .setProperty(k.cssName, t.toString)
    }
  }
  case class StringStyle(s: String) extends GenericStyle(s)
  case class BooleanStyle(b: Boolean) extends GenericStyle(b)
  case class NumericStyle[T: Numeric](n: T) extends GenericStyle(n)
  implicit def stringStyle(s: String) = new StringStyle(s)
  implicit def booleanStyle(b: Boolean) = new BooleanStyle(b)
  implicit def numericStyle[T: Numeric](n: T) = new NumericStyle(n)

  case class TypedHtmlTag[T <: Platform.Base](tag: String = "",
                                              children: List[Node],
                                              attrs: SortedMap[Attr, AttrVal[dom.Element]],
                                              styles: SortedMap[Style, StyleVal[dom.Element]],
                                              void: Boolean = false)
    extends generic.TypedHtmlTag[T, dom.Element]{
    type Self = TypedHtmlTag[T]

    /**
     * Serialize this [[HtmlTag]] and all its children out to the given dom.Element.
     */
    def writeTo(elem: dom.Element): Unit = {
      elem.appendChild(toDom)
    }
    /**
     * Converts an ScalaTag fragment into an html string
     */
    def toDom = {
      val elem = dom.document.createElement(tag)
      var styleWritten = false
      def applyStyles() = {
        for ((style, value) <- styles) value.applyTo(elem, style)
      }
      for ((attr, value) <- attrs) {
        if (attr.name >= "style" && !styleWritten){
          if (attr.name > "style") {
            applyStyles()
            value.applyTo(elem, attr)
          }else{
            value.applyTo(elem, attr)
            applyStyles()
          }
          styleWritten = true
        }else{
          value.applyTo(elem, attr)
        }
      }
      if (!styleWritten){
        applyStyles()
      }
      for (c <- children.reverseIterator) c.writeTo(elem)
      elem.asInstanceOf[T]
    }

    override def transform(children: List[Node],
                           attrs: SortedMap[Attr, AttrVal[dom.Element]],
                           styles: SortedMap[Style, StyleVal[dom.Element]]): Self = {
      this.copy(children=children, attrs=attrs, styles=styles)
    }

    override def toString = toDom.outerHTML
  }
  type HtmlTag = TypedHtmlTag[Platform.Base]
  val HtmlTag = TypedHtmlTag
}
