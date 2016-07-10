package scalatags
package generic

import java.util.Objects.requireNonNull

import acyclic.file

import scala.annotation.implicitNotFound


/**
 * Represents a value that can be nested within a [[scalatags.generic.TypedTag]]. This can be
 * another [[scalatags.generic.Modifier]], but can also be a CSS style or HTML attribute binding,
 * which will add itself to the node's attributes but not appear in the final
 * `children` list.
 */
trait Modifier[Builder] {
  /**
   * Applies this modifier to the specified [[Builder]], such that when
   * rendering is complete the effect of adding this modifier can be seen.
   */
  def applyTo(t: Builder): Unit
}

/**
 * Marker sub-type of [[scalatags.generic.Modifier]] which signifies that that type can be
 * rendered as a standalone fragment of [[FragT]]. This excludes things
 * like [[scalatags.generic.AttrPair]]s or [[scalatags.generic.StylePair]]s which only make sense as part of
 * a parent fragment
 */
trait Frag[Builder, +FragT] extends Modifier[Builder]{
  def render: FragT
}

/**
 * A generic representation of a Scalatags tag.
 *
 * @tparam Output The base type that this tag represents. On Scala-JVM, this is all
 *           `Nothing`, while on ScalaJS this could be the `dom.XXXElement`
 *           associated with that tag name.
 */
trait TypedTag[Builder, +Output <: FragT, +FragT] extends Frag[Builder, FragT]{
  protected[this] type Self <: TypedTag[Builder, Output, FragT]
  def tag: String

  /**
   * The modifiers that are applied to a TypedTag are kept in this linked-Seq
   * (which are actually WrappedArrays) data-structure in order for maximum
   * performance.
   */
  def modifiers: List[Seq[Modifier[Builder]]]

  /**
   * Walks the [[modifiers]] to apply them to a particular [[Builder]].
   * Super sketchy/procedural for max performance.
   */
  def build(b: Builder): Unit = {
    var current = modifiers
    val arr = new Array[Seq[Modifier[Builder]]](modifiers.length)

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
  def apply(xs: Modifier[Builder]*): Self

  /**
   * Collapses this scalatags tag tree and returns an [[Output]]
   */
  def render: Output
}

/**
  * Wraps up a HTML attribute in a value which isn't a string.
  *
  * @param name the name of this particular attribute
  * @param namespace an XML [[Namespace]] that this attribute lives in
  * @param raw all [[Attr]]s are checked to fail fast if their names are
  *            invalid XML attrs; flagging them as [[raw]] disables the checks
  *            in the few cases you actually want invalid XML attrs
  *            (e.g. AngularJS)
  */
case class Attr(name: String, namespace: Option[Namespace] = None, raw: Boolean = false) {

  if (!raw && !Escaping.validAttrName(name)) {
    throw new IllegalArgumentException(
      s"Illegal attribute name: $name is not a valid XML attribute name"
    )
  }
  /**
   * Creates an [[AttrPair]] from an [[Attr]] and a value of type [[T]], if
   * there is an [[AttrValue]] of the correct type.
   */
  def :=[Builder, T](v: T)(implicit ev: AttrValue[Builder, T]) = {
    requireNonNull(v)
    AttrPair(this, v, ev)
  }

  def empty[Builder](implicit ev: AttrValue[Builder, String]) = this := name
}

/**
 * Wraps up a CSS style in a value.
 */
case class Style(jsName: String, cssName: String) {
  /**
   * Creates an [[StylePair]] from an [[Style]] and a value of type [[T]], if
   * there is an [[StyleValue]] of the correct type.
   */
  def :=[Builder, T](v: T)(implicit ev: StyleValue[Builder, T]) = {
    requireNonNull(v)
    StylePair(this, v, ev)
  }
}
/**
 * Wraps up a CSS style in a value.
 */
case class PixelStyle(jsName: String, cssName: String) {
  val realStyle = Style(jsName, cssName)
  /**
   * Creates an [[StylePair]] from an [[Style]] and a value of type [[T]], if
   * there is an [[StyleValue]] of the correct type.
   */
  def :=[Builder, T](v: T)(implicit ev: PixelStyleValue[Builder, T]) = {
    requireNonNull(v)
    ev(realStyle, v)
  }

}
trait StyleProcessor{
  def apply[T](t: T): String
}
/**
 * An [[Attr]], it's associated value, and an [[AttrValue]] of the correct type
 */
case class AttrPair[Builder, T](a: Attr, v: T, ev: AttrValue[Builder, T]) extends Modifier[Builder] {
  override def applyTo(t: Builder): Unit = {
    ev.apply(t, a, v)
  }
  def :=[Builder, T](v: T)(implicit ev: AttrValue[Builder, T]) = {
    requireNonNull(v)
    AttrPair(a, v, ev)
  }
}
/**
 * Used to specify how to handle a particular type [[T]] when it is used as
 * the value of a [[Attr]]. Only types with a specified [[AttrValue]] may
 * be used.
 */
@implicitNotFound(
  "No AttrValue defined for type ${T}; scalatags does not know how to use ${T} as an attribute"
)
trait AttrValue[Builder, T]{
  def apply(t: Builder, a: Attr, v: T)
}

/**
 * A [[Style]], it's associated value, and a [[StyleValue]] of the correct type
 */
case class StylePair[Builder, T](s: Style, v: T, ev: StyleValue[Builder, T]) extends Modifier[Builder]{
  override def applyTo(t: Builder): Unit = {
    ev.apply(t, s, v)
  }
}

/**
 * Used to specify how to handle a particular type [[T]] when it is used as
 * the value of a [[Style]]. Only types with a specified [[StyleValue]] may
 * be used.
 */
@implicitNotFound(
  "No StyleValue defined for type ${T}; scalatags does not know how to use ${T} as an style"
)
trait StyleValue[Builder, T]{
  def apply(t: Builder, s: Style, v: T): Unit
}

@implicitNotFound(
  "No PixelStyleValue defined for type ${T}; scalatags does not know how to use ${T} as an style"
)
trait PixelStyleValue[Builder, T]{
  def apply(s: Style, v: T): StylePair[Builder, _]
}

/**
 * Represents a single XML namespace. This is currently ignored in `scalatags.Text`,
 * but used to create elements with the correct namespace in `scalatags.JsDom`. A
 * [[Namespace]] can be provided implicitly (or explicitly) when creating tags via
 * `"".tag`, with a default of "http://www.w3.org/1999/xhtml" if none is found.
 */
trait Namespace {
  def uri: String
}
object Namespace{
  implicit val htmlNamespaceConfig = new Namespace {
    def uri = "http://www.w3.org/1999/xhtml"
  }
  val svgNamespaceConfig = new Namespace {
    def uri = "http://www.w3.org/2000/svg"
  }
  val svgXlinkNamespaceConfig = new Namespace {
    def uri = "http://www.w3.org/1999/xlink"
  }
}
