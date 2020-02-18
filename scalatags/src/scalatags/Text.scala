package scalatags
import java.io.Writer
import java.util.Objects

import scalatags.generic._


import scalatags.stylesheet.{StyleSheetFrag, StyleTree}

import scala.reflect.ClassTag

/**
 * A Scalatags module that works with a text back-end, i.e. it creates HTML
 * `String`s.
 */
object Text extends generic.Bundle{
  type FragOutput = String
  type TagOutput = String
  object attrs extends Api with Attrs[Attr, AttrValue, AttrPair]
  object tags extends Api with text.Tags[TypedTag] with Tags
  object tags2 extends Api with text.Tags2[TypedTag] with Tags2
  object styles extends Api with Styles
  object styles2 extends Api with Styles2

  object svgTags extends Api with text.SvgTags[TypedTag] with SvgTags
  object svgAttrs extends Api with SvgAttrs[Attr]

  object all extends Api with AbstractAll with text.Tags[TypedTag]

  object short extends Api with text.Tags[TypedTag] with AbstractShort{

    object * extends Api with Attrs[Attr, AttrValue, AttrPair] with Styles
  }
  implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    def writeTo(strb: Writer): Unit = xs.foreach(ev(_).asInstanceOf[Frag].writeTo(strb))
    def render = xs.map(_.render).mkString
  }
  implicit class OptionFrag[A](xs: Option[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    def writeTo(strb: Writer): Unit = xs.foreach(ev(_).asInstanceOf[Frag].writeTo(strb))
    def render = xs.map(_.render).mkString
  }
  implicit class ArrayFrag[A](xs: Array[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    def writeTo(strb: Writer): Unit = xs.foreach(ev(_).asInstanceOf[Frag].writeTo(strb))
    def render = xs.map(_.render).mkString
  }
  implicit class GeneratorFrag[A](xs: geny.Generator[A])(implicit ev: A => super.Frag) extends Frag{
    Objects.requireNonNull(xs)
    def writeTo(strb: Writer): Unit = xs.foreach(ev(_).asInstanceOf[Frag].writeTo(strb))
    def render = xs.map(_.render).mkString
  }
  implicit def UnitFrag(u: Unit): Frag = new StringFrag("")
  trait Api extends super.Api with text.TagFactory[TypedTag]{ self =>
    type Tag = TypedTag
    def frag(frags: Text.super.Frag*): Text.Frag  = SeqFrag(frags)

    def tag(s: String, void: Boolean = false): TypedTag = TypedTag(s, Nil, void)
    def raw(s: String): Frag = new RawFrag(s)

    protected[this] implicit def stringAttrX: AttrValue[String] = new GenericAttr[String]
    protected[this] implicit def stringStyleX: StyleValue[String] = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX: PixelStyleValue[String] = new GenericPixelStyle[String](stringStyleX)

    case class doctype(s: String)(content: Text.Frag) extends geny.Writable{
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


  implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier with TagBuilder.ValueSource{
    def applyTo(t: TagBuilder) = t.appendAttr("class",this)

    override def appendAttrValue(sb: java.io.Writer): Unit = {
      Escaping.escape(s.name, sb)
    }
  }
  implicit class StyleFrag(s: StylePair[_]) extends StyleSheetFrag{
    def applyTo(c: StyleTree) = {
      val b = new TagBuilder("dummy", false)
      s.applyTo(b)
      val Array(style, value) = b.attrsString(b.attrs(b.attrIndex("style"))._2).split(":", 2)
      c.copy(styles = c.styles.updated(style, value))
    }
  }

  protected[this] def genericAttr[T]: AttrValue[T] = new GenericAttr[T]
  protected[this] def genericStyle[T]: StyleValue[T] = new GenericStyle[T]
  protected[this] def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T] = new GenericPixelStyle[T](ev)
  protected[this] def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T] = new GenericPixelStylePx[T](ev)

  implicit def stringFrag(v: String): Frag = new StringFrag(v)

  trait Frag extends super.Frag with Modifier{
    def writeTo(strb: java.io.Writer): Unit
    def writeBytesTo(out: java.io.OutputStream): Unit = {
      val w = new java.io.OutputStreamWriter(out, java.nio.charset.StandardCharsets.UTF_8)
      writeTo(w)
      w.flush()
    }
    def render: String
    def applyTo(b: TagBuilder) = b.addChild(this)
  }

  private[this] class StringFrag(v: String) extends Frag{
    Objects.requireNonNull(v)
    def render = {
      val strb = new java.io.StringWriter()
      writeTo(strb)
      strb.toString()
    }
    def writeTo(strb: java.io.Writer) = Escaping.escape(v, strb)
  }

  private[this] class RawFrag(v: String) extends Frag {
    Objects.requireNonNull(v)
    def render = v
    def writeTo(strb: java.io.Writer) = strb.append(v)
  }

  private[this] class GenericAttr[T] extends AttrValue[T] {
    def apply(t: TagBuilder, a: Attr, v: T): Unit = {
      t.setAttr(a.name, TagBuilder.GenericAttrValueSource(v.toString))
    }
  }

  private[this] class GenericStyle[T] extends StyleValue[T] {
    def apply(t: TagBuilder, s: Style, v: T): Unit = t.appendAttr("style", TagBuilder.StyleValueSource(s, v.toString))
  }
  private[this] class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v, ev)
  }
  private[this] class GenericPixelStylePx[T](ev: StyleValue[String]) extends PixelStyleValue[T]{
    def apply(s: Style, v: T) = StylePair(s, v + "px", ev)
  }

  case class TypedTag(tag: String = "",
                      modifiers: List[Seq[Modifier]],
                      void: Boolean = false)
                      extends super.TypedTag[String] with Frag with geny.Writable{

    protected[this] type Self = TypedTag


    def writeTo(strb: java.io.Writer): Unit = {
      val builder = new TagBuilder(tag, void)
      build(builder)
      builder.writeTo(strb)
    }

    def apply(xs: Text.super.Modifier*): TypedTag = {
      this.copy(tag=tag, void = void, modifiers = xs :: modifiers)
    }

    /**
     * Converts an ScalaTag fragment into an html string
     */
    override def toString = {
      val strb = new java.io.StringWriter
      writeTo(strb)
      strb.toString()
    }
    def render: String = this.toString
  }


  class TagBuilder(tag: String,
                   void: Boolean,
                   var children: Array[Text.Frag] = new Array(4),
                   var attrs: Array[(String, TagBuilder.ValueSource)] = new Array(4)){
    final var childIndex = 0
    final var attrIndex = 0

    private[this] def incrementChidren(arr: Array[Text.Frag], index: Int) = {
      if (index >= arr.length){
        val newArr = new Array[Text.Frag](arr.length * 2)
        var i = 0
        while(i < arr.length){
          newArr(i) = arr(i)
          i += 1
        }
        newArr
      }else{
        null
      }
    }

    private[this] def incrementAttr(arr: Array[(String, TagBuilder.ValueSource)], index: Int) = {
      if (index >= arr.length){
        val newArr = new Array[(String, TagBuilder.ValueSource)](arr.length * 2)
        var i = 0
        while(i < arr.length){
          newArr(i) = arr(i)
          i += 1
        }
        newArr
      }else{
        null
      }
    }

    private[this] def increment[T: ClassTag](arr: Array[T], index: Int) = {
      if (index >= arr.length){
        val newArr = new Array[T](arr.length * 2)
        var i = 0
        while(i < arr.length){
          newArr(i) = arr(i)
          i += 1
        }
        newArr
      }else{
        null
      }
    }
    def addChild(c: Text.Frag) = {
      val newChildren = incrementChidren(children, childIndex)
      if (newChildren != null) children = newChildren
      children(childIndex) = c
      childIndex += 1
    }
    def appendAttr(k: String, v: TagBuilder.ValueSource) = {

      attrIndex(k) match{
        case -1 =>
          val newAttrs = incrementAttr(attrs, attrIndex)
          if (newAttrs!= null) attrs = newAttrs

          attrs(attrIndex) = k -> v
          attrIndex += 1
        case n =>
          val (oldK, oldV) = attrs(n)
          attrs(n) = (oldK, TagBuilder.ChainedAttributeValueSource(oldV, v))
      }
    }
    def setAttr(k: String, v: TagBuilder.ValueSource) = {
      attrIndex(k) match{
        case -1 =>
          val newAttrs = incrementAttr(attrs, attrIndex)
          if (newAttrs!= null) attrs = newAttrs
          attrs(attrIndex) = k -> v
          attrIndex += 1
        case n =>
          val (oldK, oldV) = attrs(n)
          attrs(n) = (oldK, TagBuilder.ChainedAttributeValueSource(oldV, v))
      }
    }


    def appendAttrStrings(v: TagBuilder.ValueSource, sb: java.io.Writer): Unit = {
      v.appendAttrValue(sb)
    }

    def attrsString(v: TagBuilder.ValueSource): String = {
      val sb = new java.io.StringWriter
      appendAttrStrings(v, sb)
      sb.toString
    }



    def attrIndex(k: String): Int = {
      attrs.indexWhere(x => x != null && x._1 == k)
    }
    /**
     * Serialize this [[TagBuilder]] and all its children out to the given StringBuilder.
     *
     * Although the external interface is pretty simple, the internals are a huge mess,
     * because I've inlined a whole lot of things to improve the performance of this code
     * ~4x from what it originally was, which is a pretty nice speedup
     */
    def writeTo(strb: java.io.Writer) = {

      // tag
      strb.append('<').append(tag)

      // attributes
      var i = 0
      while (i < attrIndex){
        val pair = attrs(i)
        strb.append(' ').append(pair._1).append("=\"")
        appendAttrStrings(pair._2,strb)
        strb.append('\"')
        i += 1
      }

      if (childIndex == 0 && void) {
        // No children - close tag
        strb.append(" />")
      } else {
        strb.append('>')
        // Childrens
        var i = 0
        while(i < childIndex){
          children(i).writeTo(strb)
          i += 1
        }

        // Closing tag
        strb.append("</").append(tag).append('>')
      }
    }
  }
  object TagBuilder{

    /**
     * More-or-less internal trait, used to package up the parts of a textual
     * attribute or style so that we can append the chunks directly to the
     * output buffer. Improves perf over immediately combining them into a
     * string and storing that, since this avoids allocating that intermediate
     * string.
     */
    trait ValueSource {
      def appendAttrValue(strb: java.io.Writer): Unit
    }
    case class StyleValueSource(s: Style, v: String) extends ValueSource {
      override def appendAttrValue(strb: java.io.Writer): Unit = {
        Escaping.escape(s.cssName, strb)
        strb.append(": ")
        Escaping.escape(v, strb)
        strb.append(";")
      }
    }

    case class GenericAttrValueSource(v: String) extends ValueSource {
      override def appendAttrValue(strb: java.io.Writer): Unit = {
        Escaping.escape(v, strb)
      }
    }

    case class ChainedAttributeValueSource(head: ValueSource, tail: ValueSource) extends ValueSource {
      override def appendAttrValue(strb: java.io.Writer): Unit = {
        head.appendAttrValue(strb)
        strb.append(" ")
        tail.appendAttrValue(strb)
      }
    }
  }
}
