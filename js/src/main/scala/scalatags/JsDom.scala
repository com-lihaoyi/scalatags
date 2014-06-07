package scalatags

import org.scalajs.dom
import scala.scalajs.js


/**
 * A Scalatags module that generates `dom.Element`s when the tags are rendered.
 * This provides some additional flexibility over the [[Text]] backend, as you
 * can bind structured objects to the attributes of your `dom.Element` without
 * serializing them first into strings.
 */
object JsDom extends generic.Bundle[dom.Element] with LowPriorityImplicits{
  object all extends StringTags with Attrs with Styles with Tags with DataConverters with Util
  object short extends StringTags with Util with DataConverters with generic.AbstractShort[dom.Element]{
    object * extends StringTags with Attrs with Styles
  }

  object attrs extends StringTags with Attrs
  object tags extends StringTags with Tags
  object tags2 extends StringTags with Tags2
  object styles extends StringTags with Styles
  object styles2 extends StringTags with Styles2
  object svgTags extends StringTags with SvgTags
  object svgStyles extends StringTags with SvgStyles

  trait StringTags extends Util{ self =>
    type ConcreteHtmlTag[T <: Platform.Base] = TypedTag[T]

    protected[this] implicit def stringAttr = new GenericAttr[String]
    protected[this] implicit def booleanAttr= new GenericAttr[Boolean]
    protected[this] implicit def numericAttr[T: Numeric] = new GenericAttr[T]
    protected[this] implicit def stringStyle = new GenericStyle[String]
    protected[this] implicit def booleanStyle = new GenericStyle[Boolean]
    protected[this] implicit def numericStyle[T: Numeric] = new GenericStyle[T]

    def makeAbstractTypedTag[T <: Platform.Base](tag: String, void: Boolean): TypedTag[T] = {
      TypedTag(tag, Nil, void)
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
  case class StringNode(v: String) extends Modifier {
    def applyTo(elem: dom.Element) = elem.appendChild(dom.document.createTextNode(v))
  }

  def raw(s: String) = new RawNode(s)
  /**
   * A [[Node]] which contains a String which will not be escaped.
   */
  object RawNode extends Companion[RawNode]
  case class RawNode(v: String) extends Modifier {
    def applyTo(elem: dom.Element): Unit = elem.insertAdjacentHTML("beforeend", v)
  }

  class GenericAttr[T] extends AttrValue[T]{
    def apply(t: dom.Element, a: Attr, v: T): Unit = {
      t.setAttribute(a.name, v.toString)
    }
  }
  implicit def stringAttr = new GenericAttr[String]
  implicit def booleanAttr= new GenericAttr[Boolean]
  implicit def numericAttr[T: Numeric] = new GenericAttr[T]


  class GenericStyle[T] extends StyleValue[T]{
    def apply(t: dom.Element, s: Style, v: T): Unit = {
      dom.console.log("GenericStyle")
      dom.console.log(t.outerHTML + " " + s + " " + v)
      t.asInstanceOf[dom.HTMLElement]
       .style
       .setProperty(s.cssName, t.toString)
      dom.console.log(t.outerHTML)
    }
  }
  implicit def stringStyle = new GenericStyle[String]
  implicit def booleanStyle = new GenericStyle[Boolean]
  implicit def numericStyle[T: Numeric] = new GenericStyle[T]

  case class TypedTag[+T <: Platform.Base](tag: String = "",
                                           modifiers: List[Modifier],
                                           void: Boolean = false)
                                           extends generic.TypedTag[T, dom.Element]{
    protected[this] type Self = TypedTag[T]

    /**
     * Serialize this [[HtmlTag]] and all its children out to the given dom.Element.
     */
    def applyTo(elem: dom.Element): Unit = {
      elem.appendChild(toDom)
    }
    /**
     * Converts an ScalaTag fragment into an html string
     */
    def toDom: T = {
      val elem = dom.document.createElement(tag)
      modifiers.map(_.applyTo(elem))
      elem.asInstanceOf[T]
    }
    /**
     * Trivial override, not strictly necessary, but it makes IntelliJ happy...
     */
    def apply(xs: Modifier*): TypedTag[T] = {
      this.copy(tag=tag, void = void, modifiers = modifiers ::: xs.toList)
    }
    override def toString = toDom.outerHTML
  }
  type HtmlTag = TypedTag[dom.HTMLElement]
  val HtmlTag = TypedTag
  type SvgTag = TypedTag[dom.SVGElement]
  val SvgTag = TypedTag
  type Tag = TypedTag[Platform.Base]
  val Tag = TypedTag
}

trait LowPriorityImplicits{
  implicit object bindJsAny extends generic.AttrValue[dom.Element, js.Any]{
    def apply(t: dom.Element, a: generic.Attr, v: js.Any): Unit = {
      t.asInstanceOf[js.Dynamic].updateDynamic(a.name)(v)
    }
  }
  implicit def bindJsAnyLike[T <% js.Any] = new generic.AttrValue[dom.Element, T]{
    def apply(t: dom.Element, a: generic.Attr, v: T): Unit = {
      t.asInstanceOf[js.Dynamic].updateDynamic(a.name)(v)
    }
  }
}