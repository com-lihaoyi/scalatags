package scalatags
import collection.mutable


/**
 * Misc helper functions
 */
private[scalatags] trait Misc extends Attributes{
  /**
   * Shorthand to generate a <script type="text/javascript" src="..."></script> tag
   */
  def javascript(origin: String = "") =
    script(`type`:="text/javascript", src:=origin)

  /**
   * Shorthand to generate a <style rel="stylesheet" type="text/css" href="..."></style> tag
   */
  def stylesheet(origin: String = "") =
    link(rel:="stylesheet", `type`:="text/css", href:=origin)
}
