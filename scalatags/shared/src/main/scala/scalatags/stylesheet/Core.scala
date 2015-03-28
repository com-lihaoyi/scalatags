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
  val html = Selector("html")
  // Document Metadata
  val head = Selector("head")
  val base = Selector("base")

  val link = Selector("link")
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
