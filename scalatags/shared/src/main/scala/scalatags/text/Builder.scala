package scalatags
package text
import acyclic.file

import scalatags.generic.Style


/**
 * Object to aggregate the modifiers into one coherent data structure
 * so the final HTML string can be properly generated. It's really
 * gross internally, but bloody fast. Even using pre-built data structures
 * like `mutable.Buffer` slows down the benchmarks considerably. Also
 * exposes more of its internals than it probably should for performance,
 * so even though the stuff isn't private, don't touch it!
 */
class Builder(var children: Array[Frag] = null,
              var attrs: Array[(String, Builder.ValueSource)] = null){
  final var childIndex = 0
  final var attrIndex = 0

  private[this] def incrementChildren(arr: Array[Frag], index: Int) = {
    if (children == null) new Array[Frag](4)
    else if (index < arr.length) children
    else {
      val newArr = new Array[Frag](arr.length * 2)
      System.arraycopy(arr, 0, newArr, 0, arr.length)
      newArr
    }
  }

  private[this] def incrementAttr(arr: Array[(String, Builder.ValueSource)], index: Int) = {
    if (attrs == null) new Array[(String, Builder.ValueSource)](4)
    else if (index < arr.length) attrs
    else {
      val newArr = new Array[(String, Builder.ValueSource)](arr.length * 2)
      System.arraycopy(arr, 0, newArr, 0, arr.length)
      newArr
    }
  }

  def addChild(c: Frag) = {
    children = incrementChildren(children, childIndex)
    children(childIndex) = c
    childIndex += 1
  }

  def appendAttr(k: String, v: Builder.ValueSource) = {

    attrIndex(k) match{
      case -1 =>
        attrs = incrementAttr(attrs, attrIndex)

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

  def attrIndex(k: String): Int = {
    var i = 0
    var found = -1
    while(found == -1 && i < attrIndex){
      if (attrs(i)._1 == k) found = i
      i += 1
    }
    found
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

