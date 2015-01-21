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
package generic

import acyclic.file


trait SvgAttrs[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT] {

  /**
   * This attribute defines the distance from the origin to the top of accent characters,
   * measured by a distance within the font coordinate system.
   * If the attribute is not specified, the effect is as if the attribute
   * were set to the value of the ascent attribute.
   *
   * Value 	<number>
   *
   * MDN
   */
  val accentHeight = "accent-height".attr

  /**
   * This attribute controls whether or not the animation is cumulative.
   * It is frequently useful for repeated animations to build upon the previous results,
   * accumulating with each iteration. This attribute said to the animation if the value is added to
   * the previous animated attribute's value on each iteration.
   *
   * Value 	none | sum
   *
   * MDN
   */
  val accumulate = "accumulate".attr

  /**
   * This attribute controls whether or not the animation is additive.
   * It is frequently useful to define animation as an offset or delta
   * to an attribute's value, rather than as absolute values. This
   * attribute said to the animation if their values are added to the
   * original animated attribute's value.
   *
   * Value 	replace | sum
   *
   * MDN
   */
  val additive = "additive".attr

  /**
   * The alignment-baseline attribute specifies how an object is aligned
   * with respect to its parent. This property specifies which baseline
   * of this element is to be aligned with the corresponding baseline of
   * the parent. For example, this allows alphabetic baselines in Roman
   * text to stay aligned across font size changes. It defaults to the
   * baseline with the same name as the computed value of the
   * alignment-baseline property. As a presentation attribute, it also
   * can be used as a property directly inside a CSS stylesheet, see css
   * alignment-baseline for further information.
   *
   * Value: 	auto | baseline | before-edge | text-before-edge | middle | central | after-edge |
   * text-after-edge | ideographic | alphabetic | hanging | mathematical | inherit
   *
   * MDN
   */
  val alignmentBaseline = "alignment-baseline".attr


  /**
   * This attribute defines the maximum unaccented depth of the font
   * within the font coordinate system. If the attribute is not specified,
   * the effect is as if the attribute were set to the vert-origin-y value
   * for the corresponding font.
   *
   * Value 	<number>
   *
   * MDN
   */
  val ascent = "ascent".attr


  /**
   * This attribute indicates the name of the attribute in the parent element
   * that is going to be changed during an animation.
   *
   * Value 	<attributeName>
   *
   * MDN
   */
  val attributeName = "attributeName".attr


  /**
   * This attribute specifies the namespace in which the target attribute
   * and its associated values are defined.
   *
   * Value 	CSS | XML | auto
   *
   * MDN
   */
  val attributeType = "attributeType".attr


  /**
   * The azimuth attribute represent the direction angle for the light
   * source on the XY plane (clockwise), in degrees from the x axis.
   * If the attribute is not specified, then the effect is as if a
   * value of 0 were specified.
   *
   * Value 	<number>
   *
   * MDN
   */
  val azimuth = "azimuth".attr


  /**
   * The baseFrequency attribute represent The base frequencies parameter
   * for the noise function of the <feturbulence> primitive. If two <number>s
   * are provided, the first number represents a base frequency in the X
   * direction and the second value represents a base frequency in the Y direction.
   * If one number is provided, then that value is used for both X and Y.
   * Negative values are forbidden.
   * If the attribute is not specified, then the effect is as if a value
   * of 0 were specified.
   *
   * Value 	<number-optional-number>
   *
   * MDN
   */
  val baseFrequency = "baseFrequency".attr


  /**
   * The baseline-shift attribute allows repositioning of the dominant-baseline
   * relative to the dominant-baseline of the parent text content element.
   * The shifted object might be a sub- or superscript.
   * As a presentation attribute, it also can be used as a property directly
   * inside a CSS stylesheet, see css baseline-shift for further information.
   *
   * Value 	auto | baseline | sup | sub | <percentage> | <length> | inherit
   *
   * MDN
   */
  val baselineShift = "baseline-shift".attr


