package scalatags
import all._
import utest._
import StyleSheets._

object StyleSheetTests extends TestSuite{
  case class RuleSet(selector: String, rules: Seq[StylePair])
  implicit def tupleable2(t: Tuple2[HtmlTag, HtmlTag]) = t._1
  implicit def tupleable3(t: Tuple3[HtmlTag, HtmlTag, HtmlTag]) = t._1
  val tests = TestSuite{
    def cls(s: StylePair*) = ???
    class StyleSheet{
      val editor = cls(
        margin := "0",
        position.absolute,
        top := 0.px,
        bottom := 20.pct,
        left := 0.px,
        right := 50.pct,
        backgroundColor := rgb(20, 20, 20),
        boxSizing.`border-box`,
        paddingLeft := 3.px
      )
      val logspam = cls(
        margin := "0",
        position.absolute,
        top := 80.pct,
        bottom := "0",
        left := "0",
        right := "0",
        backgroundColor := "#222222",
        color.white,
        overflow.auto,
        boxSizing.`border-box`,
        padding := "2px 4px"
      )
      val sandbox = cls(
        margin := "0",
        position.absolute,
        top := "0",
        bottom := 20.pct,
        left := 50.pct,
        right := 0.px,
        backgroundColor := "black"
      )
      val output = cls(
        position.absolute,
        width := 100.pct,
        maxHeight := 100.pct,
        overflow.auto
      )
      (body, pre) (
        overflow.hidden,
        backgroundColor := "#222222",
        color := "#eeeeee",
        fontSize := 12.px,
        fontFamily := "Monaco, Menlo, 'Ubuntu Mono', Consolas, source-code-pro, monospace"
      )

      a.link(
        color := "#aaffff"
      )

      a.visited(
        color := "#ffaaff"
      )

      val spinnerHolder = cls(
        display.table
      )

      (html, body, spinnerHolder)(
        height := 100.pct,
        width := 100.pct
      )

      val aceLine = cls(
        whiteSpace.nowrap
      )
    }

    val expected = """
      |#editor {
      |    margin: 0;
      |    position: absolute;
      |    top: 0;
      |    bottom: 20%;
      |    left: 0;
      |    right: 50%;
      |    background-color: rgb(20, 20, 20);
      |    box-sizing: border-box;
      |    padding-left: 3px;
      |}
      |#logspam{
      |    margin: 0;
      |    position: absolute;
      |    top: 80%;
      |    bottom: 0;
      |    left: 0;
      |    right: 0;
      |    background-color: #222222;
      |    color: white;
      |    overflow: auto;
      |    box-sizing: border-box;
      |    padding: 2px 4px;
      |}
      |#sandbox {
      |    margin: 0;
      |    position: absolute;
      |    top: 0;
      |    bottom: 20%;
      |    left: 50%;
      |    right: 0;
      |    background-color: black;
      |}
      |#output{
      |    position: absolute;
      |    width: 100%;
      |    max-height: 100%;
      |    overflow: auto;
      |}
      |
      |body, pre {
      |    overflow: hidden;
      |    background-color: #222222;
      |    color: #eeeeee;
      |    font-size: 12px;
      |    font-family: Monaco, Menlo, 'Ubuntu Mono', Consolas, source-code-pro, monospace;
      |}
      |
      |a:link{
      |    color:#aaffff;
      |}
      |
      |a:visited{
      |    color:#ffaaff;
      |}
      |
      |html, body, #spinner-holder{
      |    height: 100%;
      |    width: 100%;
      |}
      |#spinner-holder{
      |    display: table;
      |}
      |.ace-line{
      |    white-space: nowrap;
      |}
    """.stripMargin
  }
}
