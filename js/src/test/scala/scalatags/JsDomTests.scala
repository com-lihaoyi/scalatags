package scalatags

import utest._
import jsdom.all._
import jsdom._
import org.scalajs.dom
import TestUtil.strCheck

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

    'cssChaining2-{

      val elem = div(
        float.left,
        color:="red"
      ).toDom.asInstanceOf[dom.HTMLElement]

      assert(elem.style.color == "red")
      assert(elem.style.cssFloat == "left")
      TestUtil.strCheck(
        elem.outerHTML,
        """<div style="color: red; float: left; "></div>"""
      )
    }


    'attributeChaining-strCheck(
      div(
        id:="cow",
        `class`:="thing lol"
      ),
      """<div class="thing lol" id="cow"></div>"""
    )


    'mixingAttributesStylesAndChildren-strCheck(
      div(
        id:="cow",
        float.left,
        p("i am a cow")
      ),
      """<div id="cow" style="float: left; "><p>i am a cow</p></div>"""
    )
  }
}