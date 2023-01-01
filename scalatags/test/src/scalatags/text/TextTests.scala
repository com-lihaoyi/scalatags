package scalatags.text
import utest._

object TextTests extends TestSuite {

  import scalatags.Text.all._
  def tests = TestSuite {

    test("helloWorld") {
      val sample = div("omg")
      assert(sample.toString == "<div>omg</div>")
    }

    /**
     * Tests the usage of the pre-defined tags, as well as creating
     * the tags on the fly from Strings
     */
    test("tagCreation") {
      assert(a.toString == "<a></a>")
      assert(html.toString == "<html></html>")
      assert(tag(
        "this_is_an_unusual_tag"
      ).toString == "<this_is_an_unusual_tag></this_is_an_unusual_tag>")
      assert(tag(
        "this-is-a-string-with-dashes",
        void = true
      ).toString == "<this-is-a-string-with-dashes />")
    }

    test("cssHelpers") {
      assert(10.px == "10px")
      assert(10.0.px == "10.0px" || 10.0.px == "10px")
      assert(10.em == "10em")
      assert(10.pt == "10pt")
    }
    test("fragSeqsAreFrags") {
      val frag1: Frag = Seq(
        h1("titless"),
        div("lol")
      )
      val frag2: Frag = Seq("i", "am", "cow")
      val frag3: Frag = Seq(frag1, frag2)
      val wrapped = div(frag1, frag2, frag3).toString
      assert(
        wrapped == "<div><h1>titless</h1><div>lol</div>iamcow<h1>titless</h1><div>lol</div>iamcow</div>"
      )
    }
    test("tagType") {
      import scalatags.Text.all._
      val thing: Tag = div
    }
    test("namespaced") {
      val sample = tag("abc:def")(attr("hello:world") := "moo")
      assert(sample.toString == """<abc:def hello:world="moo"></abc:def>""")
    }
    test("doctype") {
      // Just make sure that this compiles.
      val example = doctype("html")(html())
    }
  }
}
