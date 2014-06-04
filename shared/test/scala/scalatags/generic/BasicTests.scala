package scalatags
package generic

import acyclic.file
import utest.framework.TestSuite
import utest._


import TestUtil._
import scala.collection.SortedMap
import scalatags.generic.{StyleVal, Style}

class BasicTests[T](v: generic.Attrs[T] with generic.Styles[T] with generic.Tags[T],
                    omg: Omg[T]) extends TestSuite{
  import v._
  import omg._
  val tests = TestSuite{
    'cssChaining2-strCheck(
      div(
        float.left,
        color:="red"
      ),
      """<div style="color: red; float: left;"></div>"""
    )

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
