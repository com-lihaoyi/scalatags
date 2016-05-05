package scalatags.stylesheet
import acyclic.file
import scala.language.experimental.macros
import scala.collection.SortedMap
import scalatags.ScalaVersionStubs.Context


/**
 * A [[StyleSheet]] which lets you define cascading tag/class
 * selectors. Separate from [[StyleSheet]] because you almost
 * never need these things, so it's good to make it explicit
 * when you do to prevent accidental cascading.
 */
trait CascadingStyleSheet extends StyleSheet with StyleSheetTags{
  implicit def clsSelector(c: Cls): Selector = new Selector(Seq("." + c.name))
}

/**
 * Inherit from me to define a stylesheet which you can use to define
 * styles which get serialized to a `String`. Does not allow the use
 * of cascading tag/class selectors; use [[CascadingStyleSheet]] for that.
 */
trait StyleSheet{
  /**
   * The name of this CSS stylesheet. Defaults to the name of the trait,
   * but you can override
   */
  def customSheetName: Option[String] = None

  /**
   * Converts the name of the [[StyleSheet]]'s, the name of the member, and
   * any applicable pseudo-selectors into the name of the CSS class.
   */
  def nameFor(memberName: String, pseudoSelectors: String) = {
    customSheetName.getOrElse(defaultSheetName.replace(".", "-")) + "-" + memberName + pseudoSelectors
  }

  /**
   * Namespace that holds all the css pseudo-selectors, to avoid collisions
   * with tags and style-names and other things.
   */
  val & = new Selector

  /**
   * `*` in a CSS selector.
   */
  object * extends Selector(Seq("*"))

  /**
   * Used to define a new, uniquely-named class with a set of
   * styles associated with it.
   */
  object cls extends Creator(Nil)
  class Creator(selectors: Seq[String]) extends PseudoSelectors[Creator]{
    def pseudoExtend(s: String): Creator = new Creator(selectors :+ s)

    /**
     * Collapse the tree of [[StyleSheetFrag]]s into a single [[Cls]],
     * side-effect all the output into the styleSheetText, and return the
     * [[Cls]]
     */
    def apply(args: StyleSheetFrag*)(implicit name: sourcecode.Name): Cls = {
      Cls(nameFor(name.value, ""), selectors, args)
    }
  }

  /**
   * The default name of a stylesheet, filled in with the [[Sheet]] implicit macro
   */
  def defaultSheetName: String
  /**
   * All classes defined in this stylesheet, filled in with the [[Sheet]] implicit macro
   */
  def allClasses: Seq[Cls]

  def styleSheetText = allClasses.map(_.structure.stringify(Nil)).mkString("\n")
}


/**
 * Wraps a type [[T]], so we can demand an implicit `Mangled[T]` in a way
 * that triggers a macro to instantiate that type.
 */
object Sheet{
  implicit def apply[T]: T = macro manglerImpl[T]
  def manglerImpl[T: c.WeakTypeTag](c: Context) = {
    import c.universe._

    val weakType = weakTypeOf[T]

    val typeName = weakType.typeSymbol.fullName
    val names = for {
      member <- weakType.members.toSeq.reverse
      // Not sure if there's a better way to capture by-name types
      if member.typeSignature.toString == "=> scalatags.stylesheet.Cls"
    } yield member.name.toTermName

    val res = q"""new $weakType{
        def defaultSheetName = $typeName
        lazy val allClasses = Seq(..$names)
      }"""

    c.Expr[T](res)
  }
}

/**
 * A rendered class; both the class `name` (used when injected into Scalatags
 * fragments) and the `structure` (used when injected into further class definitions)
 */
case class Cls(name: String, pseudoSelectors: Seq[String], args: Seq[StyleSheetFrag]){
  lazy val structure = args.foldLeft(StyleTree(Seq("." + name + pseudoSelectors.map(':'+_).mkString), SortedMap.empty, Nil))(
    (c, f) => f.applyTo(c)
  )
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
