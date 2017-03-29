package scalatags
package text
import acyclic.file

import scala.reflect.ClassTag
import scalatags.generic.Style

/**
 * Object to aggregate the modifiers into one coherent data structure
 * so the final HTML string can be properly generated. It's really
 * gross internally, but bloody fast. Even using pre-built data structures
 * like `mutable.Buffer` slows down the benchmarks considerably. Also
 * exposes more of its internals than it probably should for performance,
 * so even though the stuff isn't private, don't touch it!
 */
class Builder(var children: Array[Frag] = new Array(4),
              var attrs: Array[(String, Builder.ValueSource)] = new Array(4)){
  final var childIndex = 0
  final var attrIndex = 0

  private[this] def incrementChidren(arr: Array[Frag], index: Int) = {
    if (index >= arr.length){
      val newArr = new Array[Frag](arr.length * 2)
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

  private[this] def incrementAttr(arr: Array[(String, Builder.ValueSource)], index: Int) = {
    if (index >= arr.length){
      val newArr = new Array[(String, Builder.ValueSource)](arr.length * 2)
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
    val newChildren = incrementChidren(children, childIndex)
    if (newChildren != null) children = newChildren
    children(childIndex) = c
    childIndex += 1
  }
  def appendAttr(k: String, v: Builder.ValueSource) = {

    attrIndex(k) match{
      case -1 =>
        val newAttrs = incrementAttr(attrs, attrIndex)
        if (newAttrs!= null) attrs = newAttrs

        attrs(attrIndex) = k -> v
        attrIndex += 1
      case n =>
        val (oldK, oldV) = attrs(n)
        attrs(n) = (oldK, Builder.ChainedAttributeValueSource(oldV, v))
    }
  }
  def setAttr(k: String, v: Builder.ValueSource) = {
    attrIndex(k) match{
      case -1 =>
        val newAttrs = incrementAttr(attrs, attrIndex)
        if (newAttrs!= null) attrs = newAttrs
        attrs(attrIndex) = k -> v
        attrIndex += 1
      case n =>
        val (oldK, oldV) = attrs(n)
        attrs(n) = (oldK, Builder.ChainedAttributeValueSource(oldV, v))
    }
  }


  def appendAttrStrings(v: Builder.ValueSource, sb: StringBuilder): Unit = {
    v.appendAttrValue(sb)
  }

  def attrsString(v: Builder.ValueSource): String = {
    val sb = new StringBuilder
    appendAttrStrings(v, sb)
    sb.toString
  }



  def attrIndex(k: String): Int = {
    attrs.indexWhere(x => x != null && x._1 == k)
  }
}
object Builder{

  /**
    * More-or-less internal trait, used to package up the parts of a textual
    * attribute or style so that we can append the chunks directly to the
    * output buffer. Improves perf over immediately combining them into a
    * string and storing that, since this avoids allocating that intermediate
    * string.
    */
  trait ValueSource {
    def appendAttrValue(strb: StringBuilder): Unit
  }
  case class StyleValueSource(s: Style, v: String) extends ValueSource {
    override def appendAttrValue(strb: StringBuilder): Unit = {
      Escaping.escape(s.cssName, strb)
      strb ++=  ": "
      Escaping.escape(v, strb)
      strb ++= ";"
    }
  }

  case class GenericAttrValueSource(v: String) extends ValueSource {
    override def appendAttrValue(strb: StringBuilder): Unit = {
      Escaping.escape(v, strb)
    }
  }

  case class ChainedAttributeValueSource(head: ValueSource, tail: ValueSource) extends ValueSource {
    override def appendAttrValue(strb: StringBuilder): Unit = {
      head.appendAttrValue(strb)
      tail.appendAttrValue(strb)
    }
  }
}

trait Frag extends generic.Frag[Builder, String]{
  def writeTo(strb: StringBuilder): Unit
  def render: String
  def applyTo(b: Builder) = b.addChild(this)
}

