package scalatags
import java.util.Objects

import org.scalajs.dom

import scala.language.implicitConversions
import scala.scalajs.js
import org.scalajs.dom.{Element, html, raw, svg}


import scalatags.generic.{Namespace, Attrs, SvgAttrs}
import scalatags.stylesheet.{StyleSheetFrag, StyleTree}


/**
 * A Scalatags module that generates `dom.Element`s when the tags are rendered.
 * This provides some additional flexibility over the [[Text]] backend, as you
 * can bind structured objects to the attributes of your `dom.Element` without
 * serializing them first into strings.
 */
object JsDom extends generic.Bundle[dom.Node, dom.Element] with LowPriorityImplicits{
  type Builder = dom.Element
  object attrs extends Api with Attrs[Attr, AttrValue, AttrPair]
  object tags extends Api with jsdom.Tags[TypedTag] with Tags
  object tags2 extends Api with jsdom.Tags2[TypedTag] with Tags2
  object styles extends Api with Styles
  object styles2 extends Api with Styles2
  object svgTags extends Api with jsdom.SvgTags[TypedTag] with SvgTags
  object svgAttrs extends Api with SvgAttrs[Attr]


  object all
    extends Api
    with AbstractAll
    with jsdom.Tags[TypedTag]

  object short
    extends Api
    with jsdom.Tags[TypedTag]
    with AbstractShort{

    object * extends Api with Attrs[Attr, AttrValue, AttrPair] with Styles
  }

  implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier{
    def applyTo(t: dom.Element) = t.classList.add(s.name)
  }
  implicit class StyleFrag(s: StylePair[_]) extends StyleSheetFrag{
    def applyTo(c: StyleTree) = {
      val b = dom.document.createElement("div")
      s.applyTo(b)
      val Array(style, value) = Option(b.getAttribute("style")).map(_.split(":", 2))
        .getOrElse(throw new IllegalArgumentException(s"Cannot apply style $s. Does it contain a syntax error?"))
      c.copy(styles = c.styles.updated(style, value))
    }
  }


