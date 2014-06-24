package scalatags
package text



trait Aggregate extends generic.Aggregate[text.Builder, String, String]{
  def genericAttr[T] = new Text.GenericAttr[T]
  def genericStyle[T] = new Text.GenericStyle[T]

  implicit def stringFrag(v: String) = new Text.StringFrag(v)

  val RawFrag = Text.RawFrag
  val StringFrag = Text.StringFrag
  type StringFrag = Text.StringFrag
  type RawFrag = Text.RawFrag
  def raw(s: String) = RawFrag(s)
}