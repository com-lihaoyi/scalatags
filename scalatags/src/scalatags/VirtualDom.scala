package scalatags

import java.util.Objects

import scala.language.implicitConversions



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
trait VirtualDom[FragT0, Output0 <: FragT0] extends generic.Bundle[FragT0, Output0] {

  trait Builder{
    def appendChild(child: FragT): Unit
    def appendClass(cls: String): Unit
    def appendStyle(cssName: String, value: String): Unit
    def setAttr(name: String, value: String): Unit
    def render(): Output
  }
  trait Frag extends super.Frag {
    def render: FragT
    def applyTo(b: Builder) = b.appendChild(this.render)
  }
  def stringToFrag(s: String): FragT
  def rawToFrag(s: String): FragT
  def makeBuilder(tag: String): Builder
  object attrs extends Cap with Attrs
  object tags extends Cap with vdom.Tags[TypedTag[Output]] with Tags
  object tags2 extends Cap with vdom.Tags2[TypedTag[Output]] with Tags2
  object styles extends Cap with Styles
  object styles2 extends Cap with Styles2
  object svgTags extends Cap with vdom.SvgTags[TypedTag[Output]] with SvgTags
  object svgAttrs extends Cap with SvgAttrs

  object all
    extends Cap
    with AbstractAll
    with vdom.Tags[TypedTag[Output]]

  object short
    extends Cap
    with vdom.Tags[TypedTag[Output]]
    with AbstractShort{

    object * extends Cap with Attrs with Styles
  }

  implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier{
    def applyTo(t: Builder) = {
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


  def genericAttr[T] = new GenericAttr[T]
  def genericStyle[T] = new GenericStyle[T]
  def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] = new GenericPixelStyle[T](ev)
  def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T] = new GenericPixelStylePx[T](ev)

  implicit def stringFrag(v: String): StringFrag = new StringFrag(v)

  def raw(s: String) = new RawFrag(s)

  val Tag = TypedTag


  trait Cap extends Util with vdom.TagFactory[TypedTag[Output]]{ self =>
    type ConcreteHtmlTag[T <: Output] = TypedTag[T]
    def frag(frags: VirtualDom.super.Frag*): Frag  = SeqFrag(frags)
    def modifier(mods: Modifier*): Modifier = SeqNode(mods)
    def css(s: String): Style = Style(camelCase(s), s)
    def tag(s: String, void: Boolean = false): TypedTag[Output] = TypedTag(s, Nil, void, implicitly)
    def makeAbstractTypedTag[T <: Output](tag: String, void: Boolean, namespaceConfig: scalatags.generic.Namespace): TypedTag[T] = {
      TypedTag(tag, Nil, void, namespaceConfig)
    }
    protected[this] implicit def stringAttrX = new GenericAttr[String]
    protected[this] implicit def stringStyleX = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX = new GenericPixelStyle[String](stringStyleX)

  }
  implicit def UnitFrag(u: Unit): VirtualDom.this.StringFrag = new StringFrag("")

  implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: Builder): Unit = xs.foreach(ev(_).asInstanceOf[Frag].applyTo(t))
    def render: FragT = {
      throw new Exception("Rendering of bare arrays of nodes is not supported in virtual dom backend")
    }
  }
  implicit class GeneratorFrag[A](xs: geny.Generator[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    override def applyTo(t: Builder): Unit = xs.foreach(ev(_).asInstanceOf[Frag].applyTo(t))
    def render: FragT = {
      throw new Exception("Rendering of bare arrays of nodes is not supported in virtual dom backend")
    }
  }

  class StringFrag(v: String) extends Frag{
    Objects.requireNonNull(v)
    def render: FragT = stringToFrag(v)
  }

  class RawFrag(v: String) extends Frag{
    Objects.requireNonNull(v)
    def render = rawToFrag(v)
  }

  class GenericAttr[T] extends AttrValue[T]{
    def apply(t: Builder, a: Attr, v: T): Unit = t.setAttr(a.name, v.toString)
  }

  class GenericStyle[T] extends StyleValue[T]{
    def apply(t: Builder, s: Style, v: T): Unit = t.appendStyle(s.cssName, v.toString)
  }
  class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v, ev)
  }
  class GenericPixelStylePx[T](ev: StyleValue[String]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v + "px", ev)
  }
  case class TypedTag[+O <: Output](tag: String = "",
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

