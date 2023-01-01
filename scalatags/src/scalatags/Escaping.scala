package scalatags

/**
 * Utility methods related to validating and escaping XML; used internally but
 * potentially useful outside of Scalatags.
 */
object Escaping {

  private[this] val tagRegex = "^[a-z][:\\w0-9-]*$".r

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
    if (len == 0)
      return false

    val sc = s.charAt(0)
    val startCharValid = (sc >= 'a' && sc <= 'z') || (sc >= 'A' && sc <= 'Z') || sc == ':'
    if (!startCharValid)
      return false

    var pos = 1
    while (pos < len) {
      val c = s.charAt(pos)
      val valid = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') ||
        c == '-' || c == ':' || c == '.' || c == '_'
      if (!valid)
        return false
      pos += 1
    }

    true
  }

  /**
   * Code to escape text HTML nodes. Based on code from scala.xml
   */
  def escape(text: String, s: java.io.Writer) = {
    // Implemented per XML spec:
    // http://www.w3.org/International/questions/qa-controls
    // Highly imperative code, ~2-3x faster than the previous implementation (2020-06-11)
    val charsArray = text.toCharArray
    val len = charsArray.size
    var pos = 0
    var i = 0
    while (i < len) {
      val c = charsArray(i)
      c match {
        case '<' =>
          s.write(charsArray, pos, i - pos)
          s.write("&lt;")
          pos = i + 1
        case '>' =>
          s.write(charsArray, pos, i - pos)
          s.write("&gt;")
          pos = i + 1
        case '&' =>
          s.write(charsArray, pos, i - pos)
          s.write("&amp;")
          pos = i + 1
        case '"' =>
          s.write(charsArray, pos, i - pos)
          s.write("&quot;")
          pos = i + 1
        case '\n' =>
        case '\r' =>
        case '\t' =>
        case c if c < ' ' =>
          s.write(charsArray, pos, i - pos)
          pos = i + 1
        case _ =>
      }
      i += 1
    }
    // Apparently this isn't technically necessary if (len - pos) == 0 as
    // it doesn't cause any exception to occur in the JVM.
    // The problem is that it isn't documented anywhere so I left this if here
    // to make the error clear.
    if (pos < len) {
      s.write(charsArray, pos, len - pos)
    }
  }
}
