package scalatags

import org.scalajs.dom
import scala.scalajs.js
import scalatags.generic.Node
import org.scalajs.dom.Element
import scala.annotation.unchecked.uncheckedVariance


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
    protected[this] implicit def stringStyle = new GenericStyle[String]

    def makeAbstractTypedTag[T <: Platform.Base](tag: String, void: Boolean): TypedTag[T] = {
      TypedTag(tag, Nil, void)
    }
  }

  /**
   * Lets you put numbers into a scalatags tree, as a no-op.
   */
  implicit def NumericNode[T: Numeric](u: T) = new StringNode(u.toString)

  /**
   * Allows you to modify a HtmlTag by adding a String to its list of children
   */
  implicit def stringNode(v: String) = new StringNode(v)
  /**
   * A [[Node]] which contains a String.
   */
  object StringNode extends Companion[StringNode]
  case class StringNode(v: String) extends Node {
    def applyTo(elem: dom.Element) = elem.appendChild(dom.document.createTextNode(v))
  }

  def raw(s: String) = new RawNode(s)
  /**
   * A [[Node]] which contains a String which will not be escaped.
   */
  object RawNode extends Companion[RawNode]
  case class RawNode(v: String) extends Node {
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
      t.asInstanceOf[dom.HTMLElement]
       .style
       .setProperty(s.cssName, v.toString)
    }
  }
  implicit def stringStyle = new GenericStyle[String]
  implicit def booleanStyle = new GenericStyle[Boolean]
  implicit def numericStyle[T: Numeric] = new GenericStyle[T]

  case class TypedTag[+Output <: Platform.Base](tag: String = "",
                                           modifiers: List[Seq[Node]],
                                           void: Boolean = false)
                                           extends generic.TypedTag[Output, dom.Element]{
    // unchecked because Scala 2.10.4 seems to not like this, even though
    // 2.11.1 works just fine. I trust that 2.11.1 is more correct than 2.10.4
    // and so just force this.
    protected[this] type Self = TypedTag[Output @uncheckedVariance]

    /**
     * Serialize this [[HtmlTag]] and all its children out to the given dom.Element.
     */
    def applyTo(elem: dom.Element): Unit = {
      elem.appendChild(render)
    }
    /**
     * Converts an ScalaTag fragment into an html string
     */
    def render: Output = {
      val elem = dom.document.createElement(tag)
      build(elem)
      elem.asInstanceOf[Output]
    }
    /**
     * Trivial override, not strictly necessary, but it makes IntelliJ happy...
     */
    def apply(xs: Node*): TypedTag[Output] = {
      this.copy(tag = tag, void = void, modifiers = xs :: modifiers)
    }
    override def toString = render.outerHTML
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
  implicit def bindElement(e: dom.Element) = new Node[dom.Element] {
    override def applyTo(t: Element) = t.appendChild(e)
  }
}