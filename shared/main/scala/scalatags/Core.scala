package scalatags

import acyclic.file
import scala.collection.{SortedMap, mutable}

/**
 * Represents a single CSS class.
 */
case class Cls(name: Any) extends Modifier{
  def transforms = Array(Mod.Cls(name))
}

/**
 * A [[scalatags.Node]] which contains a String.
 */
case class StringNode(v: String) extends Node{
  def writeTo(strb: StringBuilder): Unit = Escaping.escape(v, strb)
}

/**
 * A [[scalatags.Node]] which contains a String which will not be escaped.
 */
case class RawNode(v: String) extends Node{
  def writeTo(strb: StringBuilder): Unit = strb ++= v
}

/**
 * A general interface for all XML types which can appear in a ScalaTags fragment.
 */
trait Node extends Modifier{
  /**
   * Converts an ScalaTag fragment into an html string
   */
  override def toString = {
    val strb = new StringBuilder
    writeTo(strb)
    strb.toString()
  }

  /**
   * Appends the textual representation of the ScalaTag fragment to the
   * 'strb' StringBuilder. Used to optimize the [[toString()]] operation.
   */
  def writeTo(strb: StringBuilder): Unit

  def transforms = Array(Mod.Child(this))
}
/**
 * Represents a value that can be nested within a [[Node]]. This can be another
 * [[Node]], but can also be a CSS style or HTML attribute binding, which will
 * add itself to the node's attributes but not appear in the final `children`
 * list.
 */
trait Modifier{
  /**
   * Transforms the tag and returns a new one.
   *
   * Can't be `apply`, because some [[Modifier]]s (e.g. [[HtmlTag]]) already have an
   * [[TypedHtmlTag.apply]] method, and the overloading becomes ambiguous.
   */
  def transforms: Array[Mod]
}

/**
 * A single HTML tag.
 *
 * @param tag The name of the tag
 * @param children A backwards list of child-nodes; kept this way for fast
 *                 updates, and reversed before being rendered.
 * @param attrs A sorted map of attributes
 * @param void Whether or not the tag can be self-closing
 */
case class TypedHtmlTag[T](tag: String = "",
                           children: List[Node],
                           attrs: SortedMap[String, Any],
                           classes: List[Any],
                           styles: SortedMap[String, Any],
                           void: Boolean = false) extends Node{
  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the [[HtmlTag]].
   */
  def apply(xs: Modifier*) = {

    var children = this.children
    var attrs = this.attrs
    var classes = this.classes
    var styles = this.styles
    var i = 0
    while(i < xs.length){
      val ts = xs(i).transforms
      var j = 0
      while(j < ts.length){
        ts(j) match{
          case Mod.Attr(k, v) => attrs = attrs.updated(k, v)
          case Mod.Child(c) => children = c :: children
          case Mod.Cls(c) => classes = c :: classes
          case Mod.Style(k, v) => styles = styles.updated(k, v)
        }
        j += 1
      }
      i += 1
    }
    this.copy(
      children = children,
      attrs = attrs,
      classes = classes,
      styles = styles
    )
  }

  /**
   * Serialize this [[HtmlTag]] and all its children out to the given StringBuilder.
   */
  def writeTo(strb: StringBuilder): Unit = {
    // tag
    strb ++= "<" ++= tag
    var moddedAttrs = attrs
    if (!classes.isEmpty){
      moddedAttrs = moddedAttrs.updated(
        "class",
        (moddedAttrs.get("class") ++ classes.reverseIterator).mkString(" ")
      )
    }
    if (!styles.isEmpty){
      val styleStrings = styles.map{case (k, v) => s"$k: $v;"}.toList
      moddedAttrs = moddedAttrs.updated(
        "style",
        (moddedAttrs.get("style") ++ styleStrings).mkString(" ")
      )
    }
    // attributes
    for((attr, value) <- moddedAttrs){
      strb ++= " " ++= attr ++= "=\""
      Escaping.escape(value.toString, strb)
      strb ++= "\""
    }
    if(children.isEmpty && void)
      // No children - close tag
      strb ++= " />"
    else {
      strb ++= ">"
      // Childrens
      var x = children.reverse
      while(!x.isEmpty){
        val child :: newX = x
        x = newX
        child.writeTo(strb)
      }

      // Closing tag
      strb ++= "</" ++= tag ++= ">"
    }
  }
}

/**
 * A key value pair representing the assignment of an attribute to a value.
 */
case class AttrPair(attr: Attr, value: Any) extends Modifier{
  def transforms = Array(Mod.Attr(attr.name, value))
}

/**
 * Wraps up a HTML attribute in an untyped value with an associated
 * type; the := operator takes Strings.
 */
abstract class Attr{
  def name: String
  if (!Escaping.validAttrName(name))
    throw new IllegalArgumentException(
      s"Illegal attribute name: $name is not a valid XML attribute name"
    )
  /**
   * Force-assigns a typed attribute to a string even if its type would not
   * normally allow it.
   */
  def :=(v: String) = AttrPair(this, v)

}
case class UntypedAttr(name: String) extends Attr
/**
 * Wraps up a HTML attribute in a statically-typed value with an associated
 * type; overloads the := operator to also accept values of that type to convert
 * to strings, allowing more concise and pseudo-typesafe use of that attribute.
 */
case class TypedAttr[T](name: String) extends Attr{
  /**
   * Assign an attribute to some value of the correct type.
   */
  def :=(v: T): AttrPair = AttrPair(this, v.toString)
}

/**
 * A [[Style]] which does not have a particular type, and takes strings as its
 * values
 */
case class Style(jsName: String, cssName: String){
  def :=(value: Any) = StylePair(this, value)
}
/**
 * A key value pair representing the assignment of a style to a value.
 */
case class StylePair(style: Style, value: Any) extends Modifier{
  def transforms = Array(Mod.Style(style.cssName, value))

}

/**
 * Things that a modifier is allowed to do to a node.
 *
 * It can only set attributes, or append children.
 */
sealed trait Mod
object Mod{
  case class Attr(k: String, v: Any) extends Mod
  case class Cls(v: Any) extends Mod
  case class Style(k: String, v: Any) extends Mod
  case class Child(n: Node) extends Mod
}

