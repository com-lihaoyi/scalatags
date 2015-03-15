package scalatags
package text


import scala.collection.SortedSet
import scalatags.generic.Modifier

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
trait StyleSheet extends scalatags.generic.StyleSheet[text.Builder]{
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