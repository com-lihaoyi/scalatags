package scalatags
import java.util.Objects

import acyclic.file
import org.scalajs
import org.scalajs.dom

import scala.language.implicitConversions
import scala.scalajs.js
import org.scalajs.dom.{Element, html, raw, svg}

import scala.annotation.unchecked.uncheckedVariance
import scalatags.generic.{Aliases, Namespace, StylePair}
import scalatags.stylesheet.{StyleSheetFrag, StyleTree}


/**
 * A Scalatags module that generates `dom.Element`s when the tags are rendered.
 * This provides some additional flexibility over the [[Text]] backend, as you
 * can bind structured objects to the attributes of your `dom.Element` without
 * serializing them first into strings.
 */
object JsDom
  extends generic.Bundle[dom.Element, dom.Element, dom.Node]
  with Aliases[dom.Element, dom.Element, dom.Node]{

  object attrs extends JsDom.Cap with Attrs
  object tags extends JsDom.Cap with jsdom.Tags
  object tags2 extends JsDom.Cap with jsdom.Tags2
  object styles extends JsDom.Cap with Styles
  object styles2 extends JsDom.Cap with Styles2
  object svgTags extends JsDom.Cap with jsdom.SvgTags
  object svgAttrs extends JsDom.Cap with SvgAttrs

  object implicits extends Aggregate with DataConverters

  object all
    extends Cap
    with Attrs
    with Styles
    with jsdom.Tags
    with DataConverters
    with Aggregate
    with LowPriorityImplicits

  object short
    extends Cap
    with jsdom.Tags
    with DataConverters
    with Aggregate
    with AbstractShort
    with LowPriorityImplicits{

    object * extends Cap with Attrs with Styles
  }


  trait Aggregate extends generic.Aggregate[dom.Element, dom.Element, dom.Node]{
    implicit class ApplyTags(e: dom.Element){
      def applyTags(mods: Modifier*) = mods.foreach(_.applyTo(e))
    }
    implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier{
      def applyTo(t: dom.Element) = t.classList.add(s.name)
    }
    implicit class StyleFrag(s: generic.StylePair[dom.Element, _]) extends StyleSheetFrag{
      def applyTo(c: StyleTree) = {
        val b = dom.document.createElement("div")
        s.applyTo(b)
        val Array(style, value) = b.getAttribute("style").split(":", 2)
        c.copy(styles = c.styles.updated(style, value))
      }
    }


    def genericAttr[T] = new JsDom.GenericAttr[T]
    def genericStyle[T] = new JsDom.GenericStyle[T]
    def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] = new JsDom.GenericPixelStyle[T](ev)
    def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T] = new JsDom.GenericPixelStylePx[T](ev)

    implicit def stringFrag(v: String): StringFrag = new JsDom.StringFrag(v)


    val RawFrag = JsDom.RawFrag
    val StringFrag = JsDom.StringFrag
    type StringFrag = JsDom.StringFrag
    type RawFrag = JsDom.RawFrag
    def raw(s: String) = RawFrag(s)

    type HtmlTag = JsDom.TypedTag[html.Element]
    val HtmlTag = JsDom.TypedTag
    type SvgTag = JsDom.TypedTag[svg.Element]
    val SvgTag = JsDom.TypedTag
    type Tag = JsDom.TypedTag[dom.Element]
    val Tag = JsDom.TypedTag


  }

  trait Cap extends Util with jsdom.TagFactory{ self =>
    type ConcreteHtmlTag[T <: dom.Element] = TypedTag[T]

    protected[this] implicit def stringAttrX = new GenericAttr[String]
    protected[this] implicit def stringStyleX = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX = new GenericPixelStyle[String](stringStyleX)
    implicit def UnitFrag(u: Unit): JsDom.StringFrag = new JsDom.StringFrag("")
    def makeAbstractTypedTag[T <: dom.Element](tag: String, void: Boolean, namespaceConfig: Namespace): TypedTag[T] = {
      TypedTag(tag, Nil, void, namespaceConfig)
    }

    implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag) extends Frag{
      Objects.requireNonNull(xs)
      def applyTo(t: dom.Element): Unit = xs.foreach(_.applyTo(t))
      def render: dom.Node = {
        val frag = org.scalajs.dom.document.createDocumentFragment()
        xs.map(_.render).foreach(frag.appendChild)
        frag
      }
    }
  }

  object StringFrag extends Companion[StringFrag]
  case class StringFrag(v: String) extends jsdom.Frag{
    Objects.requireNonNull(v)
    def render: dom.Text = dom.document.createTextNode(v)
  }

  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends Modifier{
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
            t.setAttributeNode(newAttr.asInstanceOf[raw.Attr])

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
                                              extends generic.TypedTag[dom.Element, Output, dom.Node]
                                              with jsdom.Frag{
    // unchecked because Scala 2.10.4 seems to not like this, even though
    // 2.11.1 works just fine. I trust that 2.11.1 is more correct than 2.10.4
    // and so just force this.
    protected[this] type Self = TypedTag[Output @uncheckedVariance]

    def render: Output = {
      val elem = dom.document.createElementNS(namespace.uri, tag)
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
  implicit def bindJsAnyLike[T](implicit ev: T => js.Any) = new generic.AttrValue[dom.Element, T]{
    def apply(t: dom.Element, a: generic.Attr, v: T): Unit = {
      t.asInstanceOf[js.Dynamic].updateDynamic(a.name)(v)
    }
  }
  implicit class bindNode(e: dom.Node) extends generic.Frag[dom.Element, dom.Node] {
    def applyTo(t: Element) = t.appendChild(e)
    def render = e
  }
}
