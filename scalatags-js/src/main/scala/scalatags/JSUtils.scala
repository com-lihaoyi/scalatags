
package scalatags

import scala.scalajs.js
import js.Dynamic.{ global => g }
import org.scalajs.dom.document

object JSUtils {
  implicit def HtmlTag2JS(n: HtmlTag) = new {
    def toDOMNode = {
      val tmp = document.createElement("div")
      tmp.innerHTML = n.toString
      tmp.firstChild
    }

    def toJSDynamic = {
      val tmp = g.document.createElement("div")
      tmp.innerHTML = n.toString
      tmp.firstChild
    }
  }
}
