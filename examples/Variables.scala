import scalatags.XTags._

val title = "title"
val numVisitors = 1023

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1("This is my ", title),
    div(
      p("This is my first paragraph"),
      p("you are the ", numVisitors.toString, "th visitor!")
    )
  )

)

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))