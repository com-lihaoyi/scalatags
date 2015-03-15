package scalatags.generic

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.{SortedMap, SortedSet}
import scalatags.text

abstract class Cls[Builder](classes: SortedSet[String],
                            styles: SortedMap[String, String]) extends Modifier[Builder]

trait PseudoSelectors[T <: PseudoSelectors[T]]{

  def extend(s: String): T
  // Pseudo-selectors
  // https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes
  def active = extend(":active")
  def checked = extend(":checked")
  def default = extend(":default")
  def disabled = extend(":disabled")
  def empty = extend(":empty")
  def enabled = extend(":enabled")
  def first = extend(":first")
  def firstChild = extend(":first-child")
  def firstOfType = extend(":first-of-type")
  def fullscreen = extend(":fullscreen")
  def focus = extend(":focus")
  def hover = extend(":hover")
  def indeterminate = extend(":indeterminate")
  def inRange = extend(":in-range")
  def invalid = extend(":invalid")
  def lastChild = extend(":last-child")
  def lastOfType = extend(":last-of-type")
  def left = extend(":left")
  def link = extend(":link")
  def onlyChild = extend(":only-child")
  def onlyOfType = extend(":onlyOfType")
  def optional = extend(":optional")
  def outOfRange = extend(":out-of-range")
  def readOnly = extend(":read-only")
  def readWrite = extend(":read-write")
  def required = extend(":required")
  def right = extend(":right")
  def root = extend(":root")
  def scope = extend(":scope")
  def target = extend(":target")
  def valid = extend(":valid")
  def visited = extend(":visited")
}


trait StyleSheetTags{
  //  implicit def StylePairToRule[T](s: StylePair[text.Builder, T]): Rule = {
  //    s.s
  //
  //  }
  case class Tag(tag: String) extends PseudoSelectors[Tag]{
    def extend(s: String) = new Tag(tag + s)
  }
  // Root Element
  val html = Tag("html")
  // Document Metadata
  val head = Tag("head")
  val base = Tag("base")
  val link = Tag("link")
  val meta = Tag("meta")
  // Scripting
  val script = Tag("script")
  // Sections
  val body = Tag("body")
  val h1 = Tag("h1")
  val h2 = Tag("h2")
  val h3 = Tag("h3")
  val h4 = Tag("h4")
  val h5 = Tag("h5")
  val h6 = Tag("h6")
  val header = Tag("header")
  val footer = Tag("footer")
  // Grouping content
  val p = Tag("p")
  val hr = Tag("hr")
  val pre = Tag("pre")
  val blockquote = Tag("blockquote")
  val ol = Tag("ol")
  val ul = Tag("ul")
  val li = Tag("li")
  val dl = Tag("dl")
  val dt = Tag("dt")
  val dd = Tag("dd")
  val figure = Tag("figure")
  val figcaption = Tag("figcaption")
  val div = Tag("div")
  // Text-level semantics
  val a = Tag("a")
  val em = Tag("em")
  val strong = Tag("strong")
  val small = Tag("small")
  val s = Tag("s")
  val cite = Tag("cite")
  val code = Tag("code")
  val sub = Tag("sub")
  val sup = Tag("sup")
  val i = Tag("i")
  val b = Tag("b")
  val u = Tag("u")
  val span = Tag("span")
  val br = Tag("br")
  val wbr = Tag("wbr")
  // Edits
  val ins = Tag("ins")
  val del = Tag("del")
  // Embedded content
  val img = Tag("img")
  val iframe = Tag("iframe")
  val embed = Tag("embed")
  val `object` = Tag("object")
  val param = Tag("param")
  val video = Tag("video")
  val audio = Tag("audio")
  val source = Tag("source")
  val track = Tag("track")
  val canvas = Tag("canvas")
  val map = Tag("map")
  val area = Tag("area")
  // Tabular data
  val table = Tag("table")
  val caption = Tag("caption")
  val colgroup = Tag("colgroup")
  val col = Tag("col")
  val tbody = Tag("tbody")
  val thead = Tag("thead")
  val tfoot = Tag("tfoot")
  val tr = Tag("tr")
  val td = Tag("td")
  val th = Tag("th")
  // Forms
  val form = Tag("form")
  val fieldset = Tag("fieldset")
  val legend = Tag("legend")
  val label = Tag("label")
  val input = Tag("input")
  val button = Tag("button")
  val select = Tag("select")
  val datalist = Tag("datalist")
  val optgroup = Tag("optgroup")
  val option = Tag("option")
  val textarea = Tag("textarea")
}

abstract class StyleSheet[Builder]{
  type StyleSheetCls <: Cls[Builder]
  def styleSheetText: String
  def stylesheetName = this.getClass.getName.replaceAll("[.$]", "-")
  val count = new AtomicInteger()
  protected def makeClassName = stylesheetName + "-" + count.getAndIncrement
  protected def stringify(className: String, suffix: String, body: String) =
    s""".$className$suffix {
    |  $body
    |}
    |""".stripMargin

  type StyleFragCls
  protected def render(styles: String): Unit
  protected def create(suffix: String, styles: StyleFragCls*): StyleSheetCls

  def cascade(t: StyleSheetTags#Tag) = new DefaultConstructor(" " + t.tag)
  object * extends DefaultConstructor("")
  class DefaultConstructor(selector: String)
                          extends PseudoSelectors[DefaultConstructor]{
    def apply(styles: StyleFragCls*) = create(selector, styles:_*)
    def extend(s: String) = new DefaultConstructor(selector + s)
  }
}