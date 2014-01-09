/**
 * Documentation marked "MDN" is thanks to Mozilla Contributors
 * at https://developer.mozilla.org/en-US/docs/Web/API and available
 * under the Creative Commons Attribution-ShareAlike v2.5 or later.
 * http://creativecommons.org/licenses/by-sa/2.5/
 *
 * Everything else is under the MIT License
 * http://opensource.org/licenses/MIT
 */
package scalatags
import DataTypes._
/**
 * A Style that takes any value of type T as a parameter and has an auto value
 */
private[scalatags] class AutoStyle[T](jsName: String, cssName: String) extends TypedStyle[T](jsName, cssName) {
  val auto = this ~= "auto"
}
/**
 * A Style that takes any value of type T as a parameter and has an none value
 */
private[scalatags] class NoneOpenStyle[T](jsName: String, cssName: String) extends TypedStyle[T](jsName, cssName) {
  val none = this ~= "none"
}

/**
 * A Style that takes any value of type T as a parameter and has an normal value
 */
private[scalatags] class NormalOpenStyle[T](jsName: String, cssName: String) extends TypedStyle[T](jsName, cssName) {
  val normal = this ~= "normal"
}

private[scalatags] class MultiImageStyle(jsName: String, cssName: String) extends TypedStyle[Nothing](jsName, cssName) {
  def :=(image: Image, images: Image*) = {
    this ~= (image +: images).mkString(", ")
  }
}


private[scalatags] class OutlineStyle(jsName: String, cssName: String) extends Style(jsName, cssName) {
  /**
   * Displays a series of rounded dots. The spacing of the dots are not
   * defined by the specification and are implementation-specific. The radius
   * of the dots is half the calculated border-right-width.
   *
   * MDN
   */
  val dotted = this ~= "dotted"
  /**
   * Displays a series of short square-ended dashes or line segments. The exact
   * size and Length of the segments are not defined by the specification and
   * are implementation-specific.
   *
   * MDN
   */
  val dashed = this ~= "dashed"
  /**
   * Displays a single, straight, solid line.
   *
   * MDN
   */
  val solid = this ~= "solid"
  /**
   * Displays two straight lines that add up to the pixel amount defined as
   * border-width or border-right-width.
   *
   * MDN
   */
  val double = this ~= "double"
  /**
   * Displays a border leading to a carved effect. It is the opposite of ridge.
   *
   * MDN
   */
  val groove = this ~= "groove"
  /**
   * Displays a border with a 3D effect, like if it is coming out of the page.
   * It is the opposite of groove.
   *
   * MDN
   */
  val ridge = this ~= "ridge"
  /**
   * Displays a border that makes the box appear embedded. It is the opposite
   * of outset. When applied to a table cell with border-collapse set to
   * collapsed, this value behaves like groove.
   *
   * MDN
   */
  val inset = this ~= "inset"
  /**
   * Displays a border that makes the box appear in 3D, embossed. It is the
   * opposite of inset. When applied to a table cell with border-collapse set
   * to collapsed, this value behaves like ridge.
   *
   * MDN
   */
  val outset = this ~= "outset"
}

private[scalatags] class BorderStyle(jsName: String, cssName: String) extends OutlineStyle(jsName, cssName){
  /**
   * Like for the hidden keyword, displays no border. In that case, except if
   * a background image is set, the calculated values of border-right-width
   * will be 0, even if specified otherwise through the property. In case of
   * table cell and border collapsing, the none value has the lowest priority:
   * it means that if any other conflicting border is set, it will be
   * displayed.
   *
   * MDN
   */
  val none = this ~= "none"
  /**
   * Like for the none keyword, displays no border. In that case, except if a
   * background image is set, the calculated values of border-right-width will
   * be 0, even if specified otherwise through the property. In case of table
   * cell and border collapsing, the hidden value has the highest priority: it
   * means that if any other conflicting border is set, it won't be displayed.
   *
   * MDN
   */
  val hidden = this ~= "hidden"

}

private[scalatags] class Overflow(jsName: String, cssName: String) extends Style(jsName, cssName){
  /**
   * Default value. Content is not clipped, it may be rendered outside the
   * content box.
   *
   * MDN
   */
  val visible = this ~= "visible"
  /**
   * The content is clipped and no scrollbars are provided.
   *
   * MDN
   */
  val hidden = this ~= "hidden"
  /**
   * The content is clipped and desktop browsers use scrollbars, whether or
   * not any content is clipped. This avoids any problem with scrollbars
   * appearing and disappearing in a dynamic environment. Printers may print
   * overflowing content.
   *
   * MDN
   */
  val scroll = this ~= "scroll"
  /**
   * Depends on the user agent. Desktop browsers like Firefox provide
   * scrollbars if content overflows.
   *
   * MDN
   */
  val auto = this ~= "auto"
}

private[scalatags] class PageBreak(jsName: String, cssName: String) extends Style(jsName, cssName){
  /**
   * Initial value. Automatic page breaks (neither forced nor forbidden).
   *
   * MDN
   */
  val auto = this ~= "auto"
  /**
   * Always force page breaks.
   *
   * MDN
   */
  val always = this ~= "always"
  /**
   * Avoid page breaks.
   *
   * MDN
   */
  val avoid = this ~= "avoid"
  /**
   * Force page breaks so that the next page is formatted
   * as a left page.
   *
   * MDN
   */
  val left = this ~= "left"
  /**
   * Force page breaks so that the next page is formatted
   * as a right page.
   *
   * MDN
   */
  val right = this ~= "right"
}


private[scalatags] class BorderRadius(jsName: String, cssName: String) extends Style(jsName, cssName){
  def ~(r1: String, r2: String = "") = this ~= s"$r1 $r2"
}

private[scalatags] trait MarginAuto extends Style {
  /**
   * auto is replaced by some suitable value, e.g. it can be used for
   * centering of blocks.
   *
   * MDN
   */
  val auto = this ~= "auto"

}
private[scalatags] class BorderWidth(jsName: String, cssName: String)  extends TypedStyle[Number](jsName, cssName){
  val thin = this ~= "thin"
  val medium = this ~= "medium"
  val thick = this ~= "thick"
}

private[scalatags] class MultiTimeStyle(jsName: String, cssName: String) extends Style(jsName, cssName){
  def ~(times: Time*) = this ~= times.mkString(", ")
}

/**
 * Contains CSS styles which are used less frequently. These are not imported by
 * default to avoid namespace pollution.
 */
object Styles{

  /**
   * The animation-direction CSS property indicates whether the animation should
   * play in reverse on alternate cycles.
   *
   * MDN
   */
  val animationDirection = new TypedStyle[String]("animationDirection", "animation-direction")

  /**
   * The animation-duration CSS property specifies the Length of time that an
   * animation should take to complete one cycle.
   *
   * A value of 0s, which is the default value, indicates that no animation should
   * occur.
   *
   * MDN
   */
  val animationDuration = new TypedStyle[String]("animationDuration", "animation-duration")

  /**
   * The animation-name CSS property specifies a list of animations that should
   * be applied to the selected element. Each name indicates a @keyframes at-rule
   * that defines the property values for the animation sequence.
   *
   * MDN
   */
  val animationName = new TypedStyle[String]("animationName", "animation-name")

  /**
   * The animation-fill-mode CSS property specifies how a CSS animation should
   * apply styles to its target before and after it is executing.
   *
   * MDN
   */
  val animationFillMode = new TypedStyle[String]("animationFillMode", "animation-fill-mode")

  /**
   * The animation-iteration-count CSS property defines the number of times an
   * animation cycle should be played before stopping.
   *
   * MDN
   */
  val animationIterationCount = new TypedStyle[String]("animationIterationCount", "animation-iteration-count")


  /**
   * The animation-delay CSS property specifies when the animation should start.
   * This lets the animation sequence begin some time after it's applied to an
   * element.
   *
   * A value of 0s, which is the default value of the property, indicates that
   * the animation should begin as soon as it's applied. Otherwise, the value
   * specifies an offset from the moment the animation is applied to the element;
   * animation will begin that amount of time after being applied.
   *
   * Specifying a negative value for the animation delay causes the animation to
   * begin executing immediately. However, it will appear to have begun executing
   * partway through its cycle. For example, if you specify -1s as the animation
   * delay time, the animation will begin immediately but will start 1 second
   * into the animation sequence.
   *
   * If you specify a negative value for the animation delay, but the starting
   * value is implicit, the starting value is taken from the moment the animation
   * is applied to the element.
   *
   * MDN
   */
  val animationDelay = new MultiTimeStyle("animationDelay", "animation-delay")

  /**
   * The CSS animation-timing-function property specifies how a CSS animation
   * should progress over the duration of each cycle. The possible values are
   * one or several <timing-function>.
   *
   * For keyframed animations, the timing function applies between keyframes
   * rather than over the entire animation. In other words, the timing function
   * is applied at the start of the keyframe and at the end of the keyframe.
   *
   * An animation timing function defined within a keyframe block applies to that
   * keyframe; otherwise. If no timing function is specified for the keyframe,
   * the timing function specified for the overall animation is used.
   *
   * MDN
   */
  val animationTimingFunction = new TypedStyle[String]("animationTimingFunction", "animation-timing-function")


  /**
   * The animation-play-state CSS property determines whether an animation is
   * running or paused. You can query this property's value to determine whether
   * or not the animation is currently running; in addition, you can set its
   * value to pause and resume playback of an animation.
   *
   * Resuming a paused animation will start the animation from where it left off
   * at the time it was paused, rather than starting over from the beginning of
   * the animation sequence.
   *
   * MDN
   */
  val animationPlayState = new TypedStyle[String]("animationPlayState", "animation-play-state")
  /**
   * The animation CSS property is a shorthand property for animation-name,
   * animation-duration, animation-timing-function, animation-delay,
   * animation-iteration-count and animation-direction.
   *
   * MDN
   */
  val animation = new TypedStyle[String]("animation", "animation")


  /**
   * The CSS backface-visibility property determines whether or not the back
   * face of the element is visible when facing the user. The back face of an
   * element always is a transparent background, letting, when visible, a mirror
   * image of the front face be displayed.
   *
   * MDN
   */
  object backfaceVisibility extends Style("backfaceVisibility", "backface-visibility") {
    /**
     * The back face is visible.
     *
     * MDN
     */
    val visible = this ~= "visible"
    /**
     * The back face is not visible.
     *
     * MDN
     */
    val hidden = this ~= "hidden"
  }



  /**
   * The columns CSS property is a shorthand property allowing to set both the
   * column-width and the column-count properties at the same time.
   *
   * MDN
   */
  object columns extends Style("columns", "columns") {
    def ~(number: Int) = this ~= number.toString

