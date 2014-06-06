package scalatags

import scalatags.generic._
import scala.collection.SortedMap
import acyclic.file

/**
 * A Scalatags module that works with a text back-end, i.e. it creates HTML
 * `String`s.
 */
object Text extends Bundle[StringBuilder] {
  object all extends StringTags with Attrs with Styles with Tags with DataConverters with Util
  object short extends StringTags with Util with DataConverters with generic.AbstractShort[StringBuilder]{
    object * extends StringTags with Attrs with Styles
  }

  object attrs extends StringTags with Attrs
  object tags extends StringTags with Tags
  object tags2 extends StringTags with Tags2
  object styles extends StringTags with Styles
  object styles2 extends StringTags with Styles2
  object svgTags extends StringTags with SvgTags
  object svgStyles extends StringTags with SvgStyles


  trait StringTags extends Util{ self =>
    type ConcreteHtmlTag[T <: Platform.Base] = TypedTag[T]

    protected[this] implicit def stringAttrInternal(s: String) = new StringAttr(s)
    protected[this] implicit def booleanAttrInternal(b: Boolean) = new BooleanAttr(b)
    protected[this] implicit def numericAttrInternal[T: Numeric](n: T) = new NumericAttr(n)

    protected[this] implicit def stringStyleInternal(s: String) = new StringStyle(s)
    protected[this] implicit def booleanStyleInternal(b: Boolean) = new BooleanStyle(b)
    protected[this] implicit def numericStyleInternal[T: Numeric](n: T) = new NumericStyle(n)

    def makeAbstractTypedTag[T <: Platform.Base](tag: String, void: Boolean) = {
      TypedTag(tag, Nil, SortedMap.empty, SortedMap.empty, void)
    }
  }

  implicit def NumericModifier[T: Numeric](u: T) = new StringNode(u.toString)


  implicit def stringNode(v: String) = new StringNode(v)

  object StringNode extends Companion[StringNode]
  case class StringNode(v: String) extends Node {
    def writeTo(strb: StringBuilder): Unit = Escaping.escape(v, strb)
  }

  def raw(s: String) = new RawNode(s)

  object RawNode extends Companion[RawNode]
  case class RawNode(v: String) extends Node {
    def writeTo(strb: StringBuilder): Unit = strb ++= v
  }

  class GenericAttr(val writer: StringBuilder => Unit) extends AttrVal{
    override def applyTo(strb: StringBuilder, k: Attr): Unit = {
      strb ++= k.name ++= "=\""
      applyPartial(strb)
      strb ++= "\""
    }
    def applyPartial(strb: StringBuilder) = writer(strb)
    def merge(o: AttrVal) = new GenericAttr({ strb =>
      applyPartial(strb)
      strb ++= " "
      o.applyPartial(strb)
    })
  }
  case class StringAttr(s: String) extends GenericAttr(Escaping.escape(s, _))
  case class BooleanAttr(b: Boolean) extends GenericAttr(_ ++= b.toString)
  case class NumericAttr[T: Numeric](n: T) extends GenericAttr(_ ++= n.toString)
  implicit def stringAttr(s: String) = new StringAttr(s)
  implicit def booleanAttr(b: Boolean) = new BooleanAttr(b)
  implicit def numericAttr[T: Numeric](n: T) = new NumericAttr(n)


  class GenericStyle(writer: StringBuilder => Unit) extends StyleVal{
    override def applyTo(strb: StringBuilder, k: Style): Unit = {
      strb ++= k.cssName ++= ": "
      writer(strb)
      strb ++= ";"
    }
  }
  case class StringStyle(s: String) extends GenericStyle(Escaping.escape(s, _))
  case class BooleanStyle(b: Boolean) extends GenericStyle(_ ++= b.toString)
  case class NumericStyle[T: Numeric](n: T) extends GenericStyle(_ ++= n.toString)
  implicit def stringStyle(s: String) = new StringStyle(s)
  implicit def booleanStyle(b: Boolean) = new BooleanStyle(b)
  implicit def numericStyle[T: Numeric](n: T) = new NumericStyle(n)

  case class TypedTag[+T <: Platform.Base](tag: String = "",
                                          children: List[Node],
                                          attrs: SortedMap[Attr, AttrVal],
                                          styles: SortedMap[Style, StyleVal],
                                          void: Boolean = false)
    extends generic.TypedTag[T, StringBuilder]{
    protected[this] type Self = TypedTag[T]
    def collapsedAttrs = {
      var moddedAttrs = attrs

      if (!styles.isEmpty) {
        val strb = new StringBuilder()

        for ((k, v) <- styles) {
          if (strb.length > 0) strb ++= " "
          v.applyTo(strb, k)
        }

        val newVal = {
          moddedAttrs.get(Attr("style")).fold[AttrVal](StringAttr(strb.toString))(_ merge StringAttr(strb.toString))
        }
        moddedAttrs = moddedAttrs.updated(Attr("style"), newVal)
      }
      moddedAttrs
    }

    /**
     * Serialize this [[HtmlTag]] and all its children out to the given StringBuilder.
     */
    def writeTo(strb: StringBuilder): Unit = {
      // tag
      strb ++= "<" ++= tag

      // attributes
      for (pair <- collapsedAttrs) {
        strb ++= " "
        pair._2.applyTo(strb, pair._1)
      }

      if (children.isEmpty && void)
      // No children - close tag
        strb ++= " />"
      else {
        strb ++= ">"
        // Childrens
        var x = children.reverse
        while (!x.isEmpty) {
          val child :: newX = x
          x = newX
          child.writeTo(strb)
        }

        // Closing tag
        strb ++= "</" ++= tag ++= ">"
      }
    }
    /**
     * Converts an ScalaTag fragment into an html string
     */
    override def toString = {
      val strb = new StringBuilder
      writeTo(strb)
      strb.toString()
    }

    override def transform(children: List[Node] = children,
                           attrs: SortedMap[Attr, AttrVal] = attrs,
                           styles: SortedMap[Style, StyleVal] = styles): Self = {
      this.copy(children=children, attrs=attrs, styles=styles)
    }
  }
  type Tag = TypedTag[Platform.Base]
  val Tag = TypedTag

}
