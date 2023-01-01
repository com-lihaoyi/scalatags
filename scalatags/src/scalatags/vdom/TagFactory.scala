package scalatags
package vdom

import scalatags.Escaping
import scalatags.generic.Namespace

/**
 * Created by haoyi on 7/9/16.
 */
trait TagFactory[Output <: FragT, FragT]
    extends scalatags.generic.Util[Builder[Output, FragT], Output, FragT] {
  def tag(s: String, void: Boolean = false): ConcreteHtmlTag[Output] = {
    if (!Escaping.validTag(s))
      throw new IllegalArgumentException(
        s"Illegal tag name: $s is not a valid XML tag name"
      )
    makeAbstractTypedTag[Output](s, void, scalatags.generic.Namespace.htmlNamespaceConfig)
  }
}
