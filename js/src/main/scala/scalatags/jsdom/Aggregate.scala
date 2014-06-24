package scalatags
package jsdom

import org.scalajs.dom

trait Aggregate extends generic.Aggregate[dom.Element, dom.Element, dom.Node]{
  def genericAttr[T] = new JsDom.GenericAttr[T]
  def genericStyle[T] = new JsDom.GenericStyle[T]

  implicit def stringFrag(v: String) = new JsDom.StringFrag(v)

  object attrs extends JsDom.Cap with Attrs
  object tags extends JsDom.Cap with jsdom.Tags
  object tags2 extends JsDom.Cap with jsdom.Tags2
  object styles extends JsDom.Cap with Styles
  object styles2 extends JsDom.Cap with Styles2
  object svgTags extends JsDom.Cap with jsdom.SvgTags
  object svgStyles extends JsDom.Cap with SvgStyles

  val StringFrag: Companion[StringFrag]
  val RawFrag: Companion[RawFrag]
  type StringFrag <: JsDom.StringFrag
  type RawFrag <: JsDom.RawFrag
  def raw(s: String) = RawFrag(s)

  type HtmlTag = JsDom.TypedTag[dom.HTMLElement]
  val HtmlTag = JsDom.TypedTag
  type SvgTag = JsDom.TypedTag[dom.SVGElement]
  val SvgTag = JsDom.TypedTag
  type Tag = JsDom.TypedTag[dom.Element]
  val Tag = JsDom.TypedTag

  type Frag = jsdom.Frag
}
trait Frag extends generic.Frag[dom.Element, dom.Element, dom.Node]{
  def render: dom.Node
  def applyTo(b: dom.Element) = b.appendChild(this.render)
}