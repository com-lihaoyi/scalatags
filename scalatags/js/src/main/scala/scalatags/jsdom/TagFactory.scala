package scalatags.jsdom

import org.scalajs.dom

import scalatags.Escaping
import scalatags.generic.Namespace

/**
  * Created by haoyi on 7/9/16.
  */
trait TagFactory extends scalatags.generic.Util[dom.Element, dom.Element, dom.Node]{
  def tag(s: String, void: Boolean = false)(implicit ns: Namespace): ConcreteHtmlTag[dom.Element] = {
    typedTag[dom.Element](s, void)(ns)
  }
  def typedTag[T <: dom.Element](s: String, void: Boolean = false)
                                (implicit ns: Namespace): ConcreteHtmlTag[T] = {
    if (!Escaping.validTag(s))
      throw new IllegalArgumentException(
        s"Illegal tag name: $s is not a valid XML tag name"
      )
    makeAbstractTypedTag[T](s, void, ns)
  }
}
