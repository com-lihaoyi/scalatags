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

private[scalatags] class CurrentColor(jsName: String, cssName: String) extends TypedStyle[Color](jsName, cssName){
  val currentColor = this -> "currentColor"
}

private[scalatags] trait Baseline{ self: TypedStyle[String] =>
  val auto = this ~= "auto"

  val `before-edge` = this ~= "before-edge"
  val `text-before-edge` = this ~= "text-before-edge"
  val middle = this ~= "middle"
  val central = this ~= "central"
  val `after-edge` = this ~= "after-edge"
  val `text-after-edge` = this ~= "text-after-edge"
  val ideographic = this ~= "ideographic"
  val alphabetic = this ~= "alphabetic"
  val hanging = this ~= "hanging"
  val mathematical = this ~= "mathematical"
}

private[scalatags] class ClipFillRule(jsName: String, cssName: String) extends Style(jsName, cssName){
  /**
   * This value determines the "insideness" of a point in the shape by drawing
   * a ray from that point to infinity in any direction and then examining the
   * places where a segment of the shape crosses the ray. Starting with a
   * count of zero, add one each time a path segment crosses the ray from left
   * to right and subtract one each time a path segment crosses the ray from
   * right to left. After counting the crossings, if the result is zero then
   * the point is outside the path. Otherwise, it is inside.
   *
   * MDN
   */
  val nonzero = this -> "nonzero"
  /**
   * This value determines the "insideness" of a point in the shape by drawing
   * a ray from that point to infinity in any direction and counting the number
   * of path segments from the given shape that the ray crosses. If this number
   * is odd, the point is inside; if even, the point is outside.
   *
   * MDN
   */
  val evenodd = this -> "evenodd"
}

/**
 * Contains CSS styles which are only used for SVG. These are not imported by
 * default to avoid namespace pollution.
 */
object SvgStyles{


  /**
   * This property specifies which baseline of this element is to be aligned
   * with the corresponding baseline of the parent. For example, this allows
   * alphabetic baselines in Roman text to stay aligned across font size changes.
   * It defaults to the baseline with the same name as the computed value of the
   * alignment-baseline property.
   *
   * MDN
   */
  object alignmentBaseline
    extends TypedStyle[String]("alignmentBaseline", "alignment-baseline")
    with Baseline{
    val baseline = this -> "baseline"
  }

  /**
   * When two line segments meet at a sharp angle and miter joins have been
   * specified for stroke-linejoin, it is possible for the miter to extend far
   * beyond the thickness of the line stroking the path. The stroke-miterlimit
   * imposes a limit on the ratio of the miter Length to the stroke-width. When
   * the limit is exceeded, the join is converted from a miter to a bevel.
   *
   * MDN
   */
  val strokeMiterlimit = new TypedStyle[Number]("strokeMiterlimit", "stroke-miterlimit")

  /**
   * The stop-opacity attribute defines the opacity of a given gradient stop.
   *
   * MDN
   */
  val stopOpacity = new TypedStyle[Number]("stopOpacity", "stop-opacity")
  /**
   * The baseline-shift attribute allows repositioning of the dominant-baseline
   * relative to the dominant-baseline of the parent text content element. The
   * shifted object might be a sub- or superscript.
   *
   * MDN
   */
  object baselineShift extends TypedStyle[Length]("baselineShift", "baseline-shift") {
    val auto = this ~= "auto"
    val baseline = this ~= "baseline"
    val sup = this ~= "sup"
    val sub = this ~= "sub"
  }

  /**
   * The text-anchor attribute is used to align (start-, middle- or end-alignment)
   * a string of text relative to a given point.
   *
   * MDN
   */
  object textAnchor extends TypedStyle[String]("textAnchor", "text-anchor"){
    /**
     * The rendered characters are aligned such that the start of the text string
     * is at the initial current text position. For Latin in its usual orientation
     * this is equivalent to left alignment. For scripts that are inherently right
     * to left such as Hebrew and Arabic, this is equivalent to right alignment.
     * For Asian text with a vertical primary text direction, this is comparable
     * to top alignment.
     *
     * MDN
     */
    val start = this ~= "start"
    /**
     * The rendered characters are aligned such that the middle of the text
     * string is at the current text position. (For text on a path, conceptually
     * the text string is first laid out in a straight line. The midpoint between
     * the start of the text string and the end of the text string is determined.
     * Then, the text string is mapped onto the path with this midpoint placed at
     * the current text position.)
     *
     * MDN
     */
    val middle = this ~= "middle"
    /**
     * The rendered characters are aligned such that the end of the text string
     * is at the initial current text position.For Latin in its usual orientation
     * this is equivalent to right alignment. For scripts that are inherently
     * right to left such as Hebrew and Arabic, this is equivalent to left
     * alignment.
     *
     * MDN
     */
    val end = this ~= "end"
  }

