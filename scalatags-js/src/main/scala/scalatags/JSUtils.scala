package scalatags

import scala.scalajs.js
import js.Dynamic.{ global => g }
import org.scalajs.dom.document

object JSUtils {
  implicit class HtmlTag2JS(val n: HtmlTag) extends AnyVal {
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
