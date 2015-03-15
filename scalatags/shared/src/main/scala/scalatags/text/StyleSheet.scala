package scalatags
package text



trait StyleSheetImplicits{
  implicit class StyleFrag(s: generic.StylePair[text.Builder, _]) extends generic.StyleSheetFrag{
    def applyTo(c: generic.StyleTree) = {
      val b = new Builder()
      s.applyTo(b)
      val Array(style, value) = b.attrs(b.attrIndex("style"))._2.split(":", 2)
      c.copy(styles = c.styles.updated(style, value))
    }
  }
}
trait StyleSheet extends generic.StyleSheet with StyleSheetImplicits
trait CascadingStyleSheet extends StyleSheet with generic.CascadingStyleSheet