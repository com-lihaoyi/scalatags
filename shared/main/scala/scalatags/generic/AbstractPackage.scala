package scalatags.generic

/**
 * Created by haoyi on 6/4/14.
 */
trait AbstractPackage[T]{
  implicit def stringAttr(s: String): AttrVal[T]
  implicit def booleanAttr(b: Boolean): AttrVal[T]
  implicit def numericAttr[V: Numeric](n: V) : AttrVal[T]
  implicit def stringStyle(s: String): StyleVal[T]
  implicit def booleanStyle(b: Boolean): StyleVal[T]
  implicit def numericStyle[V: Numeric](n: V): StyleVal[T]

  implicit def stringNode(v: String): Node[T]
  implicit def NumericModifier[V: Numeric](u: V): Node[T]
}
