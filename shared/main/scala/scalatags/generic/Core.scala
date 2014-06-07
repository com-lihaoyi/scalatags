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
trait Node[Target] {
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
trait TypedTag[+T <: Base, Target] extends Node[Target]{
  protected[this] type Self <: TypedTag[T, Target]
  def tag: String

  /**
   * The modifiers that are applied to a TypedTag are kept in this linked-Seq
   * (which are actually WrappedArrays) data-structure in order for maximum
   * performance.
   */
  def modifiers: List[Seq[Node[Target]]]

  /**
   * Walks the [[modifiers]] to apply them to a particular [[Target]].
   * Super sketchy/procedural for max performance.
   */
  def build(b: Target): Unit = {
    var current = modifiers
    val arr = new Array[Seq[Node[Target]]](modifiers.length)

    var i = 0
    while(current != Nil){
      arr(i) = current.head
      current =  current.tail
      i += 1
    }

    var j = arr.length
    while (j > 0) {
      j -= 1
      val frag = arr(j)
      var i = 0
      while(i < frag.length){
        frag(i).applyTo(b)
        i += 1
      }
    }
  }
  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the [[TypedTag]].
   */
  def apply(xs: Node[Target]*): Self
}

/**
 * Wraps up a HTML attribute in a value.
 */
case class Attr(name: String) {

  if (!Escaping.validAttrName(name))
    throw new IllegalArgumentException(
      s"Illegal attribute name: $name is not a valid XML attribute name"
    )

  /**
   * Creates an [[AttrPair]] from an [[Attr]] and a value of type [[T]], if
   * there is an [[AttrValue]] of the correct type.
   */
  def :=[Target, T](v: T)(implicit ev: AttrValue[Target, T]) = AttrPair(this, v, ev)
}

/**
 * Wraps up a CSS style in a value.
 */
case class Style(jsName: String, cssName: String) {
  /**
   * Creates an [[StylePair]] from an [[Style]] and a value of type [[T]], if
   * there is an [[StyleValue]] of the correct type.
   */
  def :=[Target, T](v: T)(implicit ev: StyleValue[Target, T]) = StylePair(this, v, ev)
}
case class AttrPair[Target, T](a: Attr, v: T, ev: AttrValue[Target, T]) extends Node[Target] {
  override def applyTo(t: Target): Unit = {
    ev.apply(t, a, v)
  }
}
trait AttrValue[Target, T]{
  def apply(t: Target, a: Attr, v: T)
}
case class StylePair[Target, T](s: Style, v: T, ev: StyleValue[Target, T]) extends Node[Target]{
  override def applyTo(t: Target): Unit = {
    ev.apply(t, s, v)
  }
}
trait StyleValue[Target, T]{
  def apply(t: Target, s: Style, v: T)
}
