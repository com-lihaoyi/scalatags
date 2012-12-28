import scalatags.ScalaTags._

class Parent{
  def render = html(
    headFrag,
    bodyFrag

  )
  def headFrag = head(
    script("some script")
  )
  def bodyFrag = body(
    h1("This is my title"),
    div(
      p("This is my first paragraph"),
      p("This is my second paragraph")
    )
  )
}

class Child extends Parent{
  override def headFrag = head(
    script("some other script")
  )
}

val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(new Child().render.toXML))
