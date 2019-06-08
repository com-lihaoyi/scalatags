package scalatags
object TestUtil {
  def clean(s: String) = {
    " +<".r.replaceAllIn("\\n\\s*".r.replaceAllIn(s, "").trim(), "<").replace(" ", "").replace("\"/>", "\">").sorted
  }
  def strCheck(x: Any*) = {
    for (Seq(a, b) <- x.grouped(2)) {

      try
        assert(
          clean(a.toString()) == clean(b.toString())
        )
      catch {case e: AssertionError =>
        println(a)
        println(b)
        println(clean(a.toString()))
        println(clean(b.toString()))
        throw e
      }
    }
  }
}

