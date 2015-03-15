package scalatags
package text
import scalatags.Text.all._
import utest._

object StyleSheetTests extends generic.StyleSheetTests(scalatags.Text){
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
      *hover(
        backgroundColor := "red"
      ),
      opacity := 0.5
    )
  }
  object Cascade extends StyleSheet{
    val x = *.cascade(a)(
      backgroundColor := "red",
      textDecoration.none
    )
    val y = *.cascade(a.hover)(
      backgroundColor := "blue",
      textDecoration.underline
    )
    val z = *.cascade(a.hover, div)(
      opacity := 0
    )

  }
}