  /**
   * This attribute defines when an animation should begin.
   * The attribute value is a semicolon separated list of values. The interpretation
   * of a list of start times is detailed in the SMIL specification in "Evaluation
   * of begin and end time lists". Each individual value can be one of the following:
   * <offset-value>, <syncbase-value>, <event-value>, <repeat-value>, <accessKey-value>,
   * <wallclock-sync-value> or the keyword indefinite.
   *
   * Value 	<begin-value-list>
   *
   * MDN
   */
  val begin = "begin".attr


  /**
   * The bias attribute shifts the range of the filter. After applying the kernelMatrix
   * of the <feconvolvematrix> element to the input image to yield a number and applied
   * the divisor attribute, the bias attribute is added to each component. This allows
   * representation of values that would otherwise be clamped to 0 or 1.
   * If bias is not specified, then the effect is as if a value of 0 were specified.
   *
   * Value 	<number>
   *
   * MDN
   */
  val bias = "bias".attr


  /**
   * This attribute specifies the interpolation mode for the animation. The default
   * mode is linear, however if the attribute does not support linear interpolation
   * (e.g. for strings), the calcMode attribute is ignored and discrete interpolation is used.
   *
   * Value 	discrete | linear | paced | spline
   *
   * MDN
   */
  val calcMode = "calcMode".attr


  /**
   * Assigns a class name or set of class names to an element. You may assign the same
   * class name or names to any number of elements. If you specify multiple class names,
   * they must be separated by whitespace characters.
   * The class name of an element has two key roles:
   * -As a style sheet selector, for use when an author wants to assign style
   * information to a set of elements.
   * -For general usage by the browser.
   * The class can be used to style SVG content using CSS.
   *
   * Value 	<list-of-class-names>
   *
   * MDN
   */
  val `class` = "class".attr


  /**
   * The clip attribute has the same parameter values as defined for the css clip property.
   * Unitless values, which indicate current user coordinates, are permitted on the coordinate
   * values on the <shape>. The value of auto defines a clipping path along the bounds of
   * the viewport created by the given element.
   * As a presentation attribute, it also can be used as a property directly inside a
   * CSS stylesheet, see css clip for further information.
   *
   * Value 	auto | <shape> | inherit
   *
   * MDN
   */
  val clip = "clip".attr


  /**
   * The clip-path attribute bind the element is applied to with a given <clippath> element
   * As a presentation attribute, it also can be used as a property directly inside a CSS stylesheet
   *
   * Value 	<FuncIRI> | none | inherit
   *
   * MDN
   */
  val clipPath = "clip-path".attr

  /**
   * The clipPathUnits attribute defines the coordinate system for the contents
   * of the <clippath> element. the clipPathUnits attribute is not specified,
   * then the effect is as if a value of userSpaceOnUse were specified.
   * Note that values defined as a percentage inside the content of the <clippath>
   * are not affected by this attribute. It means that even if you set the value of
   * maskContentUnits to objectBoundingBox, percentage values will be calculated as
   * if the value of the attribute were userSpaceOnUse.
   *
   * Value 	userSpaceOnUse | objectBoundingBox
   *
   * MDN
   */
  val clipPathUnits = "clipPathUnits".attr

  /**
   * The clip-rule attribute only applies to graphics elements that are contained within a
   * <clippath> element. The clip-rule attribute basically works as the fill-rule attribute,
   * except that it applies to <clippath> definitions.
   *
   * Value 	nonezero | evenodd | inherit
   *
   * MDN
   */
  val clipRule = "clip-rule".attr

  /**
   * The color attribute is used to provide a potential indirect value (currentColor)
   * for the fill, stroke, stop-color, flood-color and lighting-color attributes.
   * As a presentation attribute, it also can be used as a property directly inside a CSS
   * stylesheet, see css color for further information.
   *
   * Value 	<color> | inherit
   *
   * MDN
   */
  val color = "color".attr


  /**
   * The color-interpolation attribute specifies the color space for gradient interpolations,
   * color animations and alpha compositing.When a child element is blended into a background,
   * the value of the color-interpolation attribute on the child determines the type of
   * blending, not the value of the color-interpolation on the parent. For gradients which
   * make use of the xlink:href attribute to reference another gradient, the gradient uses
   * the color-interpolation attribute value from the gradient element which is directly
   * referenced by the fill or stroke attribute. When animating colors, color interpolation
   * is performed according to the value of the color-interpolation attribute on the element
   * being animated.
   * As a presentation attribute, it also can be used as a property directly inside a CSS
   * stylesheet, see css color-interpolation for further information
   *
   * Value 	auto | sRGB | linearRGB | inherit
   *
   * MDN
   */
  val colorInterpolation = "color-interpolation".attr


