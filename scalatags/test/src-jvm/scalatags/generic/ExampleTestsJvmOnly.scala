package scalatags
package generic
import utest._

import TestUtil.strCheck

class ExampleTestsJvmOnly[Builder, Output <: FragT, FragT](bundle: Bundle[Builder, Output, FragT]) extends TestSuite{
  val tests = Tests {
    import bundle.all._
    test("properEscaping") - strCheck({
      val evilInput1 = "\"><script>alert('hello!')</script>"
      val evilInput2 = "<script>alert('hello!')</script>"

      html(
        head(
          script("some script")
        ),
        body(
          h1(
            title:=evilInput1,
            "This is my title"
          ),
          evilInput2
        )
      )

    }
    ,
    """
        <html>
            <head>
                <script>some script</script>
            </head>
            <body>
                <h1 title="&quot;&gt;&lt;script&gt;alert('hello!')&lt;/script&gt;">
                    This is my title
                </h1>
                &lt;script&gt;alert('hello!')&lt;/script&gt;
            </body>
        </html>
    """
    )
  }
}
