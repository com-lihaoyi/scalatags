package scalatags.stylesheet
import scala.collection.immutable.SortedMap
import scala.quoted.*
import _root_.javax.swing.text.Style

/** A [[StyleSheet]] which lets you define cascading tag/class
  * selectors. Separate from [[StyleSheet]] because you almost
  * never need these things, so it's good to make it explicit
  * when you do to prevent accidental cascading.
  */
abstract class CascadingStyleSheet(implicit sourceName: sourcecode.FullName)
    extends StyleSheet
    with StyleSheetTags {
  protected[this] implicit def clsSelector(c: Cls): Selector = new Selector(
    Seq("." + c.name)
  )
}

/** Inherit from me to define a stylesheet which you can use to define
  * styles which get serialized to a `String`. Does not allow the use
  * of cascading tag/class selectors; use [[CascadingStyleSheet]] for that.
  */
abstract class StyleSheet(implicit sourceName: sourcecode.FullName) {

  /** The name of this CSS stylesheet. Defaults to the name of the trait,
    * but you can override
    */
  def customSheetName: Option[String] = None

  /** Converts the name of the [[StyleSheet]]'s, the name of the member, and
    * any applicable pseudo-selectors into the name of the CSS class.
    */
  protected[this] def nameFor(memberName: String, pseudoSelectors: String) = {
    customSheetName.getOrElse(
      defaultSheetName.replace(".", "-")
    ) + "-" + memberName + pseudoSelectors
  }

  /** Namespace that holds all the css pseudo-selectors, to avoid collisions
    * with tags and style-names and other things.
    */
  protected[this] val & = new Selector

  /** `*` in a CSS selector.
    */
  protected[this] object * extends Selector(Seq("*"))

  /** Used to define a new, uniquely-named class with a set of
    * styles associated with it.
    */
  protected[this] object cls extends Creator(Nil)
  protected[this] class Creator(selectors: Seq[String])
      extends PseudoSelectors[Creator] {
    def pseudoExtend(s: String): Creator = new Creator(selectors :+ s)

    /** Collapse the tree of [[StyleSheetFrag]]s into a single [[Cls]],
      * side-effect all the output into the styleSheetText, and return the
      * [[Cls]]
      */
    def apply(args: StyleSheetFrag*)(implicit name: sourcecode.Name): Cls = {
      Cls(nameFor(name.value, ""), selectors, args)
    }
  }

  /** The default name of a stylesheet, filled in with the [[StyleSheet]] implicit macro
    */
  protected[this] def defaultSheetName =
    sourceName.value.replace(' ', '.').replace('#', '.')

  /** All classes defined in this stylesheet, filled in with the [[StyleSheet]] implicit macro
    */
  protected[this] def initStyleSheet()(implicit
      sourceClasses: SourceClasses[this.type]
  ) =
    allClasses0 = Some(() => sourceClasses.value(this))

  private[this] var allClasses0: Option[() => Seq[Cls]] = None
  def allClasses = allClasses0 match {
    case Some(f) => f()
    case None =>
      throw new Exception(
        "No CSS classes found on stylesheet " + this +
          ". Did you forget to call `initStyleSheet()` in the body of the style sheet?"
      )
  }

  def styleSheetText = allClasses.map(_.structure.stringify(Nil)).mkString("\n")
}
class SourceClasses[T <: StyleSheet](val value: T => Seq[Cls])
object SourceClasses {
  inline def apply[T <: StyleSheet]: SourceClasses[T] = $ { manglerImpl[T] }

  def manglerImpl[T <: StyleSheet: Type](using Quotes): Expr[SourceClasses[T]] = {
    ' { new SourceClasses[T]((t: T) => ${ Expr.ofSeq(terms('t)) }) }
  }

  private def terms[T <: StyleSheet : Type](t: Expr[T])(using ctx: Quotes): Seq[Expr[Cls]] = {
    import ctx.reflect._
    val valdefs = TypeRepr.of[T].typeSymbol.memberFields.map(_.tree.asInstanceOf[ValDef])
    val clsesOnly: Seq[ValDef] = valdefs.filter(_.tpt.tpe =:= TypeRepr.of[Cls])
    clsesOnly.map(valdef => t.asTerm.select(valdef.symbol).asExprOf[Cls])
  }
}


/** A rendered class; both the class `name` (used when injected into Scalatags
  * fragments) and the `structure` (used when injected into further class definitions)
  */
case class Cls(
    name: String,
    pseudoSelectors: Seq[String],
    args: Seq[StyleSheetFrag]
) {
  lazy val structure = args.foldLeft(
    StyleTree(
      Seq("." + name + pseudoSelectors.map(':' + _).mkString),
      SortedMap.empty,
      Nil
    )
  )((c, f) => f.applyTo(c))
  def splice = new StyleSheetFrag {
    def applyTo(st: StyleTree) = {
      new StyleTree(
        st.selectors,
        structure.styles ++ st.styles,
        structure.children ++ st.children
      )
    }
  }
}
