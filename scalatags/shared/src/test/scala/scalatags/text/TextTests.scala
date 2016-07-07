package scalatags.text
import utest._

object TextTests extends TestSuite{

  import scalatags.Text.all._
  def tests = TestSuite{

    'helloWorld{
      val sample = div("omg")
      assert(sample.toString == "<div>omg</div>")
    }

    'helloWorld_indent{
      val sample = div("omg").render(2)
      val expected = "<div>omg</div>"
      assert(sample == expected)
    }

    'nested_indent{
      val sample = div(span("omg")).render(2)
      val expected =
        """<div>
          |  <span>omg</span>
          |</div>""".stripMargin
      assert(sample == expected)
    }

    'voidTag_indent{
      val sample = "void".voidTag[String].render(2)
      val expected = "<void />"
      assert(sample == expected)
    }

    'tag_indent{
      val sample = "tag".tag[String].render(2)
      val expected = "<tag></tag>"
      assert(sample == expected)
    }

    'aList_indent{
      val sample = ol(
        `class` := "myList",
        li("one"),
        li(float.right),
        li(
          span(3)
        )
      ).render(2)
      val expected =
        """<ol class="myList">
          |  <li>one</li>
          |  <li style="float: right;"></li>
          |  <li>
          |    <span>3</span>
          |  </li>
          |</ol>""".stripMargin
      assert(sample == expected)
    }

    'multiSeq_indent{
      val sample = span("one", "two", "three").render(2)
      val expected = "<span>onetwothree</span>"
      assert(sample == expected)
    }

    'mixedContent_indent{
      val sample = div("one", span("two"), "three", span(span("four")), "five").render(2)
      val expected =
        """<div>one<span>two</span>three<span>
          |    <span>four</span>
          |  </span>five</div>""".stripMargin
      assert(sample == expected)
    }

    'whitespaceContent_indent{
      val sample = span("one", " and ", "two\n").render(2)
      val expected =
        """<span>one and two
          |</span>""".stripMargin
      assert(sample == expected)
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
    'fragSeqsAreFrags{
      val frag1: Frag = Seq(
        h1("titless"),
        div("lol")
      )
      val frag2: Frag = Seq("i", "am", "cow")
      val frag3: Frag = Seq(frag1, frag2)
      val wrapped = div(frag1, frag2, frag3).toString
      assert(wrapped == "<div><h1>titless</h1><div>lol</div>iamcow<h1>titless</h1><div>lol</div>iamcow</div>")
    }
    'tagType{
      import scalatags.Text.all._
      val thing: Tag = div
    }

  }
}
