package scalatags
package generic


/**
 * Created by haoyi on 6/4/14.
 */
trait AbstractPackage[T]{
  type Attrs = generic.Attrs[T]
  type Tags = generic.Tags[T]
  type Tags2 = generic.Tags2[T]
  type Styles = generic.Styles[T]
  type Styles2 = generic.Styles2[T]
  type SvgTags = generic.SvgTags[T]
  type SvgStyles = generic.SvgStyles[T]
  type Util = generic.Util[T]
  type Node = generic.Node[T]

  implicit def stringAttr(s: String): AttrVal[T]
  implicit def booleanAttr(b: Boolean): AttrVal[T]
  implicit def numericAttr[V: Numeric](n: V) : AttrVal[T]
  implicit def stringStyle(s: String): StyleVal[T]
  implicit def booleanStyle(b: Boolean): StyleVal[T]
  implicit def numericStyle[V: Numeric](n: V): StyleVal[T]

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
