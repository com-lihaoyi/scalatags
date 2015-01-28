package scalatags
package jsdom
import acyclic.file
import org.scalajs.dom

trait Frag extends generic.Frag[dom.Element, dom.Node]{
  def render: dom.Node
  def applyTo(b: dom.Element) = b.appendChild(this.render)
}
