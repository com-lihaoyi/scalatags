import scalatags.XTags._

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p.onclick("... do some js")(
        "This is my first paragraph"
      ),
      a.href("www.google.com")(
        p("Goooogle")
      )
    )
  )
)

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))