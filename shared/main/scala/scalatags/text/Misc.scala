package scalatags.text

import scala.collection.mutable
import scalatags.generic.Node
import scala.reflect.ClassTag

/**
 * Object to aggregate the modifiers into one coherent data structure
 * so the final HTML string can be properly generated. It's really
 * gross internally, but bloody fast. Even using pre-built data structures
 * like `mutable.Buffer` slows down the benchmarks considerably. Also
 * exposes more of its internals than it probably should for performance,
 * so even though the stuff isn't private, don't touch it!
 */
class Builder(var children: Array[Child] = new Array(4),
              var attrs: Array[(String, String)] = new Array(4)){
  final var childIndex = 0
  final var attrIndex = 0
  private[this] var styleIndex = -1
  private[this] def increment[T: ClassTag](arr: Array[T], index: Int) = {
    if (index >= arr.length){
      val newArr = new Array[T](children.length * 2)
      var i = 0
      while(i < children.length){
        newArr(i) = arr(i)
        i += 1
      }
      newArr
    }else{
      null
    }
  }
  def addChild(c: Child) = {
    val newChildren = increment(children, childIndex)
    if (newChildren != null) children = newChildren
    children(childIndex) = c
    childIndex += 1
  }
  def addAttr(k: String, v: String) = {
    (k, styleIndex) match{
      case ("style", -1) =>
        val newAttrs = increment(attrs, attrIndex)
        if (newAttrs!= null) attrs = newAttrs
        styleIndex = attrIndex
        attrs(attrIndex) = (k -> v)
        attrIndex += 1
      case ("style", n) =>
        val (oldK, oldV) = attrs(styleIndex)
        attrs(styleIndex) = (oldK, oldV + v)
      case _ =>
        val newAttrs = increment(attrs, attrIndex)
        if (newAttrs!= null) attrs = newAttrs
        attrs(attrIndex) = (k -> v)
        attrIndex += 1
    }
  }
}

trait Child extends Node[Builder]{
  def writeTo(strb: StringBuilder): Unit
  def applyTo(b: Builder) = b.addChild(this)
}