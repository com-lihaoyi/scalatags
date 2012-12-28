import scalatags.ScalaTags._

val numVisitors = 1023
val posts = Seq(
  ("alice", "i like pie"),
  ("bob", "pie is evil i hate you"),
  ("charlie", "i like pie and pie is evil, i hat myself")
)

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div("posts"),
    for ((name, text) <- posts) yield (
      div(
        h2("Post by ", name),
        p(text)
      )
    ),
    if(numVisitors > 100)(
      p("No more posts!")
    )else(
      p("Please post below...")
    )
  )
)

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))
