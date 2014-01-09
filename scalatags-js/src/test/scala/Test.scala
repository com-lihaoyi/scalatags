package example
package test

import scala.scalajs.js
import js.Dynamic.{ global => g }
import scala.scalajs.test.JasmineTest
import scalatags.HtmlTag


object Test extends JasmineTest {
  def strCheck(x: HtmlTag, s: String) = {
    assert(
      x.toString == "\\n\\s*".r.replaceAllIn(s, "").trim()
    )
  }
  import scalatags._

  describe("Scalatags") {
    it("Classes and CSS") {
      strCheck({
        val contentpara = "contentpara".cls
        val first = "first".cls
        html(
          head(
            script("some script")
          ),
          body(
            h1(backgroundColor~="blue", color~="red")("This is my title"),
            div(backgroundColor~="blue", color~="red")(
              p(contentpara, first)(
                "This is my first paragraph"
              ),
              a(opacity:=0.9)(
                p("contentpara".cls)("Goooogle")
              )
            )
          )
        )},
        """
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
      )
    }
    it("Different ways of static typing") {
      strCheck(
        div(
          div(backgroundColor:=hex"ababab"),
          div(color:=rgb(0, 255, 255)),
          div(color.red),
          div(borderRightColor:=hsla(100, 0, 50, 0.5)),
          div(backgroundImage:=radialGradient(hex"f00", hex"0f0"~50.pct, hex"00f")),
          div(backgroundImage:=url("www.picture.com/my_picture")),
          div(backgroundImage:=(
            radialGradient(45.px, 45.px, "ellipse farthest-corner", hex"f00", hex"0f0"~500.px, hex"00f"),
            linearGradient("to top left", hex"f00", hex"0f0"~10.px, hex"00f")
            ))
        ),
        """
        <div>
            <div style="background-color: #ababab;" />
            <div style="color: rgb(0, 255, 255);" />
            <div style="color: red;" />
            <div style="border-right-color: hsla(100, 0, 50, 0.5);" />
            <div style="background-image: radial-gradient(#f00, #0f0 50%, #00f);" />
            <div style="background-image: url(www.picture.com/my_picture);" />
            <div style="background-image: radial-gradient(45px 45px, ellipse farthest-corner, #f00, #0f0 500px, #00f), linear-gradient(to top left, #f00, #0f0 10px, #00f);" />
        </div>
        """
        )
    }
    it("Additional Imports"){
      strCheck(
      {
        import Styles.pageBreakBefore
        import Tags.address
        import SvgTags.svg
        import SvgStyles.stroke
        div(
          p(pageBreakBefore.always, "a long paragraph which should not be broken"),
          address("500 Memorial Drive, Cambridge MA"),
          svg(stroke:="blue")
        )
      },
      """
    <div>
        <p style="page-break-before: always;">
            a long paragraph which should not be broken
        </p>
        <address>500 Memorial Drive, Cambridge MA</address>
        <svg style="stroke: blue;" />
    </div>
      """
      )
    }

    it("Unsanitized Input"){
      strCheck(
      {
        val evilInput = "<script>alert('hello!')</script>"

        html(
          head(
            script("some script")
          ),
          body(
            h1("This is my title"),
            raw(evilInput)
          )
        )
      },
      """
    <html>
        <head>
            <script>some script</script>
        </head>
        <body>
            <h1>This is my title</h1>
            <script>alert('hello!')</script>
        </body>
    </html>
      """
      )
    }
  }
}