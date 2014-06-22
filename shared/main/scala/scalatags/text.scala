package scalatags

import scalatags.generic._
import scala.collection.SortedMap
import acyclic.file
import collection.mutable
import scala.annotation.unchecked.uncheckedVariance

/**
 * A Scalatags module that works with a text back-end, i.e. it creates HTML
 * `String`s.
 */

object Text extends Bundle[text.Builder, String] {

  object all extends StringTags with Attrs with Styles with text.Tags with DataConverters
  object short extends StringTags with Util with DataConverters with generic.AbstractShort[text.Builder, String]{
    object * extends StringTags with Attrs with Styles
  }

  object attrs extends StringTags with Attrs
  object tags extends StringTags with text.Tags
  object tags2 extends StringTags with text.Tags2
  object styles extends StringTags with Styles
  object styles2 extends StringTags with Styles2
  object svgTags extends StringTags with text.SvgTags
  object svgStyles extends StringTags with SvgStyles


  trait StringTags extends Util{ self =>
    type ConcreteHtmlTag[T <: String] = TypedTag[T]

    protected[this] implicit def stringAttr = new GenericAttr[String]
    protected[this] implicit def stringStyle = new GenericStyle[String]

    def makeAbstractTypedTag[T](tag: String, void: Boolean) = {
      TypedTag(tag, Nil, void)
    }
  }

  implicit def byteFrag(v: Byte) = new StringFrag(v.toString)
  implicit def shortFrag(v: Short) = new StringFrag(v.toString)
  implicit def intFrag(v: Int) = new StringFrag(v.toString)
  implicit def longFrag(v: Long) = new StringFrag(v.toString)
  implicit def floatFrag(v: Float) = new StringFrag(v.toString)
  implicit def doubleFrag(v: Double) = new StringFrag(v.toString)
  implicit def stringFrag(v: String) = new StringFrag(v)


  object StringFrag extends Companion[StringFrag]
  case class StringFrag(v: String) extends Frag{
    def render = {
      val strb = new StringBuilder()
      writeTo(strb)
      strb.toString()
    }
    def writeTo(strb: StringBuilder) = Escaping.escape(v, strb)
  }

  def raw(s: String) = new RawFrag(s)

  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends Frag {
    def render = v
    def writeTo(strb: StringBuilder) = strb ++= v
  }

  class GenericAttr[T] extends AttrValue[T]{
    def apply(t: text.Builder, a: Attr, v: T): Unit = {
      t.addAttr(a.name, v.toString)
    }
  }
  implicit val stringAttr = new GenericAttr[String]
  implicit val booleanAttr= new GenericAttr[Boolean]
  implicit def numericAttr[T: Numeric] = new GenericAttr[T]

  class GenericStyle[T] extends StyleValue[T]{
    def apply(t: text.Builder, s: Style, v: T): Unit = {
      val strb = new StringBuilder()

      Escaping.escape(s.cssName, strb)
      strb ++=  ": "
      Escaping.escape(v.toString, strb)
      strb ++= ";"

      t.addAttr("style", strb.toString)

    }
  }
  implicit val stringStyle = new GenericStyle[String]
  implicit val booleanStyle = new GenericStyle[Boolean]
  implicit def numericStyle[T: Numeric] = new GenericStyle[T]

  case class TypedTag[+Output <: String](tag: String = "",
                                         modifiers: List[Seq[Modifier]],
                                         void: Boolean = false)
                                         extends generic.TypedTag[text.Builder, Output]
                                         with Frag{
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
        Escaping.escape(pair._2, strb)
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
  type Tag = TypedTag[String]
  val Tag = TypedTag

  type Frag = text.Frag
}
