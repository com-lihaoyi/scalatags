package scalatags
package generic

import acyclic.file
import scala.collection.SortedMap
import scalatags.Platform.Base
import scalatags.generic


/**
 * Represents a value that can be nested within a [[TypedTag]]. This can be
 * another [[Node]], but can also be a CSS style or HTML attribute binding,
 * which will add itself to the node's attributes but not appear in the final
 * `children` list.
 */
trait Node[Builder] {
  /**
   * Transforms the tag and returns a new one.
   */
  def applyTo(t: Builder): Unit
}

/**
 * A generic representation of a Scalatags tag.
 *
 * @tparam Output The base type that this tag represents. On Scala-JVM, this is all
 *           `Nothing`, while on ScalaJS this could be the `dom.XXXElement`
 *           associated with that tag name.
 */
trait TypedTag[+Output, Builder] extends Node[Builder]{
  protected[this] type Self <: TypedTag[Output, Builder]
  def tag: String

  /**
   * The modifiers that are applied to a TypedTag are kept in this linked-Seq
   * (which are actually WrappedArrays) data-structure in order for maximum
   * performance.
   */
  def modifiers: List[Seq[Node[Builder]]]

  /**
   * Walks the [[modifiers]] to apply them to a particular [[Builder]].
   * Super sketchy/procedural for max performance.
   */
  def build(b: Builder): Unit = {
    var current = modifiers
    val arr = new Array[Seq[Node[Builder]]](modifiers.length)

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
  def apply(xs: Node[Builder]*): Self
  def render: Output
}

/**
 * Wraps up a HTML attribute in a value which isn't a string.
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
  def :=[Builder, T](v: T)(implicit ev: AttrValue[Builder, T]) = AttrPair(this, v, ev)
}

/**
 * Wraps up a CSS style in a value.
 */
case class Style(jsName: String, cssName: String) {
  /**
   * Creates an [[StylePair]] from an [[Style]] and a value of type [[T]], if
   * there is an [[StyleValue]] of the correct type.
   */
  def :=[Builder, T](v: T)(implicit ev: StyleValue[Builder, T]) = StylePair(this, v, ev)
}
/**
 * An [[Attr]], it's associated value, and an [[AttrValue]] of the correct type
 */
case class AttrPair[Builder, T](a: Attr, v: T, ev: AttrValue[Builder, T]) extends Node[Builder] {
  override def applyTo(t: Builder): Unit = {
    ev.apply(t, a, v)
  }
}
/**
 * Used to specify how to handle a particular type [[T]] when it is used as
 * the value of a [[Attr]]. Only types with a specified [[AttrValue]] may
 * be used.
 */
trait AttrValue[Builder, T]{
  def apply(t: Builder, a: Attr, v: T)
}

/**
 * A [[Style]], it's associated value, and a [[StyleValue]] of the correct type
 */
case class StylePair[Builder, T](s: Style, v: T, ev: StyleValue[Builder, T]) extends Node[Builder]{
  override def applyTo(t: Builder): Unit = {
    ev.apply(t, s, v)
  }
}

/**
 * Used to specify how to handle a particular type [[T]] when it is used as
 * the value of a [[Style]]. Only types with a specified [[StyleValue]] may
 * be used.
 */
trait StyleValue[Builder, T]{
  def apply(t: Builder, s: Style, v: T)
}
