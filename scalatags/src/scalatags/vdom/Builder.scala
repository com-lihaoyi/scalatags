package scalatags
package vdom
trait Builder[Output, FragT]{
  def appendChild(child: FragT): Unit
  def appendClass(cls: String): Unit
  def appendStyle(cssName: String, value: String): Unit
  def setAttr(name: String, value: String): Unit
  def render(tag: String): Output
}

trait Frag[Output, FragT] extends scalatags.generic.Frag[Builder[Output, FragT], FragT]{
  def render: FragT
  def applyTo(b: Builder[Output, FragT]) = b.appendChild(this.render)
}