package scalatags.stylesheet

import scala.collection.SortedMap

/**
 * Provides all the CSS pseudo-selectors as strongly-typed
 * properties when mixed in. The only requirement is that you
 * define `extend` to tell it what each of these properties
 * returns
 */
trait PseudoSelectors[T]{

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

/**
 * A selector that can be used in a cascading stylesheet, or combined
 * with other selectors to form a larger rule
 */
case class Selector(tag: String) extends PseudoSelectors[Selector]{
  def extend(s: String) = Selector(tag + s)
  /**
   * Combine these two selectors, allowing the right-hand-side
   * selector to cascade.
   */
  def ~(other: Selector) = Selector(tag + " " + other.tag)

  /**
   * Combine these two selectors using the `>` child selector,
   * which prevents cascading.
   */
  def >(other: Selector) = Selector(tag + " > " + other.tag)

  /**
   * Builds this selector into a [[StyleTree]] using the given
   * [[StyleSheetFrag]]s. This doesn't create a [[Cls]] on its own,
   * but can be used as part of the definition of an outer [[Cls]].
   */
  def apply(args: StyleSheetFrag*) = StyleTree.build(" " + tag, args)
}

/**
 * Provides a strongly-typed list of all the HTML tags that can be
 * used as [[Selector]]s.
 */
trait StyleSheetTags{
  // Root Element
  val html = Selector("html")
  // Document Metadata
  val head = Selector("head")
  val base = Selector("base")
  // Don't let you style these because they
  // shouldn't be visible in the first place
  //  val link = Selector("link")
  val meta = Selector("meta")
  // Scripting
  val script = Selector("script")
  // Sections
  val body = Selector("body")
  val h1 = Selector("h1")
  val h2 = Selector("h2")
  val h3 = Selector("h3")
  val h4 = Selector("h4")
  val h5 = Selector("h5")
  val h6 = Selector("h6")
  val header = Selector("header")
  val footer = Selector("footer")
  // Grouping content
  val p = Selector("p")
  val hr = Selector("hr")
  val pre = Selector("pre")
  val blockquote = Selector("blockquote")
  val ol = Selector("ol")
  val ul = Selector("ul")
  val li = Selector("li")
  val dl = Selector("dl")
  val dt = Selector("dt")
  val dd = Selector("dd")
  val figure = Selector("figure")
  val figcaption = Selector("figcaption")
  val div = Selector("div")
  // Text-level semantics
  val a = Selector("a")
  val em = Selector("em")
  val strong = Selector("strong")
  val small = Selector("small")
  val s = Selector("s")
  val cite = Selector("cite")
  val code = Selector("code")
  val sub = Selector("sub")
  val sup = Selector("sup")
  val i = Selector("i")
  val b = Selector("b")
  val u = Selector("u")
  val span = Selector("span")
  val br = Selector("br")
  val wbr = Selector("wbr")
  // Edits
  val ins = Selector("ins")
  val del = Selector("del")
  // Embedded content
  val img = Selector("img")
  val iframe = Selector("iframe")
  val embed = Selector("embed")
  val `object` = Selector("object")
  val param = Selector("param")
  val video = Selector("video")
  val audio = Selector("audio")
  val source = Selector("source")
  val track = Selector("track")
  val canvas = Selector("canvas")
  val map = Selector("map")
  val area = Selector("area")
  // Tabular data
  val table = Selector("table")
  val caption = Selector("caption")
  val colgroup = Selector("colgroup")
  val col = Selector("col")
  val tbody = Selector("tbody")
  val thead = Selector("thead")
  val tfoot = Selector("tfoot")
  val tr = Selector("tr")
  val td = Selector("td")
  val th = Selector("th")
  // Forms
  val form = Selector("form")
  val fieldset = Selector("fieldset")
  val legend = Selector("legend")
  val label = Selector("label")
  val input = Selector("input")
  val button = Selector("button")
  val select = Selector("select")
  val datalist = Selector("datalist")
  val optgroup = Selector("optgroup")
  val option = Selector("option")
  val textarea = Selector("textarea")
}
