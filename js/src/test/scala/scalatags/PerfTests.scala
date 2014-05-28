package scalatags

import scalatags.all._
import scalatags.Styles.{float, color}
import Util.strCheck
object PerfTests extends PerfTestBase{
  val samples = Seq(
    Scalatags -> "Scalatags",
    ScalaXML -> "ScalaXML"
  )
}
