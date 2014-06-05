package scalatags

//import org.fusesource.scalate.{DefaultRenderContext, RenderContext, TemplateSource, TemplateEngine}
import java.io.{StringWriter, PrintWriter}


object PerfTests extends PerfTestBase{
  val samples = Seq(
    ScalatagsText -> "ScalatagsText",
    ScalaXML -> "ScalaXML"//,
//    Twirl -> "Twirl",
//    Mustache -> "Mustache",
//    Jade -> "Jade"
  )
}
/*
case object Jade extends (() => String){
  val engine = new TemplateEngine

  val template = engine.load(TemplateSource.fromFile("jvm/src/test/resource/page.jade"))

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

  val template = engine.load(TemplateSource.fromFile("jvm/src/test/resource/page.mustache"))

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
}*/

//case object Twirl extends (() => String){
//  def apply() = {
//    html.page(
//      "contentpara",
//      "first",
//      Scalatags.titleString,
//      Scalatags.firstParaString,
//      (0 until 5).map(i =>
//        (i, if (i % 2 == 0) "red" else "green")
//      )
//    ).toString
//  }
//}
