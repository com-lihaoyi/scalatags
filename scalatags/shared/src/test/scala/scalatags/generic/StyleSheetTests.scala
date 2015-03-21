package scalatags
package generic

import utest._

import scalatags.stylesheet.{CascadingStyleSheet, StyleSheet, Sheet}

abstract class StyleSheetTests[Builder, Output <: FragT, FragT]
                              (val bundle: Bundle[Builder, Output, FragT])  extends TestSuite{

  import bundle.all._

  val pkg = "scalatags-generic-StyleSheetTests"
  object Simple extends Sheet[Simple]
  trait Simple extends StyleSheet{
    def x = *(
      backgroundColor := "red",
      height := 125
    )
    def y = *hover(
      opacity := 0.5
    )

    def z = *(x.splice, y.splice)
  }
  object Inline extends Sheet[Inline]
  trait Inline extends StyleSheet{
    def w = *(
      &.hover(
        backgroundColor := "red"
      ),
      &.active(
        backgroundColor := "blue"
      ),
      &.hover.active(
        backgroundColor := "yellow"
      ),
      opacity := 0.5
    )
  }
  object Cascade extends Sheet[Cascade]
  trait Cascade extends CascadingStyleSheet{
    def y = *()
    def x = *(
      a(
        backgroundColor := "red",
        textDecoration.none
      ),
      a.hover(
        backgroundColor := "blue",
        textDecoration.underline
      ),
      (a.hover ~ div ~ y)(
        opacity := 0
      ),
      div.hover(
        div(
          y(
            opacity := 0
          )
        )
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
        s""".$pkg-Simple-x{
          |  background-color: red;
          |  height: 125px;
          |}
          |.$pkg-Simple-y:hover{
          |  opacity: 0.5;
          |}
          |.$pkg-Simple-z{
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
        s""".$pkg-Inline-w{
          |  opacity: 0.5;
          |}
          |.$pkg-Inline-w:hover{
          |  background-color: red;
          |}
          |.$pkg-Inline-w:active{
          |  background-color: blue;
          |}
          |.$pkg-Inline-w:hover:active{
          |  background-color: yellow;
          |}
        """.stripMargin
      )
    }
    'cascade{
      check(
        Cascade.styleSheetText,
        s""".$pkg-Cascade-x a{
          |  background-color: red;
          |  text-decoration: none;
          |}
          |.$pkg-Cascade-x a:hover{
          |  background-color: blue;
          |  text-decoration: underline;
          |}
          |.$pkg-Cascade-x a:hover div .$pkg-Cascade-y{
          |  opacity: 0;
          |}
          |.$pkg-Cascade-x div:hover div .$pkg-Cascade-y{
          |  opacity: 0;
          |}
        """.stripMargin
      )
    }
//    'noCascade{
//      // Cascading stylesheets have to be enabled manually, to encourage
//      // usage only for the rare cases you actually want things to cascade
//      compileError("""
//        object Cascade extends StyleSheet{
//          val x = *(
//            a(
//              backgroundColor := "red",
//              textDecoration.none
//            )
//          )
//        }
//      """).check("""
//            a(
//             ^
//      """, "type mismatch")
//    }
    'htmlFrag{
      val x = div(
        Simple.x,
        Simple.y
      )
      val expected = s"""<div class=" ${Simple.x.name} ${Simple.y.name}"></div>"""
      assert(
        x.toString.replaceAll("\\s", "") ==
        expected.replaceAll("\\s", "")
      )
    }
  }
}
