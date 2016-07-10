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
  lazy val accentHeight = attr("accent-height")

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
  lazy val accumulate = attr("accumulate")

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
  lazy val additive = attr("additive")

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
  lazy val alignmentBaseline = attr("alignment-baseline")


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
  lazy val ascent = attr("ascent")


  /**
   * This attribute indicates the name of the attribute in the parent element
   * that is going to be changed during an animation.
   *
   * Value 	<attributeName>
   *
   * MDN
   */
  lazy val attributeName = attr("attributeName")


  /**
   * This attribute specifies the namespace in which the target attribute
   * and its associated values are defined.
   *
   * Value 	CSS | XML | auto
   *
   * MDN
   */
  lazy val attributeType = attr("attributeType")


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
  lazy val azimuth = attr("azimuth")


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
  lazy val baseFrequency = attr("baseFrequency")


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
  lazy val baselineShift = attr("baseline-shift")


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
  lazy val begin = attr("begin")


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
  lazy val bias = attr("bias")


  /**
   * This attribute specifies the interpolation mode for the animation. The default
   * mode is linear, however if the attribute does not support linear interpolation
   * (e.g. for strings), the calcMode attribute is ignored and discrete interpolation is used.
   *
   * Value 	discrete | linear | paced | spline
   *
   * MDN
   */
  lazy val calcMode = attr("calcMode")


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
  lazy val `class` = attr("class")


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
  lazy val clip = attr("clip")


  /**
   * The clip-path attribute bind the element is applied to with a given <clipPath> element
   * As a presentation attribute, it also can be used as a property directly inside a CSS stylesheet
   *
   * Value 	<FuncIRI> | none | inherit
   *
   * MDN
   */
  lazy val clipPath = attr("clip-path")

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
  lazy val clipPathUnits = attr("clipPathUnits")

  /**
   * The clip-rule attribute only applies to graphics elements that are contained within a
   * <clipPath> element. The clip-rule attribute basically works as the fill-rule attribute,
   * except that it applies to <clipPath> definitions.
   *
   * Value 	nonezero | evenodd | inherit
   *
   * MDN
   */
  lazy val clipRule = attr("clip-rule")

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
  lazy val color = attr("color")


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
  lazy val colorInterpolation = attr("color-interpolation")


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
  lazy val colorInterpolationFilters = attr("color-interpolation-filters")


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
  lazy val colorProfile = attr("color-profile")


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
  lazy val colorRendering = attr("color-rendering")


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
  lazy val contentScriptType = attr("contentScriptType")


  /**
   * This attribute specifies the style sheet language for the given document fragment.
   * The contentStyleType is specified on the <svg> element. By default, if it's not defined,
   * the value is text/css
   *
   * Value 	<content-type>
   *
   * MDN
   */
  lazy val contentStyleType = attr("contentStyleType")


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
  lazy val cursor = attr("cursor")


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
  lazy val cx = attr("cx")

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
  lazy val cy = attr("cy")


  /**
   *
   *
   * MDN
   */
  lazy val d = attr("d")


  /**
   *
   *
   * MDN
   */
  lazy val diffuseConstant = attr("diffuseConstant")


  /**
   *
   *
   * MDN
   */
  lazy val direction = attr("direction")


  /**
   *
   *
   * MDN
   */
  lazy val display = attr("display")


  /**
   *
   *
   * MDN
   */
  lazy val divisor = attr("divisor")


  /**
   *
   *
   * MDN
   */
  lazy val dominantBaseline = attr("dominant-baseline")


  /**
   *
   *
   * MDN
   */
  lazy val dur = attr("dur")


  /**
   *
   *
   * MDN
   */
  lazy val dx = attr("dx")


  /**
   *
   *
   * MDN
   */
  lazy val dy = attr("dy")


  /**
   *
   *
   * MDN
   */
  lazy val edgeMode = attr("edgeMode")


  /**
   *
   *
   * MDN
   */
  lazy val elevation = attr("elevation")


  /**
   *
   *
   * MDN
   */
  lazy val end = attr("end")


  /**
   *
   *
   * MDN
   */
  lazy val externalResourcesRequired = attr("externalResourcesRequired")


  /**
   *
   *
   * MDN
   */
  lazy val fill = attr("fill")


  /**
   *
   *
   * MDN
   */
  lazy val fillOpacity = attr("fill-opacity")


  /**
   *
   *
   * MDN
   */
  lazy val fillRule = attr("fill-rule")


  /**
   *
   *
   * MDN
   */
  lazy val filter = attr("filter")


  /**
   *
   *
   * MDN
   */
  lazy val filterRes = attr("filterRes")


  /**
   *
   *
   * MDN
   */
  lazy val filterUnits = attr("filterUnits")


  /**
   *
   *
   * MDN
   */
  lazy val floodColor = attr("flood-color")


  /**
   *
   *
   * MDN
   */
  lazy val floodOpacity = attr("flood-opacity")


  /**
   *
   *
   * MDN
   */
  lazy val fontFamily = attr("font-family")


  /**
   *
   *
   * MDN
   */
  lazy val fontSize = attr("font-size")


  /**
   *
   *
   * MDN
   */
  lazy val fontSizeAdjust = attr("font-size-adjust")


  /**
   *
   *
   * MDN
   */
  lazy val fontStretch = attr("font-stretch")


  /**
   *
   *
   * MDN
   */
  lazy val fontVariant = attr("font-variant")


  /**
   *
   *
   * MDN
   */
  lazy val fontWeight = attr("font-weight")


  /**
   *
   *
   * MDN
   */
  lazy val from = attr("from")


  /**
   *
   *
   * MDN
   */
  lazy val gradientTransform = attr("gradientTransform")


  /**
   *
   *
   * MDN
   */
  lazy val gradientUnits = attr("gradientUnits")


  /**
   *
   *
   * MDN
   */
  lazy val height = attr("height")


  /**
   *
   *
   * MDN
   */
  lazy val imageRendering = attr("imageRendering")

  lazy val id = attr("id")

  /**
   *
   *
   * MDN
   */
  lazy val in = attr("in")



  /**
   *
   *
   * MDN
   */
  lazy val in2 = attr("in2")



  /**
   *
   *
   * MDN
   */
  lazy val k1 = attr("k1")


  /**
   *
   *
   * MDN
   */
  lazy val k2 = attr("k2")


  /**
   *
   *
   * MDN
   */
  lazy val k3 = attr("k3")


  /**
   *
   *
   * MDN
   */
  lazy val k4 = attr("k4")



  /**
   *
   *
   * MDN
   */
  lazy val kernelMatrix = attr("kernelMatrix")



  /**
   *
   *
   * MDN
   */
  lazy val kernelUnitLength = attr("kernelUnitLength")


  /**
   *
   *
   * MDN
   */
  lazy val kerning = attr("kerning")


  /**
   *
   *
   * MDN
   */
  lazy val keySplines = attr("keySplines")



  /**
   *
   *
   * MDN
   */
  lazy val keyTimes = attr("keyTimes")




  /**
   *
   *
   * MDN
   */
  lazy val letterSpacing = attr("letter-spacing")



  /**
   *
   *
   * MDN
   */
  lazy val lightingColor = attr("lighting-color")



  /**
   *
   *
   * MDN
   */
  lazy val limitingConeAngle = attr("limitingConeAngle")



  /**
   *
   *
   * MDN
   */
  lazy val local = attr("local")



  /**
   *
   *
   * MDN
   */
  lazy val markerEnd = attr("marker-end")


  /**
   *
   *
   * MDN
   */
  lazy val markerMid = attr("marker-mid")


  /**
   *
   *
   * MDN
   */
  lazy val markerStart = attr("marker-start")


  /**
   *
   *
   * MDN
   */
  lazy val markerHeight = attr("markerHeight")


  /**
   *
   *
   * MDN
   */
  lazy val markerUnits = attr("markerUnits")


  /**
   *
   *
   * MDN
   */
  lazy val markerWidth = attr("markerWidth")


  /**
   *
   *
   * MDN
   */
  lazy val maskContentUnits = attr("maskContentUnits")


  /**
   *
   *
   * MDN
   */
  lazy val maskUnits = attr("maskUnits")


  /**
   *
   *
   * MDN
   */
  lazy val mask = attr("mak")



  /**
   *
   *
   * MDN
   */
  lazy val max = attr("max")



  /**
   *
   *
   * MDN
   */
  lazy val min = attr("min")


  /**
   *
   *
   * MDN
   */
  lazy val mode = attr("mode")


  /**
   *
   *
   * MDN
   */
  lazy val numOctaves = attr("numOctaves")


  lazy val offset = attr("offset")

  /**
   *
   *
   * MDN
   */
  lazy val opacity = attr("opacity")



  /**
   *
   *
   * MDN
   */
  lazy val operator = attr("operator")


  /**
   *
   *
   * MDN
   */
  lazy val order = attr("order")


  /**
   *
   *
   * MDN
   */
  lazy val overflow = attr("overflow")



  /**
   *
   *
   * MDN
   */
  lazy val paintOrder = attr("paint-order")



  /**
   *
   *
   * MDN
   */
  lazy val pathLength = attr("pathLength")



  /**
   *
   *
   * MDN
   */
  lazy val patternContentUnits = attr("patternContentUnits")


  /**
   *
   *
   * MDN
   */
  lazy val patternTransform = attr("patternTransform")



  /**
   *
   *
   * MDN
   */
  lazy val patternUnits = attr("patternUnits")



  /**
   *
   *
   * MDN
   */
  lazy val pointerEvents = attr("pointer-events")


  /**
   *
   *
   * MDN
   */
  lazy val points = attr("points")


  /**
   *
   *
   * MDN
   */
  lazy val pointsAtX = attr("pointsAtX")


  /**
   *
   *
   * MDN
   */
  lazy val pointsAtY = attr("pointsAtY")


  /**
   *
   *
   * MDN
   */
  lazy val pointsAtZ = attr("pointsAtZ")


  /**
   *
   *
   * MDN
   */
  lazy val preserveAlpha = attr("preserveAlpha")



  /**
   *
   *
   * MDN
   */
  lazy val preserveAspectRatio = attr("preserveAspectRatio")



  /**
   *
   *
   * MDN
   */
  lazy val primitiveUnits = attr("primitiveUnits")


  /**
   *
   *
   * MDN
   */
  lazy val r = attr("r")



  /**
   *
   *
   * MDN
   */
  lazy val radius = attr("radius")


  /**
   *
   *
   * MDN
   */
  lazy val repeatCount = attr("repeatCount")


  /**
   *
   *
   * MDN
   */
  lazy val repeatDur = attr("repeatDur")



  /**
   *
   *
   * MDN
   */
  lazy val requiredFeatures = attr("requiredFeatures")



  /**
   *
   *
   * MDN
   */
  lazy val restart = attr("restart")



  /**
   *
   *
   * MDN
   */
  lazy val result = attr("result")



  /**
   *
   *
   * MDN
   */
  lazy val rx = attr("rx")



  /**
   *
   *
   * MDN
   */
  lazy val ry = attr("ry")



  /**
   *
   *
   * MDN
   */
  lazy val scale = attr("scale")



  /**
   *
   *
   * MDN
   */
  lazy val seed = attr("seed")



  /**
   *
   *
   * MDN
   */
  lazy val shapeRendering = attr("shape-rendering")



  /**
   *
   *
   * MDN
   */
  lazy val specularConstant = attr("specularConstant")



  /**
   *
   *
   * MDN
   */
  lazy val specularExponent = attr("specularExponent")



  /**
   *
   *
   * MDN
   */
  lazy val spreadMethod = attr("spreadMethod")



  /**
   *
   *
   * MDN
   */
  lazy val stdDeviation = attr("stdDeviation")



  /**
   *
   *
   * MDN
   */
  lazy val stitchTiles = attr("stitchTiles")



  /**
   *
   *
   * MDN
   */
  lazy val stopColor = attr("stop-color")



  /**
   *
   *
   * MDN
   */
  lazy val stopOpacity = attr("stop-opacity")



  /**
   *
   *
   * MDN
   */
  lazy val stroke = attr("stroke")


  /**
   *
   *
   * MDN
   */
  lazy val strokeDasharray= attr("stroke-dasharray")


  /**
   *
   *
   * MDN
   */
  lazy val strokeDashoffset = attr("stroke-dashoffset")


  /**
   *
   *
   * MDN
   */
  lazy val strokeLinecap = attr("stroke-linecap")


  /**
   *
   *
   * MDN
   */
  lazy val strokeLinejoin = attr("stroke-linejoin")


  /**
   *
   *
   * MDN
   */
  lazy val strokeMiterlimit = attr("stroke-miterlimit")


  /**
   *
   *
   * MDN
   */
  lazy val strokeOpacity = attr("stroke-opacity")


  /**
   *
   *
   * MDN
   */
  lazy val strokeWidth = attr("stroke-width")


  /**
   *
   *
   * MDN
   */
  lazy val style = attr("style")



  /**
   *
   *
   * MDN
   */
  lazy val surfaceScale = attr("surfaceScale")


  /**
   *
   *
   * MDN
   */
  lazy val targetX = attr("targetX")


  /**
   *
   *
   * MDN
   */
  lazy val targetY = attr("targetY")


  /**
   *
   *
   * MDN
   */
  lazy val textAnchor = attr("text-anchor")


  /**
   *
   *
   * MDN
   */
  lazy val textDecoration = attr("text-decoration")


  /**
   *
   *
   * MDN
   */
  lazy val textRendering = attr("text-rendering")


  /**
   *
   *
   * MDN
   */
  lazy val to = attr("to")


  /*
   *
   *
   * MDN
   */
  lazy val transform = attr("transform")


  /*
   *
   *
   * MDN
   */
  lazy val `type`= attr("type")


  /*
   *
   *
   * MDN
   */
  lazy val values = attr("values")


  /**
   *
   *
   * MDN
   */
  lazy val viewBox = attr("viewBox")


  /*
   *
   *
   * MDN
   */
  lazy val visibility = attr("visibility")


  /*
   *
   *
   * MDN
   */
  lazy val width = attr("width")


  /*
   *
   *
   * MDN
   */
  lazy val wordSpacing = attr("word-spacing")

  /*
   *
   *
   * MDN
   */
  lazy val writingMode = attr("writing-mode")


  /*
   *
   *
   * MDN
   */
  lazy val x = attr("x")


  /*
   *
   *
   * MDN
   */
  lazy val x1 = attr("x1")


  /*
   *
   *
   * MDN
   */
  lazy val x2 = attr("x2")


  /*
   *
   *
   * MDN
   */
  lazy val xChannelSelector = attr("xChannelSelector")


  /*
   *
   *
   * MDN
   */
  lazy val xLinkHref= attr("xlink:href", ns = Namespace.svgXlinkNamespaceConfig)


  /*
   *
   *
   * MDN
   */
  lazy val xLink = attr("xlink:role")


  /*
   *
   *
   * MDN
   */
  lazy val xLinkTitle = attr("xlink:title")


  /*
   *
   *
   * MDN
   */
  lazy val xmlSpace = attr("xml:space")


  /**
   *
   *
   * MDN
   */
  lazy val xmlns = attr("xmlns")


  /**
   *
   *
   * MDN
   */
  lazy val xmlnsXlink = attr("xmlns:xlink")


  /*
   *
   *
   * MDN
   */
  lazy val y = attr("y")


  /*
   *
   *
   * MDN
   */
  lazy val y1 = attr("y1")


  /*
   *
   *
   * MDN
   */
  lazy val y2 = attr("y2")


  /*
   *
   *
   * MDN
   */
  lazy val yChannelSelector = attr("yChannelSelector")


  /*
   *
   *
   * MDN
   */
  lazy val z = attr("z")
}
