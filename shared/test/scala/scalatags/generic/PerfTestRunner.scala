package scalatags.generic


import scalatags.generic

/**
 * Created by haoyi on 6/4/14.
 */
class PerfTestRunner[T](val v: generic.Attrs[T] with generic.Styles[T] with generic.Tags[T],
                        val omg: AbstractPackage[T]) extends (() => String){
  import v._
  import omg._
  import generic.PerfTestRunner._
  def para(n: Int) = p(
    cls := contentpara,
    title:=s"this is paragraph $n"
  )

  def apply() =
    html(
      head(
        script("console.log(1)")
      ),
      body(
        h1(color:="red")(titleString),
        div(backgroundColor:="blue")(
          para(0)(
            cls := s"$contentpara $first",
            firstParaString
          ),
          a(href:="www.google.com")(
            p("Goooogle")
          ),
          for(i <- 0 until 5) yield para(i)(
            s"Paragraph $i",
            color:=(if (i % 2 == 0) "red" else "green")
          )
        )
      )
    ).toString()

}
object PerfTestRunner{
  val titleString = "This is my title"
  val firstParaString = "This is my first paragraph"
  val contentpara = "contentpara"
  val first = "first"

}