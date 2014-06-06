package scalatags
package generic

import acyclic.file
import scala.collection.SortedMap
import scalatags.Platform.Base
import scalatags.generic

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
   */
  def transforms: Array[Mod[Target]]
}


/**
 * A generic representation of a Scalatags tag.
 *
 * @tparam T The base type that this tag represents. On Scala-JVM, this is all
 *           `Nothing`, while on ScalaJS this could be the `dom.XXXElement`
 *           associated with that tag name.
 */
trait TypedTag[+T <: Base, Target] extends Node[Target]{
  protected[this] type Self <: TypedTag[T, Target]
  def tag: String
  def children: List[Node[Target]]
  def attrs: SortedMap[Attr, AttrVal[Target]]
  def styles: SortedMap[Style, StyleVal[Target]]
  def void: Boolean
  def transform(children: List[Node[Target]] = this.children,
                attrs: SortedMap[Attr, AttrVal[Target]] = this.attrs,
                styles: SortedMap[Style, StyleVal[Target]] = this.styles): Self

  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the [[TypedTag]].
   */
  def apply(xs: Modifier[Target]*): Self = {
    var children = this.children
    var attrs = this.attrs
    var styles = this.styles
    for(x <- xs){
      val ts = x.transforms
      var j = 0
      while (j < ts.length) {
        ts(j) match {
          case Mod.Attr(k, v) => attrs = attrs.updated(k, v)
          case Mod.Child(c) => children = c :: children
          case Mod.Style(k, v) => styles = styles.updated(k, v)
        }
        j += 1
      }
    }
    this.transform(
      children = children,
      attrs = attrs,
      styles = styles
    )
  }
}

/**
 * A key value pair representing the assignment of an attribute to a value.
 */
case class AttrPair[Target](attr: Attr, value: AttrVal[Target]) extends Modifier[Target] {
  def transforms = Array(Mod.Attr(attr, value))
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

/**
 * Represents a single value of a Style. This is often a string, but it
 * can be anything, as long as it can be applied to the specified [[Target]]
 * type
 */
trait StyleVal[Target] {
  def applyTo(t: Target, k: Style): Unit
}

/**
 * Represents a single value of a Attribute. This is often a string, but it
 * can be anything, as long as it can be applied to the specified [[Target]]
 * type
 */
trait AttrVal[Target] {
  def merge(o: AttrVal[Target]): AttrVal[Target]
  def applyPartial(t: Target): Unit
  def applyTo(t: Target, k: Attr): Unit
}

/**
 * Things that a modifier is allowed to do to a node.
 *
 * It can only set attributes, or append children.
 */
sealed trait Mod[Target]

object Mod {
  case class Attr[Target](k: generic.Attr, v: AttrVal[Target]) extends Mod[Target]
  case class Style[Target](k: generic.Style, v: StyleVal[Target]) extends Mod[Target]
  case class Child[Target](n: Node[Target]) extends Mod[Target]
}
