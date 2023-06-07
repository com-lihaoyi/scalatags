package scalatags
package jsdom
import org.scalajs.dom
import org.scalajs.dom.html
import scalatags.generic.Util
trait Tags2 extends generic.Tags2[dom.Element, dom.Element, dom.Node] with TagFactory{
   // Document Metadata
   lazy val title = typedTag[html.Title]("title")
   lazy val style = typedTag[html.Style]("style")
   // Scripting
   lazy val noscript = typedTag[html.Element]("noscript")
   // Sections
   lazy val section = typedTag[html.Element]("section")
   lazy val nav = typedTag[html.Element]("nav")
   lazy val article = typedTag[html.Element]("article")
   lazy val aside = typedTag[html.Element]("aside")
   lazy val address = typedTag[html.Element]("address")
   lazy val main = typedTag[html.Element]("main")
   // Text level semantics
   lazy val q = typedTag[html.Quote]("q")
   lazy val dfn = typedTag[html.Element]("dfn")
   lazy val abbr = typedTag[html.Element]("abbr")
   lazy val data = typedTag[html.Element]("data")
   lazy val time = typedTag[html.Element]("time")
   lazy val `var` = typedTag[html.Element]("var")
   lazy val samp = typedTag[html.Element]("samp")
   lazy val kbd = typedTag[html.Element]("kbd")
   lazy val math = typedTag[html.Element]("math")
   lazy val mark = typedTag[html.Element]("mark")
   lazy val ruby = typedTag[html.Element]("ruby")
   lazy val rt = typedTag[html.Element]("rt")
   lazy val rp = typedTag[html.Element]("rp")
   lazy val bdi = typedTag[html.Element]("bdi")
   lazy val bdo = typedTag[html.Element]("bdo")
   // Forms
   lazy val keygen = typedTag[html.Element]("keygen", void = true)
   lazy val output = typedTag[html.Element]("output")
   lazy val progress = typedTag[html.Progress]("progress")
   lazy val meter = typedTag[html.Element]("meter")
   // Interactive elements
   lazy val details = typedTag[html.Element]("details")
   lazy val summary = typedTag[html.Element]("summary")
   lazy val command = typedTag[html.Element]("command", void = true)
   lazy val menu = typedTag[html.Menu]("menu")
 }
