package scalatags
import acyclic.file
import utest.framework.TestSuite
import utest._


import TestUtil._
import scala.collection.SortedMap
import scalatags.generic.Style

object BasicTests extends TestSuite{

  import scalatags.Text._
  import all._
  def tests = TestSuite{

    'helloWorld{
      val sample = div("omg")
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

    'cssHelpers{

      assert(10.px == "10px")
      assert(10.0.px == "10.0px" || 10.0.px == "10px")
      assert(10.em == "10em")
      assert(10.pt == "10pt")
    }

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
      val expected = SortedMap[Style, StyleVal](float -> "left", height -> "10px")
      val styleList = frag.styles
      assert(styleList.toString == expected.toString)
    }
  }
}