  /**
   * The color-interpolation-filters attribute specifies the color space for imaging operations
   * performed via filter effects. Note that color-interpolation-filters has a different
   * initial value than color-interpolation. color-interpolation-filters has an initial
   * value of linearRGB, whereas color-interpolation has an initial value of sRGB. Thus,
   * in the default case, filter effects operations occur in the linearRGB color space,
   * whereas all other color interpolations occur by default in the sRGB color space.
   * As a presentation attribute, it also can be used as a property directly inside a
   * CSS stylesheet, see css color-interpolation-filters for further information
   *
   * Value 	auto | sRGB | linearRGB | inherit
   *
   * MDN
   */
  val colorInterpolationFilters = "color-interpolation-filters".attr


  /**
   * The color-profile attribute is used to define which color profile a raster image
   * included through the <image> element should use. As a presentation attribute, it
   * also can be used as a property directly inside a CSS stylesheet, see css color-profile
   * for further information.
   *
   * Value 	auto | sRGB | <name> | <IRI> | inherit
   *
   * MDN
   */
  val colorProfile = "color-profile".attr


  /**
   * The color-rendering attribute provides a hint to the SVG user agent about how to
   * optimize its color interpolation and compositing operations. color-rendering
   * takes precedence over color-interpolation-filters. For example, assume color-rendering:
   * optimizeSpeed and color-interpolation-filters: linearRGB. In this case, the SVG user
   * agent should perform color operations in a way that optimizes performance, which might
   * mean sacrificing the color interpolation precision as specified by
   * color-interpolation-filters: linearRGB.
   * As a presentation attribute, it also can be used as a property directly inside
   * a CSS stylesheet, see css color-rendering for further information
   *
   * Value 	auto | optimizeSpeed | optimizeQuality | inherit
   *
   * MDN
   */
  val colorRendering = "color-rendering".attr


  /**
   * The contentScriptType attribute on the <svg> element specifies the default scripting
   * language for the given document fragment.
   * This attribute sets the default scripting language used to process the value strings
   * in event attributes. This language must be used for all instances of script that do not
   * specify their own scripting language. The value content-type specifies a media type,
   * per MIME Part Two: Media Types [RFC2046]. The default value is application/ecmascript
   *
   * Value 	<content-type>
   *
   * MDN
   */
  val contentScriptType = "contentScriptType".attr


  /**
   * This attribute specifies the style sheet language for the given document fragment.
   * The contentStyleType is specified on the <svg> element. By default, if it's not defined,
   * the value is text/css
   *
   * Value 	<content-type>
   *
   * MDN
   */
  val contentStyleType = "contentStyleType".attr


  /**
   * The cursor attribute specifies the mouse cursor displayed when the mouse pointer
   * is over an element.This attribute behave exactly like the css cursor property except
   * that if the browser suport the <cursor> element, it should allow to use it with the
   * <funciri> notation. As a presentation attribute, it also can be used as a property
   * directly inside a CSS stylesheet, see css cursor for further information.
   *
   * Value 	 auto | crosshair | default | pointer | move | e-resize |
   * ne-resize | nw-resize | n-resize | se-resize | sw-resize | s-resize | w-resize| text |
   * wait | help | inherit
   *
   * MDN
   */
  val cursor = "cursor".attr


  /**
   * For the <circle> and the <ellipse> element, this attribute define the x-axis coordinate
   * of the center of the element. If the attribute is not specified, the effect is as if a
   * value of "0" were specified.For the <radialgradient> element, this attribute define
   * the x-axis coordinate of the largest (i.e., outermost) circle for the radial gradient.
   * The gradient will be drawn such that the 100% gradient stop is mapped to the perimeter
   * of this largest (i.e., outermost) circle. If the attribute is not specified, the effect
   * is as if a value of 50% were specified
   *
   * Value 	<coordinate>
   *
   * MDN
   */
  val cx = "cx".attr

