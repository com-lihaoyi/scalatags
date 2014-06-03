package scalatags

import acyclic.file
import scala.collection.{SortedMap, mutable}
import scalatags.Platform.Base



/**
 * A general interface for all XML types which can appear in a ScalaTags fragment.
 */
trait Node[Target] extends Modifier[Target] {


  /**
   * Appends the textual representation of the ScalaTag fragment to the
   * 'strb' StringBuilder. Used to optimize the [[toString( )]] operation.
   */
  def writeTo(strb: Target): Unit

  def transforms = Array(Mod.Child(this))
}

/**
 * Represents a value that can be nested within a [[Node]]. This can be another
 * [[Node]], but can also be a CSS style or HTML attribute binding, which will
 * add itself to the node's attributes but not appear in the final `children`
 * list.
 */
trait Modifier[Target] {
  /**
   * Transforms the tag and returns a new one.
   *
   * Can't be `apply`, because some [[Modifier]]s (e.g. [[HtmlTag]]) already have an
   * [[TypedHtmlTag.apply]] method, and the overloading becomes ambiguous.
   */
  def transforms: Array[Mod[Target]]
}


/**
 * A single HTML tag.
 *
 * @param tag The name of the tag
 * @param children A backwards list of child-nodes; kept this way for fast
 *                 updates, and reversed before being rendered.
 * @param attrs A sorted map of attributes
 * @param void Whether or not the tag can be self-closing
 */
trait AbstractTypedHtmlTag[T <: Base, Target] {

  type Self
  def tag: String
  def children: List[Node[Target]]
  def attrs: SortedMap[String, AttrVal[Target]]
  def classes: List[Any]
  def styles: SortedMap[Style, StyleVal[Target]]
  def void: Boolean
  def copy(children: List[Node[Target]],
           attrs: SortedMap[String, AttrVal[Target]],
           classes: List[Any],
           styles: SortedMap[Style, StyleVal[Target]]): Self

  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the [[HtmlTag]].
   */
  def apply(xs: Modifier[Target]*) = {

    var children = this.children
    var attrs = this.attrs
    var classes = this.classes
    var styles = this.styles
    var i = 0
    while (i < xs.length) {
      val ts = xs(i).transforms
      var j = 0
      while (j < ts.length) {
        ts(j) match {
          case Mod.Attr(k, v) => attrs = attrs.updated(k, v)
          case Mod.Child(c) => children = c :: children
          case Mod.Cls(c) => classes = c :: classes
          case Mod.Style(k, v) => styles = styles.updated(k, v)
        }
        j += 1
      }
      i += 1
    }
    this.copy(
      children = children,
      attrs = attrs,
      classes = classes,
      styles = styles
    )
  }
}

/**
 * A key value pair representing the assignment of an attribute to a value.
 */
case class AttrPair[Target](attr: Attr, value: AttrVal[Target]) extends Modifier[Target] {
  def transforms = Array(Mod.Attr(attr.name, value))
}

/**
 * Wraps up a HTML attribute in an untyped value with an associated
 * type; the := operator takes Strings.
 */
case class Attr(name: String) {

  if (!Escaping.validAttrName(name))
    throw new IllegalArgumentException(
      s"Illegal attribute name: $name is not a valid XML attribute name"
    )

  /**
   * Force-assigns a typed attribute to a string even if its type would not
   * normally allow it.
   */
  def :=[Target](v: AttrVal[Target]) = AttrPair(this, v)

}

/**
 * A [[Style]] which does not have a particular type, and takes strings as its
 * values
 */
case class Style(jsName: String, cssName: String) {
  def :=[Target](value: StyleVal[Target]) = StylePair(this, value)
}

/**
 * A key value pair representing the assignment of a style to a value.
 */
case class StylePair[Target](style: Style, value: StyleVal[Target]) extends Modifier[Target] {
  def transforms = Array(Mod.Style(style, value))
}

trait StyleVal[Target] {
  def applyTo(t: Target, k: Style): Unit
}

trait ClsVal[Target] {
  def applyTo(t: Target): Unit
}

trait AttrVal[Target] {
  def applyTo(t: Target, k: Attr): Unit
}


/**
 * Things that a modifier is allowed to do to a node.
 *
 * It can only set attributes, or append children.
 */
sealed trait Mod[Target]

object Mod {
  case class Attr[Target](k: String, v: AttrVal[Target]) extends Mod[Target]
  case class Cls[Target](v: ClsVal[Target]) extends Mod[Target]
  case class Style[Target](k: scalatags.Style, v: StyleVal[Target]) extends Mod[Target]
  case class Child[Target](n: Node[Target]) extends Mod[Target]
}
