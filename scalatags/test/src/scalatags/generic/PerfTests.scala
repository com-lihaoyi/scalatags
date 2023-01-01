package scalatags
package generic
import utest._

class ScalatagsPerf[Builder, Output <: FragT, FragT](val bundle: Bundle[Builder, Output, FragT])
    extends PerfTests {
  import bundle.all._
  import generic.PerfTests._

  def para(n: Int, m: generic.Modifier[Builder]*) = p(
    m,
    title := ("this is paragraph " + n)
  )

  def calc() = {
    html(
      head(
        script("console.log(1)")
      ),
      body(
        h1(color := "red")(titleString),
        div(backgroundColor := "blue")(
          para(0, cls := contentpara + " " + first, firstParaString),
          a(href := "www.google.com")(
            p("Goooogle")
          ),
          for (i <- 0 until 5) yield {
            para(
              i,
              cls := contentpara,
              color := (if (i % 2 == 0) "red" else "green"),
              "Paragraph ",
              i
            )
          }
        )
      )
    ).toString
  }

}
object PerfTests {
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
trait PerfTests extends TestSuite {

  def calc(): String
  def name: String = this.toString
  def tests = TestSuite {
    test("correctness") {

      TestUtil.strCheck(calc(), PerfTests.expected)
      test("performance") {
        println("Benchmarking " + this.name)
        val start = System.currentTimeMillis()
        var i = 0
        val d = 10000

        while (System.currentTimeMillis() - start < d) {
          i += 1
          calc()
        }

        name.padTo(20, ' ') + i + " in " + d

      }
    }
  }
}