  /**
   * For the <circle> and the <ellipse> element, this attribute define the y-axis coordinate
   * of the center of the element. If the attribute is not specified, the effect is as if a
   * value of "0" were specified.For the <radialgradient> element, this attribute define
   * the x-axis coordinate of the largest (i.e., outermost) circle for the radial gradient.
   * The gradient will be drawn such that the 100% gradient stop is mapped to the perimeter
   * of this largest (i.e., outermost) circle. If the attribute is not specified, the effect
   * is as if a value of 50% were specified
   *
   * Value 	<coordinate>
   *
   * MDN
   */
  val cy = "cy".attr


  /**
   *
   *
   * MDN
   */
  val d = "d".attr


  /**
   *
   *
   * MDN
   */
  val diffuseConstant = "diffuseConstant".attr


  /**
   *
   *
   * MDN
   */
  val direction = "direction".attr


  /**
   *
   *
   * MDN
   */
  val display = "display".attr


  /**
   *
   *
   * MDN
   */
  val divisor = "divisor".attr


  /**
   *
   *
   * MDN
   */
  val dominantBaseline = "dominant-baseline".attr


  /**
   *
   *
   * MDN
   */
  val dur = "dur".attr


  /**
   *
   *
   * MDN
   */
  val dx = "dx".attr


  /**
   *
   *
   * MDN
   */
  val dy = "dy".attr


  /**
   *
   *
   * MDN
   */
  val edgeMode = "edgeMode".attr


  /**
   *
   *
   * MDN
   */
  val elevation = "elevation".attr


  /**
   *
   *
   * MDN
   */
  val end = "end".attr


  /**
   *
   *
   * MDN
   */
  val externalResourcesRequired = "externalResourcesRequired".attr


  /**
   *
   *
   * MDN
   */
  val fill = "fill".attr


  /**
   *
   *
   * MDN
   */
  val fillOpacity = "fill-opacity".attr


  /**
   *
   *
   * MDN
   */
  val fillRule = "fill-rule".attr


  /**
   *
   *
   * MDN
   */
  val filter = "filter".attr


  /**
   *
   *
   * MDN
   */
  val filterRes = "filterRes".attr


  /**
   *
   *
   * MDN
   */
  val filterUnits = "filterUnits".attr


  /**
   *
   *
   * MDN
   */
  val floodColor = "flood-color".attr


  /**
   *
   *
   * MDN
   */
  val floodOpacity = "flood-opacity".attr


  /**
   *
   *
   * MDN
   */
  val fontFamily = "font-family".attr


  /**
   *
   *
   * MDN
   */
  val fontSize = "font-size".attr


  /**
   *
   *
   * MDN
   */
  val fontSizeAdjust = "font-size-adjust".attr


  /**
   *
   *
   * MDN
   */
  val fontStretch = "font-stretch".attr


  /**
   *
   *
   * MDN
   */
  val fontVariant = "font-variant".attr


  /**
   *
   *
   * MDN
   */
  val fontWeight = "font-weight".attr


  /**
   *
   *
   * MDN
   */
  val from = "from".attr


  /**
   *
   *
   * MDN
   */
  val gradientTransform = "gradientTransform".attr


  /**
   *
   *
   * MDN
   */
  val gradientUnits = "gradientUnits".attr


  /**
   *
   *
   * MDN
   */
  val height = "height".attr


  /**
   *
   *
   * MDN
   */
  val imageRendering = "imageRendering".attr

  val id = "id".attr

  /**
   *
   *
   * MDN
   */
  val in = "in".attr



  /**
   *
   *
   * MDN
   */
  val in2 = "in2".attr



  /**
   *
   *
   * MDN
   */
  val k1 = "k1".attr


  /**
   *
   *
   * MDN
   */
  val k2 = "k2".attr


  /**
   *
   *
   * MDN
   */
  val k3 = "k3".attr


  /**
   *
   *
   * MDN
   */
  val k4 = "k4".attr



  /**
   *
   *
   * MDN
   */
  val kernelMatrix = "kernelMatrix".attr



  /**
   *
   *
   * MDN
   */
  val kernelUnitLength = "kernelUnitLength".attr


  /**
   *
   *
   * MDN
   */
  val kerning = "kerning".attr


