import scalatags.ScalaTags._

def page(scripts: Seq[XNode], content: Seq[XNode]) =
  html(
    head(scripts),
    body(
      h1("This is my title"),
      div.cls("content")(content)
    )
  )

val frag =
  page(
    Seq(
      script("some script")
    ),
    Seq(
      p("This is the first ", b("image"), " displayed on the ", a("site")),
      img.src("www.myImage.com/image.jpg"),
      p("blah blah blah i am text")
    )
  )

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))
