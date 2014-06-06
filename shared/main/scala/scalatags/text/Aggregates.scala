package scalatags
package text
import Implicits._
import scala.collection.SortedMap
import scalatags.generic.AbstractMisc
import acyclic.file

object all extends StringTags with Attrs with Styles with Tags with DataConverters with Util
object short extends StringTags with Util with DataConverters with generic.AbstractShort[StringBuilder]{
  object * extends StringTags with Attrs with Styles
}
object misc extends AbstractMisc[StringBuilder]{
  object attrs extends StringTags with Attrs
  object tags extends StringTags with Tags
  object tags2 extends StringTags with Tags2
  object styles extends StringTags with Styles
  object styles2 extends StringTags with Styles2
  object svgTags extends StringTags with SvgTags
  object svgStyles extends StringTags with SvgStyles
}

/**
 * Convenience object to help import all [[Tags]], [[Attrs]], [[Styles]] and
 * [[Datatypes]] into the global namespace via `import scalatags.all._`
 */
trait StringTags extends Util{ self =>
  type ConcreteHtmlTag[T <: Platform.Base] = Implicits.TypedHtmlTag[T]

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
