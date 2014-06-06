package scalatags

import scalatags.generic._
import org.scalajs.dom
import scala.collection.SortedMap
import scala.annotation.switch
import scala.scalajs.js
object JsDom extends generic.Bundle[dom.Element]{
  object all extends StringTags with Attrs with Styles with Tags with DataConverters with Util
  object short extends StringTags with Util with DataConverters with generic.AbstractShort[dom.Element]{
    object * extends StringTags with Attrs with Styles
  }
  object misc extends AbstractMisc[dom.Element]{
    object attrs extends StringTags with Attrs
    object tags extends StringTags with Tags
    object tags2 extends StringTags with Tags2
    object styles extends StringTags with Styles
    object styles2 extends StringTags with Styles2
    object svgTags extends StringTags with SvgTags
    object svgStyles extends StringTags with SvgStyles
  }
  /**
   * Convenience object to help import all [[Tags]], [[Attrs]], [[Styles]] and
   * [[Datatypes]] into the global namespace via `import scalatags.all._`
   */
  trait StringTags extends Util{ self =>
    type ConcreteHtmlTag[T <: Platform.Base] = TypedHtmlTag[T]

    protected[this] implicit def stringAttrInternal(s: String) = new StringAttr(s)
    protected[this] implicit def booleanAttrInternal(b: Boolean) = new BooleanAttr(b)
    protected[this] implicit def numericAttrInternal[T: Numeric](n: T) = new NumericAttr(n)

    protected[this] implicit def stringStyleInternal(s: String) = new StringStyle(s)
    protected[this] implicit def booleanStyleInternal(b: Boolean) = new BooleanStyle(b)
    protected[this] implicit def numericStyleInternal[T: Numeric](n: T) = new NumericStyle(n)

    def makeAbstractTypedHtmlTag[T <: Platform.Base](tag: String, void: Boolean): TypedHtmlTag[T] = {
      TypedHtmlTag(tag, Nil, SortedMap.empty, SortedMap.empty, void)
    }
  }

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
  case class Bind(v: js.Any) extends AttrVal{
    def applyTo(elem: dom.Element, k: Attr): Unit = {
      elem.asInstanceOf[js.Dynamic].updateDynamic(k.name)(v)
    }

    override def merge(o: AttrVal): AttrVal = ???
    override def applyPartial(t: dom.Element): Unit = ???
  }
  def raw(s: String) = new RawNode(s)
  /**
   * A [[Node]] which contains a String which will not be escaped.
   */
  object RawNode extends Companion[RawNode]
  case class RawNode(v: String) extends Node {
    def writeTo(elem: dom.Element): Unit = elem.insertAdjacentHTML("beforeend", v)
  }

  class GenericAttr[T](val t: T) extends AttrVal{
    def applyTo(elem: dom.Element, k: Attr): Unit = {
      elem.setAttribute(k.name, t.toString)
    }

    override def merge(o: AttrVal): AttrVal = ???
    override def applyPartial(t: dom.Element): Unit = ???
  }
  case class StringAttr(s: String) extends GenericAttr(s)
  case class BooleanAttr(b: Boolean) extends GenericAttr(b)
  case class NumericAttr[T: Numeric](n: T) extends GenericAttr(n)
  implicit def stringAttr(s: String) = new StringAttr(s)
  implicit def booleanAttr(b: Boolean) = new BooleanAttr(b)
  implicit def numericAttr[T: Numeric](n: T) = new NumericAttr(n)


  class GenericStyle[T](t: T) extends StyleVal{
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
                                              attrs: SortedMap[Attr, AttrVal],
                                              styles: SortedMap[Style, StyleVal],
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
        for (pair <- styles) {
          pair._2.applyTo(elem, pair._1)
        }
      }
      for (pair <- attrs) {
        val attr = pair._1
        val value = pair._2

        val cmp = attr.name.toString compareTo "style"
        (cmp: @switch) match {
          case -1 => value.applyTo(elem, attr)
          case 1 if !styleWritten =>
            applyStyles()
            value.applyTo(elem, attr)
            styleWritten = true
          case 0 if !styleWritten =>
            value.applyTo(elem, attr)
            applyStyles()
            styleWritten = true
        }
      }
      if (!styleWritten) applyStyles()

      var x = children.reverse
      while (!x.isEmpty) {
        val child :: newX = x
        x = newX
        child.writeTo(elem)
      }
      elem.asInstanceOf[T]
    }

    override def transform(children: List[Node],
                           attrs: SortedMap[Attr, AttrVal],
                           styles: SortedMap[Style, StyleVal]): Self = {
      this.copy(children=children, attrs=attrs, styles=styles)
    }

    override def toString = toDom.outerHTML
  }
  type HtmlTag = TypedHtmlTag[Platform.Base]
  val HtmlTag = TypedHtmlTag
}
