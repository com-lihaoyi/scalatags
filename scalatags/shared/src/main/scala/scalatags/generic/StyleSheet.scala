package scalatags.generic

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.{SortedMap, SortedSet}
import scalatags.text

abstract class Cls[Builder](classes: SortedSet[String]) extends Modifier[Builder]
case class Rule(selector: String, styles: SortedMap[String, String]){
  override def toString = {
    val body = styles.map{ case (k, v) => s"  $k: $v;\n" }
    s"$selector{\n$body}"
  }
}
object Rule{
  implicit def StylePair[T](s: StylePair[text.Builder, T]): Rule = ???
  implicit def Cls[T](s: text.Cls): Rule = ???
  implicit def RuleSeq[T](s: Seq[Rule]): Rule = ???
}

trait PseudoSelectors[T <: PseudoSelectors[T]]{

  def extend(s: String): T
  // Pseudo-selectors
  // https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes
  def active = extend(":active")
  def checked = extend(":checked")
  def default = extend(":default")
  def disabled = extend(":disabled")
  def empty = extend(":empty")
  def enabled = extend(":enabled")
  def first = extend(":first")
  def firstChild = extend(":first-child")
  def firstOfType = extend(":first-of-type")
  def fullscreen = extend(":fullscreen")
  def focus = extend(":focus")
  def hover = extend(":hover")
  def indeterminate = extend(":indeterminate")
  def inRange = extend(":in-range")
  def invalid = extend(":invalid")
  def lastChild = extend(":last-child")
  def lastOfType = extend(":last-of-type")
  def left = extend(":left")
  def link = extend(":link")
  def onlyChild = extend(":only-child")
  def onlyOfType = extend(":onlyOfType")
  def optional = extend(":optional")
  def outOfRange = extend(":out-of-range")
  def readOnly = extend(":read-only")
  def readWrite = extend(":read-write")
  def required = extend(":required")
  def right = extend(":right")
  def root = extend(":root")
  def scope = extend(":scope")
  def target = extend(":target")
  def valid = extend(":valid")
  def visited = extend(":visited")
}
abstract class StyleSheet[Builder]{
  type StyleSheetCls <: Cls[Builder]
  def styleSheetText: String
  def stylesheetName = this.getClass.getName.replaceAll("[.$]", "-")
  val count = new AtomicInteger()
  protected def makeClassName = stylesheetName + "-" + count.getAndIncrement
  protected def stringify(className: String, suffix: String, body: String) =
    s""".$className$suffix {
    |  $body
    |}
    |""".stripMargin

  protected def render(styles: String): Unit
  protected def create(suffix: String, styles: Modifier[Builder]*): StyleSheetCls
  object * extends DefaultConstructor("")
  class DefaultConstructor(selector: String)
                          extends PseudoSelectors[DefaultConstructor]{
    def apply(styles: Modifier[Builder]*) = create(selector, styles:_*)
    def extend(s: String) = new DefaultConstructor(selector + s)
  }
}