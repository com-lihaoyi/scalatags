package scalatags.stylesheet

import acyclic.file

import scala.collection.SortedMap

/**
 * A structure representing a set of CSS rules which has not been
 * rendered into a [[Cls]].
 *
 * e.g. a StyleTree that looks like
 *
 * .cls1
 *  .cls2
 *   :hover
 * :hover
 *   cls2
 *
 * Flattens out via `stringify` into CSS rules like
 *
 * .cls1 .cls2:hover
 * .cls1:hover .cls2
 */
case class StyleTree(selectors: Seq[String],
                     styles: SortedMap[String, String],
                     children: Seq[StyleTree]){
  def stringify(prefix: Seq[String]): String = {
    val body = styles.map{case (k, v) => s"  $k:$v"}.mkString("\n")
    val (first +: rest) = prefix ++ selectors
    val all = (first +: rest.map(x => if (x(0) == ':') x else " " + x)).mkString("")
    val ours =
      if (body == "") ""
      else s"$all{\n$body\n}\n"

    (ours +: children.map(_.stringify(prefix ++ selectors))).mkString
  }
}

object StyleTree{
  def build(start: Seq[String], args: Seq[StyleSheetFrag]) = {
    args.foldLeft(StyleTree(start, SortedMap.empty, Nil))(
      (c, f) => f.applyTo(c)
    )
  }
}


/**
 * Something which can be used as part of a [[StyleSheet]]
 */
trait StyleSheetFrag{

  def applyTo(c: StyleTree): StyleTree
}
object StyleSheetFrag{
  implicit class StyleTreeFrag(st: StyleTree) extends StyleSheetFrag{
    def applyTo(c: StyleTree) = {
      new StyleTree(
        c.selectors,
        c.styles,
        c.children ++ Seq(st)
      )
    }
  }

}

/**
 * Provides all the CSS pseudo-selectors as strongly-typed
 * properties when mixed in. The only requirement is that you
 * define `extend` to tell it what each of these properties
 * returns
 */
trait PseudoSelectors[T]{

  def pseudoExtend(s: String): T
  // Pseudo-selectors
  // https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes
  def active = pseudoExtend("active")
  def checked = pseudoExtend("checked")
  def default = pseudoExtend("default")
  def disabled = pseudoExtend("disabled")
  def empty = pseudoExtend("empty")
  def enabled = pseudoExtend("enabled")
  def first = pseudoExtend("first")
  def firstChild = pseudoExtend("first-child")
  def firstOfType = pseudoExtend("first-of-type")
  def fullscreen = pseudoExtend("fullscreen")
  def focus = pseudoExtend("focus")
  def hover = pseudoExtend("hover")
  def indeterminate = pseudoExtend("indeterminate")
  def inRange = pseudoExtend("in-range")
  def invalid = pseudoExtend("invalid")
  def lastChild = pseudoExtend("last-child")
  def lastOfType = pseudoExtend("last-of-type")
  def left = pseudoExtend("left")
  def link = pseudoExtend("link")
  def onlyChild = pseudoExtend("only-child")
  def onlyOfType = pseudoExtend("onlyOfType")
  def optional = pseudoExtend("optional")
  def outOfRange = pseudoExtend("out-of-range")
  def readOnly = pseudoExtend("read-only")
  def readWrite = pseudoExtend("read-write")
  def required = pseudoExtend("required")
  def right = pseudoExtend("right")
  def root = pseudoExtend("root")
  def scope = pseudoExtend("scope")
  def target = pseudoExtend("target")
  def valid = pseudoExtend("valid")
  def visited = pseudoExtend("visited")
}


/**
 * Lets you chain pseudo-selectors e.g. `hover.visited` and have it properly
 * translate into `:hover:visited` when rendered.
 */
class Selector(val built: Seq[String] = Nil) extends PseudoSelectors[Selector]{ b =>
  def pseudoExtend(s: String) = {
    if(b.built == Nil) new Selector(Seq(":"+s))
    else new Selector(b.built.init :+ (b.built.last + ":"+s))
  }

  /**
   * Builds this selector into a [[StyleTree]] using the given
   * [[StyleSheetFrag]]s. This doesn't create a [[Cls]] on its own,
   * but can be used as part of the definition of an outer [[Cls]].
   */
  def apply(args: StyleSheetFrag*) = StyleTree.build(built, args)

