package scalatags
package jsdom
import scalatags.JsDom.all._
import utest._

object StyleSheetTests
  extends generic.StyleSheetTests(scalatags.JsDom)
  with StyleSheetImplicits
