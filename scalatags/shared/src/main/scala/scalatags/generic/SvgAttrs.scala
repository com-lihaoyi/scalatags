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
  lazy val accentHeight = "accent-height".attr

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
  lazy val accumulate = "accumulate".attr

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
  lazy val additive = "additive".attr

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
  lazy val alignmentBaseline = "alignment-baseline".attr


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
  lazy val ascent = "ascent".attr


  /**
   * This attribute indicates the name of the attribute in the parent element
   * that is going to be changed during an animation.
   *
   * Value 	<attributeName>
   *
   * MDN
   */
  lazy val attributeName = "attributeName".attr


  /**
   * This attribute specifies the namespace in which the target attribute
   * and its associated values are defined.
   *
   * Value 	CSS | XML | auto
   *
   * MDN
   */
  lazy val attributeType = "attributeType".attr


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
  lazy val azimuth = "azimuth".attr


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
  lazy val baseFrequency = "baseFrequency".attr


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
  lazy val baselineShift = "baseline-shift".attr


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
  lazy val begin = "begin".attr


  /**
   * The bias attribute shifts the range of the filter. After applying the kernelMatrix
   * of the <feConvolveMatrix> element to the input image to yield a number and applied
   * the divisor attribute, the bias attribute is added to each component. This allows
   * representation of values that would otherwise be clamped to 0 or 1.
   * If bias is not specified, then the effect is as if a value of 0 were specified.
   *
   * Value 	<number>
   *
   * MDN
   */
  lazy val bias = "bias".attr


  /**
   * This attribute specifies the interpolation mode for the animation. The default
   * mode is linear, however if the attribute does not support linear interpolation
   * (e.g. for strings), the calcMode attribute is ignored and discrete interpolation is used.
   *
   * Value 	discrete | linear | paced | spline
   *
   * MDN
   */
  lazy val calcMode = "calcMode".attr


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
  lazy val `class` = "class".attr


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
  lazy val clip = "clip".attr


  /**
   * The clip-path attribute bind the element is applied to with a given <clipPath> element
   * As a presentation attribute, it also can be used as a property directly inside a CSS stylesheet
   *
   * Value 	<FuncIRI> | none | inherit
   *
   * MDN
   */
  lazy val clipPath = "clip-path".attr

  /**
   * The clipPathUnits attribute defines the coordinate system for the contents
   * of the <clipPath> element. the clipPathUnits attribute is not specified,
   * then the effect is as if a value of userSpaceOnUse were specified.
   * Note that values defined as a percentage inside the content of the <clipPath>
   * are not affected by this attribute. It means that even if you set the value of
   * maskContentUnits to objectBoundingBox, percentage values will be calculated as
   * if the value of the attribute were userSpaceOnUse.
   *
   * Value 	userSpaceOnUse | objectBoundingBox
   *
   * MDN
   */
  lazy val clipPathUnits = "clipPathUnits".attr

  /**
   * The clip-rule attribute only applies to graphics elements that are contained within a
   * <clipPath> element. The clip-rule attribute basically works as the fill-rule attribute,
   * except that it applies to <clipPath> definitions.
   *
   * Value 	nonezero | evenodd | inherit
   *
   * MDN
   */
  lazy val clipRule = "clip-rule".attr

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
  lazy val color = "color".attr


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
  lazy val colorInterpolation = "color-interpolation".attr


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
  lazy val colorInterpolationFilters = "color-interpolation-filters".attr


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
  lazy val colorProfile = "color-profile".attr


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
  lazy val colorRendering = "color-rendering".attr


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
  lazy val contentScriptType = "contentScriptType".attr


  /**
   * This attribute specifies the style sheet language for the given document fragment.
   * The contentStyleType is specified on the <svg> element. By default, if it's not defined,
   * the value is text/css
   *
   * Value 	<content-type>
   *
   * MDN
   */
  lazy val contentStyleType = "contentStyleType".attr


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
  lazy val cursor = "cursor".attr


  /**
   * For the <circle> and the <ellipse> element, this attribute define the x-axis coordinate
   * of the center of the element. If the attribute is not specified, the effect is as if a
   * value of "0" were specified.For the <radialGradient> element, this attribute define
   * the x-axis coordinate of the largest (i.e., outermost) circle for the radial gradient.
   * The gradient will be drawn such that the 100% gradient stop is mapped to the perimeter
   * of this largest (i.e., outermost) circle. If the attribute is not specified, the effect
   * is as if a value of 50% were specified
   *
   * Value 	<coordinate>
   *
   * MDN
   */
  lazy val cx = "cx".attr

  /**
   * For the <circle> and the <ellipse> element, this attribute define the y-axis coordinate
   * of the center of the element. If the attribute is not specified, the effect is as if a
   * value of "0" were specified.For the <radialGradient> element, this attribute define
   * the x-axis coordinate of the largest (i.e., outermost) circle for the radial gradient.
   * The gradient will be drawn such that the 100% gradient stop is mapped to the perimeter
   * of this largest (i.e., outermost) circle. If the attribute is not specified, the effect
   * is as if a value of 50% were specified
   *
   * Value 	<coordinate>
   *
   * MDN
   */
  lazy val cy = "cy".attr


  /**
   *
   *
   * MDN
   */
  lazy val d = "d".attr


  /**
   *
   *
   * MDN
   */
  lazy val diffuseConstant = "diffuseConstant".attr


  /**
   *
   *
   * MDN
   */
  lazy val direction = "direction".attr


  /**
   *
   *
   * MDN
   */
  lazy val display = "display".attr


  /**
   *
   *
   * MDN
   */
  lazy val divisor = "divisor".attr


  /**
   *
   *
   * MDN
   */
  lazy val dominantBaseline = "dominant-baseline".attr


  /**
   *
   *
   * MDN
   */
  lazy val dur = "dur".attr


  /**
   *
   *
   * MDN
   */
  lazy val dx = "dx".attr


  /**
   *
   *
   * MDN
   */
  lazy val dy = "dy".attr


  /**
   *
   *
   * MDN
   */
  lazy val edgeMode = "edgeMode".attr


  /**
   *
   *
   * MDN
   */
  lazy val elevation = "elevation".attr


  /**
   *
   *
   * MDN
   */
  lazy val end = "end".attr


  /**
   *
   *
   * MDN
   */
  lazy val externalResourcesRequired = "externalResourcesRequired".attr


  /**
   *
   *
   * MDN
   */
  lazy val fill = "fill".attr


  /**
   *
   *
   * MDN
   */
  lazy val fillOpacity = "fill-opacity".attr


  /**
   *
   *
   * MDN
   */
  lazy val fillRule = "fill-rule".attr


  /**
   *
   *
   * MDN
   */
  lazy val filter = "filter".attr


  /**
   *
   *
   * MDN
   */
  lazy val filterRes = "filterRes".attr


  /**
   *
   *
   * MDN
   */
  lazy val filterUnits = "filterUnits".attr


  /**
   *
   *
   * MDN
   */
  lazy val floodColor = "flood-color".attr


  /**
   *
   *
   * MDN
   */
  lazy val floodOpacity = "flood-opacity".attr


  /**
   *
   *
   * MDN
   */
  lazy val fontFamily = "font-family".attr


  /**
   *
   *
   * MDN
   */
  lazy val fontSize = "font-size".attr


  /**
   *
   *
   * MDN
   */
  lazy val fontSizeAdjust = "font-size-adjust".attr


  /**
   *
   *
   * MDN
   */
  lazy val fontStretch = "font-stretch".attr


  /**
   *
   *
   * MDN
   */
  lazy val fontVariant = "font-variant".attr


  /**
   *
   *
   * MDN
   */
  lazy val fontWeight = "font-weight".attr


  /**
   *
   *
   * MDN
   */
  lazy val from = "from".attr


  /**
   *
   *
   * MDN
   */
  lazy val gradientTransform = "gradientTransform".attr


  /**
   *
   *
   * MDN
   */
  lazy val gradientUnits = "gradientUnits".attr


  /**
   *
   *
   * MDN
   */
  lazy val height = "height".attr


  /**
   *
   *
   * MDN
   */
  lazy val imageRendering = "imageRendering".attr

  lazy val id = "id".attr

  /**
   *
   *
   * MDN
   */
  lazy val in = "in".attr



  /**
   *
   *
   * MDN
   */
  lazy val in2 = "in2".attr



  /**
   *
   *
   * MDN
   */
  lazy val k1 = "k1".attr


  /**
   *
   *
   * MDN
   */
  lazy val k2 = "k2".attr


  /**
   *
   *
   * MDN
   */
  lazy val k3 = "k3".attr


  /**
   *
   *
   * MDN
   */
  lazy val k4 = "k4".attr



  /**
   *
   *
   * MDN
   */
  lazy val kernelMatrix = "kernelMatrix".attr



  /**
   *
   *
   * MDN
   */
  lazy val kernelUnitLength = "kernelUnitLength".attr


  /**
   *
   *
   * MDN
   */
  lazy val kerning = "kerning".attr


  /**
   *
   *
   * MDN
   */
  lazy val keySplines = "keySplines".attr



  /**
   *
   *
   * MDN
   */
  lazy val keyTimes = "keyTimes".attr




  /**
   *
   *
   * MDN
   */
  lazy val letterSpacing = "letter-spacing".attr



  /**
   *
   *
   * MDN
   */
  lazy val lightingColor = "lighting-color".attr



  /**
   *
   *
   * MDN
   */
  lazy val limitingConeAngle = "limitingConeAngle".attr



  /**
   *
   *
   * MDN
   */
  lazy val local = "local".attr



  /**
   *
   *
   * MDN
   */
  lazy val markerEnd = "marker-end".attr


  /**
   *
   *
   * MDN
   */
  lazy val markerMid = "marker-mid".attr


  /**
   *
   *
   * MDN
   */
  lazy val markerStart = "marker-start".attr


  /**
   *
   *
   * MDN
   */
  lazy val markerHeight = "markerHeight".attr


  /**
   *
   *
   * MDN
   */
  lazy val markerUnits = "markerUnits".attr


  /**
   *
   *
   * MDN
   */
  lazy val markerWidth = "markerWidth".attr


  /**
   *
   *
   * MDN
   */
  lazy val maskContentUnits = "maskContentUnits".attr


  /**
   *
   *
   * MDN
   */
  lazy val maskUnits = "maskUnits".attr


  /**
   *
   *
   * MDN
   */
  lazy val mask = "mak".attr



  /**
   *
   *
   * MDN
   */
  lazy val max = "max".attr



  /**
   *
   *
   * MDN
   */
  lazy val min = "min".attr


  /**
   *
   *
   * MDN
   */
  lazy val mode = "mode".attr


  /**
   *
   *
   * MDN
   */
  lazy val numOctaves = "numOctaves".attr


  lazy val offset = "offset".attr

  /**
   *
   *
   * MDN
   */
  lazy val opacity = "opacity".attr



  /**
   *
   *
   * MDN
   */
  lazy val operator = "operator".attr


  /**
   *
   *
   * MDN
   */
  lazy val order = "order".attr


  /**
   *
   *
   * MDN
   */
  lazy val overflow = "overflow".attr



  /**
   *
   *
   * MDN
   */
  lazy val paintOrder = "paint-order".attr



  /**
   *
   *
   * MDN
   */
  lazy val pathLength = "pathLength".attr



  /**
   *
   *
   * MDN
   */
  lazy val patternContentUnits = "patternContentUnits".attr


  /**
   *
   *
   * MDN
   */
  lazy val patternTransform = "patternTransform".attr



  /**
   *
   *
   * MDN
   */
  lazy val patternUnits = "patternUnits".attr



  /**
   *
   *
   * MDN
   */
  lazy val pointerEvents = "pointer-events".attr


  /**
   *
   *
   * MDN
   */
  lazy val points = "points".attr


  /**
   *
   *
   * MDN
   */
  lazy val pointsAtX = "pointsAtX".attr


  /**
   *
   *
   * MDN
   */
  lazy val pointsAtY = "pointsAtY".attr


  /**
   *
   *
   * MDN
   */
  lazy val pointsAtZ = "pointsAtZ".attr


  /**
   *
   *
   * MDN
   */
  lazy val preserveAlpha = "preserveAlpha".attr



  /**
   *
   *
   * MDN
   */
  lazy val preserveAspectRatio = "preserveAspectRatio".attr



  /**
   *
   *
   * MDN
   */
  lazy val primitiveUnits = "primitiveUnits".attr


  /**
   *
   *
   * MDN
   */
  lazy val r = "r".attr



  /**
   *
   *
   * MDN
   */
  lazy val radius = "radius".attr


  /**
   *
   *
   * MDN
   */
  lazy val repeatCount = "repeatCount".attr


  /**
   *
   *
   * MDN
   */
  lazy val repeatDur = "repeatDur".attr



  /**
   *
   *
   * MDN
   */
  lazy val requiredFeatures = "requiredFeatures".attr



  /**
   *
   *
   * MDN
   */
  lazy val restart = "restart".attr



  /**
   *
   *
   * MDN
   */
  lazy val result = "result".attr



  /**
   *
   *
   * MDN
   */
  lazy val rx = "rx".attr



  /**
   *
   *
   * MDN
   */
  lazy val ry = "ry".attr



  /**
   *
   *
   * MDN
   */
  lazy val scale = "scale".attr



  /**
   *
   *
   * MDN
   */
  lazy val seed = "seed".attr



  /**
   *
   *
   * MDN
   */
  lazy val shapeRendering = "shape-rendering".attr



  /**
   *
   *
   * MDN
   */
  lazy val specularConstant = "specularConstant".attr



  /**
   *
   *
   * MDN
   */
  lazy val specularExponent = "specularExponent".attr



  /**
   *
   *
   * MDN
   */
  lazy val spreadMethod = "spreadMethod".attr



  /**
   *
   *
   * MDN
   */
  lazy val stdDeviation = "stdDeviation".attr



  /**
   *
   *
   * MDN
   */
  lazy val stitchTiles = "stitchTiles".attr



  /**
   *
   *
   * MDN
   */
  lazy val stopColor = "stop-color".attr



  /**
   *
   *
   * MDN
   */
  lazy val stopOpacity = "stop-opacity".attr



  /**
   *
   *
   * MDN
   */
  lazy val stroke = "stroke".attr


  /**
   *
   *
   * MDN
   */
  lazy val strokeDasharray= "stroke-dasharray".attr


  /**
   *
   *
   * MDN
   */
  lazy val strokeDashoffset = "stroke-dashoffset".attr


  /**
   *
   *
   * MDN
   */
  lazy val strokeLinecap = "stroke-linecap".attr


  /**
   *
   *
   * MDN
   */
  lazy val strokeLinejoin = "stroke-linejoin".attr


  /**
   *
   *
   * MDN
   */
  lazy val strokeMiterlimit = "stroke-miterlimit".attr


  /**
   *
   *
   * MDN
   */
  lazy val strokeOpacity = "stroke-opacity".attr


  /**
   *
   *
   * MDN
   */
  lazy val strokeWidth = "stroke-width".attr


  /**
   *
   *
   * MDN
   */
  lazy val style = "style".attr



  /**
   *
   *
   * MDN
   */
  lazy val surfaceScale = "surfaceScale".attr


  /**
   *
   *
   * MDN
   */
  lazy val targetX = "targetX".attr


  /**
   *
   *
   * MDN
   */
  lazy val targetY = "targetY".attr


  /**
   *
   *
   * MDN
   */
  lazy val textAnchor = "text-anchor".attr


  /**
   *
   *
   * MDN
   */
  lazy val textDecoration = "text-decoration".attr


  /**
   *
   *
   * MDN
   */
  lazy val textRendering = "text-rendering".attr


  /**
   *
   *
   * MDN
   */
  lazy val to = "to".attr


  /*
   *
   *
   * MDN
   */
  lazy val transform = "transform".attr


  /*
   *
   *
   * MDN
   */
  lazy val `type`= "type".attr


  /*
   *
   *
   * MDN
   */
  lazy val values = "values".attr


  /**
   *
   *
   * MDN
   */
  lazy val viewBox = "viewBox".attr


  /*
   *
   *
   * MDN
   */
  lazy val visibility = "visibility".attr


  /*
   *
   *
   * MDN
   */
  lazy val width = "width".attr


  /*
   *
   *
   * MDN
   */
  lazy val wordSpacing = "word-spacing".attr

  /*
   *
   *
   * MDN
   */
  lazy val writingMode = "writing-mode".attr


  /*
   *
   *
   * MDN
   */
  lazy val x = "x".attr


  /*
   *
   *
   * MDN
   */
  lazy val x1 = "x1".attr


  /*
   *
   *
   * MDN
   */
  lazy val x2 = "x2".attr


  /*
   *
   *
   * MDN
   */
  lazy val xChannelSelector = "xChannelSelector".attr


  /*
   *
   *
   * MDN
   */
  lazy val xLinkHref= "xlink:href".nsAttr(Namespace.svgXlinkNamespaceConfig)


  /*
   *
   *
   * MDN
   */
  lazy val xLink = "xlink:role".attr


  /*
   *
   *
   * MDN
   */
  lazy val xLinkTitle = "xlink:title".attr


  /*
   *
   *
   * MDN
   */
  lazy val xmlSpace = "xml:space".attr


  /**
   *
   *
   * MDN
   */
  lazy val xmlns = "xmlns".attr


  /**
   *
   *
   * MDN
   */
  lazy val xmlnsXlink = "xmlns:xlink".attr


  /*
   *
   *
   * MDN
   */
  lazy val y = "y".attr


  /*
   *
   *
   * MDN
   */
  lazy val y1 = "y1".attr


  /*
   *
   *
   * MDN
   */
  lazy val y2 = "y2".attr


  /*
   *
   *
   * MDN
   */
  lazy val yChannelSelector = "yChannelSelector".attr


  /*
   *
   *
   * MDN
   */
  lazy val z = "z".attr
}
