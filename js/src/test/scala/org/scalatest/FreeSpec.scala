package org.scalatest
import scala.scalajs.test.JasmineTest

class FreeSpec extends JasmineTest {
  implicit class SuperString(s: String){
    def in(thunk: => Unit) = {
      it(s)(thunk)
    }
    def -(thunk: => Unit) = {
      describe(s)(thunk)
    }
  }
}