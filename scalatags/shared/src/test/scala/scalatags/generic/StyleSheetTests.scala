package scalatags.generic
import utest._

abstract class StyleSheetTests[Builder, Output <: FragT, FragT]
                     (bundle: Bundle[Builder, Output, FragT])  extends TestSuite{
  import bundle.all._
  def pkg: String

  def Simple: StyleSheet[Builder] {
    def x: Cls[Builder]
    def y: Cls[Builder]
    def z: Cls[Builder]
  }

  def Inline: StyleSheet[Builder] {
    def w: Cls[Builder]
  }
  def Cascade: StyleSheet[Builder] {
    def x: Cls[Builder]
    def y: Cls[Builder]
    def z: Cls[Builder]
  }

  val tests = TestSuite{
    'failures{
      compileError("""object X extends StyleSheet{val cls = *(div)}""")
    }
    'basic{
      'hello{
        val txt = Simple.styleSheetText
        assert(txt ==
          s""".scalatags-$pkg-StyleSheetTests-Simple--0 {
            |  background-color: red;
            |  height: 125px;
            |}
            |.scalatags-$pkg-StyleSheetTests-Simple--1:hover {
            |  opacity: 0.5;
            |}
            |""".stripMargin)
      }
      'combinations{
        assert(Simple.x.classes ++ Simple.y.classes subsetOf Simple.z.classes )
      }
    }
    'inline{
      val txt = Inline.styleSheetText
      assert(txt ==
        s""".scalatags-$pkg-StyleSheetTests-Inline--0:hover {
          |  background-color: red;
          |}
          |.scalatags-$pkg-StyleSheetTests-Inline--1 {
          |  opacity: 0.5;
          |}
          |""".stripMargin)
      assert(Inline.w.classes == Set(
        s"scalatags-$pkg-StyleSheetTests-Inline--0",
        s"scalatags-$pkg-StyleSheetTests-Inline--1"
      ))
    }
    'cascade{
      val txt = Cascade.styleSheetText
      assert(txt ==
        s""".scalatags-$pkg-StyleSheetTests-Cascade--0 a {
          |  background-color: red;
          |  text-decoration: none;
          |}
          |.scalatags-$pkg-StyleSheetTests-Cascade--1 a:hover {
          |  background-color: blue;
          |  text-decoration: underline;
          |}
          |.scalatags-$pkg-StyleSheetTests-Cascade--2 a:hover div {
          |  opacity: 0;
          |}
          |""".stripMargin)
    }
  }
}