  /**
   *
   *
   * MDN
   */
  val keySplines = "keySplines".attr



  /**
   *
   *
   * MDN
   */
  val keyTimes = "keyTimes".attr




  /**
   *
   *
   * MDN
   */
  val letterSpacing = "letter-spacing".attr



  /**
   *
   *
   * MDN
   */
  val lightingColor = "lighting-color".attr



  /**
   *
   *
   * MDN
   */
  val limitingConeAngle = "limitingConeAngle".attr



  /**
   *
   *
   * MDN
   */
  val local = "local".attr



  /**
   *
   *
   * MDN
   */
  val markerEnd = "marker-end".attr


  /**
   *
   *
   * MDN
   */
  val markerMid = "marker-mid".attr


  /**
   *
   *
   * MDN
   */
  val markerStart = "marker-start".attr


  /**
   *
   *
   * MDN
   */
  val markerHeight = "markerHeight".attr


  /**
   *
   *
   * MDN
   */
  val markerUnits = "markerUnits".attr


  /**
   *
   *
   * MDN
   */
  val markerWidth = "markerWidth".attr


  /**
   *
   *
   * MDN
   */
  val maskContentUnits = "maskContentUnits".attr


  /**
   *
   *
   * MDN
   */
  val maskUnits = "maskUnits".attr


  /**
   *
   *
   * MDN
   */
  val mask = "mak".attr



  /**
   *
   *
   * MDN
   */
  val max = "max".attr



  /**
   *
   *
   * MDN
   */
  val min = "min".attr


  /**
   *
   *
   * MDN
   */
  val mode = "mode".attr


  /**
   *
   *
   * MDN
   */
  val numOctaves = "numOctaves".attr


  val offset = "offset".attr

  /**
   *
   *
   * MDN
   */
  val opacity = "opacity".attr



  /**
   *
   *
   * MDN
   */
  val operator = "operator".attr


  /**
   *
   *
   * MDN
   */
  val order = "order".attr


  /**
   *
   *
   * MDN
   */
  val overflow = "overflow".attr



  /**
   *
   *
   * MDN
   */
  val paintOrder = "paint-order".attr



  /**
   *
   *
   * MDN
   */
  val pathLength = "pathLength".attr



  /**
   *
   *
   * MDN
   */
  val patternContentUnits = "patternContentUnits".attr


  /**
   *
   *
   * MDN
   */
  val patternTransform = "patternTransform".attr



  /**
   *
   *
   * MDN
   */
  val patternUnits = "patternUnits".attr



  /**
   *
   *
   * MDN
   */
  val pointerEvents = "pointer-events".attr


  /**
   *
   *
   * MDN
   */
  val points = "points".attr


  /**
   *
   *
   * MDN
   */
  val pointsAtX = "pointsAtX".attr


  /**
   *
   *
   * MDN
   */
  val pointsAtY = "pointsAtY".attr


  /**
   *
   *
   * MDN
   */
  val pointsAtZ = "pointsAtZ".attr


  /**
   *
   *
   * MDN
   */
  val preserveAlpha = "preserveAlpha".attr



  /**
   *
   *
   * MDN
   */
  val preserveAspectRatio = "preserveAspectRatio".attr



  /**
   *
   *
   * MDN
   */
  val primitiveUnits = "primitiveUnits".attr


  /**
   *
   *
   * MDN
   */
  val r = "r".attr



  /**
   *
   *
   * MDN
   */
  val radius = "radius".attr


  /**
   *
   *
   * MDN
   */
  val repeatCount = "repeatCount".attr


  /**
   *
   *
   * MDN
   */
  val repeatDur = "repeatDur".attr



  /**
   *
   *
   * MDN
   */
  val requiredFeatures = "requiredFeatures".attr



  /**
   *
   *
   * MDN
   */
  val restart = "restart".attr



  /**
   *
   *
   * MDN
   */
  val result = "result".attr



  /**
   *
   *
   * MDN
   */
  val rx = "rx".attr



  /**
   *
   *
   * MDN
   */
  val ry = "ry".attr



  /**
   *
   *
   * MDN
   */
  val scale = "scale".attr



