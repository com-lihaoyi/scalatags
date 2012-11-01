import scalatags.XTags._

val numVisitors = 1023
val posts = Seq(
  ("alice", "i like pie"),
  ("bob", "pie is evil i hate you"),
  ("charlie", "i like pie and pie is evil, i hat myself")
)

def imgBox(src: String, text: String) =
  div(
    img.src(src),
    div(
      p(text)
    )
  )

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    imgBox("www.mysite.com/imageOne.png", "This is the first image displayed on the site"),
    div.cls("content")(
      p("blah blah blah i am text"),
      imgBox("www.mysite.com/imageTwo.png", "This image is very interesting")
    )
  )
)

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))
