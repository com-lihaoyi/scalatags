package scalatags
package generic

import utest._

import scalatags.stylesheet.{CascadingStyleSheet, StyleSheet, Sheet}

abstract class StyleSheetTests[Builder, Output <: FragT, FragT]
                              (val bundle: Bundle[Builder, Output, FragT])  extends TestSuite{

  import bundle.all._

  val pkg = "scalatags-generic-StyleSheetTests"
  val Simple = Sheet[Simple]
  trait Simple extends StyleSheet{
    def x = cls(
      backgroundColor := "red",
      height := 125
    )
    def y = cls.hover(
      opacity := 0.5
    )

    def z = cls(x.splice, y.splice)
  }

  val Inline = Sheet[Inline]
  trait Inline extends StyleSheet{
    def w = cls(
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
  val Cascade = Sheet[Cascade]
  trait Cascade extends CascadingStyleSheet{
    def y = cls()
    def x = cls(
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

  val Custom = Sheet[Custom]
  trait Custom extends CascadingStyleSheet{
    override def customSheetName = Some("CuStOm")
    def x = cls(
      backgroundColor := "red",
      height := 125
    )
    def y = cls.hover(
      opacity := 0.5
    )

  }


  def check(txt: String, expected: String) = {
    def normalize(s: String) = s.lines.map(_.trim).mkString

    assert(normalize(txt) == normalize(expected))
  }
  val tests = TestSuite{
    'feature{
      'hello{

        check(
          Simple.styleSheetText,
          s"""
            .$pkg-Simple-x{
              background-color: red;
              height: 125px;
            }
            .$pkg-Simple-y:hover{
              opacity: 0.5;
            }
            .$pkg-Simple-z{
              background-color: red;
              height: 125px;
              opacity: 0.5;
            }
          """
        )
      }

      'inline{
        check(
          Inline.styleSheetText,
          s"""
            .$pkg-Inline-w{
              opacity: 0.5;
            }
            .$pkg-Inline-w:hover{
              background-color: red;
            }
            .$pkg-Inline-w:active{
              background-color: blue;
            }
            .$pkg-Inline-w:hover:active{
              background-color: yellow;
            }
          """.stripMargin
        )
      }
      'cascade{
        check(
          Cascade.styleSheetText,
          s"""
            .$pkg-Cascade-x a{
              background-color: red;
              text-decoration: none;
            }
            .$pkg-Cascade-x a:hover{
              background-color: blue;
              text-decoration: underline;
            }
            .$pkg-Cascade-x a:hover div .$pkg-Cascade-y{
              opacity: 0;
            }
            .$pkg-Cascade-x div:hover div .$pkg-Cascade-y{
              opacity: 0;
            }
          """
        )
      }
    }
    'customization{
      check(
        Custom.styleSheetText,
        s"""
          .CuStOm-x{
            background-color: red;
            height: 125px;
          }
          .CuStOm-y:hover{
            opacity: 0.5;
          }
         """
      )
    }
    'failure{
      'noDirectInstantiation{
  // This doesn't seem to work, even though that snippet does indeed
  // cause a compilation error. Maybe a bug in uTest?

  //      compileError("""
  //        object Funky extends StyleSheet
  //      """).check("""
  //        object Funky extends StyleSheet
  //               ^
  //      """, "object creation impossible")
      }
      'noCascade{
        compileError("""
          val Cascade1 = Sheet[Cascade1]
          trait Cascade1 extends StyleSheet{
            def c = cls()
            def x = cls(
              c(
                backgroundColor := "red",
                textDecoration.none
              )
            )
          }
        """).check("""
              c(
               ^
        """, "Cls does not take parameters")
        // Cascading stylesheets have to be enabled manually, to encourage
        // usage only for the rare cases you actually want things to cascade
        compileError("""
          val Cascade2 = Sheet[Cascade2]
          trait Cascade2 extends StyleSheet{
            def x = cls(
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
    'htmlFrag{
      val x = div(
        Simple.x,
        Simple.y
      )
      val expected = s"""
        <div class=" $pkg-Simple-x $pkg-Simple-y"></div>
      """
      assert(
        x.toString.replaceAll("\\s", "") ==
        expected.replaceAll("\\s", "")
      )
    }
  }
}
