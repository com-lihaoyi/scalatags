import scalatags.XTags._

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1.css("color" -> "red", "background-color" -> "blue")("This is my title"),
    div.color("red").background_color("blue")(
      p.cls("contentpara", "first")(
        "This is my first paragraph"
      ),
      a.opacity(0.9)(
        p.cls("contentpara")("Goooogle")
      )
    )
  )
)

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))