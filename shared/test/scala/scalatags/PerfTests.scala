package scalatags

import org.scalatest.FreeSpec

trait PerfTests extends FreeSpec{

  def samples: Seq[(() => String, String)]
  "perf" - {
    "Correctness" in {
      Util.strCheck(

        (samples.map(_._1()) :+ PerfTests.expected):_*
      )
    }
    "Perf" in {

      def test(f: () => String, name: String) = {
        val start = System.currentTimeMillis()
        var i = 0
        val d = 10000

        while(System.currentTimeMillis() - start < d){
          i += 1
          f()
        }

        println(name.padTo(20, ' ') + i + " in " + d)
      }
      for ((sample, name) <- samples){
        test(sample, name)
      }
    }
  }
}
object PerfTests{
  val expected =
    """
    <html>
      <head>
        <script>console.log(1)</script>
      </head>
      <body>
        <h1 style="color: red;">This is my title</h1>
        <div style="background-color: blue;">
          <p class="contentpara first" title="this is paragraph 0">This is my first paragraph</p>
          <a href="www.google.com">
            <p>Goooogle</p>
          </a>
          <p class="contentpara" style="color: red;" title="this is paragraph 0">Paragraph 0</p>
          <p class="contentpara" style="color: green;" title="this is paragraph 1">Paragraph 1</p>
          <p class="contentpara" style="color: red;" title="this is paragraph 2">Paragraph 2</p>
          <p class="contentpara" style="color: green;" title="this is paragraph 3">Paragraph 3</p>
          <p class="contentpara" style="color: red;" title="this is paragraph 4">Paragraph 4</p>
        </div>
      </body>
    </html>
    """
}

case object Scalatags extends (() => String){

  import scalatags.all._
  val contentpara = "contentpara".cls
  val first = "first".cls
  def para(n: Int) = p(
    contentpara,
    title:=s"this is paragraph $n"
  )
  val titleString = "This is my title"
  val firstParaString = "This is my first paragraph"
  def apply() =
    html(
      head(
        script("console.log(1)")
      ),
      body(
        h1(color:="red")(titleString),
        div(backgroundColor:="blue")(
          para(0)(
            first,
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


case object ScalaXML extends (() => String){
  val contentpara = "contentpara"
  val first = "first"

  def para(i: Int) = {
    val color = if (i % 2 == 0) "red" else "green"
    <p class={contentpara}
       style={s"color: $color;"}
       title={s"this is paragraph $i"}>Paragraph {i}</p>
  }
  def apply() = {

    <html>
      <head>
        <script>console.log(1)</script>
      </head>
      <body>
        <h1 style="color: red;">{Scalatags.titleString}</h1>
        <div style="background-color: blue;">
          <p class={contentpara + " " + first} title="this is paragraph 0">{Scalatags.firstParaString}</p>
          <a href="www.google.com">
            <p>Goooogle</p>
          </a>
          {0 until 5 map para}
        </div>
      </body>
    </html>
  }.toString
}