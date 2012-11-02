import scalatags.XTags._

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p.attr("onclick" -> "... do some js")(
        "This is my first paragraph"
      ),
      a.attr("href" -> "www.google.com")(
        p("Goooogle")
      )
    )
  )
)

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))