package scalatags

import java.util.Objects

import scala.language.implicitConversions
import scalatags.generic.{Attrs, SvgAttrs}


import scalatags.stylesheet.{StyleSheetFrag, StyleTree}


/**
 * A Scalatags module that can be configured to construct arbitrary virtual DOM
 * fragments of uniform type
 *
 * Simply instantiate it with `stringToFrag` and `rawToFrag` factory to construct
 * `Frag`, and a `makeBuilder` factory to construct `Tag`s, and you can then use
 * it to instantiate any sort of virtual DOM of uniform type: scala.xml trees,
 * Preact/React VDOM nodes in the browser, etc.
 */
trait VirtualDom[FragOutput0, TagOutput0 <: FragOutput0]
extends generic.Bundle[FragOutput0, TagOutput0] {
  type Tag
  trait TagBuilder{
    def appendChild(child: FragOutput): Unit
    def appendClass(cls: String): Unit
    def appendStyle(cssName: String, value: String): Unit
    def setAttr(name: String, value: String): Unit
    def render(): TagOutput
  }
  trait Frag extends super.Frag {
    def render: FragOutput
    def applyTo(b: TagBuilder) = b.appendChild(this.render)
  }
  def stringToFrag(s: String): FragOutput
  def rawToFrag(s: String): FragOutput
  def makeBuilder(tag: String): TagBuilder
  object attrs extends Api with Attrs[Attr, AttrValue, AttrPair]
  object tags extends Api with vdom.Tags[TypedTag[TagOutput]] with Tags
  object tags2 extends Api with vdom.Tags2[TypedTag[TagOutput]] with Tags2
  object styles extends Api with Styles
  object styles2 extends Api with Styles2
  object svgTags extends Api with vdom.SvgTags[TypedTag[TagOutput]] with SvgTags
  object svgAttrs extends Api with SvgAttrs[Attr]

  object all extends Api with AbstractAll with vdom.Tags[TypedTag[TagOutput]]

  object short extends Api with vdom.Tags[TypedTag[TagOutput]] with AbstractShort{

    object * extends Api with Attrs[Attr, AttrValue, AttrPair] with Styles
  }

  implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier{
    def applyTo(t: TagBuilder) = {
      t.appendClass(s.name)

    }
  }
  implicit class StyleFrag(s: StylePair[_]) extends StyleSheetFrag{
    def applyTo(c: StyleTree) = {
      ???
//        val b = makeBuilder
//        s.applyTo(b)
//        val Array(style, value) = b.attrs.get("style").map(_.split(":", 2))
//          .getOrElse(throw new IllegalArgumentException(s"Cannot apply style $s. Does it contain a syntax error?"))
//        c.copy(styles = c.styles.updated(style, value))
    }
  }


  protected def genericAttr[T]: AttrValue[T] = new GenericAttr[T]
  protected def genericStyle[T]: StyleValue[T] = new GenericStyle[T]
  protected def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] = new GenericPixelStyle[T](ev)
  protected def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T] = new GenericPixelStylePx[T](ev)

  implicit def stringFrag(v: String): Frag = new StringFrag(v)

  trait Api extends super.Api with vdom.TagFactory[TypedTag[TagOutput]]{ self =>
    def frag(frags: VirtualDom.super.Frag*): VirtualDom.this.Frag = SeqFrag(frags)
    def raw(s: String): Frag = new RawFrag(s)
    def tag(s: String, void: Boolean = false): TypedTag[TagOutput] = TypedTag(s, Nil, void, implicitly)
    protected[this] implicit def stringAttrX: AttrValue[String] = new GenericAttr[String]
    protected[this] implicit def stringStyleX: StyleValue[String] = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX: PixelStyleValue[String] = new GenericPixelStyle[String](stringStyleX)
    type Tag = TypedTag[TagOutput]
  }
  implicit def UnitFrag(u: Unit): Frag = new StringFrag("")

  implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: TagBuilder): Unit = xs.foreach(ev(_).asInstanceOf[Frag].applyTo(t))
    def render: FragOutput = {
      throw new Exception("Rendering of bare arrays of nodes is not supported in virtual dom backend")
    }
  }
  implicit class GeneratorFrag[A](xs: geny.Generator[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: TagBuilder): Unit = xs.foreach(ev(_).asInstanceOf[Frag].applyTo(t))
    def render: FragOutput = {
      throw new Exception("Rendering of bare arrays of nodes is not supported in virtual dom backend")
    }
  }

  private[this] class StringFrag(v: String) extends Frag{
    Objects.requireNonNull(v)
    def render: FragOutput = stringToFrag(v)
  }

  private[this] class RawFrag(v: String) extends Frag{
    Objects.requireNonNull(v)
    def render = rawToFrag(v)
  }

  private[this] class GenericAttr[T] extends AttrValue[T]{
    def apply(t: TagBuilder, a: Attr, v: T): Unit = t.setAttr(a.name, v.toString)
  }

  private[this] class GenericStyle[T] extends StyleValue[T]{
    def apply(t: TagBuilder, s: Style, v: T): Unit = t.appendStyle(s.cssName, v.toString)
  }
  private[this] class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v, ev)
  }
  private[this] class GenericPixelStylePx[T](ev: StyleValue[String]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v + "px", ev)
  }
  case class TypedTag[+O <: TagOutput](tag: String = "",
                                              modifiers: List[Seq[Modifier]],
                                              void: Boolean = false,
                                              namespace: scalatags.generic.Namespace)
                                              extends super.TypedTag[O] with Frag{

    protected[this] type Self = TypedTag[O]

    def render: O = {
      val builder = makeBuilder(tag)
      build(builder)
      builder.render().asInstanceOf[O]
    }

    def apply(xs: Modifier*): TypedTag[O] = {
      this.copy(tag = tag, void = void, modifiers = xs :: modifiers)
    }
  }

}

