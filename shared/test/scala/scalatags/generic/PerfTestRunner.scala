package scalatags
package generic
import acyclic.file
import utest._

class ScalatagsPerf[T](val omg: Bundle[T]) extends PerfTest {


  import omg._
  import all._
  import generic.PerfTest._

  def para(n: Int, m: generic.Modifier[T]*) = p(
    cls := contentpara,
    m,
    title := s"this is paragraph $n"
  )

  def calc() = {
    html(
      head(
        script("console.log(1)")
      ),
      body(
        h1(color := "red")(titleString),
        div(backgroundColor := "blue")(
          para(0)(
            cls := s"$contentpara $first",
            firstParaString
          ),
          a(href := "www.google.com")(
            p("Goooogle")
          ),
          for (i <- 0 until 5) yield {
            para(i, color := (if (i % 2 == 0) "red" else "green"))(
              s"Paragraph $i"
            )
          }
        )
      )
    ).toString
  }

}
object PerfTest{
  val titleString = "This is my title"
  val firstParaString = "This is my first paragraph"
  val contentpara = "contentpara"
  val first = "first"
  val expected =
    """
    <html>
      <head>
        <script>console.log(1)</script>
      </head>
      <body>
        <h1 style="color: red;">This is my title</h1>
        <div style="background-color: blue;">
          <p class="contentpara first" title="this is paragraph 0">This is my first paragraph</p>
          <a href="www.google.com">
            <p>Goooogle</p>
          </a>
          <p class="contentpara" style="color: red;" title="this is paragraph 0">Paragraph 0</p>
          <p class="contentpara" style="color: green;" title="this is paragraph 1">Paragraph 1</p>
          <p class="contentpara" style="color: red;" title="this is paragraph 2">Paragraph 2</p>
          <p class="contentpara" style="color: green;" title="this is paragraph 3">Paragraph 3</p>
          <p class="contentpara" style="color: red;" title="this is paragraph 4">Paragraph 4</p>
        </div>
      </body>
    </html>
    """
}
trait PerfTest extends TestSuite{

  def calc(): String
  def name: String = this.toString
  def tests = TestSuite{
    'correctness{

//      TestUtil.strCheck(calc, PerfTest.expected)
      'performance{
        println("Benchmarking " + this.name)
        val start = System.currentTimeMillis()
        var i = 0
        val d = 10000

        while(System.currentTimeMillis() - start < d){
          i += 1
          calc()
        }

        name.padTo(20, ' ') + i + " in " + d

      }
    }
  }
}