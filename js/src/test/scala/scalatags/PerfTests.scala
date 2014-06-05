package scalatags

import scalatags.text.all._

import TestUtil.strCheck
import org.scalajs.dom._
import scalajs.js
import scalajs.js.Dynamic._
import generic.PerfTest._


case object MustachePerf extends generic.PerfTest{
  val mainTemplate =
    """
      |<html>
      |  <head>
      |    <script>console.log(1)</script>
      |  </head>
      |  <body>
      |    <h1 style="color: red;">{{titleString}}</h1>
      |    <div style="background-color: blue;">
      |      <p class="{{contentpara}} {{first}}" title="this is paragraph 0">{{firstParaString}}</p>
      |      <a href="www.google.com">
      |        <p>Goooogle</p>
      |      </a>
      |      {{#paras}}
      |        <p class="{{contentpara}}" style="color: {{color}};" title="this is paragraph {{count}}">Paragraph {{count}}</p>
      |      {{/paras}}
      |    </div>
      |  </body>
      |</html>
    """.stripMargin

  val data: js.Array[_] = Array.tabulate(5)(i =>
    literal(
      count = i,
      color = (if (i % 2 == 0) "red" else "green")
    )
  )
  console.log("omg")
  console.log(global.Mustache)
  def calc() = {

    scala.scalajs.js.Dynamic.global.Mustache.render(
      mainTemplate,
      literal(
        titleString = titleString,
        firstParaString = firstParaString,
        contentpara = "contentpara",
        first = "first",
        paras = data
      )
    ).toString
  }
}


object JadePerf extends (() => String){
  val mainTemplate =
    """
      |-@ val titleString: String
      |-@ val contentpara: String
      |-@ val first: String
      |-@ val firstParaString: String
      |-@ val paras: Seq[(Int, String)]
      |
      |html
      |  head
      |    script console.log(1)
      |
      |  body
      |    h1(style="color: red;")
      |      = titleString
      |    div(style="background-color: blue;")
      |      p(class="#{contentpara} #{first}" title="this is paragraph 0")
      |        = firstParaString
      |
      |      a(href="www.google.com")
      |        p Goooogle
      |
      |      - paras.foreach{ case (count, color) =>
      |        - render("jvm/src/test/resource/para.jade", Map("count" -> count, "color" -> color))
      |      - }
      |
      |
      |
    """.stripMargin
  val paraTemplate =
    """
      |-@ val contentpara: String
      |-@ val color: String
      |-@ val count: Int
      |
      |p(class="#{contentpara}" style="color: #{color};" title="this is paragraph #{count}") Paragraph #{count}
    """.stripMargin
  val data: js.Array[_] = Array.tabulate(5)(i =>
    literal(
      count = i,
      color = (if (i % 2 == 0) "red" else "green")
    )
  )
  console.log("omg")
  console.log(global.Mustache)
  def apply() = {

    scala.scalajs.js.Dynamic.global.jade.render(
      mainTemplate,
      literal(
        titleString = titleString,
        firstParaString = firstParaString,
        contentpara = "contentpara",
        first = "first",
        paras = data
      )
    ).toString
  }
}