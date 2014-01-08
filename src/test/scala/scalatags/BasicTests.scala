package scalatags

import org.scalatest._
import Util._

class BasicTests extends FreeSpec{


  /**
   * Tests the usage of the pre-defined tags, as well as creating
   * the tags on the fly from Symbols and Strings
   */
  "basic tag creation" in {
    assert(a.toString === "<a />")
    assert(html.toString === "<html />")
    assert("this_is_an_unusual_tag".tag.toString === "<this_is_an_unusual_tag />")
    assert("this-is-a-string-with-dashes".tag.toString === "<this-is-a-string-with-dashes />")
  }

  /**
   * Tests nesting tags in a simple hierarchy
   */
  "structured tags" in strCheck(
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
            <string-tag />
        </head>
        <body>
            <div>
                <p />
            </div>
        </body>
    </html>
    """
  )


  "css chaining" in strCheck(
    div(
      float.left,
      color~="red"
    ),
    """<div style="float: left; color: red;" />"""
  )


  "attribute chaining" in strCheck(
    div(
      id:="cow",
      `class`:="thing lol"
    ),
    """<div class="thing lol" id="cow" />"""
  )


  "mixing attributes and styles and children" in strCheck(
    div(
      id:="cow",
      float.left,
      p("i am a cow")
    ),
    """<div id="cow" style="float: left;"><p>i am a cow</p></div>"""
  )

  "css helpers tests" in {

    assert(10.px.toString == "10px")
    assert(10.0.px.toString == "10px")
    assert(10.em.toString == "10em")
    assert(10.pt.toString == "10pt")
  }

  "class/style after attr appends, but attr after class/style overwrites" in strCheck(
    div(
      float.left,
      style:="background-color: red;",
      `class`:="my-class",
      "other-class".cls,
      p("i am a cow")
    ),
    """<div class="my-class other-class" style="background-color: red;"><p>i am a cow</p></div>"""
  )

  ".style converts things to camelcase" in {
    assert("i-am-a-cow".style.jsName == "iAmACow")
  }

  "list of classes is generated properly" in {
    val frag = div(
      `class`:="my-class",
      "other-class".cls
    )
    assert(frag.classes == Seq("my-class", "other-class"))
  }
  "list of styles is generated properly" in {
    val frag = div(
      style:="background-color: red;",
      float.left,
      height:=10.px
    )
    assert(frag.styles == Seq("background-color" -> "red", "float" -> "left", "height" -> "10px"))
  }
  "Seq of strings" in strCheck(
    div(
      h1("Hello"),
      for(i <- 0 until 5) yield "" + i
    ),
    """<div><h1>Hello</h1>01234</div>"""
  )



}
