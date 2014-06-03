package scalatags

import utest.framework.TestSuite
import org.scalajs.dom._
import all._

object BindingTests extends TestSuite{

  val tests = TestSuite{
    // This stuff should compiled, just to show that we can
    // indeed perform casts based on the type of a TypedHtmlTag.
    def func[T <: Platform.Base](tag: TypedHtmlTag[T]): T = ???

    () => func(a): HTMLAnchorElement
    () => func(div): HTMLDivElement
    () => func(input): HTMLInputElement

  }
}
