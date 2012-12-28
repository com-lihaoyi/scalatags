import scalatags.ScalaTags._
import xml.Unparsed

val input = "<p>i am a cow</p>"

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    Unparsed(input)
  )
)

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))