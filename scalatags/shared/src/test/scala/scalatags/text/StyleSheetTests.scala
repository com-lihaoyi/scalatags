package scalatags
package text
import scalatags.Text.all._
import utest._

object StyleSheetTests extends TestSuite{
  def pkg = "text"
  object Simple extends StyleSheet{
    val x = *(
      backgroundColor := "red",
      height := 125
    )
    val y = *hover(
      opacity := 0.5
    )

    val z = *(x, y)
  }
  object Inline extends StyleSheet{
    val w = *(
      &hover(
        backgroundColor := "red"
      ),
      &active(
        backgroundColor := "blue"
      ),
      &.hover.active(
        backgroundColor := "yellow"
      ),
      opacity := 0.5
    )
  }
  object Cascade extends CascadingStyleSheet{
    val y = *()
    val x = *(
      a(
        backgroundColor := "red",
        textDecoration.none
      ),
      a.hover(
        backgroundColor := "blue",
        textDecoration.underline
      ),
      (a.hover ~ div ~ y.cls)(
        opacity := 0
      )
    )
  }

  def check(txt: String, rawExpected: String) = {
    val rendered = txt.lines.map(_.trim).mkString
    val expected = rawExpected.lines.map(_.trim).mkString
    assert(rendered == expected)
  }
  val tests = TestSuite{
    'hello{
      check(
        Simple.styleSheetText,
        """.cls0{
          |  background-color: red;
          |  height: 125px;
          |}
          |.cls1:hover{
          |  opacity: 0.5;
          |}
          |.cls2{
          |  background-color: red;
          |  height: 125px;
          |  opacity: 0.5;
          |}
        """.stripMargin
      )
    }
    'inline{
      check(
        Inline.styleSheetText,
        """.cls0{
          |  opacity: 0.5;
          |}
          |.cls0:hover{
          |  background-color: red;
          |}
          |.cls0:active{
          |  background-color: blue;
          |}
          |.cls0:hover:active{
          |  background-color: yellow;
          |}
        """.stripMargin
      )
    }
    'cascade{
      check(
        Cascade.styleSheetText,
        """.cls1 a{
          |  background-color: red;
          |  text-decoration: none;
          |}
          |.cls1 a:hover{
          |  background-color: blue;
          |  text-decoration: underline;
          |}
          |.cls1 a:hover div .cls0{
          |  opacity: 0;
          |}
        """.stripMargin
      )
    }
  }
}
