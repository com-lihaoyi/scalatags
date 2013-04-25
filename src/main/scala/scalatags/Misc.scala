package scalatags

import xml.Unparsed
import util.Random

/**
 * Misc helper functions
 */
trait Misc{
  /**
   * Shorthand to generate a <script type="text/javascript" src="..."></script> tag
   */
  def javascript(origin: String = "") =
    script.attr("type" -> "text/javascript").src(origin)("")

  /**
   * Shorthand to generate a <style rel="stylesheet" type="text/css" href="..."></style> tag
   */
  def stylesheet(origin: String = "") =
    link.rel("stylesheet").ctype("text/css").href(origin)

  /**
   * Shorthand syntax for creating CSS strings from integers
   */
  implicit class cssNum(n: Int){
    def px = n + "px"
    def em = n + "em"
    def pct = n + "%"
    def pt = n + "pt"
  }

  /**
   * Shorthand syntax for creating css "rgba(r, g, b, a)" strings
   */
  def rgba(r: Int, g: Int, b: Int, a: Double) = {
    s"rgba($r, $g, $b, $a)"
  }

}
