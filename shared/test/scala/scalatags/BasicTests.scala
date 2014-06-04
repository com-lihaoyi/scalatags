package scalatags
import acyclic.file
import utest.framework.TestSuite
import utest._
import scalatags.text.all
import scalatags.text._
import all._
import TestUtil._
import scala.collection.SortedMap
import scalatags.generic.{StyleVal, Style}

object BasicTests extends TestSuite{
  def tests = TestSuite{
    'basics{
      'helloWorld{
        val sample = div("omg")
        println(sample.children)
        println(sample.attrs)
        assert(sample.toString == "<div>omg</div>")
      }
      /**
       * Tests the usage of the pre-defined tags, as well as creating
       * the tags on the fly from Strings
       */
      'tagCreation{
        assert(a.toString == "<a></a>")
        assert(html.toString == "<html></html>")
        assert("this_is_an_unusual_tag".tag.toString == "<this_is_an_unusual_tag></this_is_an_unusual_tag>")
        assert("this-is-a-string-with-dashes".voidTag.toString == "<this-is-a-string-with-dashes />")
      }

      /**
       * Tests nesting tags in a simple hierarchy
       */
      'cssChaining-{
        val x = script("")
        strCheck(
          html(
            head(
              x,
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
      }


      'cssChaining2-strCheck(
        div(
          float.left,
          color:="red"
        ),
        """<div style="color: red; float: left;"></div>"""
      )


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
        """<div id="cow" style="float: left;"><p>i am a cow</p></div>"""
      )

      'cssHelpers{

        assert(10.px == "10px")
        assert(10.0.px == "10.0px")
        assert(10.em == "10em")
        assert(10.pt == "10pt")
      }

      'classStyleAttrOverwriting-strCheck(
        //class/style after attr appends, but attr after class/style overwrites
        div(
          float.left,
          style:="background-color: red;",
          cls:="my-class",
          p("i am a cow")
        ),
        """<div class="my-class" style="background-color: red; float: left;"><p>i am a cow</p></div>"""
      )

      'styleConvertsThingsToCamelcase{
        assert("i-am-a-cow".style.jsName == "iAmACow")
      }
      'styleListIsGenerated{
        val frag = div(
          style:="background-color: red;",
          float.left,
          height:=10.px
        )
        // "background-color" -> "red" is stored in `attrs` not `styles`
        val expected = SortedMap[Style, StyleVal[StringBuilder]](float -> "left", height -> "10px")
        val styleList = frag.styles
        assert(styleList.toString == expected.toString)
      }
      'intSeq-strCheck(
        div(
          h1("Hello"),
          for(i <- 0 until 5) yield i
        ),
        """<div><h1>Hello</h1>01234</div>"""
      )
      'stringArray{
        val strArr = Array("hello")
        strCheck(
          div(
            Some("lol"),
            Some(1),
            None: Option[String],
            h1("Hello"),
            Array(1, 2, 3),
            strArr,
            ()
          ),
          """<div>lol1<h1>Hello</h1>123hello</div>"""
        )
      }
    }
  }

}
