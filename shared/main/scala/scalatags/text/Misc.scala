package scalatags.text

import scala.collection.mutable
import scalatags.generic.Modifier


class Builder(var children: Array[Child] = new Array(4),
              var attrs: mutable.ArrayBuffer[(String, String)] = mutable.ArrayBuffer.empty){
  var childIndex = 0
  private[this] var styleIndex = -1
  def addChild(c: Child) = {
    if (childIndex >= children.length){
      val newChildren = new Array[Child](children.length * 2)
      var i = 0
      while (i < children.length){
        newChildren(i) = children(i)
        i += 1
      }
      children = newChildren
    }
    children(childIndex) = c
    childIndex += 1
  }
  def addAttr(k: String, v: String) = {
    (k, styleIndex) match{
      case ("style", -1) =>
        styleIndex = attrs.length
        attrs += (k -> v)
      case ("style", n) =>
        val (oldK, oldV) = attrs(styleIndex)
        attrs(styleIndex) = (oldK, oldV + v)
      case _ =>
        attrs += (k -> v)
    }
  }
}

trait Child extends Modifier[Builder]{
  def writeTo(strb: StringBuilder): Unit
  def applyTo(b: Builder) = b.addChild(this)
}