package scalatags
package jsdom

import scala.collection.SortedMap
import scalatags.generic._
import scalatags.generic.Style
import scalatags.generic.Attr
import org.scalajs.dom

object all extends StringTags with Attrs[dom.Element] with Styles[dom.Element] with Tags[dom.Element] with DataConverters with Util[dom.Element]
object short extends StringTags with Util[dom.Element] with DataConverters{
  object * extends StringTags with Attrs[dom.Element] with Styles[dom.Element]
}
object misc {
  object attrs extends StringTags with Attrs[dom.Element]
  object tags extends StringTags with Tags[dom.Element]
  object tags2 extends StringTags with Tags2[dom.Element]
  object styles extends StringTags with Styles[dom.Element]
  object styles2 extends StringTags with Styles2[dom.Element]
  object svgTags extends StringTags with SvgTags[dom.Element]
  object svgStyles extends StringTags with SvgStyles[dom.Element]

}
/**
 * Convenience object to help import all [[Tags]], [[Attrs]], [[Styles]] and
 * [[Datatypes]] into the global namespace via `import scalatags.all._`
 */
trait StringTags extends Util[dom.Element]{ self =>
  type ConcreteHtmlTag[T <: Platform.Base] = jsdom.TypedHtmlTag[T]

  protected[this] implicit def stringAttrInternal(s: String) = new StringAttr(s)
  protected[this] implicit def booleanAttrInternal(b: Boolean) = new BooleanAttr(b)
  protected[this] implicit def numericAttrInternal[T: Numeric](n: T) = new NumericAttr(n)

  protected[this] implicit def stringStyleInternal(s: String) = new StringStyle(s)
  protected[this] implicit def booleanStyleInternal(b: Boolean) = new BooleanStyle(b)
  protected[this] implicit def numericStyleInternal[T: Numeric](n: T) = new NumericStyle(n)

  def makeAbstractTypedHtmlTag[T <: Platform.Base](tag: String, void: Boolean): jsdom.TypedHtmlTag[T] = {
    TypedHtmlTag(tag, Nil, SortedMap.empty, SortedMap.empty, void)
  }
}