  /**
   *
   *
   * MDN
   */
  val seed = "seed".attr



  /**
   *
   *
   * MDN
   */
  val shapeRendering = "shape-rendering".attr



  /**
   *
   *
   * MDN
   */
  val specularConstant = "specularConstant".attr



  /**
   *
   *
   * MDN
   */
  val specularExponent = "specularExponent".attr



  /**
   *
   *
   * MDN
   */
  val spreadMethod = "spreadMethod".attr



  /**
   *
   *
   * MDN
   */
  val stdDeviation = "stdDeviation".attr



  /**
   *
   *
   * MDN
   */
  val stitchTiles = "stitchTiles".attr



  /**
   *
   *
   * MDN
   */
  val stopColor = "stop-color".attr



  /**
   *
   *
   * MDN
   */
  val stopOpacity = "stop-opacity".attr



  /**
   *
   *
   * MDN
   */
  val stroke = "stroke".attr


  /**
   *
   *
   * MDN
   */
  val strokeDasharray= "stroke-dasharray".attr


  /**
   *
   *
   * MDN
   */
  val strokeDashoffset = "stroke-dashoffset".attr


  /**
   *
   *
   * MDN
   */
  val strokeLinecap = "stroke-linecap".attr


  /**
   *
   *
   * MDN
   */
  val strokeLinejoin = "stroke-linejoin".attr


  /**
   *
   *
   * MDN
   */
  val strokeMiterlimit = "stroke-miterlimit".attr


  /**
   *
   *
   * MDN
   */
  val strokeOpacity = "stroke-opacity".attr


  /**
   *
   *
   * MDN
   */
  val strokeWidth = "stroke-width".attr


  /**
   *
   *
   * MDN
   */
  val style = "style".attr



  /**
   *
   *
   * MDN
   */
  val surfaceScale = "surfaceScale".attr


  /**
   *
   *
   * MDN
   */
  val targetX = "targetX".attr


  /**
   *
   *
   * MDN
   */
  val targetY = "targetY".attr


  /**
   *
   *
   * MDN
   */
  val textAnchor = "text-anchor".attr


  /**
   *
   *
   * MDN
   */
  val textDecoration = "text-decoration".attr


  /**
   *
   *
   * MDN
   */
  val textRendering = "text-rendering".attr


  /**
   *
   *
   * MDN
   */
  val to = "to".attr


  /*
   *
   *
   * MDN
   */
  val transform = "transform".attr


  /*
   *
   *
   * MDN
   */
  val `type`= "type".attr


  /*
   *
   *
   * MDN
   */
  val values = "values".attr


  /**
   *
   *
   * MDN
   */
  val viewBox = "viewBox".attr


  /*
   *
   *
   * MDN
   */
  val visibility = "visibility".attr


  /*
   *
   *
   * MDN
   */
  val width = "width".attr


  /*
   *
   *
   * MDN
   */
  val wordSpacing = "word-spacing".attr

  /*
   *
   *
   * MDN
   */
  val writingMode = "writing-mode".attr


  /*
   *
   *
   * MDN
   */
  val x = "x".attr


  /*
   *
   *
   * MDN
   */
  val x1 = "x1".attr


  /*
   *
   *
   * MDN
   */
  val x2 = "x2".attr


  /*
   *
   *
   * MDN
   */
  val xChannelSelector = "xChannelSelector".attr


  /*
   *
   *
   * MDN
   */
  val xLinkHref= "xlink:href".attr


  /*
   *
   *
   * MDN
   */
  val xLink = "xlink:role".attr


  /*
   *
   *
   * MDN
   */
  val xLinkTitle = "xlink:title".attr


  /*
   *
   *
   * MDN
   */
  val xmlSpace = "xml:space".attr


  /**
   *
   *
   * MDN
   */
  val xmlns = "xmlns".attr


  /*
   *
   *
   * MDN
   */
  val y = "y".attr


  /*
   *
   *
   * MDN
   */
  val y1 = "y1".attr


  /*
   *
   *
   * MDN
   */
  val y2 = "y2".attr


  /*
   *
   *
   * MDN
   */
  val yChannelSelector = "yChannelSelector".attr


  /*
   *
   *
   * MDN
   */
  val z = "z".attr
}
