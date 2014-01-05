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
    assert("this_is_an_unusual_tag".x.toString === "<this_is_an_unusual_tag />")
    assert("this-is-a-string-with-dashes".x.toString === "<this-is-a-string-with-dashes />")
  }

  /**
   * Tests nesting tags in a simple hierarchy
   */
  "structured tags" in strCheck(
    html(
      head(
        script(""),
        "string-tag".x
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
      color->"red"
    ),
    """<div style="color: red; float: left;" />"""
  )


  "attribute chaining" in strCheck(
    div(
      id->"cow",
      `class`->"thing lol"
    ),
    """<div class="thing lol" id="cow" />"""
  )


  "mixing attributes and styles and children" in strCheck(
    div(
      id->"cow",
      float.left,
      p("i am a cow")
    ),
    """<div id="cow" style="float: left;"><p>i am a cow</p></div>"""
  )

  "css helpers tests" in {
    assert(10.px == "10px")
    assert(10.em == "10em")
    assert(10.pt == "10pt")
  }
}