    def ~(number: Int, width: Length) = this ~= s"$number $width"
  }

  /**
   * The column-count CSS property describes the number of columns of the element.
   *
   * MDN
   */
  val columnCount = new AutoStyle[Int]("columnCount", "column-count")

  /**
   * The column-fill CSS property controls how contents are partitioned into
   * columns. Contents are either balanced, which means that contents in all
   * columns will have the same height or, when using auto, just take up the
   * room the content needs.
   *
   * MDN
   */
  object columnFill extends Style("columnFill", "column-fill") {
    /**
     * Is a keyword indicating that columns are filled sequentially.
     *
     * MDN
     */
    val auto = this ~= "auto"

    /**
     * Is a keyword indicating that content is equally divided between columns.
     *
     * MDN
     */
    val balance = this ~= "balance"
  }

  /**
   * The column-gap CSS property sets the size of the gap between columns for
   * elements which are specified to display as a multi-column element.
   *
   * MDN
   */
  val columnGap = new NormalOpenStyle[Length]("columnGap", "column-gap")

  /**
   * In multi-column layouts, the column-rule CSS property specifies a straight
   * line, or "rule", to be drawn between each column. It is a convenient
   * shorthand to avoid setting each of the individual column-rule-* properties
   * separately : column-rule-width, column-rule-style and column-rule-color.
   *
   * MDN
   */
  val columnRule = new TypedStyle[String]("columnRule", "column-rule")

  /**
   * The column-span CSS property makes it possible for an element to span across
   * all columns when its value is set to all. An element that spans more than
   * one column is called a spanning element.
   *
   * MDN
   */
  object columnSpan extends Style("columnSpan", "column-span") {
    /**
     * The element does not span multiple columns.
     *
     * MDN
     */
    val none = this ~= "none"
    /**
     * The element spans across all columns. Content in the normal flow that
     * appears before the element is automatically balanced across all columns
     * before the element appears. The element establishes a new block formatting
     * context.
     *
     * MDN
     */
    val all = this ~= "all"
  }


  /**
   * The column-width CSS property suggests an optimal column width. This is not
   * a absolute value but a mere hint. Browser will adjust the width of the
   * column around that suggested value, allowing to achieve scalable designs
   * that fit different screen size. Especially in presence of the column-count
   * CSS property which has precedence, to set an exact column width, all Length
   * values must be specified. In horizontal text these are width, column-width,
   * column-gap, and column-rule-width
   *
   * MDN
   */
  val columnWidth = new AutoStyle[Length]("columnWidth", "column-width")

  /**
   * The column-rule-color CSS property lets you set the color of the rule drawn
   * between columns in multi-column layouts.
   *
   * MDN
   */
  val columnRuleColor = new TypedStyle[Color]("columnRuleColor", "column-rule-color")

  /**
   * The column-rule-width CSS property lets you set the width of the rule drawn
   * between columns in multi-column layouts.
   *
   * MDN
   */
  object columnRuleWidth extends TypedStyle[Length]("columnRuleWidth", "column-rule-width") {
    val thin = this ~= "thin"
    val medium = this ~= "medium"
    val thick = this ~= "thick"
  }

  /**
   * The column-rule-style CSS property lets you set the style of the rule drawn
   * between columns in multi-column layouts.
   *
   * MDN
   */
  object columnRuleStyle
    extends OutlineStyle("columnRuleStyle", "column-rule-style"){
    val hidden = this ~= "hidden"
  }


  /**
   * The counter-increment CSS property is used to increase the value of CSS
   * Counters by a given value. The counter's value can be reset using the
   * counter-reset CSS property.
   *
   * MDN
   */
  val counterIncrement = new TypedStyle[String]("counterIncrement", "counter-increment")

  /**
   * The counter-reset CSS property is used to reset CSS Counters to a given
   * value.
   *
   * MDN
   */
  val counterReset = new TypedStyle[String]("counterReset", "counter-reset")


  /**
   * The orphans CSS property refers to the minimum number of lines in a block
   * container that must be left at the bottom of the page. This property is
   * normally used to control how page breaks occur.
   *
   * MDN
   */
  val orphans = new TypedStyle[Int]("orphans", "orphans")


  /**
   * The widows CSS property defines how many minimum lines must be left on top
   * of a new page, on a paged media. In typography, a widow is the last line of
   * a paragraph appearing alone at the top of a page. Setting the widows property
   * allows to prevent widows to be left.
   *
   * On a non-paged media, like screen, the widows CSS property has no effect.
   *
   * MDN
   */
  val widows = new TypedStyle[Number]("widows", "widows")


  /**
   * The page-break-after CSS property adjusts page breaks after the current
   * element.
   *
   * MDN
   */
  val pageBreakAfter = new PageBreak("pageBreakAfter", "page-break-after")

  /**
   * The page-break-inside CSS property adjusts page breaks inside the current
   * element.
   *
   * MDN
   */
  val pageBreakInside = new PageBreak("pageBreakInside", "page-break-inside")


  /**
   * The page-break-before CSS property adjusts page breaks before the current
   * element.
   *
   * This properties applies to block elements that generate a box. It won't
   * apply on an empty `<div>` that won't generate a box.
   *
   * MDN
   */
  val pageBreakBefore = new PageBreak("pageBreakBefore", "page-break-before")


  /**
   * The perspective CSS property determines the distance between the z=0 plane
   * and the user in order to give to the 3D-positioned element some perspective.
   * Each 3D element with z>0 becomes larger; each 3D-element with z<0 becomes
   * smaller. The strength of the effect is determined by the value of this
   * property.
   *
   * MDN
   */
  val perspective = new NoneOpenStyle[Length]("perspective", "perspective")

  /**
   * The perspective-origin CSS property determines the position the viewer is
   * looking at. It is used as the vanishing point by the perspective property.
   *
   * MDN
   */
  val perspectiveOrigin = new TypedStyle[String]("perspectiveOrigin", "perspective-origin")


  /**
   * The transition-delay CSS property specifies the amount of time to wait
   * between a change being requested to a property that is to be transitioned
   * and the start of the transition effect.
   *
   * A value of 0s, or 0ms, indicates that the property will begin to animate its
   * transition immediately when the value changes; positive values will delay
   * the start of the transition effect for the corresponding number of seconds.
   * Negative values cause the transition to begin immediately, but to cause the
   * transition to seem to begin partway through the animation effect.
   *
   * You may specify multiple delays; each delay will be applied to the
   * corresponding property as specified by the transition-property property,
   * which acts as a master list. If there are fewer delays specified than in the
   * master list, missing values are set to the initial value (0s). If there are
   * more delays, the list is simply truncated to the right size. In both case
   * the CSS declaration stays valid.
   *
   * MDN
   */
  val transitionDelay = new MultiTimeStyle("transitionDelay", "transition-delay")

  /**
   * The CSS transition property is a shorthand property for transition-property,
   * transition-duration, transition-timing-function, and transition-delay. It
   * allows to define the transition between two states of an element. Different
   * states may be defined using pseudo-classes like :hover or :active or
   * dynamically set using JavaScript.
   *
   * MDN
   */
  val transition = new TypedStyle[String]("transition", "transition")

  /**
   * The CSS transition-timing-function property is used to describe how the
   * intermediate values of the CSS properties being affected by a transition
   * effect are calculated. This in essence lets you establish an acceleration
   * curve, so that the speed of the transition can vary over its duration.
   *
   * MDN
   */
  val transitionTimingFunction = new TypedStyle[String]("transitionTimingFunction", "transition-timing-function")

  /**
   * The transition-duration CSS property specifies the number of seconds or
   * milliseconds a transition animation should take to complete. By default,
   * the value is 0s, meaning that no animation will occur.
   *
   * You may specify multiple durations; each duration will be applied to the
   * corresponding property as specified by the transition-property property,
   * which acts as a master list. If there are fewer durations specified than in
   * the master list, the user agent repeat the list of durations. If there are
   * more durations, the list is simply truncated to the right size. In both
   * case the CSS declaration stays valid.
   *
   * MDN
   */
  val transitionDuration = new MultiTimeStyle("transitionDuration", "transition-duration")

  /**
   * The transition-property CSS property is used to specify the names of CSS
   * properties to which a transition effect should be applied.
   *
   * MDN
   */
  val transitionProperty = new TypedStyle[String]("transitionProperty", "transition-property")

  /**
   * The CSS transform property lets you modify the coordinate space of the CSS
   * visual formatting model. Using it, elements can be translated, rotated,
   * scaled, and skewed according to the values set.
   *
   * If the property has a value different than none, a stacking context will be
   * created. In that case the object will act as a containing block for
   * position: fixed elements that it contains.
   *
   * MDN
   */
  val transform = new TypedStyle[String]("transform", "transform")


  /**
   * The transform-origin CSS property lets you modify the origin for
   * transformations of an element. For example, the transform-origin of the
   * rotate() function is the centre of rotation. (This property is applied by
   * first translating the element by the negated value of the property, then
   * applying the element's transform, then translating by the property value.)
   *
   * Not explicitely set values are reset to their corresponding values.
   *
   * MDN
   */
  val transformOrigin = new TypedStyle[String]("transformOrigin", "transform-origin")
  /**
   * The transform-style CSS property determines if the children of the element
   * are positioned in the 3D-space or are flattened in the plane of the element.
   *
   * MDN
   */
  object transformStyle extends Style("transformStyle", "transform-style") {
    /**
     * Indicates that the children of the element should be positioned in the
     * 3D-space.
     *
     * MDN
     */
    val `preserve-3d` = this ~= "preserve-3d"
    /**
     * Indicates that the children of the element are lying in the plane of the
     * element itself.
     *
     * MDN
     */
    val flat = this ~= "flat"
  }

  /**
   * The unicode-bidi CSS property together with the direction property relates
   * to the handling of bidirectional text in a document. For example, if a block
   * of text contains both left-to-right and right-to-left text then the
   * user-agent uses a complex Unicode algorithm to decide how to display the
   * text. This property overrides this algorithm and allows the developer to
   * control the text embedding.
   *
   * MDN
   */
  object unicodeBidi extends Style("unicodeBidi", "unicode-bidi") {
    /**
     * The element does not offer a additional level of embedding with respect
     * to the bidirectional algorithm. For inline elements implicit reordering
     * works across element boundaries.
     *
     * MDN
     */
    val normal = this ~= "normal"
    /**
     * If the element is inline, this value opens an additional level of
     * embedding with respect to the bidirectional algorithm. The direction of
     * this embedding level is given by the direction property.
     *
     * MDN
     */
    val embed = this ~= "embed"
    /**
     * For inline elements this creates an override. For block container elements
     * this creates an override for inline-level descendants not within another
     * block container element. This means that inside the element, reordering
     * is strictly in sequence according to the direction property; the implicit
     * part of the bidirectional algorithm is ignored.
     *
     * MDN
     */
    val `bidi-override` = this ~= "bidi-override"
  }



