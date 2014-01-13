package scalatags
import scala.collection.{SortedMap, mutable}

/**
 * Represents a single CSS class.
 */
case class Cls(name: String) extends Modifier{
  def transform(tag: HtmlTag) = {
    tag.copy(attrs =
      tag.attrs.updated(
        "class",
        tag.attrs.get("class").fold(name)(_ + " " + name)
      )
    )
  }
}

/**
 * A [[scalatags.Node] which contains a String.
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

  def transform(tag: HtmlTag) = {
    tag.copy(children = this :: tag.children)
  }
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
   * [[apply]] method, and the overloading becomes ambiguous.
   */
  def transform(tag: HtmlTag): HtmlTag
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
case class HtmlTag(tag: String = "",
                   children: List[Node],
                   attrs: SortedMap[String, String],
                   void: Boolean = false) extends Node{

  /**
   * Represents the list of CSS classes this HtmlTag contains; lazily derived
   * from `attrs`.
   */
  lazy val classes = attrs("class").split(" ").toSeq

  /**
   * Represents the list of CSS styles this HtmlTag contains; lazily derived
   * from `attrs`.
   */
  lazy val styles = {
    SortedMap.empty[String, String] ++
      attrs("style").split(";|:")
        .iterator
        .map(_.trim)
        .filter(_ != "")
        .grouped(2)
        .map(pair => pair(0) -> pair(1))
  }

  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the [[HtmlTag]]. Note that any these modifications are queued up and only
   * evaluated when (if!) the [[HtmlTag]] is rendered.
   */
  def apply(xs: Modifier*) = {
    var newTag = this

    var i = 0
    while(i < xs.length){
      newTag = xs(i).transform(newTag)
      i += 1
    }
    newTag
  }

  /**
   * Serialize this [[HtmlTag]] and all its children out to the given StringBuilder.
   */
  def writeTo(strb: StringBuilder): Unit = {
    // tag
    strb ++= "<" ++= tag

    // attributes
    for((attr, value) <- attrs){
      strb ++= " " ++= attr ++= "=\""
      Escaping.escape(value, strb)
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
case class AttrPair(attr: Attr, value: String) extends Modifier{
  def transform(tag: HtmlTag) = {
    tag.copy(attrs = tag.attrs.updated(attr.name, value))
  }
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
 * A Style that only has a fixed set of possible values, provided by its members.
 */
abstract class Style{
  /**
   * The name of this style from Javascript, normally camel-case
   */
  def jsName: String
  /**
   * The name of this style in CSS, normally dash-separated
   */
  def cssName: String
  /**
   * Force-assigns a typed style to a string even if its type would not normally
   * .allow it
   */
  def :=(value: String) = StylePair(this, value)

}

/**
 * A [[Style]] which does not have a particular type, and takes strings as its
 * values
 */
case class UntypedStyle(jsName: String, cssName: String) extends Style
/**
 * A key value pair representing the assignment of a style to a value.
 */
case class StylePair(style: Style, value: String) extends Modifier{
  def transform(tag: HtmlTag) = {
    val str = style.cssName + ": " + value + ";"
    tag.copy(attrs =
      tag.attrs.updated(
        "style",
        tag.attrs.get("style").fold(str)(_ + " " + str)
      )
    )
  }
}
/**
 * A [[Style]] that takes any value of type T as a parameter; overloads the :=
 * operator to also accept values of that type to convert to strings, allowing
 * more concise and pseudo-typesafe use of that style.
 */
case class TypedStyle[T](jsName: String, cssName: String) extends Style{
  /**
   * Assign this style to some value of the correct type.
   */
  def :=(value: T) = StylePair(this, value.toString)
}

