package scalatags

object Util extends org.scalatest.FreeSpec{
  def strCheck(x: HtmlTag, s: String) = {
    assert(
      x.toString === s
    )
  }
}
