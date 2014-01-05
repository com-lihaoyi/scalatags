package scalatags

object Util extends org.scalatest.FreeSpec{
  val pattern = ">\\s+<".r
  def strCheck(x: HtmlTag, s: String) = {
    assert(
      x.toString === pattern.replaceAllIn(s, "><").trim()
    )
  }
}
