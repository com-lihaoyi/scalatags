package scalatags
package text


import scala.collection.{SortedMap, SortedSet}
import scalatags.generic.{PseudoSelectors, StylePair, Modifier}

case class Cls(classes: SortedSet[String],
               styles: SortedMap[String, String]) extends generic.Cls[text.Builder]{
  override def applyTo(t: text.Builder): Unit = {
    val clsIndex = t.attrIndex("class")
    if (clsIndex == -1) t.addAttr("class", classes.mkString(" "))
    else{
      t.attrs(clsIndex) = ("class", t.attrs(clsIndex)._2 + " " + classes.mkString(" "))
    }
  }
}


trait StyleFrag extends generic.StyleFrag[Cls]{
  def applyTo(c: Cls): Cls
}

trait StyleSheet extends scalatags.generic.StyleSheet[text.Builder] with generic.StyleSheetTags {
  type StyleSheetCls = Cls
  type StyleFragCls = StyleFrag
  def styleSheetText = styleSheetBuilder
  var styleSheetBuilder = ""
  def render(styles: String) = styleSheetBuilder += styles
  def newCls(className: String) = new Cls(SortedSet(className), SortedMap.empty)

  implicit class StylePairFrag(p: StylePair[text.Builder, _]) extends StyleFrag{
    def applyTo(c: Cls): Cls = {
      val builder = new Builder()
      p.applyTo(builder)

      val styleIndex = builder.attrIndex("style")
      val styleTxt = builder.attrs(styleIndex)._2
      val Array(first, second) = styleTxt.split(":", 2)
      c.copy(styles = c.styles.updated(first, second))
    }
  }
  implicit class ClsFrag(p: Cls) extends StyleFrag{
    def applyTo(c: Cls) = {
      c.copy(classes = c.classes ++ p.classes)
    }
  }
}