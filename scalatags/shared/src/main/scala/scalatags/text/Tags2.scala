package scalatags
package text
import acyclic.file
trait Tags2 extends generic.Tags2[Builder, String, String]{
  // Document Metadata
 lazy val title = "title".tag
 lazy val style = "style".tag
  // Scripting
 lazy val noscript = "noscript".tag
  // Sections
 lazy val section = "section".tag
 lazy val nav = "nav".tag
 lazy val article = "article".tag
 lazy val aside = "aside".tag
 lazy val address = "address".tag
 lazy val main = "main".tag
  // Text level semantics
 lazy val q = "q".tag
 lazy val dfn = "dfn".tag
 lazy val abbr = "abbr".tag
 lazy val data = "data".tag
 lazy val time = "time".tag
 lazy val `var` = "var".tag
 lazy val samp = "samp".tag
 lazy val kbd = "kbd".tag
 lazy val math = "math".tag
 lazy val mark = "mark".tag
 lazy val ruby = "ruby".tag
 lazy val rt = "rt".tag
 lazy val rp = "rp".tag
 lazy val bdi = "bdi".tag
 lazy val bdo = "bdo".tag
  // Forms
 lazy val keygen = "keygen".voidTag
 lazy val output = "output".tag
 lazy val progress = "progress".tag
 lazy val meter = "meter".tag
  // Interactive elements
 lazy val details = "details".tag
 lazy val summary = "summary".tag
 lazy val command = "command".voidTag
 lazy val menu = "menu".tag
}
