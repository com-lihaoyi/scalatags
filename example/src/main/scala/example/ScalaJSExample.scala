package example
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
class Example[Builder, Output <: FragT, FragT]
             (val bundle: scalatags.generic.Bundle[Builder, Output, FragT], exampleName: String){
  val htmlFrag = {
    import bundle.all._
    div(
      h1(exampleName),
      h1("Header 1"),
      h2("Header 2"),
      h3("Header 3"),
      h4("Header 4"),
      h5("Header 5"),
      h6("Header 6"),
      hr,
      p(
        "This is a paragraph with a whole bunch of ",
        "text inside. You can have lots and lots of ",
        "text"
      ),
      b("bold"), " ",
      i("italic"), " ",
      s("strikethrough"), " ",
      u("underlined"), " ",
      em("emphasis"), " ",
      strong("strong"), " ",
      sub("sub"), " ",
      sup("sup"), " ",
      code("code"), " ",
      a("a-link"), " ",
      small("small"), " ",
      cite("cite"), " ",
      ins("ins"), " ",
      del("del"), " ",
      hr,
      ol(
        li("Ordered List Item 1"),
        li("Ordered List Item 2"),
        li("Ordered List Item 3")
      ),
      ul(
        li("Unordered List Item 1"),
        li("Unordered List Item 2"),
        li("Unordered List Item 3")
      ),
      dl(
        dt("Definition List Term 1"),
        dd("Definition List Item 1"),
        dt("Definition List Term 2"),
        dd("Definition List Item 2")
      ),
      hr,
      pre(
        """
          |Preserved formatting area with all the whitespace
          |kept around and probably some kind of monospace font
        """.stripMargin
      ),
      blockquote(
        """
          |Block quote with a bunch of text inside
          |which really rox
        """.stripMargin
      ),
      hr,
      table(
        caption("This is a table caption"),
        thead(
          tr(
            th("Header Content 1"),
            th("Header Content 2")
          )
        ),
        tbody(
          tr(
            td("Body Content 1"),
            td("Body Content 2")
          ),
          tr(
            td("Body Content A"),
            td("Body Content B")
          )
        ),
        tfoot(
          tr(
            td("Foot Content 1"),
            td("Foot Content 2")
          )
        )
      ),
      hr,
      label("input"), input,
      br,
      label("select"),
      select(
        option("lol"),
        option("wtf")
      ),
      br,
      label("textarea"),
      textarea
    )
  }
  val svgFrag = {
    import bundle.implicits._
    import bundle.svgTags._
    import bundle.svgAttrs._
    svg(height := "800", width := "500")(
      polyline(
        points := "20,20 40,25 60,40 80,120 120,140 200,180",
        fill := "none",
        stroke := "black",
        strokeWidth := "3"
      ),
      line(
        x1 := 175, y1 := 100,
        x2 := 275, y2 := 0,
        stroke := "rgb(255,0,0)",
        strokeWidth := 10
      ),
      rect(
        x := 300, y := 10,
        rx := 20, ry := 20,
        width := 100,
        height := 100,
        fill := "rgb(0,0,255)",
        strokeWidth := 3,
        stroke := "rgb(0,0,0)",
        fillOpacity := "0.1",
        strokeOpacity := "0.5"
      ),
      circle(
        cx := 30, cy := 250,
        r := 10,
        stroke := "black",
        strokeWidth := 3,
        fill := "red"
      ),
      ellipse(
        cx := 150, cy := 250,
        rx := 100, ry := 50,
        fill := "yellow",
        stroke := "purple",
        strokeWidth := 4
      ),
      polygon(
        points := "300,110 350,290 260,310",
        fill := "line",
        stroke := "purple",
        strokeWidth := 10
      ),
      path(
        d := "M100 300 L25 500 L175 500 Z"
      ),
      text(
        x := 350, y := 250,
        fill := "red",
        transform := "rotate(30 20, 40)",
        "I love SVG!"
      ),
      text(x := 350, y := 350, fill := "green")(
        "Several lines",
        tspan(x := 350, y := 380, "First line."),
        tspan(x := 350, y := 410, "Second line.")
      ),
      defs(
        linearGradient(
          id := "grad1",
          x1 := "0%",
          y1 := "0%",
          x2 := "100%",
          y2 := "0%",
          stop(
            offset := "0%",
            stopColor := "rgb(255,255,0)"
          ),
          stop(
            offset := "100%",
            stopColor := "rgb(255,0,0)"
          )
        )
      ),
      ellipse(
        cx := 100, cy := 590,
        rx := 85, ry := 55,
        fill := "url(#grad1)"
      )
    )
  }
}
@JSExport
object ScalaJSExample {
  @JSExport
  def main(): Unit = {
    val textExample = new Example(scalatags.Text, "scalatags.Text Example")
    val jsDomExample = new Example(scalatags.JsDom, "scalatags.JsDom Example")
    dom.document.body.innerHTML = textExample.htmlFrag.render + textExample.svgFrag.render
    dom.document.body.appendChild(jsDomExample.htmlFrag.render)
    dom.document.body.appendChild(jsDomExample.svgFrag.render)
  }
}
