package scalatags

import util.Random

/**
 * Misc helper functions
 */
private[scalatags] trait Misc{
  /**
   * Shorthand to generate a <script type="text/javascript" src="..."></script> tag
   */
  def javascript(origin: Any = "") =
    script.attr("type" -> "text/javascript").src(origin)("")

  /**
   * Shorthand to generate a <style rel="stylesheet" type="text/css" href="..."></style> tag
   */
  def stylesheet(origin: Any = "") =
    link.rel("stylesheet").ctype("text/css").href(origin)

}
