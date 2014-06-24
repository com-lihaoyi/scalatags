package scalatags
package text



trait Aggregate extends generic.Aggregate[text.Builder, String, String]{
  type Tag = Text.TypedTag[String]
  val Tag = Text.TypedTag

  type Frag = text.Frag

  implicit val stringAttr = new Text.GenericAttr[String]
  implicit val booleanAttr= new Text.GenericAttr[Boolean]
  implicit def numericAttr[T: Numeric] = new Text.GenericAttr[T]
  implicit val stringStyle = new Text.GenericStyle[String]
  implicit val booleanStyle = new Text.GenericStyle[Boolean]
  implicit def numericStyle[T: Numeric] = new Text.GenericStyle[T]


  implicit def byteFrag(v: Byte) = new Text.StringFrag(v.toString)
  implicit def shortFrag(v: Short) = new Text.StringFrag(v.toString)
  implicit def intFrag(v: Int) = new Text.StringFrag(v.toString)
  implicit def longFrag(v: Long) = new Text.StringFrag(v.toString)
  implicit def floatFrag(v: Float) = new Text.StringFrag(v.toString)
  implicit def doubleFrag(v: Double) = new Text.StringFrag(v.toString)
  implicit def stringFrag(v: String) = new Text.StringFrag(v)

  object attrs extends Text.Cap with Attrs
  object tags extends Text.Cap with text.Tags
  object tags2 extends Text.Cap with text.Tags2
  object styles extends Text.Cap with Styles
  object styles2 extends Text.Cap with Styles2
  object svgTags extends Text.Cap with text.SvgTags
  object svgStyles extends Text.Cap with SvgStyles

  val StringFrag: Companion[StringFrag]
  val RawFrag: Companion[RawFrag]
  type StringFrag <: Text.StringFrag
  type RawFrag <: Text.RawFrag
  def raw(s: String) = RawFrag(s)
}