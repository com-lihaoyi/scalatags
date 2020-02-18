package scalatags
package generic
import scalatags.stylesheet.StyleSheetFrag
import scalatags.text

/**
 * An abstract representation of the Scalatags package. This allows you to
 * customize Scalatags to work with different backends, by defining your own
 * implementation of `Tag`, and specifying how the various [[Attr]]s
 * and [[Style]]s contribute to construct the [[Builder]]. Apart from satisfying the
 * default String/Boolean/Numeric implementations of [[Attr]] and [[Style]],
 * you can also define your own, e.g. ScalaJS ships with an implicit conversion
 * from `js.Any` to `Attr`, so that you can attach objects to the resultant
 * `dom.Element` without serializing them.
 *
 * By default, Scalatags ships with [[scalatags.Text]]: `Bundle[StringBuilder]`
 * on all platforms, and [[scalatags.JsDom]]: `Bundle[dom.Element]` on ScalaJS.
 *
 * It is possible to write entirely backend-agnostic Scalatags code by making
 * your code parametric on a [[Bundle]] (or some subclass of it), and importing
 * from that rather than importing directly from [[scalatags.JsDom]] or
 * [[scalatags.Text]]. You will naturally only be able to use functionality
 * (e.g. implicit conversions to [[Attr]]s and [[Style]]s which are present
 * in the common interface.
 *
 * @tparam Builder The type to which [[Attr]]s and [[Style]]s are applied to when the
 *                 `Tag` is being rendered to give a final result.
 */
trait Bundle extends Core with StylesWrapper with LowPriBundle{
  trait TypedTag[+O <: Output] extends Frag {
    protected[this] type Self <: TypedTag[O]
    def tag: String

    /**
     * The modifiers that are applied to a TypedTag are kept in this linked-Seq
     * (which are actually WrappedArrays) data-structure in order for maximum
     * performance.
     */
    def modifiers: List[Seq[Modifier]]

    /**
     * Walks the [[modifiers]] to apply them to a particular [[Builder]].
     * Super sketchy/procedural for max performance.
     */
    def build(b: Builder): Unit = {
      var current = modifiers
      val arr = new Array[Seq[Modifier]](modifiers.length)

      var i = 0
      while(current != Nil){
        arr(i) = current.head
        current =  current.tail
        i += 1
      }

      var j = arr.length
      while (j > 0) {
        j -= 1
        val frag = arr(j)
        var i = 0
        while(i < frag.length){
          frag(i).applyTo(b)
          i += 1
        }
      }
    }
    /**
     * Add the given modifications (e.g. additional children, or new attributes)
     * to the [[TypedTag]].
     */
    def apply(xs: Modifier*): Self

    /**
     * Collapses this scalatags tag tree and returns an [[Output]]
     */
    def render: Output
  }
  /**
   * Convenience object for importing all of Scalatags' functionality at once
   */
  val all: AbstractAll
  trait AbstractAll extends Attrs[Attr, AttrValue, AttrPair] with Styles with Tags with Api with DataConverters{

  }

  /**
   * Convenience object for importing only Scalatags' tags (e.g. `div`, `p`)
   * into the local namespace, while leaving Styles and Attributes accessible
   * via the `*` object
   */
  val short: AbstractShort

  trait AbstractShort extends Tags with Api with DataConverters{
    val `*`: Attrs[Attr, AttrValue, AttrPair] with Styles
  }

  object converters extends DataConverters

  /**
   * Common attributes.
   */
  val attrs: Attrs[Attr, AttrValue, AttrPair]
  /**
   * Common tags
   */
  val tags: Tags
  /**
   * Less common tags
   */
  val tags2: Tags2
  /**
   * Common styles
   */
  val styles: Styles
  /**
   * Less common styles
   */
  val styles2: Styles2
  /**
   * SVG only tags
   */
  val svgTags: SvgTags

  /**
   * SVG only attributes
   */
  val svgAttrs: SvgAttrs[Attr]


  type Tags = generic.Tags[TypedTag[Output]]
  type Tags2 = generic.Tags2[TypedTag[Output]]
  type SvgTags = generic.SvgTags[TypedTag[Output]]

  implicit def StyleFrag(s: StylePair[_]): StyleSheetFrag
  implicit def ClsModifier(s: stylesheet.Cls): Modifier
  protected[this] def genericAttr[T]: AttrValue[T]
  implicit val stringAttr = genericAttr[String]
  implicit val booleanAttr = genericAttr[Boolean]
  implicit val byteAttr = genericAttr[Byte]
  implicit val shortAttr = genericAttr[Short]
  implicit val intAttr = genericAttr[Int]
  implicit val longAttr = genericAttr[Long]
  implicit val floatAttr = genericAttr[Float]
  implicit val doubleAttr = genericAttr[Double]

