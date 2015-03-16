package scalatags.generic

import utest._


abstract class StyleSheetTests[Builder, Output <: FragT, FragT]
                     (bundle: Bundle[Builder, Output, FragT])  extends TestSuite{

  import bundle.all._
  implicit def StyleFrag(s: StylePair[Builder, _]): StyleSheetFrag
  val pkg = "scalatags-generic-StyleSheetTests"
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
      hover(
        backgroundColor := "red"
        ),
      active(
        backgroundColor := "blue"
        ),
      hover.active(
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
        s""".$pkg-Simple-0{
          |  background-color: red;
          |  height: 125px;
          |}
          |.$pkg-Simple-1:hover{
          |  opacity: 0.5;
          |}
          |.$pkg-Simple-2{
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
        s""".$pkg-Inline-0{
          |  opacity: 0.5;
          |}
          |.$pkg-Inline-0:hover{
          |  background-color: red;
          |}
          |.$pkg-Inline-0:active{
          |  background-color: blue;
          |}
          |.$pkg-Inline-0:hover:active{
          |  background-color: yellow;
          |}
        """.stripMargin
      )
    }
    'cascade{
      check(
        Cascade.styleSheetText,
        s""".$pkg-Cascade-1 a{
          |  background-color: red;
          |  text-decoration: none;
          |}
          |.$pkg-Cascade-1 a:hover{
          |  background-color: blue;
          |  text-decoration: underline;
          |}
          |.$pkg-Cascade-1 a:hover div .$pkg-Cascade-0{
          |  opacity: 0;
          |}
        """.stripMargin
      )
    }
    'noCascade{
      // Cascading stylesheets have to be enabled manually, to encourage
      // usage only for the rare cases you actually want things to cascade
      compileError("""
        object Cascade extends StyleSheet{
          val x = *(
            a(
              backgroundColor := "red",
              textDecoration.none
            )
          )
        }
      """).check("""
            a(
             ^
      """, "type mismatch")
    }
  }
}