  /**
   * Combine these two selectors, allowing the right-hand-side
   * selector to cascade.
   */
  def ~(other: Selector) = new Selector(built ++ other.built)

  /**
   * Combine these two selectors using the `>` child selector,
   * which prevents cascading.
   */
  def >(other: Selector) = new Selector(built ++ Seq(">") ++ other.built)
}

object Selector{
  def apply(s: String) = new Selector(Seq(s))
}
/**
 * Provides a strongly-typed list of all the HTML tags that can be
 * used as [[Selector]]s.
 */
trait StyleSheetTags{

  // Root Element
  protected[this] val html = Selector("html")
  // Document Metadata
  protected[this] val head = Selector("head")
  protected[this] val base = Selector("base")

  protected[this] val link = Selector("link")
  protected[this] val meta = Selector("meta")
  // Scripting
  protected[this] val script = Selector("script")
  // Sections
  protected[this] val body = Selector("body")
  protected[this] val h1 = Selector("h1")
  protected[this] val h2 = Selector("h2")
  protected[this] val h3 = Selector("h3")
  protected[this] val h4 = Selector("h4")
  protected[this] val h5 = Selector("h5")
  protected[this] val h6 = Selector("h6")
  protected[this] val header = Selector("header")
  protected[this] val footer = Selector("footer")
  // Grouping content
  protected[this] val p = Selector("p")
  protected[this] val hr = Selector("hr")
  protected[this] val pre = Selector("pre")
  protected[this] val blockquote = Selector("blockquote")
  protected[this] val ol = Selector("ol")
  protected[this] val ul = Selector("ul")
  protected[this] val li = Selector("li")
  protected[this] val dl = Selector("dl")
  protected[this] val dt = Selector("dt")
  protected[this] val dd = Selector("dd")
  protected[this] val figure = Selector("figure")
  protected[this] val figcaption = Selector("figcaption")
  protected[this] val div = Selector("div")
  // Text-level semantics
  protected[this] val a = Selector("a")
  protected[this] val em = Selector("em")
  protected[this] val strong = Selector("strong")
  protected[this] val small = Selector("small")
  protected[this] val s = Selector("s")
  protected[this] val cite = Selector("cite")
  protected[this] val code = Selector("code")
  protected[this] val sub = Selector("sub")
  protected[this] val sup = Selector("sup")
  protected[this] val i = Selector("i")
  protected[this] val b = Selector("b")
  protected[this] val u = Selector("u")
  protected[this] val span = Selector("span")
  protected[this] val br = Selector("br")
  protected[this] val wbr = Selector("wbr")
  // Edits
  protected[this] val ins = Selector("ins")
  protected[this] val del = Selector("del")
  // Embedded content
  protected[this] val img = Selector("img")
  protected[this] val iframe = Selector("iframe")
  protected[this] val embed = Selector("embed")
  protected[this] val `object` = Selector("object")
  protected[this] val param = Selector("param")
  protected[this] val video = Selector("video")
  protected[this] val audio = Selector("audio")
  protected[this] val source = Selector("source")
  protected[this] val track = Selector("track")
  protected[this] val canvas = Selector("canvas")
  protected[this] val map = Selector("map")
  protected[this] val area = Selector("area")
  // Tabular data
  protected[this] val table = Selector("table")
  protected[this] val caption = Selector("caption")
  protected[this] val colgroup = Selector("colgroup")
  protected[this] val col = Selector("col")
  protected[this] val tbody = Selector("tbody")
  protected[this] val thead = Selector("thead")
  protected[this] val tfoot = Selector("tfoot")
  protected[this] val tr = Selector("tr")
  protected[this] val td = Selector("td")
  protected[this] val th = Selector("th")
  // Forms
  protected[this] val form = Selector("form")
  protected[this] val fieldset = Selector("fieldset")
  protected[this] val legend = Selector("legend")
  protected[this] val label = Selector("label")
  protected[this] val input = Selector("input")
  protected[this] val button = Selector("button")
  protected[this] val select = Selector("select")
  protected[this] val datalist = Selector("datalist")
  protected[this] val optgroup = Selector("optgroup")
  protected[this] val option = Selector("option")
  protected[this] val textarea = Selector("textarea")
}
