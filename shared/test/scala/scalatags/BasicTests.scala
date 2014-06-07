package scalatags
import acyclic.file
import utest.framework.TestSuite
import utest._


import TestUtil._
import scala.collection.SortedMap


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
  }
}
