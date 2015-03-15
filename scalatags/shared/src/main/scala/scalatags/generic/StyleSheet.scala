package scalatags.generic

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.SortedSet

abstract class Cls[Builder](classes: SortedSet[String]) extends Modifier[Builder]

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
  object *{
    def apply(styles: Modifier[Builder]*) = create("", styles:_*)

    // Pseudo-selectors
    // https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes
    def active(styles: Modifier[Builder]*) = create(":active", styles:_*)
    def checked(styles: Modifier[Builder]*) = create(":checked", styles:_*)
    def default(styles: Modifier[Builder]*) = create(":default", styles:_*)
    def disabled(styles: Modifier[Builder]*) = create(":disabled", styles:_*)
    def empty(styles: Modifier[Builder]*) = create(":empty", styles:_*)
    def enabled(styles: Modifier[Builder]*) = create(":enabled", styles:_*)
    def first(styles: Modifier[Builder]*) = create(":first", styles:_*)
    def firstChild(styles: Modifier[Builder]*) = create(":first-child", styles:_*)
    def firstOfType(styles: Modifier[Builder]*) = create(":first-of-type", styles:_*)
    def fullscreen(styles: Modifier[Builder]*) = create(":fullscreen", styles:_*)
    def focus(styles: Modifier[Builder]*) = create(":focus", styles:_*)
    def hover(styles: Modifier[Builder]*) = create(":hover", styles:_*)
    def indeterminate(styles: Modifier[Builder]*) = create(":indeterminate", styles:_*)
    def inRange(styles: Modifier[Builder]*) = create(":in-range", styles:_*)
    def invalid(styles: Modifier[Builder]*) = create(":invalid", styles:_*)
    def lastChild(styles: Modifier[Builder]*) = create(":last-child", styles:_*)
    def lastOfType(styles: Modifier[Builder]*) = create(":last-of-type", styles:_*)
    def left(styles: Modifier[Builder]*) = create(":left", styles:_*)
    def link(styles: Modifier[Builder]*) = create(":link", styles:_*)
    def onlyChild(styles: Modifier[Builder]*) = create(":only-child", styles:_*)
    def onlyOfType(styles: Modifier[Builder]*) = create(":onlyOfType", styles:_*)
    def optional(styles: Modifier[Builder]*) = create(":optional", styles:_*)
    def outOfRange(styles: Modifier[Builder]*) = create(":out-of-range", styles:_*)
    def readOnly(styles: Modifier[Builder]*) = create(":read-only", styles:_*)
    def readWrite(styles: Modifier[Builder]*) = create(":read-write", styles:_*)
    def required(styles: Modifier[Builder]*) = create(":required", styles:_*)
    def right(styles: Modifier[Builder]*) = create(":right", styles:_*)
    def root(styles: Modifier[Builder]*) = create(":root", styles:_*)
    def scope(styles: Modifier[Builder]*) = create(":scope", styles:_*)
    def target(styles: Modifier[Builder]*) = create(":target", styles:_*)
    def valid(styles: Modifier[Builder]*) = create(":valid", styles:_*)
    def visited(styles: Modifier[Builder]*) = create(":visited", styles:_*)
  }
}