  /**
   * the stroke-dasharray attribute controls the pattern of dashes and gaps used
   * to stroke paths.
   *
   * MDN
   */
  val strokeDasharray = new TypedStyle[String]("strokeDasharray", "stroke-dasharray")

  /**
   * the stroke-width attribute specifies the width of the outline on the current
   * object. Its default value is 1. If a `<percentage>` is used, the value
   * represents a percentage of the current viewport. If a value of 0 is used
   * the outline will never be drawn.
   *
   * MDN
   */
  val strokeWidth = new TypedStyle[Length]("strokeWidth", "stroke-width")


  /**
   * The fill-rule attribute indicates the algorithm which is to be used to
   * determine what side of a path is inside the shape. For a simple,
   * non-intersecting path, it is intuitively clear what region lies "inside";
   * however, for a more complex path, such as a path that intersects itself or
   * where one subpath encloses another, the interpretation of "inside" is not
   * so obvious.
   *
   * MDN
   */
  val fillRule = new ClipFillRule("fillRule", "fill-rule")

  /**
   * The marker-end defines the arrowhead or polymarker that will be drawn at
   * the final vertex of the given `<path>` element or basic shape. Note that for
   * a `<path>` element which ends with a closed sub-path, the last vertex is the
   * same as the initial vertex on the given sub-path. In this case, if
   * marker-end does not equal none, then it is possible that two markers will
   * be rendered on the given vertex. One way to prevent this is to set
   * marker-end to none. (Note that the same comment applies to `<polygon>`
   * elements.)
   *
   * MDN
   */
  val markerEnd = new TypedStyle[String]("markerEnd", "marker-end")

  /**
   * The dominant-baseline attribute is used to determine or re-determine a 
   * scaled-baseline-table. A scaled-baseline-table is a compound value with 
   * three components: a baseline-identifier for the dominant-baseline, a 
   * baseline-table and a baseline-table font-size. Some values of the property 
   * re-determine all three values; other only re-establish the baseline-table 
   * font-size. When the initial value, auto, would give an undesired result, 
   * this property can be used to explicitly set the desire scaled-baseline-table.
   *
   * MDN
   */
  object dominantBaseline
    extends TypedStyle[String]("dominantBaseline", "dominant-baseline")
    with Baseline{
      val `use-script` = this -> "user-script"
      val `no-change` = this -> "no-change"
      val `reset-size` = this -> "reset-size"
  }

  /**
   * The fill attribute has two meanings based on the context it's used.
   *
   * By default, when animation elements end their effects are no longer applied
   * to the presentation value for the target attributes. The fill attribute can
   * be used to maintain the value of an animation after the active duration of
   * an animation element ends.
   *
   * For shapes and text, the fill attribute is a presentation attribute that
   * define the color of the interior of the given graphical element. What is
   * called the "interior" depends on the shape itself and the value of the
   * fill-rule attribute. As a presentation attribute, it also can be used as a
   * property directly inside a CSS stylesheet
   *
   * MDN
   */
  object fill extends TypedStyle[String]("fill", "fill"){
    val remove = this ~= "remove"
    val freeze = this ~= "freeze"
  }

  /**
   * the stroke-dashoffset attribute specifies the distance into the dash
   * pattern to start the dash.
   *
   * MDN
   */
  val strokeDashoffset = new TypedStyle[Length]("strokeDashoffset", "stroke-dashoffset")

  /**
   * The stroke-linejoin attribute specifies the shape to be used at the corners
   * of paths or basic shapes when they are stroked.
   *
   * MDN
   */
  object strokeLinejoin extends Style("strokeLinejoin", "stroke-linejoin"){
    val miter = this -> "miter"
    val round = this -> "round"
    val bevel = this -> "bevel"
  }

  /**
   * The clipping path restricts the region to which paint can be applied.
   * Conceptually, any parts of the drawing that lie outside of the region
   * bounded by the currently active clipping path are not drawn.
   *
   * MDN
   */
  val clipPath = new TypedStyle[String]("clipPath", "clip-path")


