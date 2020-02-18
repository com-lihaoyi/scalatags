package scalatags
package generic

import java.util.Objects.requireNonNull

import scala.annotation.implicitNotFound

trait Core {
  protected type FragT
  protected type Output <: FragT
  protected type Builder

  /**
   * Represents a value that can be nested within a [[scalatags.generic.TypedTag]]. This can be
   * another [[scalatags.generic.Modifier]], but can also be a CSS style or HTML attribute binding,
   * which will add itself to the node's attributes but not appear in the final
   * `children` list.
   */
  trait Modifier {
    /**
     * Applies this modifier to the specified [], such that when
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
  trait Frag extends Modifier {
    def render: FragT
  }


  /**
   * Wraps up a HTML attribute in a value which isn't a string.
   *
   * @param name      the name of this particular attribute
   * @param namespace an XML [[Namespace]] that this attribute lives in
   * @param raw       all [[Attr]]s are checked to fail fast if their names are
   *                  invalid XML attrs; flagging them as [[raw]] disables the checks
   *                  in the few cases you actually want invalid XML attrs
   *                  (e.g. AngularJS)
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
    def :=[T](v: T)(implicit ev: AttrValue[T]) = {
      requireNonNull(v)
      AttrPair(this, v, ev)
    }

    def empty(implicit ev: AttrValue[String]) = this := name
  }
  object Attr{


    implicit object attrOrdering extends Ordering[Attr]{
      override def compare(x: Attr, y: Attr): Int = x.name compareTo y.name
    }
  }
  /**
   * Wraps up a CSS style in a value.
   */
  case class Style(jsName: String, cssName: String) {
    /**
     * Creates an [[StylePair]] from an [[Style]] and a value of type [[T]], if
     * there is an [[StyleValue]] of the correct type.
     */
    def :=[T](v: T)(implicit ev: StyleValue[T]) = {
      requireNonNull(v)
      StylePair(this, v, ev)
    }
  }
  object Style{
    implicit object styleOrdering extends Ordering[Style]{
      override def compare(x: Style, y: Style): Int = x.cssName compareTo y.cssName
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
    def :=[T](v: T)(implicit ev: PixelStyleValue[T]) = {
      requireNonNull(v)
      ev(realStyle, v)
    }

  }

  trait StyleProcessor {
    def apply[T](t: T): String
  }

  /**
   * An [[Attr]], it's associated value, and an [[AttrValue]] of the correct type
   */
  case class AttrPair[T](a: Attr, v: T, ev: AttrValue[T]) extends Modifier {
    override def applyTo(t: Builder): Unit = {
      ev.apply(t, a, v)
    }

    def :=[T](v: T)(implicit ev: AttrValue[T]) = {
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
  trait AttrValue[T] {
    def apply(t: Builder, a: Attr, v: T): Unit
  }

  /**
   * A [[Style]], it's associated value, and a [[StyleValue]] of the correct type
   */
  case class StylePair[T](s: Style, v: T, ev: StyleValue[T]) extends Modifier {
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
  trait StyleValue[T] {
    def apply(t: Builder, s: Style, v: T): Unit
  }

  @implicitNotFound(
    "No PixelStyleValue defined for type ${T}; scalatags does not know how to use ${T} as an style"
  )
  trait PixelStyleValue[T] {
    def apply(s: Style, v: T): StylePair[_]
  }

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
