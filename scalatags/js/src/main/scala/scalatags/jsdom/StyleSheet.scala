package scalatags.jsdom

import org.scalajs.dom
import org.scalajs.dom.css

import scala.collection.{SortedMap, SortedSet}
import scalatags.JsDom.tags2
import scalatags.text.Builder
import scalatags.generic
import scalatags.generic.{StylePair, Modifier}


case class Cls(classes: SortedSet[String],
               styles: SortedMap[String, String]) extends generic.Cls[dom.Element]{
  def applyTo(t: dom.Element) = classes.foreach(t.classList.add)
  def add(t: dom.Element) = classes.foreach(t.classList.add)
  def toggle(t: dom.Element) = classes.foreach(t.classList.toggle)
  def remove(t: dom.Element) = classes.foreach(t.classList.remove)
}


trait StyleFrag extends generic.StyleFrag[Cls]{
  def applyTo(c: Cls): Cls
}

trait StyleSheet extends scalatags.generic.StyleSheet[dom.Element] with generic.StyleSheetTags{
  type StyleSheetCls = Cls
  type StyleFragCls = StyleFrag
  def styleSheetText = styleSheetBuilder
  def newCls(className: String) = new Cls(SortedSet(className), SortedMap.empty)
  var styleSheetBuilder = ""
  def render(styles: String) = styleSheetBuilder += styles

  implicit class StylePairFrag(p: StylePair[dom.Element, _]) extends StyleFrag{
    def applyTo(c: Cls) = {
      val builder = dom.document.createElement("div")
      p.applyTo(builder)

      val styleTxt = builder.getAttribute("style")
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
class AutoStyleSheet extends StyleSheet{
  lazy val styleTag = tags2.style.render
  dom.document.head.appendChild(styleTag)
  override def render(styles: String) = {
    super.render(styles)
    styleTag.textContent += styles
  }
}