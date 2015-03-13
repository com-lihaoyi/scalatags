package scalatags.jsdom
import scalatags.JsDom.all._
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
  }

  val tests = TestSuite{
    'hello{
      val txt = SS.styleSheetText
      assert(txt ==
        """.scalatags-jsdom-StyleSheetTests-SS--0 {
          |  background-color: red; height: 125px;
          |}
          |.scalatags-jsdom-StyleSheetTests-SS--1:hover {
          |  opacity: 0.5;
          |}
          |""".stripMargin)
    }
  }
}
