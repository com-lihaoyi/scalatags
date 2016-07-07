package scalatags
package text
import acyclic.file

import scala.reflect.ClassTag

/**
 * Object to aggregate the modifiers into one coherent data structure
 * so the final HTML string can be properly generated. It's really
 * gross internally, but bloody fast. Even using pre-built data structures
 * like `mutable.Buffer` slows down the benchmarks considerably. Also
 * exposes more of its internals than it probably should for performance,
 * so even though the stuff isn't private, don't touch it!
 */
class Builder(var children: Array[Frag] = new Array(4),
              var attrs: Array[(String, String)] = new Array(4)){
  final var childIndex = 0
  final var attrIndex = 0

  private[this] def increment[T: ClassTag](arr: Array[T], index: Int) = {
    if (index >= arr.length){
      val newArr = new Array[T](arr.length * 2)
      var i = 0
      while(i < arr.length){
        newArr(i) = arr(i)
        i += 1
      }
      newArr
    }else{
      null
    }
  }
  def addChild(c: Frag) = {
    val newChildren = increment(children, childIndex)
    if (newChildren != null) children = newChildren
    children(childIndex) = c
    childIndex += 1
  }
  def appendAttr(k: String, v: String) = {

    attrIndex(k) match{
      case -1 =>
        val newAttrs = increment(attrs, attrIndex)
        if (newAttrs!= null) attrs = newAttrs

        attrs(attrIndex) = k -> v
        attrIndex += 1
      case n =>
        val (oldK, oldV) = attrs(n)
        attrs(n) = (oldK, oldV + v)
    }
  }
  def setAttr(k: String, v: String) = {
    attrIndex(k) match{
      case -1 =>
        val newAttrs = increment(attrs, attrIndex)
        if (newAttrs!= null) attrs = newAttrs
        attrs(attrIndex) = k -> v
        attrIndex += 1
      case n =>
        val (oldK, oldV) = attrs(n)
        attrs(n) = (oldK, v)
    }
  }
  def attrIndex(k: String): Int = {
    attrs.indexWhere(x => x != null && x._1 == k)
  }
}
trait Frag extends generic.Frag[Builder, String]{
  /**
    * Write into a string buffer.
    *
    * @param strb     buffer to write into
    * @param indentBy the amount to indent per unit depth
    * @param depth    depth of this node
    * @param fromTag  true if the previous node to be written to strb is a tag
    * @return         true if the last thing to be written to strb by this method was a tag
    */
  def writeTo(strb: StringBuilder, indentBy: Int, depth: Int, fromTag: Boolean): Boolean
  override def applyTo(b: Builder) = b.addChild(this)
}
