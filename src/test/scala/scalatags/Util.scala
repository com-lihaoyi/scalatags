package scalatags

object Util {

  def strCheck(x: HtmlTag, s: String) = {
    assert(
      x.toString == "\\n\\s*".r.replaceAllIn(s, "").trim()
    )
  }
}
