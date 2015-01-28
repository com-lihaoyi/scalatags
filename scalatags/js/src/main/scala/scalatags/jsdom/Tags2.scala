package scalatags
package jsdom
import acyclic.file
import org.scalajs.dom
import org.scalajs.dom.html
import scalatags.generic.Util
trait Tags2 extends generic.Tags2[dom.Element, dom.Element, dom.Node]{
   // Document Metadata
   val title = "title".tag[html.Title]
   val style = "style".tag[html.Style]
   // Scripting
   val noscript = "noscript".tag[html.Element]
   // Sections
   val section = "section".tag[html.Element]
   val nav = "nav".tag[html.Element]
   val article = "article".tag[html.Element]
   val aside = "aside".tag[html.Element]
   val address = "address".tag[html.Element]
   val main = "main".tag[html.Element]
   // Text level semantics
   val q = "q".tag[html.Quote]
   val dfn = "dfn".tag[html.Element]
   val abbr = "abbr".tag[html.Element]
   val data = "data".tag[html.Element]
   val time = "time".tag[html.Element]
   val `var` = "var".tag[html.Element]
   val samp = "samp".tag[html.Element]
   val kbd = "kbd".tag[html.Element]
   val math = "math".tag[html.Element]
   val mark = "mark".tag[html.Element]
   val ruby = "ruby".tag[html.Element]
   val rt = "rt".tag[html.Element]
   val rp = "rp".tag[html.Element]
   val bdi = "bdi".tag[html.Element]
   val bdo = "bdo".tag[html.Element]
   // Forms
   val keygen = "keygen".voidTag[html.Element]
   val output = "output".tag[html.Element]
   val progress = "progress".tag[html.Progress]
   val meter = "meter".tag[html.Element]
   // Interactive elements
   val details = "details".tag[html.Element]
   val summary = "summary".tag[html.Element]
   val command = "command".voidTag[html.Element]
   val menu = "menu".tag[html.Menu]
 }