  /**
   * The kerning attribute indicates whether the browser should adjust inter-glyph
   * spacing based on kerning tables that are included in the relevant font (i.e.,
   * enable auto-kerning) or instead disable auto-kerning and instead set
   * inter-character spacing to a specific Length (typically, zero).
   *
   * MDN
   */
  val kerning = new TypedStyle[Length]("kerning", "kerning")

  /**
   * the stroke-opacity attribute specifies the opacity of the outline on the
   * current object. Its default value is 1.
   *
   * MDN
   */
  val strokeOpacity = new TypedStyle[Double]("strokeOpacity", "stroke-opacity")

  /**
   * The marker-start attribute defines the arrowhead or polymarker that will be
   * drawn at the first vertex of the given `<path>` element or basic shape.
   *
   * MDN
   */
  val markerStart = new TypedStyle[String]("markerStart", "marker-start")

  /**
   * The clip-rule attribute only applies to graphics elements that are contained
   * within a `<clippath>` element. The clip-rule attribute basically works as the
   * fill-rule attribute, except that it applies to `<clippath>` definitions.
   *
   * MDN
   */
  val clipRule = new ClipFillRule("clipRule", "clip-rule")

  /**
   * The stroke-linecap attribute specifies the shape to be used at the end of
   * open subpaths when they are stroked.
   *
   * MDN
   */
  object strokeLinecap extends Style("strokeLinecap", "stroke-linecap"){
    val butt = this -> "butt"
    val round = this -> "round"
    val square = this -> "square"
  }

  /**
   * This attribute specifies the opacity of the color or the content the current
   * object is filled with.
   *
   * MDN
   */
  val fillOpacity = new TypedStyle[Double]("fillOpacity", "fill-opacity")

  /**
   * The marker element defines the graphics that is to be used for drawing
   * arrowheads or polymarkers on a given `<path>`, `<line>`, `<polyline>` or
   * `<polygon>` element.
   *
   * MDN
   */
  val marker = new TypedStyle[String]("marker", "marker")

  /**
   * The marker-mid defines the arrowhead or polymarker that shall be drawn at
   * every vertex other than the first and last vertex of the given `<path>`
   *   element or basic shape.
   *
   * MDN
   */
  val markerMid = new NoneOpenStyle[String]("markerMid", "marker-mid")


  /**
   * the stroke attribute is a presentation attribute that define the color of
   * the outline of the given graphical element. The default value for the stroke
   * attribute is none which mean that the outline is never drawn.
   *
   * MDN
   */
  val stroke = new TypedStyle[String]("stroke", "stroke")

  /**
   * The flood-color attribute indicates what color to use to flood the current
   * filter primitive subregion defined through the `<feflood>` element. The
   * keyword currentColor and ICC colors can be specified in the same manner as
   * within a `<paint>` specification for the fill and stroke attributes.
   *
   * MDN
   */
  val floodColor = new CurrentColor("floodColor", "flood-color")

  /**
   * The stop-color attribute indicates what color to use at that gradient stop.
   * The keyword currentColor and ICC colors can be specified in the same manner
   * as within a `<paint>` specification for the fill and stroke attributes.
   *
   * MDN
   */
  val stopColor = new CurrentColor("stopColor", "stop-color")

  /**
   * The color-interpolation-filters attribute specifies the color space for
   * imaging operations performed via filter effects.
   *
   * MDN
   */
  object colorInterpolationFilters extends Style("colorInterpolationFilters", "color-interpolation-filters"){
    /**
     * Indicates that the user agent can choose either the sRGB or linearRGB
     * spaces for color interpolation. This option indicates that the author
     * doesn't require that color interpolation occur in a particular color space.
     *
     * MDN
     */
    val auto = this -> "auto"
    /**
     * Indicates that color interpolation should occur in the sRGB color space.
     *
     * MDN
     */
    val sRGB = this -> "sRGB"
    /**
     * Indicates that color interpolation should occur in the linearized RGB
     * color space as described in the sRGB specification.
     *
     * MDN
     */
    val linearRGB = this -> "linearRGB"
  }

  /**
   * The lighting-color attribute defines the color of the light source for
   * filter primitives elements `<fediffuselighting>` and `<fespecularlighting>`.
   *
   * MDN
   */
  val lightingColor = new CurrentColor("lightingColor", "lighting-color")


  /**
   * The flood-opacity attribute indicates the opacity value to use across the
   * current filter primitive subregion defined through the `<feflood>` element.
   *
   * MDN
   */
  val floodOpacity = new TypedStyle[Double]("floodOpacity", "flood-opacity")
}
