package scalatags
import acyclic.file
/**
 * Utility methods related to validating and escaping XML; used internally but
 * potentially useful outside of Scalatags.
 */
object Escaping {

  private[this] val tagRegex = "^[a-z][\\w0-9-]*$".r

  /**
   * Uses a regex to check if something is a valid tag name.
   */
  def validTag(s: String) = tagRegex.unapplySeq(s).isDefined

  /**
   * Check if 's' is a valid attribute name.
   */
  def validAttrName(s: String): Boolean = {
    // this is equivalent of the regex but without a huge amount of object creation.
    // original regex - ^[a-zA-Z_:][-a-zA-Z0-9_:.]*$
    // n.b. I know its ugly, but its fast
    val len = s.length
    if(len == 0)
      return false

    val sc = s.charAt(0)
    val startCharValid = (sc >= 'a' && sc <='z') || (sc >= 'A' && sc <='Z') || sc ==':'
    if(!startCharValid)
      return false

    var pos = 1
    while (pos < len) {
      val c = s.charAt(pos)
      val valid = (c >= 'a' && c <='z') || (c >= 'A' && c <='Z') || (c >= '0' && c <='9') ||
        c == '-' || c ==':' || c =='.' || c == '_'
      if(!valid)
        return false
      pos += 1
    }

    true
  }

  /**
   * Code to escape text HTML nodes. Taken from scala.xml
   */
  def escape(text: String, s: StringBuilder) = {
    // Implemented per XML spec:
    // http://www.w3.org/International/questions/qa-controls
    // imperative code 3x-4x faster than current implementation
    // dpp (David Pollak) 2010/02/03
    val len = text.length
    var pos = 0

    while (pos < len) {
      text.charAt(pos) match {
        case '<' => s.append("&lt;")
        case '>' => s.append("&gt;")
        case '&' => s.append("&amp;")
        case '"' => s.append("&quot;")
        case '\n' => s.append('\n')
        case '\r' => s.append('\r')
        case '\t' => s.append('\t')
        case c if c < ' ' =>
        case c => s.append(c)
      }
      pos += 1
    }
  }
}
