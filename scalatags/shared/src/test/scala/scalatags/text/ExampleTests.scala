package scalatags
package text

import acyclic.file
import utest._

object ExampleTests extends generic.ExampleTests(Text)

object PrettyPrintTests extends TestSuite {

  import scalatags.Text.all._

  def tests = TestSuite {

    'prettyPrint {

      val f1 =
        html(
          head(
            script(src := "..."),
            script(
              "alert('Hello World')"
            )
          ),
          body(
            div(
              h1(id := "title", "This is a title"),
              p("This is a big paragraph of text")
            )
          )
        ).pretty(4)

      val res1 =
"""<html>
  |    <head>
  |        <script src = "..." >
  |        </script>
  |        <script>
  |            alert('Hello World')
  |        </script>
  |    </head>
  |    <body>
  |        <div>
  |            <h1 id = "title" >
  |                This is a title
  |            </h1>
  |            <p>
  |                This is a big paragraph of text
  |            </p>
  |        </div>
  |    </body>
  |</html>""".stripMargin

      assert(f1 == res1)
    }
  }
}

