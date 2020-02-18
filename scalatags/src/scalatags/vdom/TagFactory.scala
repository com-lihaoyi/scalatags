package scalatags
package vdom

trait TagFactory[TypedTag] {
  def tag(s: String, void: Boolean = false): TypedTag
}
