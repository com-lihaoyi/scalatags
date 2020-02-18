package scalatags.jsdom

import org.scalajs.dom

import scalatags.Escaping
import scalatags.generic.Namespace

/**
  * Created by haoyi on 7/9/16.
  */
trait TagFactory[TypedTag[_ <: dom.Element]] {
  private[jsdom] def tag(s: String, void: Boolean = false): TypedTag[dom.Element]
  def typedTag[T <: dom.Element](s: String, void: Boolean = false)
                                (implicit ns: scalatags.generic.Namespace): TypedTag[T]
}
