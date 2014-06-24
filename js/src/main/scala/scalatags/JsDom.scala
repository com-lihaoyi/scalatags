package scalatags

import org.scalajs.dom
import scala.scalajs.js

import org.scalajs.dom.Element
import scala.annotation.unchecked.uncheckedVariance
import scalatags.generic.Modifier


/**
 * A Scalatags module that generates `dom.Element`s when the tags are rendered.
 * This provides some additional flexibility over the [[Text]] backend, as you
 * can bind structured objects to the attributes of your `dom.Element` without
 * serializing them first into strings.
 */
object JsDom extends generic.Bundle[dom.Element, dom.Element, dom.Node] with LowPriorityImplicits with jsdom.Aggregate{
  object all
    extends Cap
    with Attrs
    with Styles
    with jsdom.Tags
    with DataConverters
    with jsdom.Aggregate
    with LowPriorityImplicits{
    val RawFrag = JsDom.RawFrag
    val StringFrag = JsDom.StringFrag
    type StringFrag = JsDom.StringFrag
    type RawFrag = JsDom.RawFrag
  }
  object short
    extends Cap
    with Util
    with DataConverters
    with AbstractShort
    with jsdom.Aggregate
    with LowPriorityImplicits{
    val RawFrag = JsDom.RawFrag
    val StringFrag = JsDom.StringFrag
    type StringFrag = JsDom.StringFrag
    type RawFrag = JsDom.RawFrag
    object * extends Cap with Attrs with Styles
  }

  trait Cap extends Util{ self =>
    type ConcreteHtmlTag[T <: dom.Element] = TypedTag[T]

    protected[this] implicit def stringAttrX = new GenericAttr[String]
    protected[this] implicit def stringStyleX = new GenericStyle[String]

    def makeAbstractTypedTag[T <: dom.Element](tag: String, void: Boolean): TypedTag[T] = {
      TypedTag(tag, Nil, void)
    }
  }

  object StringFrag extends Companion[StringFrag]
  case class StringFrag(v: String) extends Frag{
    def render: dom.Text = dom.document.createTextNode(v)
  }

  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends Modifier{
    def applyTo(elem: dom.Element): Unit = {
      elem.insertAdjacentHTML("beforeend", v)
    }
  }

  class GenericAttr[T] extends AttrValue[T]{
    def apply(t: dom.Element, a: Attr, v: T): Unit = {
      t.setAttribute(a.name, v.toString)
    }
  }

  class GenericStyle[T] extends StyleValue[T]{
    def apply(t: dom.Element, s: Style, v: T): Unit = {
      t.asInstanceOf[dom.HTMLElement]
       .style
       .setProperty(s.cssName, v.toString)
    }
  }

  case class TypedTag[+Output <: dom.Element](tag: String = "",
                                              modifiers: List[Seq[Modifier]],
                                              void: Boolean = false)
                                              extends generic.TypedTag[dom.Element, Output, dom.Node]
                                              with Frag{
    // unchecked because Scala 2.10.4 seems to not like this, even though
    // 2.11.1 works just fine. I trust that 2.11.1 is more correct than 2.10.4
    // and so just force this.
    protected[this] type Self = TypedTag[Output @uncheckedVariance]

    def render: Output = {
      val elem = dom.document.createElement(tag)
      build(elem)
      elem.asInstanceOf[Output]
    }
    /**
     * Trivial override, not strictly necessary, but it makes IntelliJ happy...
     */
    def apply(xs: Modifier*): TypedTag[Output] = {
      this.copy(tag = tag, void = void, modifiers = xs :: modifiers)
    }
    override def toString = render.outerHTML
  }
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
  implicit class bindElement(e: dom.Element) extends generic.Modifier[dom.Element] {
    override def applyTo(t: Element) = t.appendChild(e)
  }
}