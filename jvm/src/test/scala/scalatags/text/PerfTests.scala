package scalatags
package text
import acyclic.file
import org.fusesource.scalate.{DefaultRenderContext, RenderContext, TemplateSource, TemplateEngine}
import java.io.{StringWriter, PrintWriter}

case object JadePerf extends generic.PerfTest{
  val engine = new TemplateEngine

  val template = engine.load(TemplateSource.fromFile("jvm/src/test/resource/page.jade"))

  def calc() = {
    val stringWriter = new StringWriter()
    val ctx = new DefaultRenderContext("", engine, new PrintWriter(stringWriter))
    ctx.attributes("titleString") = generic.PerfTest.titleString
    ctx.attributes("firstParaString") = generic.PerfTest.firstParaString
    ctx.attributes("contentpara") = "contentpara"
    ctx.attributes("first") = "first"
    ctx.attributes("paras") = (0 until 5).map(i =>
      (i, if (i % 2 == 0) "red" else "green")
    )
    template.render(ctx)
    stringWriter.toString
  }
}
case object MustachePerf extends generic.PerfTest{
  val engine = new TemplateEngine

  val template = engine.load(TemplateSource.fromFile("jvm/src/test/resource/page.mustache"))

  def calc() = {
    val stringWriter = new StringWriter()
    val ctx = new DefaultRenderContext("", engine, new PrintWriter(stringWriter))
    ctx.attributes("titleString") = generic.PerfTest.titleString
    ctx.attributes("firstParaString") = generic.PerfTest.firstParaString
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
