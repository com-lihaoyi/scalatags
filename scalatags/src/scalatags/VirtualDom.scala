//package scalatags
//
//import java.util.Objects
//
//
//import scala.language.implicitConversions
//
//import scala.annotation.unchecked.uncheckedVariance
//import scalatags.generic.{Namespace, StylePair}
//import scalatags.stylesheet.{StyleSheetFrag, StyleTree}
//
//
///**
// * A Scalatags module that can be configured to construct arbitrary virtual DOM
// * fragments of uniform type
// *
// * Simply instantiate it with `stringToFrag` and `rawToFrag` factory to construct
// * `Frag`, and a `makeBuilder` factory to construct `Tag`s, and you can then use
// * it to instantiate any sort of virtual DOM of uniform type: scala.xml trees,
// * Preact/React VDOM nodes in the browser, etc.
// */
//trait VirtualDom[FragT, Output <: FragT] extends generic.Bundle[vdom.Builder[Output, FragT], Output, FragT] {
//
//  trait Frag extends super.Frag {
//    def render: FragT
//    def applyTo(b: vdom.Builder[Output, FragT]) = b.appendChild(this.render)
//  }
//  def stringToFrag(s: String): FragT
//  def rawToFrag(s: String): FragT
//  def makeBuilder(tag: String): vdom.Builder[Output, FragT]
//  object attrs extends Cap with Attrs
//  object tags extends Cap with vdom.Tags[Output, FragT]
//  object tags2 extends Cap with vdom.Tags2[Output, FragT]
//  object styles extends Cap with Styles
//  object styles2 extends Cap with Styles2
//  object svgTags extends Cap with vdom.SvgTags[Output, FragT]
//  object svgAttrs extends Cap with SvgAttrs
//
//  object all
//    extends Cap
//    with Attrs
//    with Styles
//    with vdom.Tags[Output, FragT]
//
//  object short
//    extends Cap
//    with vdom.Tags[Output, FragT]
//    with AbstractShort{
//
//    object * extends Cap with Attrs with Styles
//  }
//
//  implicit class ApplyTags(e: vdom.Builder[Output, FragT]){
//    def applyTags(mods: Modifier*) = mods.foreach(_.applyTo(e))
//  }
//  implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier{
//    def applyTo(t: vdom.Builder[Output, FragT]) = {
//      t.appendClass(s.name)
//
//    }
//  }
//  implicit class StyleFrag(s: generic.StylePair[vdom.Builder[Output, FragT], _]) extends StyleSheetFrag{
//    def applyTo(c: StyleTree) = {
//      ???
////        val b = makeBuilder
////        s.applyTo(b)
////        val Array(style, value) = b.attrs.get("style").map(_.split(":", 2))
////          .getOrElse(throw new IllegalArgumentException(s"Cannot apply style $s. Does it contain a syntax error?"))
////        c.copy(styles = c.styles.updated(style, value))
//    }
//  }
//
//
//  def genericAttr[T] = new GenericAttr[T]
//  def genericStyle[T] = new GenericStyle[T]
//  def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] = new GenericPixelStyle[T](ev)
//  def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T] = new GenericPixelStylePx[T](ev)
//
//  implicit def stringFrag(v: String): StringFrag = new VirtualDom.this.StringFrag(v)
//
//  def raw(s: String) = RawFrag(s)
//
//  val Tag = TypedTag
//
//
//  trait Cap extends Util with vdom.TagFactory[Output, FragT]{ self =>
//    type ConcreteHtmlTag[T <: Output] = TypedTag[T]
//
//    protected[this] implicit def stringAttrX = new GenericAttr[String]
//    protected[this] implicit def stringStyleX = new GenericStyle[String]
//    protected[this] implicit def stringPixelStyleX = new GenericPixelStyle[String](stringStyleX)
//    def makeAbstractTypedTag[T](tag: String, void: Boolean, namespaceConfig: Namespace) = {
//      TypedTag(tag, Nil, void, namespaceConfig)
//    }
//
//  }
//  implicit def UnitFrag(u: Unit): VirtualDom.this.StringFrag = new VirtualDom.this.StringFrag("")
//
//  implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag) extends Frag{
//    Objects.requireNonNull(xs)
////    def applyTo(t: vdom.Builder[Output, FragT]): Unit = xs.foreach(_.applyTo(t))
//    def render: FragT = {
//      throw new Exception("Rendering of bare arrays of nodes is not supported in virtual dom backend")
//    }
//  }
//  implicit class GeneratorFrag[A](xs: geny.Generator[A])(implicit ev: A => Frag) extends Frag{
//    Objects.requireNonNull(xs)
////    def applyTo(t: vdom.Builder[Output, FragT]): Unit = xs.foreach(_.applyTo(t))
//    def render: FragT = {
//      throw new Exception("Rendering of bare arrays of nodes is not supported in virtual dom backend")
//    }
//  }
//
//  object StringFrag extends Companion[StringFrag]
//  case class StringFrag(v: String) extends Frag{
//    Objects.requireNonNull(v)
//    def render: FragT = stringToFrag(v)
//  }
//
//  object RawFrag extends Companion[RawFrag]
//  case class RawFrag(v: String) extends Frag{
//    Objects.requireNonNull(v)
//    def render = rawToFrag(v)
//  }
//
//  class GenericAttr[T] extends AttrValue[T]{
//    def apply(t: vdom.Builder[Output, FragT], a: Attr, v: T): Unit = {
//      t.setAttr(a.name, v.toString)
//    }
//  }
//
//  class GenericStyle[T] extends StyleValue[T]{
//    def apply(t: vdom.Builder[Output, FragT], s: Style, v: T): Unit = {
//      t.appendStyle(s.cssName, v.toString)
//    }
//  }
//  class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T]{
//    def apply(s: Style, v: T) = StylePair(s, v, ev)
//  }
//  class GenericPixelStylePx[T](ev: StyleValue[String]) extends PixelStyleValue[T]{
//    def apply(s: Style, v: T) = StylePair(s, v + "px", ev)
//  }
//  case class TypedTag[+O <: Output](tag: String = "",
//                                              modifiers: List[Seq[Modifier]],
//                                              void: Boolean = false,
//                                              namespace: Namespace)
//                                              extends super.TypedTag[O] with Frag{
//    // unchecked because Scala 2.10.4 seems to not like this, even though
//    // 2.11.1 works just fine. I trust that 2.11.1 is more correct than 2.10.4
//    // and so just force this.
//    protected[this] type Self = TypedTag[O @uncheckedVariance]
//
//    def render: O = {
//      val builder = makeBuilder(tag)
//      build(builder)
//      builder.render().asInstanceOf[O]
//    }
//    /**
//     * Trivial override, not strictly necessary, but it makes IntelliJ happy...
//     */
//    def apply(xs: Modifier*): TypedTag[O] = {
//      this.copy(tag = tag, void = void, modifiers = xs :: modifiers)
//    }
//  }
//
//}
//
