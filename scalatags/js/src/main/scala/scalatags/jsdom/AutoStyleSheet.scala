package scalatags.jsdom

import org.scalajs.dom

import scalatags.JsDom.tags2

trait AutoStyleSheet extends scalatags.stylesheet.StyleSheet{
  lazy val styleTag = tags2.style.render
  dom.document.head.appendChild(styleTag)
  styleTag.textContent += this.styleSheetText
}