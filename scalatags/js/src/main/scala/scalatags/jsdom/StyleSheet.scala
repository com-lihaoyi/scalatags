package scalatags.jsdom

import org.scalajs.dom
import org.scalajs.dom.css

import scalatags.JsDom.tags2
import scalatags.generic.Modifier

case class Cls(classes: Seq[String]) extends scalatags.generic.Cls[dom.Element](classes){
  def applyTo(t: dom.Element) = classes.foreach(t.classList.add)
  def add(t: dom.Element) = classes.foreach(t.classList.add)
  def toggle(t: dom.Element) = classes.foreach(t.classList.toggle)
  def remove(t: dom.Element) = classes.foreach(t.classList.remove)
}

class StyleSheet extends scalatags.generic.StyleSheet[dom.Element]{
  def styleSheetText = styleSheetBuilder
  var styleSheetBuilder = ""

  def render(styles: String) = styleSheetBuilder += styles
  def create(suffix: String, styles: Modifier[dom.Element]*) = {
    val className = this.makeClassName
    val el = scalatags.JsDom.all.div.render
    styles.foreach{_.applyTo(el)}
    val body = Option(el.getAttribute("style")).getOrElse("")

    render(stringify(className, suffix, body))

    val seq = collection.mutable.Buffer.empty[String]
    seq.append(className)
    for(i <- 0 until el.classList.length){
      seq.append(el.classList.apply(i))
    }
    Cls(seq)
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