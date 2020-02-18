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


object Text extends generic.Bundle[String, String]{

  object attrs extends Text.Cap with Attrs
  object tags extends Text.Cap with text.Tags[TypedTag[String]] with Tags
  object tags2 extends Text.Cap with text.Tags2[TypedTag[String]] with Tags2
  object styles extends Text.Cap with Styles
  object styles2 extends Text.Cap with Styles2

  object svgTags extends Text.Cap with text.SvgTags[TypedTag[String]] with SvgTags
  object svgAttrs extends Text.Cap with SvgAttrs


  object all
    extends Cap
    with AbstractAll
    with text.Tags[TypedTag[String]]

  object short
    extends Cap
    with text.Tags[TypedTag[String]]
    with AbstractShort{

    object * extends Cap with Attrs with Styles
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
  implicit def UnitFrag(u: Unit) = new Text.StringFrag("")
  trait Cap extends Util with text.TagFactory[TypedTag[String]]{ self =>
    def frag(frags: Text.super.Frag*): Frag  = SeqFrag(frags)
    def modifier(mods: Modifier*): Modifier = SeqNode(mods)
    def css(s: String): Style = Style(camelCase(s), s)
    def tag(s: String, void: Boolean = false): TypedTag[String] = TypedTag(s, Nil, void)
    def makeAbstractTypedTag[T <: String](tag: String, void: Boolean, namespaceConfig: Namespace): TypedTag[T] = {
      TypedTag(tag, Nil, void)
    }
//    type Tag = Text.TypedTag[String]
    type Frag = Text.Frag
    type Modifier = Text.Modifier
    type Style = Text.Style

    type ConcreteHtmlTag[T <: String] = TypedTag[T]
    type BaseTagType = TypedTag[String]
    protected[this] implicit def stringAttrX = new GenericAttr[String]
    protected[this] implicit def stringStyleX = new GenericStyle[String]
    protected[this] implicit def stringPixelStyleX = new GenericPixelStyle[String](stringStyleX)

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

//  trait Aggregate extends generic.Aggregate[String, String]{
  implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier with Builder.ValueSource{
    def applyTo(t: Builder) = t.appendAttr("class",this)

    override def appendAttrValue(sb: java.io.Writer): Unit = {
      Escaping.escape(s.name, sb)
    }
  }
  implicit class StyleFrag(s: StylePair[_]) extends StyleSheetFrag{
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
    def apply(t: Builder, a: Attr, v: T): Unit = {
      t.setAttr(a.name, Builder.GenericAttrValueSource(v.toString))
    }
  }

  class GenericStyle[T] extends StyleValue[T] {
    def apply(t: Builder, s: Style, v: T): Unit = {
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

    protected[this] type Self = TypedTag[O]

    /**
     * Serialize this [[TypedTag]] and all its children out to the given StringBuilder.
     *
     * Although the external interface is pretty simple, the internals are a huge mess,
     * because I've inlined a whole lot of things to improve the performance of this code
     * ~4x from what it originally was, which is a pretty nice speedup
     */
    def writeTo(strb: java.io.Writer): Unit = {
      val builder = new Builder()
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
    def render: O = this.toString.asInstanceOf[O]
  }


  class Builder(var children: Array[Text.Frag] = new Array(4),
                var attrs: Array[(String, Builder.ValueSource)] = new Array(4)){
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

    private[this] def incrementAttr(arr: Array[(String, Builder.ValueSource)], index: Int) = {
      if (index >= arr.length){
        val newArr = new Array[(String, Builder.ValueSource)](arr.length * 2)
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
    def appendAttr(k: String, v: Builder.ValueSource) = {

      attrIndex(k) match{
        case -1 =>
          val newAttrs = incrementAttr(attrs, attrIndex)
          if (newAttrs!= null) attrs = newAttrs

          attrs(attrIndex) = k -> v
          attrIndex += 1
        case n =>
          val (oldK, oldV) = attrs(n)
          attrs(n) = (oldK, Builder.ChainedAttributeValueSource(oldV, v))
      }
    }
    def setAttr(k: String, v: Builder.ValueSource) = {
      attrIndex(k) match{
        case -1 =>
          val newAttrs = incrementAttr(attrs, attrIndex)
          if (newAttrs!= null) attrs = newAttrs
          attrs(attrIndex) = k -> v
          attrIndex += 1
        case n =>
          val (oldK, oldV) = attrs(n)
          attrs(n) = (oldK, Builder.ChainedAttributeValueSource(oldV, v))
      }
    }


    def appendAttrStrings(v: Builder.ValueSource, sb: java.io.Writer): Unit = {
      v.appendAttrValue(sb)
    }

    def attrsString(v: Builder.ValueSource): String = {
      val sb = new java.io.StringWriter
      appendAttrStrings(v, sb)
      sb.toString
    }



    def attrIndex(k: String): Int = {
      attrs.indexWhere(x => x != null && x._1 == k)
    }
  }
  object Builder{

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
