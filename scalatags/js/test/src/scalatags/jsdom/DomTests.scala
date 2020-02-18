package scalatags
package jsdom
import utest._



import org.scalajs.dom
import org.scalajs.dom.html.Paragraph

object DomTests extends TestSuite{
  def tests = TestSuite{
    test("basic"){
      import scalatags.JsDom.all._
      test("children"){
        val elem = div.render
        assert(elem.children.length == 0)
        elem.appendChild(p(1, "wtf", "bbq").render)
        assert(elem.children.length == 1)
        val pElem = elem.children(0).asInstanceOf[Paragraph]
        assert(pElem.childNodes.length == 3)
        assert(pElem.textContent == "1wtfbbq")
      }

      test("attributes"){
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

      test("styles"){
        val elem = div(
          color := "red",
          float.left,
          backgroundColor := "yellow"
        ).render
        assert(elem.style.color == "red")
        assert(elem.style.cssFloat == "left")
        assert(elem.style.backgroundColor == "yellow")
        // styles end up being sorted in alphabetical order
        val styleAttr = elem.getAttribute("style")
        assert(
          styleAttr.trim == "color: red; float: left; background-color: yellow;"
        )
      }
    }
    test("fancy"){
      import scalatags.JsDom.all._
      test("fragSeqsAreFrags"){
        val rendered = Seq(
          h1("titless"),
          div("lol")
        ).render

        val wrapped = div(rendered).toString
        assert(wrapped == "<div><h1>titless</h1><div>lol</div></div>")
      }
      test("boundAttributes"){
        var count = 0
        val elem = div(
          onclick := { () => count += 1},
          tabindex := 1
        ).render

        assert(count == 0)
        elem.onclick(null)
        assert(count == 1)
      }
      test("triggers"){
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
    }
    test("tagType"){
      import scalatags.JsDom.all._
      val thing: Tag = div
    }

    test("crossTag"){
      class SharedTemplates[FragT, Output <: FragT](val bundle: scalatags.generic.Bundle[Output, FragT]){
        import bundle._, bundle.all._
        val widget: Tag = div("hello").asInstanceOf[TypedTag[Output]]
      }

      object JsTemplates extends SharedTemplates(scalatags.JsDom)
      object TextTemplates extends SharedTemplates(scalatags.Text)

      val jsVersion: dom.Element = JsTemplates.widget.render
      val txtVersion: String = TextTemplates.widget.render

      assert(
        jsVersion.tagName.toLowerCase == "div",
        jsVersion.textContent == "hello",
        txtVersion == "<div>hello</div>"
      )
    }
  }

}
