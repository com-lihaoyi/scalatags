package scalatags
package generic

import acyclic.file
import utest.framework.TestSuite
import utest._

import TestUtil._


class BasicTests[Builder, Output <: FragT, FragT](omg: Bundle[Builder, Output, FragT]) extends TestSuite{
  import omg.all._
  private[this] type Omg = Attrs
  val tests = TestSuite{

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
      """<div style="float: left; color: red;"></div>"""
    )


    'attributeChaining-strCheck(
      div(
        `class`:="thing lol",
        id:="cow"
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

    'classStyleAttrOverwriting-{
      strCheck(
        //class/style after attr appends, but attr after class/style overwrites
        div(
          cls:="my-class",
          style:="background-color: red;",
          float.left,
          p("i am a cow")
        ),
        """<div class="my-class" style="background-color: red; float: left;"><p>i am a cow</p></div>"""
      )
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
    'applyChaining{
      strCheck(
        a(
          tabindex := 1,
          onclick := "lol"
        )(
          href := "boo",
          alt := "g"
        ),
        """<a tabindex="1" onclick="lol" href="boo" alt="g"></a>"""
      )
    }
    'autoPixel{
      strCheck(
        div(width:=100, zIndex:=100, height:=100),
        """
          |<div style="width: 100px; z-index: 100; height: 100px;"></div>
        """.stripMargin
      )
    }
    'compileErrors{
      'niceErrorsForAttributes{
        val msg = compileError("""a(onclick := {() => "lol"})""").msg
        assert(msg.contains("scalatags does not know how to use () => String as an attribute"))
      }
      'niceErrorsForStyles{
        val msg = compileError("""a(opacity:= {() => "lol"})""").msg
        assert(msg.contains("scalatags does not know how to use () => String as an style"))
      }
    }
    'nulls{
      val nullString: String = null
      * - intercept[NullPointerException](div(nullString))
      * - intercept[NullPointerException](div(null: Seq[Int]))
      * - intercept[NullPointerException](div(height := nullString))
      * - intercept[NullPointerException](div(opacity := nullString))
    }

  }
}