  protected[this] def genericStyle[T]: StyleValue[T]
  implicit val stringStyle = genericStyle[String]
  implicit val booleanStyle = genericStyle[Boolean]
  implicit val byteStyle = genericStyle[Byte]
  implicit val shortStyle = genericStyle[Short]
  implicit val intStyle = genericStyle[Int]
  implicit val longStyle = genericStyle[Long]
  implicit val floatStyle = genericStyle[Float]
  implicit val doubleStyle = genericStyle[Double]

  protected[this] def genericPixelStyle[T](implicit ev: StyleValue[T]): PixelStyleValue[T]
  protected[this] def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T]
  implicit val stringPixelStyle = genericPixelStyle[String]
  implicit val booleanPixelStyle = genericPixelStyle[Boolean]
  implicit val bytePixelStyle = genericPixelStylePx[Byte]
  implicit val shortPixelStyle = genericPixelStylePx[Short]
  implicit val intPixelStyle = genericPixelStylePx[Int]
  implicit val longPixelStyle = genericPixelStylePx[Long]
  implicit val floatPixelStyle = genericPixelStylePx[Float]
  implicit val doublePixelStyle = genericPixelStylePx[Double]

  implicit def byteFrag(v: Byte): Frag = stringFrag(v.toString)
  implicit def shortFrag(v: Short): Frag = stringFrag(v.toString)
  implicit def intFrag(v: Int): Frag = stringFrag(v.toString)
  implicit def longFrag(v: Long): Frag = stringFrag(v.toString)
  implicit def floatFrag(v: Float): Frag = stringFrag(v.toString)
  implicit def doubleFrag(v: Double): Frag = stringFrag(v.toString)
  implicit def stringFrag(v: String): Frag

  /**
   * Lets you put Unit into a scalatags tree, as a no-op.
   */
  implicit def UnitFrag(u: Unit): Frag
  implicit def OptionFrag[A](xs: Option[A])(implicit ev: A => Frag): Frag
  implicit def ArrayFrag[A](xs: Array[A])(implicit ev: A => Frag): Frag
  implicit def SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag): Frag
  implicit def GeneratorFrag[A](xs: geny.Generator[A])(implicit ev: A => super.Frag): Frag

  trait Api {
    def frag(frags: Bundle.this.Frag*): Bundle.this.Frag
    def modifier(mods: Modifier*): Modifier = SeqNode(mods)

    def tag(s: String, void: Boolean = false): TypedTag[Output]
    /**
     * Delimits a string that should be included in the result as raw,
     * un-escaped HTML
     */
    def raw(s: String): Modifier
    def css(s: String): Style = Style(scalatags.Util.camelCase(s), s)
    def attr(s: String, ns: scalatags.generic.Namespace = null, raw: Boolean = false): Attr = Attr(s, Option(ns), raw)
    def emptyAttr(s: String, ns: scalatags.generic.Namespace = null, raw: Boolean = false): AttrPair[_] = Attr(s, Option(ns), raw).empty
    def AttrPair[T](attr: Attr, v: T, ev: AttrValue[T]) = Bundle.this.AttrPair(attr, v, ev)

    type HtmlTag
    type SvgTag
    type Tag = TypedTag[Output]
    type Modifier = Bundle.this.Modifier
    type Style = Bundle.this.Style
    type Frag = Bundle.this.Frag
  }
}
trait LowPriBundle{this: Bundle =>
  /**
   * Allows you to modify a [[ConcreteHtmlTag]] by adding a Seq containing other nest-able
   * objects to its list of children.
   */
  implicit class SeqNode[A](xs: Seq[A])(implicit ev: A => Modifier) extends Modifier{
    java.util.Objects.requireNonNull(xs)
    def applyTo(t: Builder) = xs.foreach(_.applyTo(t))
  }

  /**
   * Allows you to modify a [[ConcreteHtmlTag]] by adding a Seq containing other nest-able
   * objects to its list of children.
   */
  implicit class GeneratorNode[A](xs: geny.Generator[A])(implicit ev: A => Modifier) extends Modifier{
    java.util.Objects.requireNonNull(xs)
    def applyTo(t: Builder) = xs.foreach(_.applyTo(t))
  }

  /**
   * Allows you to modify a [[ConcreteHtmlTag]] by adding an Option containing other nest-able
   * objects to its list of children.
   */
  implicit def OptionNode[A](xs: Option[A])(implicit ev: A => Modifier) = new SeqNode(xs.toSeq)

  /**
   * Allows you to modify a [[ConcreteHtmlTag]] by adding an Array containing other nest-able
   * objects to its list of children.
   */
  implicit def ArrayNode[A](xs: Array[A])(implicit ev: A => Modifier) = new SeqNode[A](xs.toSeq)

}
