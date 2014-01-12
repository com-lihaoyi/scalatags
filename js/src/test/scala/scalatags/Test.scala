package scalatags

import org.scalatest.FreeSpec
import scalatags.all._
import scalatags.Styles.{float, color}
import Util.strCheck
object BasicTestsJs extends BasicTests
object ExampleTestsJs extends ExampleTests
object PerfTestsJs extends PerfTests{
  val samples = Seq(
    Scalatags -> "Scalatags",
    ScalaXML -> "ScalaXML"
  )
}
