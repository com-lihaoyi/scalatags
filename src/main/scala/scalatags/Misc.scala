package scalatags

import xml.Unparsed
import util.Random

trait Misc {this: ScalaTags =>


  def javascript(origin: String = "") =
    script.attr("type" -> "text/javascript").src(origin)("")

  def stylesheet(origin: String = "") =
    link.rel("stylesheet").ctype("text/css").href(origin)

  def js(contents: Any, id: String = ""): HtmlTag ={
    script.attr("type" -> "text/javascript")(
      Raw(
        contents,
        x => s"(function(self){$x})($id);"
      )
    )
  }
  implicit class pimpedSTag(s: HtmlTag){
    def withJs(contents: Any) = {
      val uuid = genUUID
      Seq(s.cls(uuid), js(contents, id = s"$$('.$uuid')"))
    }
  }
  implicit class cssNum(n: Int){
    def px = n + "px"
    def em = n + "em"
    def pct = n + "%"
    def pt = n + "pt"
  }
}
