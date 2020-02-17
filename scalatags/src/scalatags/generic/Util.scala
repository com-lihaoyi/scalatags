package scalatags
package generic
import java.util.Objects

import scala.language.higherKinds

/**
 * Created by haoyi on 6/2/14.
 */
trait Util[Builder, Output <: FragT, FragT] {

  type ConcreteHtmlTag[T <: Output]

  def frag(frags: Frag[Builder, FragT]*): Frag[Builder, FragT] = ??? //SeqFrag(frags)
  def modifier(mods: Modifier[Builder]*): Modifier[Builder] = ??? //SeqNode(mods)

  def tag(s: String, void: Boolean = false): ConcreteHtmlTag[Output]
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
}

