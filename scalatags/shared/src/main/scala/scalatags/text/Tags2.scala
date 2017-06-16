package scalatags
package text
import acyclic.file
trait Tags2 extends generic.Tags2[Builder, String, String] with TagFactory{
  // Document Metadata
  def title = tag("title")
  def style = tag("style")
  // Scripting
  def noscript = tag("noscript")
  // Sections
  def section = tag("section")
  def nav = tag("nav")
  def article = tag("article")
  def aside = tag("aside")
  def address = tag("address")
  def main = tag("main")
  // Text level semantics
  def q = tag("q")
  def dfn = tag("dfn")
  def abbr = tag("abbr")
  def data = tag("data")
  def time = tag("time")
  def `var` = tag("var")
  def samp = tag("samp")
  def kbd = tag("kbd")
  def math = tag("math")
  def mark = tag("mark")
  def ruby = tag("ruby")
  def rt = tag("rt")
  def rp = tag("rp")
  def bdi = tag("bdi")
  def bdo = tag("bdo")
  // Forms
  def keygen = tag("keygen", void = true)
  def output = tag("output")
  def progress = tag("progress")
  def meter = tag("meter")
  // Interactive elements
  def details = tag("details")
  def summary = tag("summary")
  def command = tag("command", void = true)
  def menu = tag("menu")
}
