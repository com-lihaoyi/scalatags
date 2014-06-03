package scalatags

import acyclic.file
import scala.collection.SortedMap

object all extends StringTags with Attrs[StringBuilder] with Styles[StringBuilder] with Tags[StringBuilder] with DataConverters with Util[StringBuilder]
object short extends StringTags with Util[StringBuilder] with DataConverters{
  object * extends StringTags with Attrs[StringBuilder] with Styles[StringBuilder]
}
object misc {
  object attrs extends StringTags with Attrs[StringBuilder]
  object tags extends StringTags with Tags[StringBuilder]
  object tags2 extends StringTags with Tags2[StringBuilder]
  object styles extends StringTags with Styles[StringBuilder]
  object styles2 extends StringTags with Styles2[StringBuilder]
  object svgTags extends StringTags with SvgTags[StringBuilder]
  object svgStyles extends StringTags with SvgStyles[StringBuilder]

}
/**
 * Convenience object to help import all [[Tags]], [[Attrs]], [[Styles]] and
 * [[Datatypes]] into the global namespace via `import scalatags.all._`
 */
trait StringTags extends Util[StringBuilder]{ self =>
  class GenericAttr(val writer: StringBuilder => Unit) extends AttrVal[StringBuilder]{
    override def applyTo(strb: StringBuilder, k: Attr): Unit = {
      strb ++= k.name ++= "=\""
      applyPartial(strb)
      strb ++= "\""
    }
    def applyPartial(strb: StringBuilder) = writer(strb)
    def merge(o: AttrVal[StringBuilder]) = new GenericAttr({ strb =>
      applyPartial(strb)
      strb ++= " "
      o.applyPartial(strb)
    })
  }
  implicit def stringAttr(s: String) = new StringAttr(s)
  case class StringAttr(s: String) extends GenericAttr(Escaping.escape(s, _))
  implicit def booleanAttr(b: Boolean) = new BooleanAttr(b)
  case class BooleanAttr(b: Boolean) extends GenericAttr(_ ++= b.toString)
  implicit def numericAttr[T: Numeric](n: T) = new NumericAttr(n)
  case class NumericAttr[T: Numeric](n: T) extends GenericAttr(_ ++= n.toString)

  class GenericStyle(writer: StringBuilder => Unit) extends StyleVal[StringBuilder]{
    override def applyTo(strb: StringBuilder, k: Style): Unit = {
      strb ++= k.cssName ++= ": "
      writer(strb)
      strb ++= ";"
    }
  }
  implicit def stringStyle(s: String) = new StringStyle(s)
  case class StringStyle(s: String) extends GenericStyle(Escaping.escape(s, _))
  implicit def booleanStyle(b: Boolean) = new BooleanStyle(b)
  case class BooleanStyle(b: Boolean) extends GenericStyle(_ ++= b.toString)
  implicit def numericStyle[T: Numeric](n: T) = new NumericStyle(n)
  case class NumericStyle[T: Numeric](n: T) extends GenericStyle(_ ++= n.toString)

  def raw(s: String) = new RawNode(s)

  /**
   * A [[scalatags.Node]] which contains a String.
   */
  case class StringNode(v: String) extends Node[StringBuilder] {
    def writeTo(strb: StringBuilder): Unit = Escaping.escape(v, strb)
  }

  /**
   * A [[scalatags.Node]] which contains a String which will not be escaped.
   */
  case class RawNode(v: String) extends Node[StringBuilder] {
    def writeTo(strb: StringBuilder): Unit = strb ++= v
  }

  /**
   * Allows you to modify a HtmlTag by adding a String to its list of children
   */
  implicit def stringNode(v: String) = new StringNode(v)

  /**
   * Lets you put numbers into a scalatags tree, as a no-op.
   */
  implicit def NumericModifier[T: Numeric](u: T) = new StringNode(u.toString)
  type HtmlTag = TypedHtmlTag[Platform.Base]
  val HtmlTag = TypedHtmlTag
  def makeAbstractTypedHtmlTag[T <: Platform.Base](tag: String, void: Boolean) = {
    TypedHtmlTag(tag, Nil, SortedMap.empty, SortedMap.empty, void)
  }
  case class TypedHtmlTag[T <: Platform.Base](tag: String = "",
                                              children: List[Node[StringBuilder]],
                                              attrs: SortedMap[Attr, AttrVal[StringBuilder]],
                                              styles: SortedMap[Style, StyleVal[StringBuilder]],
                                              void: Boolean = false)
                                              extends AbstractTypedHtmlTag[T, StringBuilder]{
    type Self = TypedHtmlTag[T]
    def collapsedAttrs = {
      var moddedAttrs = attrs

      if (!styles.isEmpty) {
        val strb = new StringBuilder()

        for ((k, v) <- styles) {
          if (strb.length > 0) strb ++= " "
          v.applyTo(strb, k)
        }

        val newVal = {
          moddedAttrs.get(Attr("style")).fold[AttrVal[StringBuilder]](strb.toString)(_ merge strb.toString)
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
      for ((attr, value) <- collapsedAttrs) {
        strb ++= " "
        value.applyTo(strb, attr)
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

    override def transform(children: List[Node[StringBuilder]] = children,
                      attrs: SortedMap[Attr, AttrVal[StringBuilder]] = attrs,
                      styles: SortedMap[Style, StyleVal[StringBuilder]] = styles): Self = {
      this.copy(children=children, attrs=attrs, styles=styles)
    }
  }
}
