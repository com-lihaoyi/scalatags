package scalatags
package generic

import acyclic.file
import scala.collection.SortedMap
import scalatags.Platform.Base
import scalatags.generic


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
  def applyTo(t: Target): Unit
}


/**
 * A generic representation of a Scalatags tag.
 *
 * @tparam T The base type that this tag represents. On Scala-JVM, this is all
 *           `Nothing`, while on ScalaJS this could be the `dom.XXXElement`
 *           associated with that tag name.
 */
trait TypedTag[+T <: Base, Target] extends Modifier[Target]{
  protected[this] type Self <: TypedTag[T, Target]
  def tag: String
  def modifiers: List[Modifier[Target]]


  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the [[TypedTag]].
   */
  def apply(xs: Modifier[Target]*): Self
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
  def :=[Target, T](v: T)(implicit ev: AttrValue[Target, T]) = AttrPair(this, v, ev)
}

/**
 * A [[Style]] which does not have a particular type, and takes strings as its
 * values
 */
case class Style(jsName: String, cssName: String) {
  def :=[Target, T](v: T)(implicit ev: StyleValue[Target, T]) = StylePair(this, v, ev)
}
case class AttrPair[Target, T](a: Attr, v: T, ev: AttrValue[Target, T]) extends Modifier[Target] {
  override def applyTo(t: Target): Unit = {
    ev.apply(t, a, v)
  }
}
trait AttrValue[Target, T]{
  def apply(t: Target, a: Attr, v: T)
}
case class StylePair[Target, T](s: Style, v: T, ev: StyleValue[Target, T]) extends Modifier[Target]{
  override def applyTo(t: Target): Unit = {
    ev.apply(t, s, v)
  }
}
trait StyleValue[Target, T]{
  def apply(t: Target, s: Style, v: T)
}
