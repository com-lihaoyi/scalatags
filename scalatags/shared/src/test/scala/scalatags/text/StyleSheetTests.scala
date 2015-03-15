package scalatags.text
import scalatags.Text.all._
import utest._

object StyleSheetTests extends TestSuite{
  object SS extends StyleSheet{
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
      *hover(
        backgroundColor := "red"
      ),
      opacity := 0.5
    )
  }
//  object Cascade extends StyleSheet{
//    val x = *(
//      cascade(a)(
//        backgroundColor := "red",
//        textDecoration.none
//      ),
//      cascade(a).hover(
//        backgroundColor := "blue",
//        textDecoration.underline
//      ),
//      border := "1px solid red"
//    )
//  }
  val tests = TestSuite{
    'basic{
      'hello{
        val txt = SS.styleSheetText
        assert(txt ==
          """.scalatags-text-StyleSheetTests-SS--0 {
            |  background-color: red;height: 125px;
            |}
            |.scalatags-text-StyleSheetTests-SS--1:hover {
            |  opacity: 0.5;
            |}
            |""".stripMargin)
      }
      'combinations{
        assert(SS.x.classes ++ SS.y.classes subsetOf SS.z.classes )
      }
    }
    'inline{
      val txt = Inline.styleSheetText
      assert(txt ==
        """.scalatags-text-StyleSheetTests-Inline--0:hover {
          |  background-color: red;
          |}
          |.scalatags-text-StyleSheetTests-Inline--1 {
          |  opacity: 0.5;
          |}
          |""".stripMargin)
      assert(Inline.w.classes == Set(
        "scalatags-text-StyleSheetTests-Inline--0",
        "scalatags-text-StyleSheetTests-Inline--1"
      ))
    }
//    'cascade{
//      val txt = Cascade.styleSheetText
//      assert(txt ==
//        """.scalatags-text-StyleSheetTests-SS2--0 a{
//          |  background-color: red;text-decoration: none;
//          |}
//          |.scalatags-text-StyleSheetTests-SS2--1 *{
//          |  border: 1px solid red;
//          |}
//          |""".stripMargin)
//    }
  }
}
