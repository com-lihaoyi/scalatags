package scalatags

import org.scalatest.FreeSpec

import org.fusesource.scalate.{DefaultRenderContext, RenderContext, TemplateSource, TemplateEngine}
import java.io.{StringWriter, PrintWriter}


class PerfTests extends FreeSpec{

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

  "Correctness" in {
    println("Correctness")
    Util.strCheck(
      Scalatags(),
      Mustache(),
      Jade(),
      Twirl(),
      expected
    )
  }
  "Perf" in {
    println("Perf")
    // Status Quo: 1000000 in 11739ms

    def test(f: () => String, name: String) = {
      val start = System.currentTimeMillis()
      var i = 0
      val d = 10000

      while(System.currentTimeMillis() - start < d){
        i += 1
        f()
      }

      println(name + "\t" + i + " in " + d)
    }
    test(Scalatags, "Scalatags")
    test(Mustache, "Mustache")
    test(Jade, "Jade")
    test(Twirl, "Twirl")
  }
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
  def apply() = html(
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
case object Jade extends (() => String){
  val engine = new TemplateEngine

  val template = engine.load(TemplateSource.fromFile("src/test/resource/page.jade"))

  def apply() = {
    val stringWriter = new StringWriter()
    val ctx = new DefaultRenderContext("", engine, new PrintWriter(stringWriter))
    ctx.attributes("titleString") = Scalatags.titleString
    ctx.attributes("firstParaString") = Scalatags.firstParaString
    ctx.attributes("contentpara") = "contentpara"
    ctx.attributes("first") = "first"
    ctx.attributes("paras") = (0 until 5).map(i =>
      (i, if (i % 2 == 0) "red" else "green")
    )
    template.render(ctx)
    stringWriter.toString
  }
}
case object Mustache extends (() => String){
  val engine = new TemplateEngine

  val template = engine.load(TemplateSource.fromFile("src/test/resource/page.mustache"))

  def apply() = {
    val stringWriter = new StringWriter()
    val ctx = new DefaultRenderContext("", engine, new PrintWriter(stringWriter))
    ctx.attributes("titleString") = Scalatags.titleString
    ctx.attributes("firstParaString") = Scalatags.firstParaString
    ctx.attributes("contentpara") = "contentpara"
    ctx.attributes("first") = "first"
    ctx.attributes("paras") = (0 until 5).map(i =>  Map(
      "count" -> i,
      "color" -> (if (i % 2 == 0) "red" else "green")
    ))
    template.render(ctx)
    stringWriter.toString
  }
}
case object Twirl extends (() => String){
  def apply() = {
    html.page(
      "contentpara",
      "first",
      Scalatags.titleString,
      Scalatags.firstParaString,
      (0 until 5).map(i =>
        (i, if (i % 2 == 0) "red" else "green")
      )
    ).toString
  }
}