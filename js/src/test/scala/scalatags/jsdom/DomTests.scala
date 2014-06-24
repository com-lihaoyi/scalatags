package scalatags
package jsdom
import acyclic.file
import utest._
import JsDom._
import JsDom.all._


import TestUtil._
import org.scalajs.dom
import scala.scalajs.js
object DomTests extends TestSuite{
  def tests = TestSuite{
    'basic {
      'children {
        val elem = div.render
        assert(elem.children.length == 0)
        elem.appendChild(p(1, "wtf", "bbq").render)
        assert(elem.children.length == 1)
        val pElem = elem.children(0).asInstanceOf[dom.HTMLParagraphElement]
        assert(pElem.childNodes.length == 3)
        assert(pElem.textContent == "1wtfbbq")
      }

      'attributes {
        val url = "https://www.google.com/"
        val elem = a(
          href := url,
          "Google"
        ).render

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
        ).render
        assert(elem.style.color == "red")
        assert(elem.style.cssFloat == "left")
        assert(elem.style.backgroundColor == "yellow")
        // styles end up being sorted in alphabetical order
        assert(
          elem.getAttribute("style") == "color: red; float: left; background-color: yellow; "
        )
      }
    }
    'fancy {
      'boundAttributes {
        var count = 0
        val elem = div(
          onclick := { () => count += 1},
          tabindex := 1
        ).render

        assert(count == 0)
        elem.onclick(null)
        assert(count == 1)
      }
      'triggers {
        // Wrapping this, because for some reason if I leave it top-level
        // the compiler crashes =(
        def runTriggers() = {
          val labelElem = label("Default").render

          val inputElem = input(
            `type`:="text",
            onfocus := { () => labelElem.textContent = ""}
          ).render

          val box = div(
            inputElem,
            labelElem
          ).render

          assert(labelElem.textContent == "Default")
          inputElem.onfocus(null)
          assert(labelElem.textContent == "")
        }
        runTriggers()

      }
    }
  }

}
