package scalatags.stylesheet

import java.util.concurrent.atomic.AtomicInteger
import scala.language.experimental.macros
import scala.collection.SortedMap
import scala.reflect.macros.blackbox.Context

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
trait StyleSheet{
  def nameFor(typeName: String, memberName: String, baseName: String) = {
    (typeName.split('.') :+ memberName).mkString("-") + baseName
  }
  val & = new Selector
  def sheetName = getClass.getName.replaceAll("[.$]", "-")

  object * extends Creator("")
  class Creator(selectors: String) extends PseudoSelectors[Creator]{
    def pseudoExtend(s: String) = new Creator(selectors + s)

    /**
     * Collapse the tree of [[StyleSheetFrag]]s into a single [[Cls]],
     * side-effect all the output into the styleSheetText, and return the
     * [[Cls]]
     */
    def apply(args: StyleSheetFrag*): Cls = {
      Cls(selectors, args)
    }
  }

  def allClasses: Seq[Cls]

  def styleSheetText = allClasses.map(_.structure.stringify(Nil)).mkString("\n")
}

class Sheet[T](implicit i: Mangled[T]){
  def apply() = i.t
}

/**
 * Wraps a type [[T]], so we can demand an implicit `Mangled[T]` in a way
 * that triggers a macro to instantiate that type.
 */
case class Mangled[T](t: T)
object Mangled{
  implicit def mangler[T]: Mangled[T] = macro manglerImpl[T]
  def manglerImpl[T: c.WeakTypeTag](c: Context) = {
    import c.universe._

    val weakType = weakTypeOf[T]

    val typeName = weakType.typeSymbol.fullName
    val names = for {
      member <- weakType.members.toSeq.reverse
      // Not sure if there's a better way to capture by-name types
      if member.typeSignature.toString == "=> scalatags.stylesheet.Cls"
    } yield member.name.toTermName

    val overrides = for(name<- names) yield q"""
        override lazy val $name = super.$name.copy(name = nameFor($typeName, ${name.decodedName.toString}, super.$name.name))
      """

    val res = q"""scalatags.stylesheet.Mangled(new $weakType{
        ..$overrides
        val allClasses = Seq(..$names)
      })"""

    c.Expr[T](res)
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
case class Cls(name: String, args: Seq[StyleSheetFrag]) extends Selector(Seq("." + name)){
  lazy val structure = args.foldLeft(StyleTree(Seq("." + name), SortedMap.empty, Nil))(
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