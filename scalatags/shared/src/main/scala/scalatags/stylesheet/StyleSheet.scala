package scalatags.stylesheet

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.SortedMap

/**
 * A [[StyleSheet]] which lets you define cascading tag/class
 * selectors. Separate from [[StyleSheet]] because you almost
 * never need these things, so it's good to make it explicit
 * when you do to prevent accidental cascading.
 */
trait CascadingStyleSheet extends StyleSheet with StyleSheetTags

/**
 * Inherit from me to define a stylesheet which you can use to define
 * styles which get serialized to a `String`. Does not allow the use
 * of cascading tag/class selectors; use [[CascadingStyleSheet]] for that.
 */
trait StyleSheet extends Selector{
  def sheetName = getClass.getName.replaceAll("[.$]", "-")

  var styleSheetText = ""
  val count = new AtomicInteger()
  object * extends Creator("")
  class Creator(selectors: String) extends PseudoSelectors[Creator]{
    def pseudoExtend(s: String) = new Creator(selectors + s)

    /**
     * Collapse the tree of [[StyleSheetFrag]]s into a single [[Cls]],
     * side-effect all the output into the styleSheetText, and return the
     * [[Cls]]
     */
    def apply(args: StyleSheetFrag*): Cls = {
      val name = sheetName + count.getAndIncrement
      val constructed = args.foldLeft(StyleTree(Seq("." + name + selectors), SortedMap.empty, Nil))(
        (c, f) => f.applyTo(c)
      )

      styleSheetText += constructed.stringify(Nil)
      Cls(name, constructed)
    }
  }
}

/**
 * A structure representing a set of CSS rules which has not been
 * rendered into a `String` and a [[Cls]].
 */
case class StyleTree(selectors: Seq[String],
                     styles: SortedMap[String, String],
                     children: Seq[StyleTree]){
  def stringify(prefix: Seq[String]): String = {
    val body = styles.map{case (k, v) => s"  $k:$v"}.mkString("\n")
    val (first +: rest) = prefix ++ selectors
    val all = first +: rest.map(x => if(x(0) == ':') x else " " + x)
    val ours =
      if (body == "") ""
      else s"${all.mkString}{\n$body\n}\n"

    (ours +: children.map(_.stringify(prefix ++ selectors))).mkString
  }
}
object StyleTree{
  def build(start: Seq[String], args: Seq[StyleSheetFrag]) = {
    args.foldLeft(StyleTree(start, SortedMap.empty, Nil))(
      (c, f) => f.applyTo(c)
    )
  }
}
/**
 * A rendered class; both the class `name` (used when injected into Scalatags
 * fragments) and the `structure` (used when injected into further class definitions)
 */
case class Cls(name: String, structure: StyleTree) extends Selector(Seq("." + name)){

  def splice = new StyleSheetFrag{
    def applyTo(st: StyleTree) = {
      new StyleTree(
        st.selectors,
        structure.styles ++ st.styles,
        structure.children ++ st.children
      )
    }
  }
}


/**
 * Something which can be used as part of a [[StyleSheet]]
 */
trait StyleSheetFrag{
  def applyTo(c: StyleTree): StyleTree
}
object StyleSheetFrag{
  implicit class StyleTreeFrag(st: StyleTree) extends StyleSheetFrag{
    def applyTo(c: StyleTree) = {
      new StyleTree(
        c.selectors,
        c.styles,
        c.children ++ Seq(st)
      )
    }
  }

}