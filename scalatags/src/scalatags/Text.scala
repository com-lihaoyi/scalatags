package scalatags
import java.io.Writer
import java.util.Objects

import scalatags.generic._

import scala.annotation.unchecked.uncheckedVariance
import scalatags.stylesheet.{StyleSheetFrag, StyleTree}
import scalatags.text.Builder

/**
 * A Scalatags module that works with a text back-end, i.e. it creates HTML
 * `String`s.
 */


object Text extends generic.Bundle[text.Builder, String, String] {
  trait Modifier extends super.Modifier
  object attrs extends Text.Cap with Attrs
  object tags extends Text.Cap with text.Tags[TypedTag[String]] with Tags
  object tags2 extends Text.Cap with text.Tags2[TypedTag[String]] with Tags2
  object styles extends Text.Cap with Styles
  object styles2 extends Text.Cap with Styles2

  object svgTags extends Text.Cap with text.SvgTags[TypedTag[String]] with SvgTags
  object svgAttrs extends Text.Cap with SvgAttrs


  object all
    extends Cap
    with Attrs
    with Styles
    with text.Tags[TypedTag[String]] with Tags

  object short
    extends Cap
    with text.Tags[TypedTag[String]] with Tags
    with AbstractShort{

    object * extends Cap with Attrs with Styles
  }
  implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)

    def writeTo(strb: Writer): Unit = ??? //xs.foreach(_.writeTo(strb))
    def render = xs.map(_.render).mkString
  }
  implicit class GeneratorFrag[A](xs: geny.Generator[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    def writeTo(strb: Writer): Unit = ???//xs.foreach(_.writeTo(strb))
    def render = xs.map(_.render).mkString
  }
  implicit def UnitFrag(u: Unit) = new Text.StringFrag("")
  trait Cap extends Util with text.TagFactory[TypedTag[String]]{ self =>
    type ConcreteHtmlTag[T <: String] = TypedTag[T]
    type BaseTagType = TypedTag[String]
    protected[this] implicit def stringAttrX = new GenericAttr[String]
    protected[this] implicit def stringStyleX = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX = new GenericPixelStyle[String](stringStyleX)

    def makeAbstractTypedTag[T](tag: String, void: Boolean, namespaceConfig: Namespace) = {
      TypedTag(tag, Nil, void)
    }


    case class doctype(s: String)(content: Frag) extends geny.Writable{
      def writeTo(strb: java.io.Writer): Unit = {
        strb.write(s"<!DOCTYPE $s>")
        content.writeTo(strb)
      }
      def writeBytesTo(out: java.io.OutputStream): Unit = {
        val w = new java.io.OutputStreamWriter(out, java.nio.charset.StandardCharsets.UTF_8)
        writeTo(w)
        w.flush()
      }
      def render = {
        val strb = new java.io.StringWriter()
        writeTo(strb)
        strb.toString()
      }
    }
  }

//  trait Aggregate extends generic.Aggregate[text.Builder, String, String]{
  implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier with text.Builder.ValueSource{
    def applyTo(t: text.Builder) = t.appendAttr("class",this)

    override def appendAttrValue(sb: java.io.Writer): Unit = {
      Escaping.escape(s.name, sb)
    }
  }
  implicit class StyleFrag(s: generic.StylePair[text.Builder, _]) extends StyleSheetFrag{
    def applyTo(c: StyleTree) = {
      val b = new Builder()
      s.applyTo(b)
      val Array(style, value) = b.attrsString(b.attrs(b.attrIndex("style"))._2).split(":", 2)
      c.copy(styles = c.styles.updated(style, value))
    }
  }

  def genericAttr[T] = new Text.GenericAttr[T]
  def genericStyle[T] = new Text.GenericStyle[T]
  def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] = new Text.GenericPixelStyle[T](ev)
  def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T] = new Text.GenericPixelStylePx[T](ev)

  implicit def stringFrag(v: String) = new Text.StringFrag(v)

  def raw(s: String) = RawFrag(s)


  val Tag = Text.TypedTag


  trait Frag extends super.Frag with Modifier{
    def writeTo(strb: java.io.Writer): Unit
    def writeBytesTo(out: java.io.OutputStream): Unit = {
      val w = new java.io.OutputStreamWriter(out, java.nio.charset.StandardCharsets.UTF_8)
      writeTo(w)
      w.flush()
    }
    def render: String
    def applyTo(b: Builder) = b.addChild(this)
  }


  case class StringFrag(v: String) extends Frag{
    Objects.requireNonNull(v)
    def render = {
      val strb = new java.io.StringWriter()
      writeTo(strb)
      strb.toString()
    }
    def writeTo(strb: java.io.Writer) = Escaping.escape(v, strb)
  }
  object StringFrag extends Companion[StringFrag]
  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends Frag {
    Objects.requireNonNull(v)
    def render = v
    def writeTo(strb: java.io.Writer) = strb.append(v)
  }

  class GenericAttr[T] extends AttrValue[T] {
    def apply(t: text.Builder, a: Attr, v: T): Unit = {
      t.setAttr(a.name, Builder.GenericAttrValueSource(v.toString))
    }
  }

  class GenericStyle[T] extends StyleValue[T] {
    def apply(t: text.Builder, s: Style, v: T): Unit = {
      t.appendAttr("style", Builder.StyleValueSource(s, v.toString))
    }
  }
  class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v, ev)
  }
  class GenericPixelStylePx[T](ev: StyleValue[String]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = {
      StylePair(s, v + "px", ev)
    }
  }



  case class TypedTag[+O <: String](tag: String = "",
                                         modifiers: List[Seq[Modifier]],
                                         void: Boolean = false)
                                         extends super.TypedTag[O] with Frag with geny.Writable{
    // unchecked because Scala 2.10.4 seems to not like this, even though
    // 2.11.1 works just fine. I trust that 2.11.1 is more correct than 2.10.4
    // and so just force this.
    protected[this] type Self = TypedTag[O @uncheckedVariance]

    /**
     * Serialize this [[TypedTag]] and all its children out to the given StringBuilder.
     *
     * Although the external interface is pretty simple, the internals are a huge mess,
     * because I've inlined a whole lot of things to improve the performance of this code
     * ~4x from what it originally was, which is a pretty nice speedup
     */
    def writeTo(strb: java.io.Writer): Unit = {
      val builder = new text.Builder()
      build(builder)

      // tag
      strb.append('<').append(tag)

      // attributes
      var i = 0
      while (i < builder.attrIndex){
        val pair = builder.attrs(i)
        strb.append(' ').append(pair._1).append("=\"")
        builder.appendAttrStrings(pair._2,strb)
        strb.append('\"')
        i += 1
      }

      if (builder.childIndex == 0 && void) {
        // No children - close tag
        strb.append(" />")
      } else {
        strb.append('>')
        // Childrens
        var i = 0
        while(i < builder.childIndex){
          builder.children(i).writeTo(strb)
          i += 1
        }

        // Closing tag
        strb.append("</").append(tag).append('>')
      }
    }

    def apply(xs: Text.super.Modifier*): TypedTag[O] = {
      this.copy(tag=tag, void = void, modifiers = ??? /*xs :: modifiers*/)
    }

    /**
     * Converts an ScalaTag fragment into an html string
     */
    override def toString = {
      val strb = new java.io.StringWriter
      writeTo(strb)
      strb.toString()
    }
    def render: O = this.toString.asInstanceOf[O]
  }


}
