package scalatags
package text
import utest._
object BundlingTests extends TestSuite {
  object CustomBundle extends Text.Cap with text.Tags with text.Tags2 with Text.Aggregate {
    object st extends Text.Cap with Text.Styles with Text.Styles2
    object at extends Text.Cap with Text.Attrs
  }
  val tests = TestSuite {
    val expected = """
        <html>
            <head>
                <script>some script</script>
            </head>
            <body>
                <h1 style="background-color: blue; color: red;">This is my title</h1>
                <div style="background-color: blue; color: red;">
                <p class="contentpara first">This is my first paragraph</p>
                <a style="opacity: 0.9;">
                    <p class="contentpara">Goooogle</p>
                </a>
                </div>
            </body>
        </html>
                   """
    test("custom") {
      import CustomBundle._
      TestUtil.strCheck(
        html(
          head(
            script("some script")
          ),
          body(
            h1(st.backgroundColor := "blue", st.color := "red")("This is my title"),
            div(st.backgroundColor := "blue", st.color := "red")(
              p(at.cls := "contentpara first")(
                "This is my first paragraph"
              ),
              a(st.opacity := 0.9)(
                p(at.cls := "contentpara")("Goooogle")
              )
            )
          )
        ),
        expected
      )
    }
    test("interop") {
      import CustomBundle.{st, at}
      import Text.all._
      TestUtil.strCheck(
        CustomBundle.html(
          head(
            script("some script")
          ),
          CustomBundle.body(
            h1(backgroundColor := "blue", st.color := "red")("This is my title"),
            div(st.backgroundColor := "blue", color := "red")(
              p(cls := "contentpara first")(
                "This is my first paragraph"
              ),
              CustomBundle.a(st.opacity := 0.9)(
                p(at.cls := "contentpara")("Goooogle")
              )
            )
          )
        ),
        expected
      )
    }
  }
}
