package scalatags

object Util {
  def clean(s: String) = {
    " +<".r.replaceAllIn("\\n\\s*".r.replaceAllIn(s, "").trim(), "<")
  }
  def strCheck(x: Any*) = {
    for (Seq(a, b) <- x.grouped(2)) {
//      println(clean(a.toString()))
//      println(clean(b.toString()))
      try
        assert(
          clean(a.toString()) == clean(b.toString())
        )
      catch {case e: AssertionError =>
        println(clean(a.toString()))
        println(clean(a.toString()))
        throw e
      }
    }
  }
}
