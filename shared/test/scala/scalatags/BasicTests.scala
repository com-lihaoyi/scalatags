package scalatags

import utest.framework.TestSuite
import utest._
import scalatags.all._
import Util._
object BasicTests extends TestSuite{
  def tests = TestSuite{
    "basics" - {
      /**
       * Tests the usage of the pre-defined tags, as well as creating
       * the tags on the fly from Strings
       */

      "tagCreation" - {
        assert(a.toString == "<a></a>")
        assert(html.toString == "<html></html>")
        assert("this_is_an_unusual_tag".tag.toString == "<this_is_an_unusual_tag></this_is_an_unusual_tag>")
        assert("this-is-a-string-with-dashes".voidTag.toString == "<this-is-a-string-with-dashes />")
      }

      /**
       * Tests nesting tags in a simple hierarchy
       */
      "structuredTags" - strCheck(
        html(
          head(
            script(""),
            "string-tag".tag
          ),
          body(
            div(
              p
            )
          )
        ),
        """
        <html>
            <head>
                <script></script>
                <string-tag></string-tag>
            </head>
            <body>
                <div>
                    <p></p>
                </div>
            </body>
        </html>
        """
      )


      "cssChaining" - strCheck(
        div(
          float.left,
          color:="red"
        ),
        """<div style="float: left; color: red;"></div>"""
      )


      "attributeChaining" - strCheck(
        div(
          id:="cow",
          `class`:="thing lol"
        ),
        """<div class="thing lol" id="cow"></div>"""
      )


      "mixingAttributesStylesAndChildren" - strCheck(
        div(
          id:="cow",
          float.left,
          p("i am a cow")
        ),
        """<div id="cow" style="float: left;"><p>i am a cow</p></div>"""
      )

      "cssHelpers" - {

        assert(10.px.toString == "10px")
        assert(10.0.px.toString == "10px")
        assert(10.em.toString == "10em")
        assert(10.pt.toString == "10pt")
      }

      "classStyleAttrOverwriting" - strCheck(
        //class/style after attr appends, but attr after class/style overwrites
        div(
          float.left,
          style:="background-color: red;",
          `class`:="my-class",
          "other-class".cls,
          p("i am a cow")
        ),
        """<div class="my-class other-class" style="background-color: red;"><p>i am a cow</p></div>"""
      )

      "styleConvertsThingsToCamelcase" - {
        assert("i-am-a-cow".style.jsName == "iAmACow")
      }

      "classesListIsGenerated" - {
        val frag = div(
          `class`:="my-class",
          "other-class".cls
        )
        assert(frag.classes == Seq("my-class", "other-class"))
      }
      "styleListIsGenerated" - {
        val frag = div(
          style:="background-color: red;",
          float.left,
          height:=10.px
        )
        assert(frag.styles == Map("background-color" -> "red", "float" -> "left", "height" -> "10px"))
      }
      "stringSeq" - strCheck(
        div(
          h1("Hello"),
          for(i <- 0 until 5) yield "" + i
        ),
        """<div><h1>Hello</h1>01234</div>"""
      )
    }
  }

}
