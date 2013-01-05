package scalatags

import org.scalatest._
import xml.NodeSeq


class MiscTests extends FreeSpec{

  import default._
  def xmlCheck(x: HtmlTag, ns: NodeSeq) = {

    def canonicalize(n: NodeSeq) = scala.xml.Utility.trim(n(0)).toString
    assert(
      canonicalize(x.toXML) === canonicalize(ns)
    )
  }

  /**
   * Tests the usage of the pre-defined tags, as well as creating
   * the tags on the fly from Symbols and Strings
   */
  "finding direct elements" in {
    val first = new Object()
    val second = new Object()
    val view = html(
      head(
        first
      ),
      body(
        div(
          second
        ),
        second
      )
    )

    assert (view.find{case x if x == first => x} === Seq(first))
    assert (view.find{case x if x == second => x} === Seq(second, second))
  }

  "finding attribute elements" in {
    val first = new Object()
    val second = new Object()

    val view = html(
      head(),
      body(
        div.attr("cow" -> first),
        div(
          p(
            "i am cow ",
            "hear me moo ",
            a("i weight twice as much as ", b.href(second)("you").css("color" -> first))
          )
        ).cls(first)
      )
    )
    assert( view.find { case x if x == first => x } === Seq(first, first, first) )
    assert( view.find { case x if x == second => x } === Seq(second) )
  }

  "css length converters" in {

    xmlCheck(
      html.w(10 px)(
        head(),
        body(
          div.padding(15 px, 15 px, 20 px, 20 px)(
            p.w(90 pct).h(50 pct)(
              "i am cow"
            )
          )

        )
      ),
      <html style="width: 10px; ">
        <head></head>
        <body>
          <div style="padding: 15px 15px 20px 20px; ">
            <p style="width: 90%; height: 50%; ">
              i am cow
            </p>
          </div>
        </body>
      </html>
    )
  }
}