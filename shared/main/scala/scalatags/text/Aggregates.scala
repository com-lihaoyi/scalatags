package scalatags
package text

import scala.collection.SortedMap
import scalatags.generic._
import scalatags.generic.Style
import scalatags.generic.Attr

object all extends StringTags with Attrs[StringBuilder] with Styles[StringBuilder] with Tags[StringBuilder] with DataConverters with Util[StringBuilder]
object short extends StringTags with Util[StringBuilder] with DataConverters{
  object * extends StringTags with Attrs[StringBuilder] with Styles[StringBuilder]
}
object misc {
  object attrs extends StringTags with Attrs[StringBuilder]
  object tags extends StringTags with Tags[StringBuilder]
  object tags2 extends StringTags with Tags2[StringBuilder]
  object styles extends StringTags with Styles[StringBuilder]
  object styles2 extends StringTags with Styles2[StringBuilder]
  object svgTags extends StringTags with SvgTags[StringBuilder]
  object svgStyles extends StringTags with SvgStyles[StringBuilder]

}
/**
 * Convenience object to help import all [[Tags]], [[Attrs]], [[Styles]] and
 * [[Datatypes]] into the global namespace via `import scalatags.all._`
 */
trait StringTags extends Util[StringBuilder]{ self =>
  implicit val styleOrdering = Orderings.styleOrdering
  implicit val attrOrdering = Orderings.attrOrdering
  protected[this] implicit def stringAttrInternal(s: String) = new StringAttr(s)
  protected[this] implicit def booleanAttrInternal(b: Boolean) = new BooleanAttr(b)
  protected[this] implicit def numericAttrInternal[T: Numeric](n: T) = new NumericAttr(n)

  protected[this] implicit def stringStyleInternal(s: String) = new StringStyle(s)
  protected[this] implicit def booleanStyleInternal(b: Boolean) = new BooleanStyle(b)
  protected[this] implicit def numericStyleInternal[T: Numeric](n: T) = new NumericStyle(n)

  def makeAbstractTypedHtmlTag[T <: Platform.Base](tag: String, void: Boolean) = {
    TypedHtmlTag(tag, Nil, SortedMap.empty, SortedMap.empty, void)
  }
}
