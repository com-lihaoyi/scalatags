package scalatags
package jsdom
import acyclic.file
import org.scalajs.dom
import org.scalajs.dom.html
import scalatags.generic.Util
trait Tags2 extends generic.Tags2[dom.Element, dom.Element, dom.Node]{
   // Document Metadata
   lazy val title = "title".tag[html.Title]
   lazy val style = "style".tag[html.Style]
   // Scripting
   lazy val noscript = "noscript".tag[html.Element]
   // Sections
   lazy val section = "section".tag[html.Element]
   lazy val nav = "nav".tag[html.Element]
   lazy val article = "article".tag[html.Element]
   lazy val aside = "aside".tag[html.Element]
   lazy val address = "address".tag[html.Element]
   lazy val main = "main".tag[html.Element]
   // Text level semantics
   lazy val q = "q".tag[html.Quote]
   lazy val dfn = "dfn".tag[html.Element]
   lazy val abbr = "abbr".tag[html.Element]
   lazy val data = "data".tag[html.Element]
   lazy val time = "time".tag[html.Element]
   lazy val `var` = "var".tag[html.Element]
   lazy val samp = "samp".tag[html.Element]
   lazy val kbd = "kbd".tag[html.Element]
   lazy val math = "math".tag[html.Element]
   lazy val mark = "mark".tag[html.Element]
   lazy val ruby = "ruby".tag[html.Element]
   lazy val rt = "rt".tag[html.Element]
   lazy val rp = "rp".tag[html.Element]
   lazy val bdi = "bdi".tag[html.Element]
   lazy val bdo = "bdo".tag[html.Element]
   // Forms
   lazy val keygen = "keygen".voidTag[html.Element]
   lazy val output = "output".tag[html.Element]
   lazy val progress = "progress".tag[html.Progress]
   lazy val meter = "meter".tag[html.Element]
   // Interactive elements
   lazy val details = "details".tag[html.Element]
   lazy val summary = "summary".tag[html.Element]
   lazy val command = "command".voidTag[html.Element]
   lazy val menu = "menu".tag[html.Menu]
 }
