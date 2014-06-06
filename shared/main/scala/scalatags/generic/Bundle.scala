package scalatags
package generic
import acyclic.file

trait Bundle[T]{
  val all: Attrs with Styles with Tags with DataConverters with Util
  val short: AbstractShort[T]
  val misc: AbstractMisc[T]

  type Attrs = generic.Attrs[T]
  type Tags = generic.Tags[T]
  type Tags2 = generic.Tags2[T]
  type Styles = generic.Styles[T]
  type Styles2 = generic.Styles2[T]
  type SvgTags = generic.SvgTags[T]
  type SvgStyles = generic.SvgStyles[T]
  type Util = generic.Util[T]
  type Node = generic.Node[T]
  type AttrVal = generic.AttrVal[T]
  type StyleVal = generic.StyleVal[T]

  implicit def stringAttr(s: String): AttrVal
  implicit def booleanAttr(b: Boolean): AttrVal
  implicit def numericAttr[V: Numeric](n: V) : AttrVal
  implicit def stringStyle(s: String): StyleVal
  implicit def booleanStyle(b: Boolean): StyleVal
  implicit def numericStyle[V: Numeric](n: V): StyleVal

  implicit def stringNode(v: String): Node
  implicit def NumericModifier[V: Numeric](u: V): Node
  type HtmlTag <: generic.TypedHtmlTag[Platform.Base, T]
  type RawNode <: Node
  val RawNode: Companion[RawNode]
  def raw(s: String): RawNode
  type StringNode <: Node

  val StringNode: Companion[StringNode]
}

trait AbstractShort[T]{
  val `*`: generic.Attrs[T] with generic.Styles[T]
}
trait AbstractMisc[T]{
  val attrs: generic.Attrs[T]
  val tags: generic.Tags[T]
  val tags2: generic.Tags2[T]
  val styles: generic.Styles[T]
  val styles2: generic.Styles2[T]
  val svgTags: generic.SvgTags[T]
  val svgStyles: generic.SvgStyles[T]
}
