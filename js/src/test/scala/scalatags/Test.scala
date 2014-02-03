package scalatags

import scalatags.all._
import scalatags.Styles.{float, color}
import Util.strCheck
object PerfTestsJs extends PerfTests{
  val samples = Seq(
    Scalatags -> "Scalatags",
    ScalaXML -> "ScalaXML"
  )
}
