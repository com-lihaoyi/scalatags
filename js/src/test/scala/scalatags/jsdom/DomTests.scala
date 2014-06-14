package scalatags
package jsdom
import acyclic.file
import utest._
import JsDom._
import JsDom.all.ExtendedString
import JsDom.svgTags._
import JsDom.svgAttrs.{x, y, width, height}
import all._
import TestUtil._
import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
object DomTests extends TestSuite{
  def tests = TestSuite{
    'basic {
      'children {
        val elem = div.render
        assert(elem.children.length == 0)
        elem.appendChild(p("omg", "wtf", "bbq").render)
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

      'svg {
        val tag = svg(id := "test")(
          metadata(
          ),
          clipPath(id := "clip")(
            rect(x := 50, y := 50, width := 100, height := 100)
          )
        )
        g.document.body.appendChild(tag.render)

        val elem = g.document.getElementById("test")
        assert(elem != null)
        // casting will fail unless the tag was created with a proper namespace.
        assert(elem.isInstanceOf[dom.SVGSVGElement])

        val clip = g.document.getElementById("clip")
        assert(clip != null)
        // casting will fail unless the tag's name is in correct case.
        assert(clip.isInstanceOf[dom.SVGClipPathElement])
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
//        def runTriggers() = {
//          val labelElem = label("Default").render
//
//          val inputElem = input(
//            `type`:="text",
//            onfocus := { () => labelElem.textContent = ""}
//          ).render
//
//          val box = div(
//            inputElem,
//            labelElem
//          ).render
//
//          assert(labelElem.textContent == "Default")
//          inputElem.onfocus(null)
//          assert(labelElem.textContent == "")
//        }
//        runTriggers()

      }
    }
  }

}
