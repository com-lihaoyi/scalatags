package scalatags.jsdom

import org.scalajs.dom

import scalatags.text.Builder
import scalatags.{text, generic}

trait StyleSheetImplicits{
  implicit class StyleFrag(s: generic.StylePair[dom.Element, _]) extends generic.StyleSheetFrag{
    def applyTo(c: generic.StyleTree) = {
      val b = dom.document.createElement("div")
      s.applyTo(b)
      val Array(style, value) = b.getAttribute("style").split(":", 2)
      c.copy(styles = c.styles.updated(style, value))
    }
  }
}
trait StyleSheet extends generic.StyleSheet with StyleSheetImplicits
trait CascadingStyleSheet extends StyleSheet with generic.CascadingStyleSheet