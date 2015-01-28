package scalatags
package jsdom
import acyclic.file
import org.scalajs.dom
import scalatags.generic.Util
trait Tags2 extends generic.Tags2[dom.Element, dom.Element, dom.Node]{
   // Document Metadata
   val title = "title".tag[dom.HTMLTitleElement]
   val style = "style".tag[dom.HTMLStyleElement]
   // Scripting
   val noscript = "noscript".tag[dom.HTMLElement]
   // Sections
   val section = "section".tag[dom.HTMLElement]
   val nav = "nav".tag[dom.HTMLElement]
   val article = "article".tag[dom.HTMLElement]
   val aside = "aside".tag[dom.HTMLElement]
   val address = "address".tag[dom.HTMLElement]
   val main = "main".tag[dom.HTMLElement]
   // Text level semantics
   val q = "q".tag[dom.HTMLQuoteElement]
   val dfn = "dfn".tag[dom.HTMLElement]
   val abbr = "abbr".tag[dom.HTMLElement]
   val data = "data".tag[dom.HTMLElement]
   val time = "time".tag[dom.HTMLElement]
   val `var` = "var".tag[dom.HTMLElement]
   val samp = "samp".tag[dom.HTMLElement]
   val kbd = "kbd".tag[dom.HTMLElement]
   val math = "math".tag[dom.HTMLElement]
   val mark = "mark".tag[dom.HTMLElement]
   val ruby = "ruby".tag[dom.HTMLElement]
   val rt = "rt".tag[dom.HTMLElement]
   val rp = "rp".tag[dom.HTMLElement]
   val bdi = "bdi".tag[dom.HTMLElement]
   val bdo = "bdo".tag[dom.HTMLElement]
   // Forms
   val keygen = "keygen".voidTag[dom.HTMLElement]
   val output = "output".tag[dom.HTMLElement]
   val progress = "progress".tag[dom.HTMLProgressElement]
   val meter = "meter".tag[dom.HTMLElement]
   // Interactive elements
   val details = "details".tag[dom.HTMLElement]
   val summary = "summary".tag[dom.HTMLElement]
   val command = "command".voidTag[dom.HTMLElement]
   val menu = "menu".tag[dom.HTMLMenuElement]
 }