  /**
   * The word-break CSS property is used to specify how (or if) to break lines
   * within words.
   *
   * MDN
   */
  object wordBreak extends Style("wordBreak", "word-break") {
    /**
     * Use the default line break rule.
     *
     * MDN
     */
    val normal = this ~= "normal"
    /**
     * Word breaks may be inserted between any character for non-CJK
     * (Chinese/Japanese/Korean) text.
     *
     * MDN
     */
    val `break-all` = this ~= "break-all"
    /**
     * Don't allow word breaks for CJK text.  Non-CJK text behavior is same
     * as normal.
     *
     * MDN
     */
    val `keep-all` = this ~= "keep-all"
  }


}
private[scalatags] trait Styles {
  /**
   * If a background-image is specified, the background-attachment CSS
   * property determines whether that image's position is fixed within
   * the viewport, or scrolls along with its containing block.
   *
   * MDN
   */
  object backgroundAttachment extends Style("backgroundAttachment", "background-attachment") {
    /**
     * This keyword means that the background image will scroll within the
     * viewport along with the block the image is contained within.
     *
     * MDN
     */
    val scroll = this ~= "scroll"
    /**
     * This keyword means that the background image will not scroll with its
     * containing element, instead remaining stationary within the viewport.
     *
     * MDN
     */
    val fixed = this ~= "fixed"
    /**
     * This keyword means that the background image will not scroll with its
     * containing element, but will scroll when the element's content scrolls:
     * it is fixed regarding the element's content.
     *
     * MDN
     */
    val local = this ~= "local"
  }


  /**
   * The background CSS property is a shorthand for setting the individual
   * background values in a single place in the style sheet. background can be
   * used to set the values for one or more of: background-clip, background-color,
   * background-image, background-origin, background-position, background-repeat,
   * background-size, and background-attachment.
   *
   * MDN
   */
  val background = new TypedStyle[String]("background", "background")

  /**
   * The background-repeat CSS property defines how background images are repeated.
   * A background image can be repeated along the horizontal axis, the vertical
   * axis, both, or not repeated at all. When the repetition of the image tiles
   * doesn't let them exactly cover the background, the way adjustments are done
   * can be controlled by the author: by default, the last image is clipped, but
   * the different tiles can instead be re-sized, or space can be inserted
   * between the tiles.
   *
   * MDN
   */
  val backgroundRepeat = new TypedStyle[String]("backgroundRepeat", "background-repeat")


  /**
   * The background-position CSS property sets the initial position, relative to
   * the background position layer defined by background-origin for each defined
   * background image.
   *
   * MDN
   */
  val backgroundPosition = new TypedStyle[String]("backgroundPosition", "background-position")

  /**
   * The background-color CSS property sets the background color of an element,
   * either through a color value or the keyword transparent.
   *
   * MDN
   */
  val backgroundColor = new TypedStyle[Color]("backgroundColor", "background-color")


  /**
   * The background-origin CSS property determines the background positioning
   * area, that is the position of the origin of an image specified using the
   * background-image CSS property.
   *
   * Note that background-origin is ignored when background-attachment is fixed.
   *
   * MDN
   */
  object backgroundOrigin extends Style("backgroundOrigin", "background-origin") {
    /**
     * The background extends to the outside edge of the border (but underneath
     * the border in z-ordering).
     *
     * MDN
     */
    val `border-box` = this ~= "border-box"
    /**
     * No background is drawn below the border (background extends to the
     * outside edge of the padding).
     *
     * MDN
     */
    val `padding-box` = this ~= "border-box"
    /**
     * The background is painted within (clipped to) the content box.
     *
     * MDN
     */
    val `content-box` = this ~= "content-box"
  }

  /**
   * The background-clip CSS property specifies whether an element's background,
   * either the color or image, extends underneath its border.
   *
   * If there is no background image, this property has only visual effect when
   * the border has transparent regions (because of border-style) or partially
   * opaque regions; otherwise the border covers up the difference.
   *
   * MDN
   */
  object backgroundClip extends Style("backgroundClip", "background-clip") {
    /**
     * The background extends to the outside edge of the border (but underneath
     * the border in z-ordering).
     *
     * MDN
     */
    val `border-box` = this ~= "border-box"
    /**
     * No background is drawn below the border (background extends to the
     * outside edge of the padding).
     *
     * MDN
     */
    val `padding-box` = this ~= "padding-box"
    /**
     * The background is painted within (clipped to) the content box.
     *
     * MDN
     */
    val `content-box` = this ~= "content-box"
  }
  /**
   * The background-size CSS property specifies the size of the background
   * images. The size of the image can be fully constrained or only partially in
   * order to preserve its intrinsic ratio.
   *
   * MDN
   */
  object backgroundSize extends TypedStyle[Length]("backgroundSize", "background-size") {
    /**
     * The auto keyword that scales the background image in the corresponding
     * direction such that its intrinsic proportion is maintained.
     *
     * MDN
     */
    val auto = this ~= "auto"
    /**
     * This keyword specifies that the background image should be scaled to be
     * as small as possible while ensuring both its dimensions are greater than
     * or equal to the corresponding dimensions of the background positioning
     * area.
     *
     * MDN
     */
    val cover = this ~= "cover"
    /**
     * This keyword specifies that the background image should be scaled to be
     * as large as possible while ensuring both its dimensions are less than or
     * equal to the corresponding dimensions of the background positioning area.
     *
     * MDN
     */
    val contain = this ~= "contain"
  }
  /**
   * The CSS background-image property sets one or several background images for
   * an element. The images are drawn on successive stacking context layers, with
   * the first specified being drawn as if it is the closest to the user. The
   * borders of the element are then drawn on top of them, and the background-color
   * is drawn beneath them.
   *
   * MDN
   */
  val backgroundImage = new MultiImageStyle("backgroundImage", "background-image")


  /**
   * The border-top-color CSS property sets the color of the top border of an
   * element. Note that in many cases the shorthand CSS properties border-color
   * or border-top are more convenient and preferable.
   *
   * MDN
   */
  val borderTopColor = new TypedStyle[Color]("borderTopColor", "border-top-color")

  /**
   * The border-style CSS property is a shorthand property for setting the line
   * style for all four sides of the elements border.
   *
   * MDN
   */
  val borderStyle = new TypedStyle[String]("borderStyle", "border-style")

  /**
   * The border-top-style CSS property sets the line style of the top border of a box.
   *
   * MDN
   */
  val borderTopStyle = new BorderStyle("borderTopStyle", "border-top-style")


  /**
   * The border-right-style CSS property sets the line style of the right border
   * of a box.
   *
   * MDN
   */
  val borderRightStyle = new BorderStyle("bocrderRightStyle", "border-right-style")

  /**
   * The border-right-width CSS property sets the width of the right border of
   * a box.
   *
   * MDN
   */
  val borderRightWidth = new BorderWidth("borderRightWidth", "border-right-width")

  /**
   * The border-top-right-radius CSS property sets the rounding of the top-right
   * corner of the element. The rounding can be a circle or an ellipse, or if
   * one of the value is 0 no rounding is done and the corner is square.
   *
   * MDN
   */
  val borderTopRightRadius = new BorderRadius("borderTopRightRadius", "border-top-right-radius")

  /**
   * The border-bottom-left-radius CSS property sets the rounding of the
   * bottom-left corner of the element. The rounding can be a circle or an
   * ellipse, or if one of the value is 0 no rounding is done and the corner is
   * square.
   *
   * MDN
   */
  val borderBottomLeftRadius = new BorderRadius("borderBottomLeftRadius", "border-bottom-left-radius")

  /**
   * The border-right-color CSS property sets the color of the top border of an
   * element. Note that in many cases the shorthand CSS properties border-color
   * or border-right are more convenient and preferable.
   *
   * MDN
   */
  val borderRightColor = new TypedStyle[Color]("borderRightColor", "border-right-color")

  /**
   * The border-bottom CSS property is a shorthand that sets the values of
   * border-bottom-color, border-bottom-style, and border-bottom-width. These
   * properties describe the bottom border of elements.
   *
   * MDN
   */
  val borderBottom = new TypedStyle[String]("borderBottom", "border-bottom")
  /**
   * The border CSS property is a shorthand property for setting the individual
   * border property values in a single place in the style sheet. border can be
   * used to set the values for one or more of: border-width, border-style,
   * border-color.
   *
   * MDN
   */
  val border = new TypedStyle[String]("border", "border")


  /**
   * The border-bottom-width CSS property sets the width of the bottom border of
   * a box.
   *
   * MDN
   */
  val borderBottomWidth = new BorderWidth("borderBottomWidth", "border-bottom-width")

  /**
   * The border-right-color CSS property sets the color of the right border of
   * an element. Note that in many cases the shorthand CSS properties
   * border-color or border-right are more convenient and preferable.
   *
   * MDN
   */
  val borderLeftColor = new TypedStyle[Color]("borderLeftColor", "border-left-color")

  /**
   * The border-bottom-color CSS property sets the color of the bottom border of
   * an element. Note that in many cases the shorthand CSS properties border-color
   * or border-bottom are more convenient and preferable.
   *
   * MDN
   */
  val borderBottomColor = new TypedStyle[Color]("borderBottomColor", "border-bottom-color")

  /**
   * The border-collapse CSS property selects a table's border model. This has
   * a big influence on the look and style of the table cells.
   *
   * MDN
   */
  object borderCollapse extends Style("borderCollapse", "border-collapse") {
    /**
     * Is a keyword requesting the use of the separated-border table rendering
     * model. It is the default value.
     *
     * MDN
     */
    val separate = this ~= "separate"
    /**
     * Is a keyword requesting the use of the collapsed-border table rendering
     * model.
     *
     * MDN
     */
    val collapse = this ~= "collapse"
  }
  /**
   * The border-left CSS property is a shorthand that sets the values of
   * border-left-color, border-left-style, and border-left-width. These
   * properties describe the left border of elements.
   *
   * The three values of the shorthand property can be specified in any order,
   * and one or two of them may be omitted.
   *
   * MDN
   */
  object borderLeft extends Style("borderLeft", "border-left") {
    def ~(width: Length, style: String, color: Color) = this ~= s"$width $style $color"
  }
  /**
   * The border-left-style CSS property sets the line style of the left border
   * of a box.
   *
   * MDN
   */
  val borderLeftStyle = new BorderStyle("borderLeftStyle", "border-left-style")

  /**
   * The border-right CSS property is a shorthand that sets the values of
   * border-right-color, border-right-style, and border-right-width. These
   * properties describe the right border of elements.
   *
   * MDN
   */
  val borderRight = new TypedStyle[String]("borderRight", "border-right")

  /**
   * The border-bottom-style CSS property sets the line style of the bottom
   * border of a box.
   *
   * MDN
   */
  val borderBottomStyle = new BorderStyle("borderBottomStyle", "border-bottom-style")
  /**
   * The border-left-width CSS property sets the width of the left border of a box.
   *
   * MDN
   */
  val borderLeftWidth = new BorderWidth("borderLeftWidth", "border-left-width")
  /**
   * The border-top-width CSS property sets the width of the top border of a box.
   *
   * MDN
   */
  val borderTopWidth = new BorderWidth("borderTopWidth", "border-top-width")
  /**
   * The border-top CSS property is a shorthand that sets the values of
   * border-top-color, border-top-style, and border-top-width. These
   * properties describe the top border of elements.
   *
   * MDN
   */
  val borderTop = new TypedStyle[String]("borderTop", "border-top")
  /**
   * The border-spacing CSS property specifies the distance between the borders
   * of adjacent cells (only for the separated borders model). This is equivalent
   * to the cellspacing attribute in presentational HTML, but an optional second
   * value can be used to set different horizontal and vertical spacing.
   *
   * MDN
   */
  object borderSpacing extends Style("borderSpacing", "border-spacing") {
    def :=(length: Length) = this ~= length.toString

    def :=(horizontal: Length, vertical: Length) = this ~= s"$horizontal $vertical"
  }


  /**
   * The border-radius CSS property allows Web authors to define how rounded
   * border corners are. The curve of each corner is defined using one or two
   * radii, defining its shape: circle or ellipse.
   *
   * MDN
   */
  val borderRadius = new TypedStyle[String]("borderRadius", "border-radius")

  /**
   * The border-width CSS property sets the width of the border of a box. Using
   * the shorthand property border is often more convenient.
   *
   * MDN
   */
  val borderWidth = new TypedStyle[String]("borderWidth", "border-width")

  /**
   * The border-bottom-right-radius CSS property sets the rounding of the
   * bottom-right corner of the element. The rounding can be a circle or an
   * ellipse, or if one of the value is 0 no rounding is done and the corner is
   * square.
   *
   * MDN
   */
  val borderBottomRightRadius = new BorderRadius("borderBottomRightRadius", "border-bottom-right-radius")

  /**
   * The border-top-left-radius CSS property sets the rounding of the
   * top-left corner of the element. The rounding can be a circle or an
   * ellipse, or if one of the value is 0 no rounding is done and the corner is
   * square.
   *
   * MDN
   */
  val borderTopLeftRadius = new BorderRadius("borderTopLeftRadius", "border-top-left-radius")

  /**
   * The border-color CSS property is a shorthand for setting the color of the
   * four sides of an element's border: border-top-color, border-right-color,
   * border-bottom-color, border-left-color
   *
   * MDN
   */
  object borderColor extends Style("borderColor", "border-color") {
    def ~(color: Color) = this ~= color.toString

    def ~(horizontal: Color, vertical: Color) = this ~= s"$horizontal $vertical"

    def ~(top: Color, vertical: Color, bottom: Color) = this ~= s"$top $vertical $bottom"

    def ~(top: Color, right: Color, bottom: Color, left: Color) = this ~= s"$top $right $bottom $left"
  }

  /**
   * The box-sizing CSS property is used to alter the default CSS box model used
   * to calculate widths and heights of elements. It is possible to use this
   * property to emulate the behavior of browsers that do not correctly support
   * the CSS box model specification.
   *
   * MDN
   */
  object boxSizing extends Style("boxSizing", "box-sizing") {
    /**
     * This is the default style as specified by the CSS standard. The width and
     * height properties are measured including only the content, but not the
     * border, margin, or padding.
     *
     * MDN
     */
    val `content-box` = this ~= "content-box"
    /**
     * The width and height properties include the padding and border, but not
     * the margin. This is the box model used by Internet Explorer when the
     * document is in Quirks mode.
     *
     * MDN
     */
    val `border-box` = this ~= "border-box"
  }

  /**
   * The CSS color property sets the foreground color of an element's text
   * content, and its decorations. It doesn't affect any other characteristic of
   * the element; it should really be called text-color and would have been
   * named so, save for historical reasons and its appearance in CSS Level 1.
   *
   * MDN
   */
  object color extends CurrentColor("color", "color"){
    val black = this ~= "black"
    val silver = this ~= "silver"
    val gray = this ~= "gray"
    val white = this ~= "white"
    val maroon = this ~= "maroon"
    val red = this ~= "red"
    val purple = this ~= "purple"
    val fuschia = this ~= "fuschia"
    val green = this ~= "green"
    val lime = this ~= "lime"
    val olive = this ~= "olive"
    val yellow = this ~= "yellow"
    val navy = this ~= "navy"
    val blue = this ~= "blue"
    val teal = this ~= "teal"
    val aqua = this ~= "aqua"
  }



  /**
   * The clip CSS property defines what portion of an element is visible. The
   * clip property applies only to elements with position:absolute.
   *
   * MDN
   */
  object clip extends Style("clip", "clip") {
    def rect(top: Length, right: Length, bottom: Length, left: Length) =
      this ~= s"rect($top, $right, $bottom, $left)"

    def auto = this ~= "auto"
  }


  /**
   * The cursor CSS property specifies the mouse cursor displayed when the mouse
   * pointer is over an element.
   *
   * MDN
   */
  object cursor extends TypedStyle[Url]("cursor", "cursor") {

    /**
     * The browser determines the cursor to display based on the current context.
     * E.g. equivalent to text when hovering text.
     *
     * MDN
     */
    val auto = this ~= "auto"
    /**
     * Default cursor, typically an arrow.
     *
     * MDN
     */
    val default = this ~= "default"
    /**
     * No cursor is rendered.
     *
     * MDN
     */
    val none = this ~= "none"
    /**
     * A context menu is available under the cursor.
     *
     * MDN
     */
    val `context-menu` = this ~= "context-menu"
    /**
     * Indicating help is available.
     *
     * MDN
     */
    val help = this ~= "help"
    /**
     * E.g. used when hovering over links, typically a hand.
     *
     * MDN
     */
    val pointer = this ~= "pointer"
    /**
     * The program is busy in the background but the user can still interact
     * with the interface (unlike for wait).
     *
     * MDN
     */
    val progress = this ~= "progress"
    /**
     * The program is busy (sometimes an hourglass or a watch).
     *
     * MDN
     */
    val cssWait = this ~= "wait"
    /**
     * Indicating that cells can be selected.
     *
     * MDN
     */
    val cell = this ~= "cell"
    /**
     * Cross cursor, often used to indicate selection in a bitmap.
     *
     * MDN
     */
    val crosshair = this ~= "crosshair"
    /**
     * Indicating text can be selected, typically an I-beam.
     *
     * MDN
     */
    val text = this ~= "text"
    /**
     * Indicating that vertical text can be selected, typically a sideways I-beam
     *
     * MDN
     */
    val `vertical-text` = this ~= "vertical-text"
    /**
     * Indicating an alias or shortcut is to be created.
     *
     * MDN
     */
    val alias = this ~= "alias"
    /**
     * Indicating that something can be copied
     *
     * MDN
     */
    val copy = this ~= "copy"
    /**
     * The hoevered object may be moved.
     *
     * MDN
     */
    val move = this ~= "move"
    /**
     * Cursor showing that a drop is not allowed at the current location.
     *
     * MDN
     */
    val `no-drop` = this ~= "no-drop"
    /**
     * Cursor showing that something cannot be done.
     *
     * MDN
     */
    val `not-allowed` = this ~= "not-allowed"
    /**
     * Cursor showing that something can be scrolled in any direction (panned).
     *
     * MDN
     */
    val `all-scroll` = this ~= "all-scroll"
    /**
     * The item/column can be resized horizontally. Often rendered as arrows
     * pointing left and right with a vertical separating.
     *
     * MDN
     */
    val `col-resize` = this ~= "col-resize"
    /**
     * The item/row can be resized vertically. Often rendered as arrows pointing
     * up and down with a horizontal bar separating them.
     *
     * MDN
     */
    val `row-resize` = this ~= "row-resize"
    /**
     * The top edge is to be moved.
     *
     * MDN
     */
    val `n-resize` = this ~= "n-resize"
    /**
     * The right edge is to be moved.
     *
     * MDN
     */
    val `e-resize` = this ~= "e-resize"
    /**
     * The bottom edge is to be moved.
     *
     * MDN
     */
    val `s-resize` = this ~= "s-resize"
    /**
     * The left edge is to be moved.
     *
     * MDN
     */
    val `w-resize` = this ~= "w-resize"
    /**
     * The top-right corner is to be moved.
     *
     * MDN
     */
    val `ne-resize` = this ~= "ne-resize"
    /**
     * The top-left corner is to be moved.
     *
     * MDN
     */
    val `nw-resize` = this ~= "nw-resize"
    /**
     * The bottom-right corner is to be moved.
     *
     * MDN
     */
    val `se-resize` = this ~= "se-resize"
    /**
     * The bottom-left corner is to be moved.
     *
     * MDN
     */
    val `sw-resize` = this ~= "sw-resize"

    val `ew-resize` = this ~= "ew-resize"
    val `ns-resize` = this ~= "ns-resize"
    val `nesw-resize` = this ~= "nesw-resize"
    val `nwse-resize` = this ~= "nwse-resize"

    /**
     * Indicates that something can be zoomed (magnified) in.
     *
     * MDN
     */
    val `zoom-in` = this ~= "zoom-in"
    /**
     * Indicates that something can be zoomed (magnified) out.
     *
     * MDN
     */
    val `zoom-out` = this ~= "zoom-out"
    /**
     * Indicates that something can be grabbed (dragged to be moved).
     *
     * MDN
     */
    val grab = this ~= "grab"
    /**
     * Indicates that something can be grabbed (dragged to be moved).
     *
     * MDN
     */
    val grabbing = this ~= "grabbing"
  }


  /**
   * The float CSS property specifies that an element should be taken from the
   * normal flow and placed along the left or right side of its container, where
   * text and inline elements will wrap around it. A floating element is one
   * where the computed value of float is not none.
   *
   * MDN
   */
  object float extends Style("cssFloat", "float") {
    /**
     * Is a keyword indicating that the element must float on the left side of
     * its containing block.
     *
     * MDN
     */
    val left = this ~= "left"
    /**
     * Is a keyword indicating that the element must float on the right side of
     * its containing block.
     *
     * MDN
     */
    val right = this ~= "right"
    /**
     * Is a keyword indicating that the element must not float
     *
     * MDN
     */
    val none = this ~= "none"
  }


  /**
   * Set the direction CSS property to match the direction of the text: rtl for
   * Hebrew or Arabic text and ltr for other scripts. This is typically done as
   * part of the document (e.g., using the dir attribute in HTML) rather than
   * through direct use of CSS.
   *
   * The property sets the base text direction of block-level elements and the
   * direction of embeddings created by the unicode-bidi property. It also sets
   * the default alignment of text and block-level elements and the direction
   * that cells flow within a table row.
   *
   * Unlike the dir attribute in HTML, the direction property is not inherited
   * from table columns into table cells, since CSS inheritance follows the
   * document tree, and table cells are inside of the rows but not inside of the
   * columns.
   *
   * The direction and unicode-bidi properties are the two only properties which
   * are not affected by the all shorthand.
   *
   * MDN
   */
  object direction extends Style("direction", "direction") {
    /**
     * The initial value of direction (that is, if not otherwise specified). Text
     * and other elements go from left to right.
     *
     * MDN
     */
    val ltr = this ~= "ltr"
    /**
     * Text and other elements go from right to left
     *
     * MDN
     */
    val rtl = this ~= "rtl"
  }

  /**
   * The display CSS property specifies the type of rendering box used for an
   * element. In HTML, default display property values are taken from behaviors
   * described in the HTML specifications or from the browser/user default
   * stylesheet. The default value in XML is inline.
   *
   * In addition to the many different display box types, the value none lets
   * you turn off the display of an element; when you use none, all descendant
   * elements also have their display turned off. The document is rendered as
   * though the element doesn't exist in the document tree.
   *
   * MDN
   */
  object display extends Style("display", "display") {
    /**
     * Turns off the display of an element (it has no effect on layout); all
     * descendant elements also have their display turned off. The document is
     * rendered as though the element did not exist.
     *
     * To render an element box's dimensions, yet have its contents be invisible,
     * see the visibility property.
     *
     * MDN
     */
    val none = this ~= "none"
    /**
     * The element generates one or more inline element boxes.
     *
     * MDN
     */
    val inline = this ~= "inline"
    /**
     * The element generates a block element box.
     *
     * MDN
     */
    val block = this ~= "block"
    /**
     * The element generates a block box for the content and a separate
     * list-item inline box.
     *
     * MDN
     */
    val `list-item` = this ~= "list-item"
    /**
     * The element generates a block element box that will be flowed with
     * surrounding content as if it were a single inline box.
     *
     * MDN
     */
    val `inline-block` = this ~= "inline-block"
    /**
     * The inline-table value does not have a direct mapping in HTML. It behaves
     * like a `<table>` HTML element, but as an inline box, rather than a
     * block-level box. Inside the table box is a block-level context
     *
     * MDN
     */
    val `inline-table` = this ~= "inline-table"
    /**
     * Behaves like the `<table>` HTML element. It defines a block-level box.
     *
     * MDN
     */
    val table = this ~= "table"
    /**
     * Behaves like the `<caption>` HTML element.
     *
     * MDN
     */
    val `table-caption` = this ~= "table-caption"
    /**
     * Behaves like the `<td>` HTML element
     *
     * MDN
     */
    val `table-cell` = this ~= "table-cell"
    /**
     * These elements behave like the corresponding `<col>` HTML elements.
     *
     * MDN
     */
    val `table-column` = this ~= "table-column"
    /**
     * These elements behave like the corresponding `<colgroup>` HTML elements.
     *
     * MDN
     */
    val `table-column-group` = this ~= "table-column-group"
    /**
     * These elements behave like the corresponding `<tfoot>` HTML elements
     *
     * MDN
     */
    val `table-footer-group` = this ~= "table-footer-group"
    /**
     * These elements behave like the corresponding `<thead>` HTML elements
     *
     * MDN
     */
    val `table-header-group` = this ~= "table-header-group"
    /**
     * Behaves like the `<tr>` HTML element
     *
     * MDN
     */
    val `table-row` = this ~= "table-row"
    /**
     * These elements behave like the corresponding `<tbody>` HTML elements
     *
     * MDN
     */
    val `table-row-group` = this ~= "table-row-group"
    /**
     * The element behaves like a block element and lays out its content according
     * to the flexbox model.
     *
     * MDN
     */
    val flex = this ~= "flex"
    /**
     * The element behaves like an inline element and lays out its content
     * according to the flexbox model.
     *
     * MDN
     */
    val `inline-flex` = this ~= "inline-flex"
  }



  /**
   * The CSS property pointer-events allows authors to control under what
   * circumstances (if any) a particular graphic element can become the target
   * of mouse events. When this property is unspecified, the same characteristics
   * of the visiblePainted value apply to SVG content.
   *
   * In addition to indicating that the element is not the target of mouse events,
   * the value none instructs the mouse event to go "through" the element and
   * target whatever is "underneath" that element instead.
   *
   * MDN
   */
  object pointerEvents extends Style("pointerEvents", "pointer-events") {
    /**
     * The element behaves as it would if the pointer-events property was not
     * specified. In SVG content, this value and the value visiblePainted have
     * the same effect.
     *
     * MDN
     */
    val auto = this ~= "auto"
    /**
     * The element is never the target of mouse events; however, mouse events
     * may target its descendant elements if those descendants have pointer-events
     * set to some other value. In these circumstances, mouse events will trigger
     * event listeners on this parent element as appropriate on their way to/from
     * the descendant during the event capture/bubble phases.
     *
     * MDN
     */
    val none = this ~= "none"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * visibility property is set to visible and when the mouse cursor is over
     * the interior (i.e., 'fill') of the element and the fill property is set
     * to a value other than none, or when the mouse cursor is over the perimeter
     * (i.e., 'stroke') of the element and the stroke property is set to a value
     * other than none.
     *
     * MDN
     */
    val visiblePainted = this ~= "visiblePainted"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * visibility property is set to visible and when the mouse cursor is over
     * the interior (i.e., fill) of the element. The value of the fill property
     * does not effect event processing.
     *
     * MDN
     */
    val visibleFill = this ~= "visibleFill"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * visibility property is set to visible and when the mouse cursor is over
     * the perimeter (i.e., stroke) of the element. The value of the stroke
     * property does not effect event processing.
     *
     * MDN
     */
    val visibleStroke = this ~= "visibleStroke"
    /**
     * SVG only. The element can be the target of a mouse event when the
     * visibility property is set to visible and the mouse cursor is over either
     * the interior (i.e., fill) or the perimeter (i.e., stroke) of the element.
     * The values of the fill and stroke do not effect event processing.
     *
     * MDN
     */
    val visible = this ~= "visible"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * mouse cursor is over the interior (i.e., 'fill') of the element and the
     * fill property is set to a value other than none, or when the mouse cursor
     * is over the perimeter (i.e., 'stroke') of the element and the stroke
     * property is set to a value other than none. The value of the visibility
     * property does not effect event processing.
     *
     * MDN
     */
    val painted = this ~= "painted"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * pointer is over the interior (i.e., fill) of the element. The values of
     * the fill and visibility properties do not effect event processing.
     *
     * MDN
     */
    val fill = this ~= "fill"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * pointer is over the perimeter (i.e., stroke) of the element. The values
     * of the stroke and visibility properties do not effect event processing.
     *
     * MDN
     */
    val stroke = this ~= "stroke"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * pointer is over the interior (i.e., fill) or the perimeter (i.e., stroke)
     * of the element. The values of the fill, stroke and visibility properties
     * do not effect event processing.
     *
     * MDN
     */
    val all = this ~= "all"
  }


  /**
   * The list-style-image CSS property sets the image that will be used as the
   * list item marker. It is often more convenient to use the shorthand
   * list-style.
   *
   * MDN
   */
  object listStyleImage extends TypedStyle[Url]("listStyleImage", "list-style-image"){

    def none = this ~= "none"
  }


  /**
   * The list-style-position CSS property specifies the position of the marker
   * box in the principal block box. It is often more convenient to use the
   * shortcut list-style.
   *
   * MDN
   */
  object listStylePosition extends Style("listStylePosition", "list-style-position") {
    /**
     * The marker box is outside the principal block box.
     *
     * MDN
     */
    val outside = this ~= "outside"
    /**
     * The marker box is the first inline box in the principal block box, after
     * which the element's content flows.
     *
     * MDN
     */
    val inside = this ~= "inside"
  }

  object wordWrap extends Style("wordWrap", "word-wrap") {
    /**
     * Indicates that lines may only break at normal word break points.
     *
     * MDN
     */
    val normal = this ~= "normal"
    /**
     * Indicates that normally unbreakable words may be broken at arbitrary
     * points if there are no otherwise acceptable break points in the line.
     *
     * MDN
     */
    val `break-word` = this ~= "break-word"
  }


  /**
   * The opacity CSS property specifies the transparency of an element, that is,
   * the degree to which the background behind the element is overlaid.
   *
   * The value applies to the element as a whole, including its contents, even
   * though the value is not inherited by child elements. Thus, an element and
   * its contained children all have the same opacity relative to the element's
   * background, even if the element and its children have different opacities
   * relative to one another.
   *
   * Using this property with a value different than 1 places the element in a
   * new stacking context.
   *
   * MDN
   */
  val opacity = new TypedStyle[Double]("opacity", "opacity")


  /**
   * The max-width CSS property is used to set the maximum width of a given
   * element. It prevents the used value of the width property from becoming
   * larger than the value specified for max-width.
   *
   * max-width overrides width, but min-width overrides max-width.
   *
   * MDN
   */
  val maxWidth = new TypedStyle[Length]("maxWidth", "max-width")


  /**
   * The vertical-align CSS property specifies the vertical alignment of an
   * inline or table-cell box.
   *
   * MDN
   */
  object verticalAlign extends TypedStyle[Length]("verticalAlign", "vertical-align") {
    /**
     * Aligns the baseline of the element with the baseline of its parent. The
     * baseline of some replaced elements, like `<textarea>` is not specified by
     * the HTML specification, meaning that their behavior with this keyword may
     * change from one browser to the other.
     *
     * MDN
     */
    val baseline = this ~= "baseline"
    /**
     * Aligns the baseline of the element with the subscript-baseline of its
     * parent.
     *
     * MDN
     */
    val sub = this ~= "sub"
    /**
     * Aligns the baseline of the element with the superscript-baseline of its
     * parent.
     *
     * MDN
     */
    val `super` = this ~= "super"
    /**
     * Aligns the top of the element with the top of the parent element's font.
     *
     * MDN
     */
    val `text-top` = this ~= "text-top"
    /**
     * Aligns the bottom of the element with the bottom of the parent element's
     * font.
     *
     * MDN
     */
    val `text-bottom` = this ~= "text-bottom"
    /**
     * Aligns the middle of the element with the middle of lowercase letters in
     * the parent.
     *
     * MDN
     */
    val middle = this ~= "middle"
  }


  /**
   * The overflow CSS property specifies whether to clip content, render scroll
   * bars or display overflow content of a block-level element.
   *
   * MDN
   */
  object overflow extends Overflow("overflow", "overflow")

  /**
   * If the value is a URI value, the element pointed to by the URI is used as
   * an SVG mask.
   *
   * MDN
   */
  object mask extends Style("mask", "mask") {
    val none = this ~= "none"

    def uri(s: String) = this ~= s"uri($s)"
  }



  /**
   * he empty-cells CSS property specifies how user agents should render borders
   * and backgrounds around cells that have no visible content.
   *
   * MDN
   */
  object emptyCells extends Style("emptyCells", "empty-cells") {
    /**
     * Is a keyword indicating that borders and backgrounds should be drawn like
     * in a normal cells.
     *
     * MDN
     */
    val show = this ~= "show"
    /**
     * Is a keyword indicating that no border or backgrounds should be drawn.
     *
     * MDN
     */
    val hide = this ~= "hide"
  }


  /**
   * The height CSS property specifies the height of the content area of an
   * element. The content area is inside the padding, border, and margin of the
   * element.
   *
   * The min-height and max-height properties override height.
   *
   * MDN
   */
  val height = new AutoStyle[Length]("height", "height")


  /**
   * The padding-right CSS property of an element sets the padding space
   * required on the right side of an element. The padding area is the space
   * between the content of the element and its border. Negative values are not
   * allowed.
   *
   * MDN
   */
  val paddingRight = new TypedStyle[Length]("paddingRight", "padding-right")

  /**
   * The padding-top CSS property of an element sets the padding space required
   * on the top of an element. The padding area is the space between the content
   * of the element and its border. Contrary to margin-top values, negative
   * values of padding-top are invalid.
   *
   * MDN
   */
  val paddingTop = new TypedStyle[Length]("paddingTop", "padding-top")

  /**
   * The padding-left CSS property of an element sets the padding space required
   * on the left side of an element. The padding area is the space between the
   * content of the element and it's border. A negative value is not allowed.
   *
   * MDN
   */
  val paddingLeft = new TypedStyle[Length]("paddingLeft", "padding-left")

  /**
   * The padding CSS property sets the required padding space on all sides of an
   * element. The padding area is the space between the content of the element
   * and its border. Negative values are not allowed.
   *
   * The padding property is a shorthand to avoid setting each side separately
   * (padding-top, padding-right, padding-bottom, padding-left).
   *
   * MDN
   */
  val padding = new TypedStyle[String]("padding", "padding")

  /**
   * The padding-bottom CSS property of an element sets the height of the padding
   * area at the bottom of an element. The padding area is the space between the
   * content of the element and it's border. Contrary to margin-bottom values,
   * negative values of padding-bottom are invalid.
   *
   * MDN
   */
  val paddingBottom = new TypedStyle[Length]("paddingBottom", "padding-bottom")

  /**
   * The right CSS property specifies part of the position of positioned elements.
   *
   * For absolutely positioned elements (those with position: absolute or
   * position: fixed), it specifies the distance between the right margin edge
   * of the element and the right edge of its containing block.
   *
   * The right property has no effect on non-positioned elements.
   *
   * When both the right CSS property and the left CSS property are defined, the
   * position of the element is overspecified. In that case, the left value has
   * precedence when the container is left-to-right (that is that the right
   * computed value is set to -left), and the right value has precedence when
   * the container is right-to-left (that is that the left computed value is set
   * to -right).
   *
   * MDN
   */
  val right = new AutoStyle[Length]("right", "right")



  /**
   * On block level elements, the line-height CSS property specifies the minimal
   * height of line boxes within the element.
   *
   * On non-replaced inline elements, line-height specifies the height that is
   * used in the calculation of the line box height.
   *
   * On replaced inline elements, like buttons or other input element, line-height has no effect.
   *
   * MDN
   */
  val lineHeight = new NormalOpenStyle[Length]("lineHeight", "lineheight")

  /**
   * The left CSS property specifies part of the position of positioned elements.
   *
   * For absolutely positioned elements (those with position: absolute or
   * position: fixed), it specifies the distance between the left margin edge of
   * the element and the left edge of its containing block.
   *
   * MDN
   */
  val left = new AutoStyle[Length]("left", "left")







  /**
   * The list-style-type CSS property specifies appearance of a list item element.
   * As it is the only one who defaults to display:list-item, this is usually a
   * `<li>` element, but can be any element with this display value.
   *
   * MDN
   */
  object listStyleType extends Style("listStyleType", "list-style-type") {
    /**
     * No item marker is shown
     *
     * MDN
     */
    val none = this ~= "none"
    /**
     * A filled circle (default value)
     *
     * MDN
     */
    val disc = this ~= "disc"
    /**
     * A hollow circle
     *
     * MDN
     */
    val circle = this ~= "circle"
    /**
     * A filled square
     *
     * MDN
     */
    val square = this ~= "square"
    /**
     * Decimal numbers begining with 1
     *
     * MDN
     */
    val decimal = this ~= "decimal"
    /**
     * Han decimal numbers
     *
     * MDN
     */
    val `cjk-decimal` = this ~= "cjk-decimal"
    /**
     * Decimal numbers padded by initial zeros
     *
     * MDN
     */
    val `decimal-leading-zero` = this ~= "decimal-leading-zero"
    /**
     * Lowercase roman numerals
     *
     * MDN
     */
    val `lower-roman` = this ~= "lower-roman"
    /**
     * Uppercase roman numerals
     *
     * MDN
     */
    val `upper-roman` = this ~= "upper-roman"
    /**
     * Lowercase classical greek
     *
     * MDN
     */
    val `lower-greek` = this ~= "lower-greek"
    /**
     * Lowercase ASCII letters
     *
     * MDN
     */
    val `lower-alpha` = this ~= "lower-alpha"
    /**
     * Lowercase ASCII letters
     *
     * MDN
     */
    val `lower-latin` = this ~= "lower-latin"
    /**
     * Uppercase ASCII letters
     *
     * MDN
     */
    val `upper-alpha` = this ~= "upper-alpha"
    /**
     * Uppercase ASCII letters
     *
     * MDN
     */
    val `upper-latin` = this ~= "upper-latin"
    /**
     * Traditional Armenian numbering
     *
     * MDN
     */
    val armenian = this ~= "armenian"
    /**
     * Traditional Georgian numbering
     *
     * MDN
     */
    val georgian = this ~= "georgian"
    /**
     * Traditional Hebrew numbering
     *
     * MDN
     */
    val hebrew = this ~= "hebrew"
    /**
     * Japanese Hiragana
     *
     * MDN
     */
    val hiragana = this ~= "hiragana"
    /**
     * Japanese Hiragana
     *
     * Iroha is the old japanese ordering of syllabs
     *
     * MDN
     */
    val `hiragana-iroha` = this ~= "hiragana-iroha"
    /**
     * Japanese Katakana
     *
     * MDN
     */
    val katakana = this ~= "katakana"
    /**
     * Japanese Katakana
     *
     * Iroha is the old japanese ordering of syllabs
     *
     * MDN
     */
    val `katakana-iroha` = this ~= "katakana-iroha"
  }



  /**
   * The list-style CSS property is a shorthand property for setting
   * list-style-type, list-style-image and list-style-position.
   *
   * MDN
   */
  val listStyle = new TypedStyle[String]("listStyle", "list-style")

  /**
   * The overflow-y CSS property specifies whether to clip content, render a
   * scroll bar, or display overflow content of a block-level element, when it
   * overflows at the top and bottom edges.
   *
   * MDN
   */
  val overflowY = new Overflow("overflowY", "overflow-y")

  /**
   * The caption-side CSS property positions the content of a table's `<caption>`
   * on the specified side.
   *
   * MDN
   */
  object captionSide extends Style("captionSide", "caption-side") {
    /**
     * The caption box will be above the table.
     *
     * MDN
     */
    val top = this ~= "top"
    /**
     * The caption box will be below the table.
     *
     * MDN
     */
    val bottom = this ~= "bottom"
  }

  /**
   * The box-shadow CSS property describes one or more shadow effects as a
   * comma-separated list. It allows casting a drop shadow from the frame of
   * almost any element. If a border-radius is specified on the element with a
   * box shadow, the box shadow takes on the same rounded corners. The z-ordering
   * of multiple box shadows is the same as multiple text shadows (the first
   * specified shadow is on top).
   *
   * MDN
   */
  val boxShadow = new TypedStyle[String]("boxShadow", "box-shadow")


  /**
   * The position CSS property chooses alternative rules for positioning elements,
   * designed to be useful for scripted animation effects.
   *
   * MDN
   */
  object position extends Style("position", "position") {
    /**
     * This keyword let the element use the normal behavior, that is it is laid
     * out in its current position in the flow.  The top, right, bottom, and left
     * properties do not apply.
     *
     * MDN
     */
    val static = this ~= "static"
    /**
     * This keyword lays out all elements as though the element were not
     * positioned, and then adjust the element's position, without changing
     * layout (and thus leaving a gap for the element where it would have been
     * had it not been positioned). The effect of position:relative on
     * table-*-group, table-row, table-column, table-cell, and table-caption
     * elements is undefined.
     *
     * MDN
     */
    val relative = this ~= "relative"
    /**
     * Do not leave space for the element. Instead, position it at a specified
     * position relative to its closest positioned ancestor or to the containing
     * block. Absolutely positioned boxes can have margins, they do not collapse
     * with any other margins.
     *
     * MDN
     */
    val absolute = this ~= "absolute"
    /**
     * Do not leave space for the element. Instead, position it at a specified
     * position relative to the screen's viewport and doesn't move when scrolled.
     * When printing, position it at that fixed position on every page.
     *
     * MDN
     */
    val fixed = this ~= "fixed"
  }


  object quotes extends Style("quotes", "quotes") {
    /**
     * The open-quote and close-quote values of the content property produce no
     * quotation marks.
     *
     * MDN
     */
    val none = this ~= "none"

    def ~(pairs: (String, String)*) = {
      this ~= pairs.flatMap(x => Seq(x._1, x._2)).map('"' + _ + '"').mkString(" ")
    }

  }

  object tableLayout extends Style("tableLayout", "table-layout") {
    /**
     * An automatic table layout algorithm is commonly used by most browsers for
     * table layout. The width of the table and its cells depends on the content
     * thereof.
     *
     * MDN
     */
    val auto = this ~= "auto"
    /**
     * Table and column widths are set by the widths of table and col elements
     * or by the width of the first row of cells. Cells in subsequent rows do
     * not affect column widths.
     *
     * MDN
     */
    val fixed = this ~= "fixed"
  }


  /**
   * The font-size CSS property specifies the size of the font – specifically
   * the desired height of glyphs from the font. Setting the font size may, in
   * turn, change the size of other items, since it is used to compute the value
   * of em and ex Length units.
   *
   * MDN
   */
  object fontSize extends TypedStyle[Length]("fontSize", "font-size") {
    val `xx-small` = this ~= "xx-small"
    val `x-small` = this ~= "x-small"
    val small = this ~= "small"
    val medium = this ~= "medium"
    val large = this ~= "large"
    val `x-large` = this ~= "x-large"
    val `xx-large` = this ~= "xx-large"
    /**
     * Larger than the parent element's font size, by roughly the ratio used to
     * separate the absolute size keywords above.
     *
     * MDN
     */
    val larger = this ~= "larger"
    /**
     * Smaller than the parent element's font size, by roughly the ratio used to
     * separate the absolute size keywords above.
     *
     * MDN
     */
    val smaller = this ~= "smaller"
  }


  /**
   * The font-size-adjust CSS property specifies that font size should be chosen
   * based on the height of lowercase letters rather than the height of capital
   * letters.
   *
   * This is useful since the legibility of fonts, especially at small sizes, is
   * determined more by the size of lowercase letters than by the size of capital
   * letters. This can cause problems when the first-choice font-family is
   * unavailable and its replacement has a significantly different aspect ratio
   * (the ratio of the size of lowercase letters to the size of the font).
   *
   * MDN
   */
  val fontSizeAdjust = new TypedStyle[Number]("fontSizeAdjust", "font-size-adjust")

  /**
   * The font-family CSS property allows for a prioritized list of font family
   * names and/or generic family names to be specified for the selected element.
   * Unlike most other CSS properties, values are separated by a comma to indicate
   * that they are alternatives. The browser will select the first font on the
   * list that is installed on the computer, or that can be downloaded using the
   * information provided by a @font-face at-rule.
   *
   * MDN
   */
  val fontFamily = new TypedStyle[String]("fontFamily", "font-family")


  /**
   * The font-weight CSS property specifies the weight or boldness of the font.
   * However, some fonts are not available in all weights; some are available
   * only on normal and bold.
   *
   * Numeric font weights for fonts that provide more than just normal and bold.
   * If the exact weight given is unavailable, then 600-900 use the closest
   * available darker weight (or, if there is none, the closest available
   * lighter weight), and 100-500 use the closest available lighter weight (or,
   * if there is none, the closest available darker weight). This means that for
   * fonts that provide only normal and bold, 100-500 are normal, and 600-900
   * are bold.
   *
   * MDN
   */
  object fontWeight extends TypedStyle[Int]("fontWeight", "font-weight") {
    /**
     * Normal font weight. Same as 400.
     *
     * MDN
     */
    val normal = this ~= "normal"
    /**
     * Bold font weight. Same as 700.
     *
     * MDN
     */
    val bold = this ~= "bold"
    /**
     * One font weight lighter than the parent element (among the available
     * weights of the font).
     *
     * MDN
     */
    val lighter = this ~= "lighter"
    /**
     * One font weight darker than the parent element (among the available
     * weights of the font)
     *
     * MDN
     */
    val bolder = this ~= "bolder"

  }

  /**
   * The font CSS property is either a shorthand property for setting font-style,
   * font-variant, font-weight, font-size, line-height and font-family, or a way
   * to set the element's font to a system font, using specific keywords.
   *
   * MDN
   */
  val font = new TypedStyle[String]("font", "font")

  /**
   * The font-feature-settings CSS property allows control over advanced
   * typographic features in OpenType fonts.
   *
   * MDN
   */
  val fontFeatureSettings = new TypedStyle[String]("fontFeatureSettings", "font-feature-settings")

  /**
   * The font-style CSS property allows italic or oblique faces to be selected
   * within a font-family.
   *
   * MDN
   */
  object fontStyle extends Style("fontStyle", "font-style"){
    /**
     * Selects a font that is classified as normal within a font-family
     *
     * MDN
     */
    val normal = this ~= "normal"
    /**
     * Selects a font that is labeled italic, if that is not available, one labeled oblique
     *
     * MDN
     */
    val italic = this ~= "italic"
    /**
     * Selects a font that is labeled oblique
     *
     * MDN
     */
    val oblique = this ~= "oblique"
  }

  /**
   * The clear CSS property specifies whether an element can be next to floating
   * elements that precede it or must be moved down (cleared) below them.
   *
   * The clear property applies to both floating and non-floating elements.
   *
   * MDN
   */
  object clear extends Style("clear", "clear") {
    /**
     * The element is not moved down to clear past floating elements.
     *
     * MDN
     */
    val none = this ~= "none"
    /**
     * The element is moved down to clear past left floats.
     *
     * MDN
     */
    val left = this ~= "left"
    /**
     * The element is moved down to clear past right floats.
     *
     * MDN
     */
    val right = this ~= "right"
    /**
     * The element is moved down to clear past both left and right floats.
     *
     * MDN
     */
    val both = this ~= "both"
  }

  /**
   * The content CSS property is used with the ::before and ::after pseudo-elements
   * to generate content in an element. Objects inserted using the content
   * property are anonymous replaced elements.
   *
   * MDN
   */
  val content = new TypedStyle[String]("content", "content")


  /**
   * The margin-bottom CSS property of an element sets the margin space required
   * on the bottom of an element. A negative value is also allowed.
   *
   * MDN
   */
  val marginBottom = new AutoStyle[Length]("marginBottom", "margin-bottom")

  /**
   * The margin-right CSS property of an element sets the margin space required
   * on the bottom of an element. A negative value is also allowed.
   *
   * MDN
   */
  val marginRight = new TypedStyle[Length]("marginRight", "margin-right") with MarginAuto


  /**
   * The margin-top CSS property of an element sets the margin space required on
   * the top of an element. A negative value is also allowed.
   *
   * MDN
   */
  val marginTop = new TypedStyle[Length]("marginTop", "margin-top") with MarginAuto


  /**
   * The margin-left CSS property of an element sets the margin space required
   * on the left side of a box associated with an element. A negative value is
   * also allowed.
   *
   * The vertical margins of two adjacent boxes may fuse. This is called margin
   * collapsing.
   *
   * MDN
   */
  val marginLeft = new TypedStyle[Length]("marginLeft", "margin-left") with MarginAuto
  /**
   * The margin CSS property sets the margin for all four sides. It is a
   * shorthand to avoid setting each side separately with the other margin
   * properties: margin-top, margin-right, margin-bottom and margin-left.
   *
   * Negative values are also allowed.
   *
   * MDN
   */
  object margin extends Style("margin", "margin") {
    /**
     * auto is replaced by some suitable value, e.g. it can be used for
     * centering of blocks.
     *
     * MDN
     */
    val auto = this ~= "auto"

    def ~(allSides: Length) = this ~= allSides.toString

    def ~(vertical: Length, horizontal: Length) = this ~= s"$vertical $horizontal"

    def ~(top: Length, horizontal: Length, bottom: Length) = this ~= s"$top $horizontal $bottom"

    def ~(top: Length, right: Length, bottom: Length, left: Length) = this ~= s"$top $right $bottom $left"
  }


  /**
   * The top CSS property specifies part of the position of positioned elements.
   * It has no effect on non-positioned elements.
   *
   * For absolutely positioned elements (those with position: absolute or
   * position: fixed), it specifies the distance between the top margin edge of
   * the element and the top edge of its containing block.
   *
   * For relatively positioned elements (those with position: relative), it
   * specifies the amount the element is moved below its normal position.
   *
   * When both top and bottom are specified, the element position is
   * over-constrained and the top property has precedence: the computed value
   * of bottom is set to -top, while its specified value is ignored.
   *
   * MDN
   */
  val top = new AutoStyle[Length]("top", "top")


  /**
   * The width CSS property specifies the width of the content area of an element.
   * The content area is inside the padding, border, and margin of the element.
   *
   * The min-width and max-width properties override width.
   *
   * MDN
   */
  val width = new AutoStyle[Length]("width", "width")

  /**
   * The bottom CSS property participates in specifying the position of
   * positioned elements.
   *
   * For absolutely positioned elements, that is those with position: absolute
   * or position: fixed, it specifies the distance between the bottom margin edge
   * of the element and the bottom edge of its containing block.
   *
   * For relatively positioned elements, that is those with position: relative,
   * it specifies the distance the element is moved above its normal position.
   *
   * However, the top property overrides the bottom property, so if top is not
   * auto, the computed value of bottom is the negative of the computed value of
   * top.
   *
   * MDN
   */
  val bottom = new AutoStyle[Length]("bottom", "bottom")

  /**
   * The letter-spacing CSS property specifies spacing behavior between text
   * characters.
   *
   * MDN
   */
  val letterSpacing = new NormalOpenStyle[Length]("letterSpacing", "letter-spacing")


  /**
   * The max-height CSS property is used to set the maximum height of a given
   * element. It prevents the used value of the height property from becoming
   * larger than the value specified for max-height.
   *
   * max-height overrides height, but min-height overrides max-height.
   *
   * MDN
   */
  val maxHeight = new NoneOpenStyle[Length]("maxHeight", "max-height")

  /**
   * The min-width CSS property is used to set the minimum width of a given
   * element. It prevents the used value of the width property from becoming
   * smaller than the value specified for min-width.
   *
   * The value of min-width overrides both max-width and width.
   *
   * MDN
   */
  val minWidth = new TypedStyle[Length]("minWidth", "minWidth")


  /**
   * The min-height CSS property is used to set the minimum height of a given
   * element. It prevents the used value of the height property from becoming
   * smaller than the value specified for min-height.
   *
   * The value of min-height overrides both max-height and height.
   *
   * MDN
   */
  val minHeight = new TypedStyle[Length]("minHeight", "min-height")


  /**
   * The CSS outline property is a shorthand property for setting one or more of
   * the individual outline properties outline-style, outline-width and
   * outline-color in a single rule. In most cases the use of this shortcut is
   * preferable and more convenient.
   *
   * Outlines do not take up space, they are drawn above the content.
   *
   * MDN
   */
  val outline = new TypedStyle[String]("outline", "outline")

  /**
   * The outline-style CSS property is used to set the style of the outline of
   * an element. An outline is a line that is drawn around elements, outside the
   * border edge, to make the element stand out.
   *
   * MDN
   */
  val outlineStyle = new OutlineStyle("outlineStyle", "outline-style")

  /**
   * The outline-width CSS property is used to set the width of the outline of
   * an element. An outline is a line that is drawn around elements, outside the
   * border edge, to make the element stand out.
   *
   * MDN
   */
  object outlineWidth extends TypedStyle[Length]("outlineWidth", "outline-width") {
    /**
     * Typically 1px in desktop browsers like Firefox.
     *
     * MDN
     */
    val thin = this ~= "thin"
    /**
     * Typically 3px in desktop browsers like Firefox.
     *
     * MDN
     */
    val medium = this ~= "medium"
    /**
     * Typically 5px in desktop browsers like Firefox.
     *
     * MDN
     */
    val thick = this ~= "thick"
  }

  /**
   * The outline-color CSS property sets the color of the outline of an element.
   * An outline is a line that is drawn around elements, outside the border edge,
   * to make the element stand out.
   *
   * MDN
   */
  object outlineColor extends TypedStyle[Color]("outlineColor", "outline-color") {
    /**
     * To ensure the outline is visible, performs a color inversion of the
     * background. This makes the focus border more salient, regardless of the
     * color in the background. Note that browser are not required to support
     * it. If not, they just consider the statement as invalid
     *
     * MDN
     */
    val invert = this ~= "invert"
  }


  /**
   * The overflow-x CSS property specifies whether to clip content, render a
   * scroll bar or display overflow content of a block-level element, when it
   * overflows at the left and right edges.
   *
   * MDN
   */
  val overflowX = new Overflow("overflowX", "overflow-x")


  /**
   * The text-align-last CSS property describes how the last line of a block or
   * a line, right before a forced line break, is aligned.
   *
   * MDN
   */
  val textAlignLast = new Style("textAlignLast", "text-align-last") with TextAlign
  trait TextAlign extends Style{
    /**
     * The same as left if direction is left-to-right and right if direction is
     * right-to-left.
     *
     * MDN
     */
    val start = this ~= "start"
    /**
     * The same as right if direction is left-to-right and left if direction is
     * right-to-left.
     *
     * MDN
     */
    val end = this ~= "end"
    /**
     * The inline contents are aligned to the left edge of the line box.
     *
     * MDN
     */
    val left = this ~= "left"
    /**
     * The inline contents are aligned to the right edge of the line box.
     *
     * MDN
     */
    val right = this ~= "right"
    /**
     * The inline contents are centered within the line box.
     *
     * MDN
     */
    val center = this ~= "center"
    /**
     * The text is justified. Text should line up their left and right edges to
     * the left and right content edges of the paragraph.
     *
     * MDN
     */
    val justify = this ~= "justify"
  }
  /**
   * The text-align CSS property describes how inline content like text is
   * aligned in its parent block element. text-align does not control the
   * alignment of block elements itself, only their inline content.
   *
   * MDN
   */
  val textAlign = new Style("textAlign", "text-align") with TextAlign
  /**
   * The text-decoration CSS property is used to set the text formatting to
   * underline, overline, line-through or blink.
   *
   * MDN
   */
  object textDecoration extends Style("textDecoration", "text-decoration") {
    /**
     * Produces no text decoration.
     *
     * MDN
     */
    val none = this ~= "none"
    /**
     * Each line of text is underlined.
     *
     * MDN
     */
    val underline = this ~= "underline"
    /**
     * Each line of text has a line above it.
     *
     * MDN
     */
    val overline = this ~= "overline"
    /**
     * Each line of text has a line through the middle.
     *
     * MDN
     */
    val `line-through` = this ~= "line-through"
  }

  /**
   * The text-indent CSS property specifies how much horizontal space should be
   * left before the beginning of the first line of the text content of an element.
   * Horizontal spacing is with respect to the left (or right, for right-to-left
   * layout) edge of the containing block element's box.
   *
   * MDN
   */
  val textIndent = new TypedStyle[Length]("textIndent", "text-indent")

  /**
   * The text-overflow CSS property determines how overflowed content that is
   * not displayed is signaled to the users. It can be clipped, or display an
   * ellipsis ('…', U+2026 HORIZONTAL ELLIPSIS) or a Web author-defined string.
   *
   * MDN
   */
  object textOverflow extends Style("textOverflow", "text-overflow") {
    /**
     * This keyword value indicates to truncate the text at the limit of the
     * content area, therefore the truncation can happen in the middle of a
     * character. To truncate at the transition between two characters, the
     * empty string value must be used. The value clip is the default for
     * this property.
     *
     * MDN
     */
    val clip = this ~= "clip"
    /**
     * This keyword value indicates to display an ellipsis ('…', U+2026 HORIZONTAL
     * ELLIPSIS) to represent clipped text. The ellipsis is displayed inside the
     * content area, decreasing the amount of text displayed. If there is not
     * enough space to display the ellipsis, it is clipped.
     *
     * MDN
     */
    val ellipsis = this ~= "ellipsis"
  }
  /**
   * The CSS text-underline-position property specifies the position of the
   * underline which is set using the text-decoration property underline value.
   *
   * This property inherits and is not reset by the text-decoration shorthand,
   * allowing to easily set it globally for a given document.
   *
   * MDN
   */
  object textUnderlinePosition extends Style("textUnderlinePosition", "text-underline-position") {
    /**
     * This keyword allows the browser to use an algorithm to choose between
     * under and alphabetic.
     *
     * MDN
     */
    val auto = this ~= "auto"
    /**
     * This keyword forces the line to be set below the alphabetic baseline, at
     * a position where it won't cross any descender. This is useful to prevent
     * chemical or mathematical formulas, which make a large use of subscripts,
     * to be illegible.
     *
     * MDN
     */
    val under = this ~= "under"
    /**
     * In vertical writing-modes, this keyword forces the line to be placed on
     * the left of the characters. In horizontal writing-modes, it is a synonym
     * of under.
     *
     * MDN
     */
    val left = this ~= "left"
    /**
     * In vertical writing-modes, this keyword forces the line to be placed on
     * the right of the characters. In horizontal writing-modes, it is a synonym
     * of under.
     *
     * MDN
     */
    val right = this ~= "right"
    val `under left` = this ~= "under left"
    val `under right` = this ~= "under right"
  }
  /**
   * The text-transform CSS property specifies how to capitalize an element's
   * text. It can be used to make text appear in all-uppercase or all-lowercase,
   * or with each word capitalized.
   *
   * MDN
   */
  object textTransform extends Style("textTransform", "text-transform") {
    /**
     * Forces the first letter of each word to be converted to
     * uppercase. Other characters are unchanged.
     *
     * MDN
     */
    val capitalize = this ~= "capitalize"
    /**
     * Forces all characters to be converted to uppercase.
     *
     * MDN
     */
    val uppercase = this ~= "uppercase"
    /**
     * Forces all characters to be converted to lowercase.
     *
     * MDN
     */
    val lowercase = this ~= "lowercase"
    /**
     * Prevents the case of all characters from being changed
     *
     * MDN
     */
    val none = this ~= "none"
  }


  /**
   * The text-shadow CSS property adds shadows to text. It accepts a comma-separated
   * list of shadows to be applied to the text and text-decorations of the element.
   *
   * Each shadow is specified as an offset from the text, along with optional
   * color and blur radius values.
   *
   * Multiple shadows are applied front-to-back, with the first-specified shadow
   * on top.
   *
   * MDN
   */
  val textShadow = new NoneOpenStyle[String]("textShadow", "text-shadow")

  object visibility extends Style("visibility", "visibility") {
    /**
     * Default value, the box is visible
     *
     * MDN
     */
    val visible = this ~= "visible"
    /**
     * The box is invisible (fully transparent, nothing is drawn), but still
     * affects layout.  Descendants of the element will be visible if they have
     * visibility:visible
     *
     * MDN
     */
    val hidden = this ~= "hidden"
    /**
     * For table rows, columns, column groups, and row groups the row(s) or
     * column(s) are hidden and the space they would have occupied is (as if
     * display: none were applied to the column/row of the table)
     *
     * MDN
     */
    val collapse = this ~= "collapse"
  }



  /**
   * The white-space CSS property is used to to describe how whitespace inside
   * the element is handled.
   *
   * MDN
   */
  object whiteSpace extends Style("whiteSpace", "white-space"){
    /**
     * Sequences of whitespace are collapsed. Newline characters in the source
     * are handled as other whitespace. Breaks lines as necessary to fill line
     * boxes.
     *
     * MDN
     */
    val normal = this ~= "normal"
    /**
     * Collapses whitespace as for normal, but suppresses line breaks (text
     * wrapping) within text.
     *
     * MDN
     */
    val nowrap = this ~= "nowrap"
    /**
     * Sequences of whitespace are preserved, lines are only broken at newline
     * characters in the source and at `<br>` elements.
     *
     * MDN
     */
    val pre = this ~= "pre"
    /**
     * Sequences of whitespace are preserved. Lines are broken at newline
     * characters, at `<br>`, and as necessary to fill line boxes.
     *
     * MDN
     */
    val `pre-wrap` = this ~= "pre-wrap"
    /**
     * Sequences of whitespace are collapsed. Lines are broken at newline
     * characters, at `<br>`, and as necessary to fill line boxes.
     *
     * MDN
     */
    val `pre-line` = this ~= "pre-line"
  }
  /**
   * The word-spacing CSS property specifies spacing behavior between tags and
   * words.
   *
   * MDN
   */
  val wordSpacing = new NormalOpenStyle[Length]("wordSpacing", "word-spacing")
  /**
   * The z-index CSS property specifies the z-order of an element and its
   * descendants. When elements overlap, z-order determines which one covers the
   * other. An element with a larger z-index generally covers an element with a
   * lower one.
   *
   * MDN
   */
  val zIndex = new AutoStyle[Int]("zIndex", "z-index")



}
