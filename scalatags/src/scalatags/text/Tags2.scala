package scalatags
package text
trait Tags2 extends generic.Tags2[Builder, String, String] with TagFactory {
  // Document Metadata
  lazy val title: ConcreteHtmlTag[String] = tag("title")
  lazy val style: ConcreteHtmlTag[String] = tag("style")
  // Scripting
  lazy val noscript: ConcreteHtmlTag[String] = tag("noscript")
  // Sections
  lazy val section: ConcreteHtmlTag[String] = tag("section")
  lazy val nav: ConcreteHtmlTag[String] = tag("nav")
  lazy val article: ConcreteHtmlTag[String] = tag("article")
  lazy val aside: ConcreteHtmlTag[String] = tag("aside")
  lazy val address: ConcreteHtmlTag[String] = tag("address")
  lazy val main: ConcreteHtmlTag[String] = tag("main")
  // Text level semantics
  lazy val q: ConcreteHtmlTag[String] = tag("q")
  lazy val dfn: ConcreteHtmlTag[String] = tag("dfn")
  lazy val abbr: ConcreteHtmlTag[String] = tag("abbr")
  lazy val data: ConcreteHtmlTag[String] = tag("data")
  lazy val time: ConcreteHtmlTag[String] = tag("time")
  lazy val `var`: ConcreteHtmlTag[String] = tag("var")
  lazy val samp: ConcreteHtmlTag[String] = tag("samp")
  lazy val kbd: ConcreteHtmlTag[String] = tag("kbd")
  lazy val math: ConcreteHtmlTag[String] = tag("math")
  lazy val mark: ConcreteHtmlTag[String] = tag("mark")
  lazy val ruby: ConcreteHtmlTag[String] = tag("ruby")
  lazy val rt: ConcreteHtmlTag[String] = tag("rt")
  lazy val rp: ConcreteHtmlTag[String] = tag("rp")
  lazy val bdi: ConcreteHtmlTag[String] = tag("bdi")
  lazy val bdo: ConcreteHtmlTag[String] = tag("bdo")
  // Forms
  lazy val keygen: ConcreteHtmlTag[String] = tag("keygen", void = true)
  lazy val output: ConcreteHtmlTag[String] = tag("output")
  lazy val progress: ConcreteHtmlTag[String] = tag("progress")
  lazy val meter: ConcreteHtmlTag[String] = tag("meter")
  // Interactive elements
  lazy val details: ConcreteHtmlTag[String] = tag("details")
  lazy val summary: ConcreteHtmlTag[String] = tag("summary")
  lazy val command: ConcreteHtmlTag[String] = tag("command", void = true)
  lazy val menu: ConcreteHtmlTag[String] = tag("menu")
}
