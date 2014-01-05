package scalatags

import org.scalatest._
import Util._

class BasicTests extends FreeSpec{


  /**
   * Tests the usage of the pre-defined tags, as well as creating
   * the tags on the fly from Symbols and Strings
   */
  "basic tag creation" in {
    assert(a.toString === "<a/>")
    assert(html.toString === "<html/>")
    assert("this_is_an_unusual_tag".x.toString === "<this_is_an_unusual_tag/>")
    assert("this-is-a-string-with-dashes".x.toString === "<this-is-a-string-with-dashes/>")
  }

  /**
   * Tests nesting tags in a simple hierarchy
   */
  "structured tags" in {
    strCheck(
      html(
        head(
          script(),
          "string-tag".x()
        ),
        body(
          div(
            p()
          )
        )
      ), """<html>
              <head>
                <script/>
                <string-tag/>
              </head>
              <body>
                <div>
                  <p/>
                </div>
              </body>
            </html>"""
    )
  }


  "css chaining" in {
    strCheck(
      div.fL.color("red"), "<div style=\"float:left;color:red;\"/>"
    )
  }
}
