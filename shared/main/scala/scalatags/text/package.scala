package scalatags

import scalatags.generic._
import scala.collection.SortedMap
import scalatags.generic.Style
import scalatags.generic.Attr

/**
 * Created by haoyi on 6/3/14.
 */
package object text {

  /**
   * Lets you put numbers into a scalatags tree, as a no-op.
   */
  implicit def NumericModifier[T: Numeric](u: T) = new StringNode(u.toString)

  /**
   * Allows you to modify a HtmlTag by adding a String to its list of children
   */
  implicit def stringNode(v: String) = new StringNode(v)
  /**
   * A [[Node]] which contains a String.
   */
  case class StringNode(v: String) extends Node[StringBuilder] {
    def writeTo(strb: StringBuilder): Unit = Escaping.escape(v, strb)
  }

  def raw(s: String) = new RawNode(s)
  /**
   * A [[Node]] which contains a String which will not be escaped.
   */
  case class RawNode(v: String) extends Node[StringBuilder] {
    def writeTo(strb: StringBuilder): Unit = strb ++= v
  }

  class GenericAttr(val writer: StringBuilder => Unit) extends AttrVal[StringBuilder]{
    override def applyTo(strb: StringBuilder, k: Attr): Unit = {
      strb ++= k.name ++= "=\""
      applyPartial(strb)
      strb ++= "\""
    }
    def applyPartial(strb: StringBuilder) = writer(strb)
    def merge(o: AttrVal[StringBuilder]) = new GenericAttr({ strb =>
      applyPartial(strb)
      strb ++= " "
      o.applyPartial(strb)
    })
  }
  case class StringAttr(s: String) extends GenericAttr(Escaping.escape(s, _))
  case class BooleanAttr(b: Boolean) extends GenericAttr(_ ++= b.toString)
  case class NumericAttr[T: Numeric](n: T) extends GenericAttr(_ ++= n.toString)
  implicit def stringAttr(s: String) = new StringAttr(s)
  implicit def booleanAttr(b: Boolean) = new BooleanAttr(b)
  implicit def numericAttr[T: Numeric](n: T) = new NumericAttr(n)


  class GenericStyle(writer: StringBuilder => Unit) extends StyleVal[StringBuilder]{
    override def applyTo(strb: StringBuilder, k: Style): Unit = {
      strb ++= k.cssName ++= ": "
      writer(strb)
      strb ++= ";"
    }
  }
  case class StringStyle(s: String) extends GenericStyle(Escaping.escape(s, _))
  case class BooleanStyle(b: Boolean) extends GenericStyle(_ ++= b.toString)
  case class NumericStyle[T: Numeric](n: T) extends GenericStyle(_ ++= n.toString)
  implicit def stringStyle(s: String) = new StringStyle(s)
  implicit def booleanStyle(b: Boolean) = new BooleanStyle(b)
  implicit def numericStyle[T: Numeric](n: T) = new NumericStyle(n)



}