  def genericAttr[T] = new GenericAttr[T]
  def genericStyle[T] = new GenericStyle[T]
  def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] = new JsDom.GenericPixelStyle[T](ev)
  def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T] = new JsDom.GenericPixelStylePx[T](ev)

  implicit def stringFrag(v: String): StringFrag = new JsDom.StringFrag(v)

  def raw(s: String) = new RawFrag(s)

  implicit def UnitFrag(u: Unit): JsDom.StringFrag = new JsDom.StringFrag("")
  trait Api extends super.Api with jsdom.TagFactory[TypedTag]{ self =>
    type ConcreteHtmlTag[T <: dom.Element] = TypedTag[T]

    protected[this] implicit def stringAttrX = new GenericAttr[String]
    protected[this] implicit def stringStyleX = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX = new GenericPixelStyle[String](stringStyleX)

    type HtmlTag = JsDom.TypedTag[html.Element]
    val HtmlTag = JsDom.TypedTag
    type SvgTag = JsDom.TypedTag[svg.Element]
    val SvgTag = JsDom.TypedTag

    val Tag = JsDom.TypedTag
//    type Tag = JsDom.TypedTag[dom.Element]

    def frag(frags: JsDom.super.Frag*): Frag  = SeqFrag(frags)
    def tag(s: String, void: Boolean = false): TypedTag[dom.Element] = TypedTag(s, Nil, void, implicitly)
    def typedTag[T <: dom.Element](s: String, void: Boolean = false)
                                  (implicit ns: scalatags.generic.Namespace): TypedTag[T] = {

      TypedTag(s, Nil, void, ns)
    }
  }
  implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => JsDom.super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: dom.Element): Unit = xs.foreach(_.applyTo(t))
    def render: dom.Node = {
      val frag = org.scalajs.dom.document.createDocumentFragment()
      xs.map(ev(_).asInstanceOf[Frag].render).foreach(frag.appendChild)
      frag
    }
  }
  implicit class GeneratorFrag[A](xs: geny.Generator[A])(implicit ev: A => JsDom.super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: dom.Element): Unit = xs.foreach(_.applyTo(t))
    def render: dom.Node = {
      val frag = org.scalajs.dom.document.createDocumentFragment()
      xs.map(ev(_).asInstanceOf[Frag].render).foreach(frag.appendChild)
      frag
    }
  }
  implicit class ArrayFrag[A](xs: Array[A])(implicit ev: A => JsDom.super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: dom.Element): Unit = xs.foreach(_.applyTo(t))
    def render: dom.Node = {
      val frag = org.scalajs.dom.document.createDocumentFragment()
      xs.map(ev(_).asInstanceOf[Frag].render).foreach(frag.appendChild)
      frag
    }
  }
  implicit class OptionFrag[A](xs: Option[A])(implicit ev: A => JsDom.super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: dom.Element): Unit = xs.foreach(_.applyTo(t))
    def render: dom.Node = {
      val frag = org.scalajs.dom.document.createDocumentFragment()
      xs.map(ev(_).asInstanceOf[Frag].render).foreach(frag.appendChild)
      frag
    }
  }

  class StringFrag(v: String) extends Frag{
    Objects.requireNonNull(v)
    def render: dom.Text = dom.document.createTextNode(v)
  }

  class RawFrag(v: String) extends Modifier{
    Objects.requireNonNull(v)
    def applyTo(elem: dom.Element): Unit = {
      elem.insertAdjacentHTML("beforeend", v)
    }
  }

  class GenericAttr[T] extends AttrValue[T]{
    def apply(t: dom.Element, a: Attr, v: T): Unit = {
      a.namespace match {
        case None =>
          if (!a.raw) t.setAttribute(a.name, v.toString)
          else {

            // Ugly workaround for https://www.w3.org/Bugs/Public/show_bug.cgi?id=27228
            val tmpElm = dom.document.createElement("p")
            tmpElm.innerHTML = s"""<p ${a.name}="${v.toString}"><p>"""
            val newAttr = tmpElm.children(0).attributes(0).cloneNode(true)
            t.setAttributeNode(newAttr.asInstanceOf[org.scalajs.dom.raw.Attr])

          }
        case Some(namespace) =>
          t.setAttributeNS(namespace.uri, a.name, v.toString)
      }
    }
  }

  class GenericStyle[T] extends StyleValue[T]{
    def apply(t: dom.Element, s: Style, v: T): Unit = {
      t.asInstanceOf[html.Html]
       .style
       .setProperty(s.cssName, v.toString)
    }
  }
  class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v, ev)
  }
  class GenericPixelStylePx[T](ev: StyleValue[String]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v + "px", ev)
  }
  case class TypedTag[+Output <: dom.Element](tag: String = "",
                                              modifiers: List[Seq[Modifier]],
                                              void: Boolean = false,
                                              namespace: Namespace)
                                              extends super.TypedTag[Output]
                                              with Frag{

    protected[this] type Self = TypedTag[Output]

    def render: Output = {
      val elem = dom.document.createElementNS(namespace.uri, tag)
      build(elem)
      elem.asInstanceOf[Output]
    }

    def apply(xs: Modifier*): TypedTag[Output] = {
      this.copy(tag = tag, void = void, modifiers = xs :: modifiers)
    }
    override def toString = render.outerHTML
  }


  trait Frag extends super.Frag{
    def render: dom.Node
    def applyTo(b: dom.Element) = b.appendChild(this.render)
  }

}

trait LowPriorityImplicits{ this: JsDom.type =>
  implicit object bindJsAny extends AttrValue[js.Any]{
    def apply(t: dom.Element, a: Attr, v: js.Any): Unit = {
      t.asInstanceOf[js.Dynamic].updateDynamic(a.name)(v)
    }
  }
  implicit def bindJsAnyLike[T](implicit ev: T => js.Any) = new AttrValue[ T]{
    def apply(t: dom.Element, a: Attr, v: T): Unit = {
      t.asInstanceOf[js.Dynamic].updateDynamic(a.name)(v)
    }
  }
  implicit class bindNode(e: dom.Node) extends Frag {
    override def applyTo(t: Element) = t.appendChild(e)
    def render = e
  }
}
