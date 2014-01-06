package scalatags

/**
 * Utility methods related to validating and escaping XML; used internally but
 * potentially useful outside of scalatags aswell.
 */
object Escaping {

  val tagRegex = "\\A(?!XML)[a-z][\\w0-9-]*".r
  def validTag(s: String) = tagRegex.unapplySeq(s).isDefined

  val attrNameRegex = "[a-zA-Z_:][-a-zA-Z0-9_:.]*".r
  def validAttrName(s: String) = attrNameRegex.unapplySeq(s).isDefined

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
        case c => if (c >= ' ') s.append(c)
      }

      pos += 1
    }
  }
}
