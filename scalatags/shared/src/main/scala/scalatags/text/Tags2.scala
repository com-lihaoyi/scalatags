package scalatags
package text
import acyclic.file
trait Tags2 extends generic.Tags2[Builder, String, String] with TagFactory{
  // Document Metadata
 lazy val title = tag("title")
 lazy val style = tag("style")
  // Scripting
 lazy val noscript = tag("noscript")
  // Sections
 lazy val section = tag("section")
 lazy val nav = tag("nav")
 lazy val article = tag("article")
 lazy val aside = tag("aside")
 lazy val address = tag("address")
 lazy val main = tag("main")
  // Text level semantics
 lazy val q = tag("q")
 lazy val dfn = tag("dfn")
 lazy val abbr = tag("abbr")
 lazy val data = tag("data")
 lazy val time = tag("time")
 lazy val `var` = tag("var")
 lazy val samp = tag("samp")
 lazy val kbd = tag("kbd")
 lazy val math = tag("math")
 lazy val mark = tag("mark")
 lazy val ruby = tag("ruby")
 lazy val rt = tag("rt")
 lazy val rp = tag("rp")
 lazy val bdi = tag("bdi")
 lazy val bdo = tag("bdo")
  // Forms
 lazy val keygen = tag("keygen", void = true)
 lazy val output = tag("output")
 lazy val progress = tag("progress")
 lazy val meter = tag("meter")
  // Interactive elements
 lazy val details = tag("details")
 lazy val summary = tag("summary")
 lazy val command = tag("command", void = true)
 lazy val menu = tag("menu")
}
