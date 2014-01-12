package scalatags

import org.scalatest.FreeSpec

import org.fusesource.scalate.{DefaultRenderContext, RenderContext, TemplateSource, TemplateEngine}
import java.io.{StringWriter, PrintWriter}

class PerfTestsImpl extends PerfTests{
  val samples = Seq(
    Scalatags -> "Scalatags",
    ScalaXML -> "ScalaXML",
    Twirl -> "Twirl",
    Mustache -> "Mustache",
    Jade -> "Jade"
  )
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