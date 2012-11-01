import scalatags.XTags._

var content = "I am cow"

val frag =
  html(
    body(
      h1("This is my title"),
      div.cls("content")(() => content)
    )
  )


val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(frag.toXML))

content = "Hear me moo"

println(prettier.format(frag.toXML))