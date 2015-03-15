package scalatags
package text


import scala.collection.{SortedMap, SortedSet}
import scalatags.generic.{PseudoSelectors, Rule, StylePair, Modifier}

case class Cls(classes: SortedSet[String]) extends generic.Cls[text.Builder](classes){
  override def applyTo(t: text.Builder): Unit = {
    val clsIndex = t.attrIndex("class")
    if (clsIndex == -1) t.addAttr("class", classes.mkString(" "))
    else{
      t.attrs(clsIndex) = ("class", t.attrs(clsIndex)._2 + " " + classes.mkString(" "))
    }
  }
}
object StyleSheet{


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


trait StyleSheet extends scalatags.generic.StyleSheet[text.Builder] with StyleSheetTags{
  type StyleSheetCls = Cls
  def styleSheetText = styleSheetBuilder
  var styleSheetBuilder = ""
  def render(styles: String) = styleSheetBuilder += styles
  def create(suffix: String, styles: Modifier[text.Builder]*) = {
    val className = makeClassName
    val builder = new Builder()
    styles.foreach{_.applyTo(builder)}

    val styleIndex = builder.attrIndex("style")
    val body =
      if (styleIndex == -1) ""
      else builder.attrs(styleIndex)._2

    if (body != "")
      render(stringify(className, suffix, body))

    val clsIndex = builder.attrIndex("class")

    val seq = collection.mutable.SortedSet.empty[String]
    if (clsIndex != -1)
      builder.attrs(clsIndex)._2.split(" ").foreach(seq.add)

    seq.add(className)
    Cls(seq)
  }
}