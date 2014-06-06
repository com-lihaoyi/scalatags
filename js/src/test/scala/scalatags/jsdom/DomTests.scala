package scalatags
package jsdom
import acyclic.file
import utest._
import JsDom._
import all._
import TestUtil._
import org.scalajs.dom
import scala.scalajs.js
object DomTests extends TestSuite{

  def tests = TestSuite{
    'basic {
      'children {
        val elem = div.toDom
        assert(elem.children.length == 0)
        elem.appendChild(p("omg", "wtf", "bbq").toDom)
        assert(elem.children.length == 1)
        val pElem = elem.children(0).asInstanceOf[dom.HTMLParagraphElement]
        assert(pElem.childNodes.length == 3)
        assert(pElem.textContent == "omgwtfbbq")
      }

      'attributes {
        val url = "https://www.google.com/"
        val elem = a(
          href := url,
          "Google"
        ).toDom

        assert(elem.href == url)
        assert(elem.children.length == 0)
        assert(elem.childNodes.length == 1)
        val textNode = elem.childNodes(0).asInstanceOf[dom.Text]
        assert(textNode.textContent == "Google")
      }

      'styles {
        val elem = div(
          color := "red",
          float.left,
          backgroundColor := "yellow"
        ).toDom

        assert(elem.style.color == "red")
        assert(elem.style.cssFloat == "left")
        assert(elem.style.backgroundColor == "yellow")
        // styles end up being sorted in alphabetical order
        assert(
          elem.getAttribute("style") == "background-color: yellow; color: red; float: left; "
        )
      }
    }
    'boundAttributes{
      var count = 0
      val elem = div(
        onclick := {() => count += 1},
        tabindex := 1
      ).toDom

      assert(count == 0)
      elem.onclick(null)
      assert(count == 1)
    }
  }
}
