package scalatags
import acyclic.file
object TestUtil {
  def clean(s: String) = {
    " +<".r.replaceAllIn("\\n\\s*".r.replaceAllIn(s, "").trim(), "<").replace(" ", "").replace("\"/>", "\">")
  }
  def strCheck(x: Any*) = {
    for (Seq(a, b) <- x.grouped(2)) {

      try
        assert(
          clean(a.toString()) == clean(b.toString())
        )
      catch {case e: AssertionError =>
        println(clean(a.toString()))
        println(clean(b.toString()))
        throw e
      }
    }
  }
  def main(args: Array[String]){
    import utest._
    import utest.framework.TestSuite
    import utest.ExecutionContext.RunNow
    val tests = TestSuite{
      "hello"-{
        println("world")
      }
    }
    println("A")
    tests.run()
    println("B")
  }
}
