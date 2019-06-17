package scalatags
package generic

import utest._

import TestUtil._


class BasicTests[Builder, Output <: FragT, FragT](omg: Bundle[Builder, Output, FragT]) extends TestSuite{
  import omg.all._
  private[this] type Omg = Attrs
  val tests = TestSuite{

    /**
     * Tests nesting tags in a simple hierarchy
     */
    test("cssChaining"){
      val x = script("")
      strCheck(
        html(
          head(
            x,
            tag("string-tag")
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
    test("cssChaining2") - strCheck(
      div(
        float.left,
        color:="red"
      ),
      """<div style="float: left; color: red;"></div>"""
    )


    test("attributeChaining") - strCheck(
      div(
        `class`:="thing lol",
        id:="cow"
      ),
      """<div class="thing lol" id="cow"></div>"""
    )


    test("mixingAttributesStylesAndChildren") - strCheck(
      div(
        id:="cow",
        float.left,
        p("i am a cow")
      ),
      """<div id="cow" style="float: left;"><p>i am a cow</p></div>"""
    )

    test("classStyleAttrOverwriting"){
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

    test("intSeq") - strCheck(
      div(
        h1("Hello"),
        for(i <- 0 until 5) yield i
      ),
      """<div><h1>Hello</h1>01234</div>"""
    )

    test("stringArray"){
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
    test("applyChaining"){
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
    test("autoPixel"){
      strCheck(
        div(width:=100, zIndex:=100, height:=100),
        """
          |<div style="width: 100px; z-index: 100; height: 100px;"></div>
        """.stripMargin
      )
    }
    test("compileErrors"){
      test("niceErrorsForAttributes"){
        val msg = compileError("""a(onclick := {() => "lol"})""").msg
        assert(msg.contains("scalatags does not know how to use () => String as an attribute"))
      }
      test("niceErrorsForStyles"){
        val msg = compileError("""a(opacity:= {() => "lol"})""").msg
        assert(msg.contains("scalatags does not know how to use () => String as an style"))
      }
    }
    test("nulls"){
      val nullString: String = null
      * - intercept[NullPointerException](div(nullString))
      * - intercept[NullPointerException](div(null: Seq[Int]))
      * - intercept[NullPointerException](div(height := nullString))
      * - intercept[NullPointerException](div(opacity := nullString))
    }
    test("rawAttrs"){
      strCheck(
        button(
          attr("[class.active]", raw = true):= "isActive",
          attr("(click)", raw = true) := "myEvent()"
        ),
        """<button [class.active]="isActive" (click)="myEvent()"></button>"""

      )
    }
    test("specialChars"){
      * - intercept[java.lang.IllegalArgumentException](div(attr("[(ngModel)]") := "myModel"))
    }

  }
}

