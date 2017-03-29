package scalatags
import java.util.Objects

import acyclic.file

import scalatags.generic._
import scala.annotation.unchecked.uncheckedVariance
import scalatags.stylesheet.{StyleSheetFrag, StyleTree}
import scalatags.text.Builder

/**
 * A Scalatags module that works with a text back-end, i.e. it creates HTML
 * `String`s.
 */


object Text
  extends generic.Bundle[text.Builder, String, String]
  with Aliases[text.Builder, String, String]{
  object attrs extends Text.Cap with Attrs
  object tags extends Text.Cap with text.Tags
  object tags2 extends Text.Cap with text.Tags2
  object styles extends Text.Cap with Styles
  object styles2 extends Text.Cap with Styles2

  object svgTags extends Text.Cap with text.SvgTags
  object svgAttrs extends Text.Cap with SvgAttrs

  object implicits extends Aggregate with DataConverters

  object all
    extends Cap
    with Attrs
    with Styles
    with text.Tags
    with DataConverters
    with Aggregate

  object short
    extends Cap
    with text.Tags
    with DataConverters
    with Aggregate
    with AbstractShort{

    object * extends Cap with Attrs with Styles
  }

  trait Cap extends Util with text.TagFactory{ self =>
    type ConcreteHtmlTag[T <: String] = TypedTag[T]
    type BaseTagType = TypedTag[String]
    protected[this] implicit def stringAttrX = new GenericAttr[String]
    protected[this] implicit def stringStyleX = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX = new GenericPixelStyle[String](stringStyleX)
    implicit def UnitFrag(u: Unit) = new Text.StringFrag("")
    def makeAbstractTypedTag[T](tag: String, void: Boolean, namespaceConfig: Namespace) = {
      TypedTag(tag, Nil, void)
    }
    implicit class SeqFrag[A <% Frag](xs: Seq[A]) extends Frag{
      Objects.requireNonNull(xs)
      def applyTo(t: text.Builder) = xs.foreach(_.applyTo(t))
      def render = xs.map(_.render).mkString
    }
  }

  trait Aggregate extends generic.Aggregate[text.Builder, String, String]{
    implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier with text.Builder.ValueSource{
      def applyTo(t: text.Builder) = t.appendAttr("class",this)

      override def appendAttrValue(sb: StringBuilder): Unit = {
        sb.append(' ')
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

    val RawFrag = Text.RawFrag
    val StringFrag = Text.StringFrag
    type StringFrag = Text.StringFrag
    type RawFrag = Text.RawFrag
    def raw(s: String) = RawFrag(s)


    val Tag = Text.TypedTag
  }



  case class StringFrag(v: String) extends text.Frag{
    Objects.requireNonNull(v)
    def render = {
      val strb = new StringBuilder()
      writeTo(strb)
      strb.toString()
    }
    def writeTo(strb: StringBuilder) = Escaping.escape(v, strb)
  }
  object StringFrag extends Companion[StringFrag]
  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends text.Frag {
    Objects.requireNonNull(v)
    def render = v
    def writeTo(strb: StringBuilder) = strb ++= v
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



  case class TypedTag[+Output <: String](tag: String = "",
                                         modifiers: List[Seq[Modifier]],
                                         void: Boolean = false)
                                         extends generic.TypedTag[text.Builder, Output, String]
                                         with text.Frag{
    // unchecked because Scala 2.10.4 seems to not like this, even though
    // 2.11.1 works just fine. I trust that 2.11.1 is more correct than 2.10.4
    // and so just force this.
    protected[this] type Self = TypedTag[Output @uncheckedVariance]

    /**
     * Serialize this [[TypedTag]] and all its children out to the given StringBuilder.
     *
     * Although the external interface is pretty simple, the internals are a huge mess,
     * because I've inlined a whole lot of things to improve the performance of this code
     * ~4x from what it originally was, which is a pretty nice speedup
     */
    def writeTo(strb: StringBuilder): Unit = {
      val builder = new text.Builder()
      build(builder)

      // tag
      strb += '<' ++= tag

      // attributes
      var i = 0
      while (i < builder.attrIndex){
        val pair = builder.attrs(i)
        strb += ' ' ++= pair._1 ++= "=\""
        builder.appendAttrStrings(pair._2,strb)
        strb += '\"'
        i += 1
      }

      if (builder.childIndex == 0 && void) {
        // No children - close tag
        strb ++= " />"
      } else {
        strb += '>'
        // Childrens
        var i = 0
        while(i < builder.childIndex){
          builder.children(i).writeTo(strb)
          i += 1
        }

        // Closing tag
        strb ++= "</" ++= tag += '>'
      }
    }

    def apply(xs: Modifier*): TypedTag[Output] = {
      this.copy(tag=tag, void = void, modifiers = xs :: modifiers)
    }

    /**
     * Converts an ScalaTag fragment into an html string
     */
    override def toString = {
      val strb = new StringBuilder
      writeTo(strb)
      strb.toString()
    }
    def render: Output = this.toString.asInstanceOf[Output]
  }


}
