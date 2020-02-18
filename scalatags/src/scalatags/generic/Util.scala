package scalatags
package generic
import java.util.Objects

import scala.language.higherKinds

/**
 * Created by haoyi on 6/2/14.
 */
trait Util[Output, Modifier, Frag, TypedTag[_ <: Output], Style] {


  def frag(frags: Frag*): Frag //SeqFrag(frags)
  def modifier(mods: Modifier*): Modifier //SeqNode(mods)

  def tag(s: String, void: Boolean = false): TypedTag[Output]
  def makeAbstractTypedTag[T <: Output](tag: String, void: Boolean, namespaceConfig: Namespace): TypedTag[T]
  def css(s: String): Style

  type HtmlTag
  type SvgTag
  type Tag = TypedTag[Output]
}

