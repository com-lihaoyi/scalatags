package scalatags

import utest._
import jsdom.all._
import jsdom._
import org.scalajs.dom

object JsDomTests extends TestSuite{

  val tests = TestSuite{
    'helloWorld{
      val tag: jsdom.TypedHtmlTag[dom.HTMLAnchorElement] = a("Hello", href := "https://www.google.com")
      val elem = tag.toDom
      assert(
        elem.isInstanceOf[dom.HTMLAnchorElement],
        elem.href == "https://www.google.com/",
        elem.outerHTML == """<a href="https://www.google.com">Hello</a>""",
        elem.innerHTML == "Hello"
      )
    }
  }
}