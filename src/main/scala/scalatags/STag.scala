package scalatags

/**
 * A general interface for all types which can appear in a ScalaTags fragment.
 */
trait STag{
  /**
   * Converts an ScalaTag fragment into an html string
   */
  def toString(): String

  /**
   * The children of a ScalaTag node
   */
  def children: Seq[STag]
}

/**
 * An algebraic data type representing a HTML tag
 *
 * @param tag The name of the tag
 * @param children The children of the tag
 * @param attrMap The tag's attributes
 * @param classes The tag's classes
 * @param styles The tag's CSS styles
 */
case class HtmlTag(tag: String = "",
                   children: Seq[STag] = Nil,
                   attrMap: Map[String, Any] = Map.empty,
                   classes: Seq[Any] = Nil,
                   styles: Map[String, Any] = Map.empty) extends STag with HtmlTrait{

  def apply(x1: STag*) = this.copy(children = children ++ x1)

  def attr(t: (String, Any)*) = {
    copy(attrMap = t.foldLeft(attrMap)(_+_))
  }

  override def toString(): String = 
    "<" + tag + classString + attrString + stylesString +
      (if(children == Nil)
        "/>"
      else
        ">" + children.foldLeft("")(_ + _.toString()) + "</" + tag + ">")

  private def classString: String =
    if(classes == Nil)
      ""
    else
      " class=\"" + classes.mkString(" ") + "\""

  private def attrString: String =
    attrMap.foldLeft("")((s, a) => s + " " + a._1 + "=\"" + a._2.toString + "\"")

  private def stylesString: String =
    if(styles.isEmpty)
      ""
    else
      " style=\"" + styles.foldLeft("")((s, a) => s + a._1 + ":" + a._2.toString + ";") + "\""
}
