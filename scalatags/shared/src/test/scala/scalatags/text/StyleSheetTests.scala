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
  object SS2 extends StyleSheet{
    val x = *(

    )
  }
  val tests = TestSuite{
    'SS{
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
    'cascade{

    }
  }
}
