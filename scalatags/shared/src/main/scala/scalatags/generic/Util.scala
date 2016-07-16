package scalatags
package generic
import java.util.Objects

import acyclic.file

import scala.language.higherKinds

/**
 * Created by haoyi on 6/2/14.
 */
trait Util[Builder, Output <: FragT, FragT] extends LowPriUtil[Builder, Output, FragT]{

  type ConcreteHtmlTag[T <: Output] <: TypedTag[Builder, T, FragT]
  def tag(s: String, void: Boolean = false): TypedTag[Builder, Output, FragT]
  def makeAbstractTypedTag[T <: Output](tag: String, void: Boolean, namespaceConfig: Namespace): ConcreteHtmlTag[T]
  protected[this] implicit def stringAttrX: AttrValue[Builder, String]
  protected[this] implicit def stringStyleX: StyleValue[Builder, String]
  protected[this] implicit def stringPixelStyleX: PixelStyleValue[Builder, String]

  /**
    * Constructs an [[Attr]] attribute object from a string; can be used inline:
    *
    * {{{
    *   div(
    *     attr("hello-world-special-attr") := "foo
    *   )
    * }}}
    *
    * Or assigned to a name and used later
    *
    *
    * {{{
    *   val hello = attr("hello-world-special-attr")
    *   div(
    *     hello := "foo
    *   )
    * }}}
    */
  def attr(s: String, ns: Namespace = null, raw: Boolean = false) = Attr(s, Option(ns), raw)

  /**
    * Constructs a CSS [[Style]] from a string, can be used inline
    *
    * {{{
    *   div(
    *     css("-moz-special-style") := "foo"
    *   )
    * }}}
    *
    * Or assigned to a name and used later
    *
    * {{{
    *   val mozSpecial := css("-moz-special-style")
    *   div(
    *     mozSpecial := "foo"
    *   )
    * }}}
    */
  def css(s: String) = Style(camelCase(s), s)


  /**
   * Allows you to modify a [[ConcreteHtmlTag]] by adding a Seq containing other nest-able
   * objects to its list of children.
   */
  implicit class SeqNode[A](xs: Seq[A])(implicit ev: A => Modifier[Builder]) extends Modifier[Builder]{
    Objects.requireNonNull(xs)
    def applyTo(t: Builder) = xs.foreach(_.applyTo(t))
  }

  /**
   * Allows you to modify a [[ConcreteHtmlTag]] by adding an Option containing other nest-able
   * objects to its list of children.
   */
  implicit def OptionNode[A](xs: Option[A])(implicit ev: A => Modifier[Builder]) = new SeqNode(xs.toSeq)

  /**
   * Allows you to modify a [[ConcreteHtmlTag]] by adding an Array containing other nest-able
   * objects to its list of children.
   */
  implicit def ArrayNode[A](xs: Array[A])(implicit ev: A => Modifier[Builder]) = new SeqNode[A](xs.toSeq)


}

trait LowPriUtil[Builder, Output <: FragT, FragT]{
  /**
   * Renders an Seq of [[FragT]] into a single [[FragT]]
   */
  implicit def SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag[Builder, FragT]): Frag[Builder, FragT]

  /**
   * Renders an Option of [[FragT]] into a single [[FragT]]
   */
  implicit def OptionFrag[A](xs: Option[A])(implicit ev: A => Frag[Builder, FragT]) = SeqFrag(xs.toSeq)

  /**
   * Renders an Seq of [[FragT]] into a single [[FragT]]
   */
  implicit def ArrayFrag[A](xs: Array[A])(implicit ev: A => Frag[Builder, FragT]) = SeqFrag[A](xs.toSeq)

  /**
   * Lets you put Unit into a scalatags tree, as a no-op.
   */
  implicit def UnitFrag(u: Unit): Frag[Builder, FragT]
}
