package scalatags
package generic
import acyclic.file
import scalatags.stylesheet.StyleSheetFrag
import scalatags.text

/**
 * An abstract representation of the Scalatags package. This allows you to
 * customize Scalatags to work with different backends, by defining your own
 * implementation of `Tag`, and specifying how the various [[Attr]]s
 * and [[Style]]s contribute to construct the [[Builder]]. Apart from satisfying the
 * default String/Boolean/Numeric implementations of [[Attr]] and [[Style]],
 * you can also define your own, e.g. ScalaJS ships with an implicit conversion
 * from `js.Any` to `Attr`, so that you can attach objects to the resultant
 * `dom.Element` without serializing them.
 *
 * By default, Scalatags ships with [[scalatags.Text]]: `Bundle[StringBuilder]`
 * on all platforms, and [[scalatags.JsDom]]: `Bundle[dom.Element]` on ScalaJS.
 *
 * It is possible to write entirely backend-agnostic Scalatags code by making
 * your code parametric on a [[Bundle]] (or some subclass of it), and importing
 * from that rather than importing directly from [[scalatags.JsDom]] or
 * [[scalatags.Text]]. You will naturally only be able to use functionality
 * (e.g. implicit conversions to [[Attr]]s and [[Style]]s which are present
 * in the common interface.
 *
 * @tparam Builder The type to which [[Attr]]s and [[Style]]s are applied to when the
 *                 `Tag` is being rendered to give a final result.
 */
trait Bundle[Builder, Output <: FragT, FragT] extends Aliases[Builder, Output, FragT] {
  /**
   * Convenience object for importing all of Scalatags' functionality at once
   */
  val all: Attrs with Styles with Tags with DataConverters with Util with Aggregate[Builder, Output, FragT]
  /**
   * Convenience object for importing only Scalatags' tags (e.g. `div`, `p`)
   * into the local namespace, while leaving Styles and Attributes accessible
   * via the `*` object
   */
  val short: AbstractShort with Tags with DataConverters with Aggregate[Builder, Output, FragT]
  /**
   * Convenience object for only importing Scalatag's implicits, without importing
   * any of the tags, styles or attributes themselves. This includes conversions to
   * [[Modifier]], typeclass instances for treating strings and numbers as attributes
   * or style values, and other things.
   */
  val implicits: Aggregate[Builder, Output, FragT] with DataConverters

  type AbstractShort = generic.AbstractShort[Builder, Output, FragT]


  /**
   * Common attributes.
   */
  val attrs: Attrs
  /**
   * Common tags
   */
  val tags: Tags
  /**
   * Less common tags
   */
  val tags2: Tags2
  /**
   * Common styles
   */
  val styles: Styles
  /**
   * Less common styles
   */
  val styles2: Styles2
  /**
   * SVG only tags
   */
  val svgTags: SvgTags

  /**
   * SVG only attributes
   */
  val svgAttrs: SvgAttrs


}

trait Aliases[Builder, Output <: FragT, FragT]{
  type Attrs = generic.Attrs[Builder, Output, FragT]
  type Tags = generic.Tags[Builder, Output, FragT]
  type Tags2 = generic.Tags2[Builder, Output, FragT]
  type Styles = generic.Styles[Builder, Output, FragT]
  type Styles2 = generic.Styles2[Builder, Output, FragT]
  type SvgTags = generic.SvgTags[Builder, Output, FragT]
  type SvgAttrs = generic.SvgAttrs[Builder, Output, FragT]
  type Util = generic.Util[Builder, Output, FragT]
  type AttrPair = generic.AttrPair[Builder, FragT]

  type Attr = generic.Attr
  type Style = generic.Style
  type Modifier = generic.Modifier[Builder]

  type AttrValue[V] = generic.AttrValue[Builder, V]
  type StyleValue[V] = generic.StyleValue[Builder, V]
  type PixelStyleValue[V] = generic.PixelStyleValue[Builder, V]

  type Tag = generic.TypedTag[Builder, Output, FragT]

  /**
   * A [[Modifier]] which contains a String which will not be escaped.
   */
  protected[this] type RawFrag <: Modifier
  protected[this] val RawFrag: Companion[RawFrag]

  /**
   * A [[Modifier]] which contains a String.
   */
  protected[this] type StringFrag <: Modifier
  protected[this] val StringFrag: Companion[StringFrag]

  type Frag = generic.Frag[Builder, FragT]
}
trait Aggregate[Builder, Output <: FragT, FragT] extends Aliases[Builder, Output, FragT]{
  implicit def StyleFrag(s: StylePair[Builder, _]): StyleSheetFrag
  implicit def ClsModifier(s: stylesheet.Cls): Modifier
  def genericAttr[T]: AttrValue[T]
  implicit val stringAttr = genericAttr[String]
  implicit val booleanAttr = genericAttr[Boolean]
  implicit val byteAttr = genericAttr[Byte]
  implicit val shortAttr = genericAttr[Short]
  implicit val intAttr = genericAttr[Int]
  implicit val longAttr = genericAttr[Long]
  implicit val floatAttr = genericAttr[Float]
  implicit val doubleAttr = genericAttr[Double]

  def genericStyle[T]: StyleValue[T]
  implicit val stringStyle = genericStyle[String]
  implicit val booleanStyle = genericStyle[Boolean]
  implicit val byteStyle = genericStyle[Byte]
  implicit val shortStyle = genericStyle[Short]
  implicit val intStyle = genericStyle[Int]
  implicit val longStyle = genericStyle[Long]
  implicit val floatStyle = genericStyle[Float]
  implicit val doubleStyle = genericStyle[Double]

  def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T]
  def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T]
  implicit val stringPixelStyle = genericPixelStyle[String]
  implicit val booleanPixelStyle = genericPixelStyle[Boolean]
  implicit val bytePixelStyle = genericPixelStylePx[Byte]
  implicit val shortPixelStyle = genericPixelStylePx[Short]
  implicit val intPixelStyle = genericPixelStylePx[Int]
  implicit val longPixelStyle = genericPixelStylePx[Long]
  implicit val floatPixelStyle = genericPixelStylePx[Float]
  implicit val doublePixelStyle = genericPixelStylePx[Double]

  implicit def byteFrag(v: Byte): Frag = stringFrag(v.toString)
  implicit def shortFrag(v: Short): Frag = stringFrag(v.toString)
  implicit def intFrag(v: Int): Frag = stringFrag(v.toString)
  implicit def longFrag(v: Long): Frag = stringFrag(v.toString)
  implicit def floatFrag(v: Float): Frag = stringFrag(v.toString)
  implicit def doubleFrag(v: Double): Frag = stringFrag(v.toString)
  implicit def stringFrag(v: String): Frag

  /**
   * Delimits a string that should be included in the result as raw,
   * un-escaped HTML
   */
  def raw(s: String): RawFrag
}
trait AbstractShort[Builder, Output <: FragT, FragT]{
  val `*`: generic.Attrs[Builder, Output, FragT] with generic.Styles[Builder, Output, FragT]
}
