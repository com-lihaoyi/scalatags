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
class Builder(val children: StringBuilder){

  var mode: Option[String] = Some("")

  def addChild(c: Frag) = {
    if (mode.isDefined){
      if (mode.get.nonEmpty) children.append('"')

      children.append('>')
      mode = None
    }
    c.writeTo(children)
  }

  def appendAttr(k: String, v: Builder.ValueSource) = {
    mode match{
      case None =>
        throw new Exception("Cannot add attributes to tag after adding children")
      case Some(`k`) =>
        children.append(' ')
        v.appendAttrValue(children)
      case Some(x) =>
        if (x != "") children.append('"')
        children.append(' ')
        children.append(k)
        children.append("=\"")
        v.appendAttrValue(children)
    }
    mode = Some(k)
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

