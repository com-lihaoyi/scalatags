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
/**
 * Trait containing the contents of the [[SvgAttrs]] module, so they can be
 * mixed in to other objects if needed.
 * 
 * Those attributes are only used for SVG and are not imported by default 
 * to avoid namespace pollution.
 */
trait SvgAttrs[Builder, Output] extends Util[Builder, Output]{

  /**
   * The viewBox attribute allows to specify that a given set of graphics stretch 
   * to fit a particular container element.
   * 
   * The value of the viewBox attribute is a list of four numbers min-x, min-y, 
   * width and height, separated by whitespace and/or a comma, which specify 
   * a rectangle in user space which should be mapped to the bounds of the 
   * viewport established by the given element, taking into account attribute 
   * preserveAspectRatio.
   * 
   * Negative values for width or height are not permitted and a value of zero 
   * disables rendering of the element.
   * 
   * MDN
   */
  val viewBox = "viewBox".attr

  /**
   * The transform attribute defines a list of transform definitions that are 
   * applied to an element and the element's children. The items in the 
   * transform list are separated by whitespace and/or commas, and are 
   * applied from right to left.
   * 
   * MDN
   */
  val transform = "transform".attr

  /**
   * This attribute indicates a x-axis coordinate in the user coordinate system. 
   * The exact effect of this coordinate depend on each element. Most of the 
   * time, it represent the x-axis coordinate of the upper-left corner of the 
   * rectangular region of the reference element (see each individual element's 
   * documentation for exceptions).
   * 
   * If the attribute is not specified, the effect is as if a value of 0 were 
   * specified except for the <filter> and <mask> elements where the default 
   * value is -10%.
   * 
   * MDN
   */
  val x = "x".attr

  /**
   * This attribute indicates a y-axis coordinate in the user coordinate system. 
   * The exact effect of this coordinate depend on each element. Most of the 
   * time, it represent the y-axis coordinate of the upper-left corner of the 
   * rectangular region of the reference element (see each individual element's 
   * documentation for exceptions).
   * 
   * If the attribute is not specified, the effect is as if a value of 0 were 
   * specified except for the <filter> and <mask> elements where the default 
   * value is -10%.
   * 
   * MDN
   */
  val y = "y".attr

  /**
   * This attribute indicates an horizontal length in the user coordinate system. 
   * The exact effect of this coordinate depend on each element. Most of the 
   * time, it represent the width of the rectangular region of the reference 
   * element (see each individual element's documentation for exceptions).
   * 
   * This attribute must be specified except for the <svg> element where the 
   * default value is 100% (except for root <svg> elements that have HTML 
   * parents) and the <filter> and <mask> elements where the default value 
   * is 120%.
   * 
   * MDN
   */
  val width = "width".attr

  /**
   * This attribute indicates a vertical length in the user coordinate system. 
   * The exact effect of this coordinate depends on each element. Most of the 
   * time, it represents the height of the rectangular region of the reference 
   * element (see each individual element's documentation for exceptions).
   * 
   * This attribute must be specified except for the <svg> element where the 
   * default value is 100% and the <filter> and <mask> elements where the 
   * default value is 120%.
   * 
   * MDN
   */
  val height = "height".attr

  /**
   * This attribute defines a path to follow.
   * 
   * MDN
   */
  val d = "d".attr

  /**
   * For the <circle> and the <ellipse> element, this attribute define the x-axis 
   * coordinate of the center of the element. If the attribute is not specified, the 
   * effect is as if a value of "0" were specified.
   * 
   * For the <radialgradient> element, this attribute define the x-axis coordinate 
   * of the largest (i.e., outermost) circle for the radial gradient. The gradient 
   * will be drawn such that the 100% gradient stop is mapped to the perimeter 
   * of this largest (i.e., outermost) circle. If the attribute is not specified, the 
   * effect is as if a value of 50% were specified.
   * 
   * MDN
   */
  val cx = "cx".attr

  /**
   * For the <circle> and the <ellipse> element, this attribute define the y-axis 
   * coordinate of the center of the element. If the attribute is not specified, the 
   * effect is as if a value of "0" were specified.
   * 
   * For the <radialgradient> element, this attribute define the y-axis coordinate 
   * of the largest (i.e., outermost) circle for the radial gradient. The gradient 
   * will be drawn such that the 100% gradient stop is mapped to the perimeter 
   * of this largest (i.e., outermost) circle. If the attribute is not specified, the 
   * effect is as if a value of 50% were specified.
   * 
   * MDN
   */
  val cy = "cy".attr

  /**
   * For the <circle> this attribute defines the radius of the element. A value 
   * of zero disables rendering of the circle.
   * 
   * For the <radialgradient> element, this attribute defines the radius of the 
   * largest (i.e., outermost) circle for the radial gradient. The gradient will be 
   * drawn such that the 100% gradient stop is mapped to the perimeter of 
   * this largest (i.e., outermost) circle. A value of zero will cause the area to 
   * be painted as a single color using the color and opacity of the last gradient 
   * <stop>. If the attribute is not specified, the effect is as if a value of 50% 
   * were specified.
   * 
   * MDN
   */
  val r = "r".attr

  /**
   * The fill attribute has two meanings based on the context it's used.
   * 
   * By default, when animation elements end their effects are no longer 
   * applied to the presentation value for the target attributes. The fill attribute 
   * can be used to maintain the value of an animation after the active duration 
   * of an animation element ends.
   * 
   * For shapes and text, the fill attribute is a presentation attribute that define 
   * the color of the interior of the given graphical element. What is called the 
   * "interior" depends on the shape itself and the value of the fill-rule attribute.
   *  
   *
   * As a presentation attribute, it also can be used as a property directly inside 
   * a CSS stylesheet.
   * 
   * MDN
   */
  val fill = "fill".attr

  /**
   * This attribute specifies the opacity of the color or the content the current object 
   * is filled with.
   * 
   * MDN
   */
  val fillOpacity = "fill-opacity".attr

  /**
   * the stroke attribute is a presentation attribute that define the color of the 
   * outline of the given graphical element. The default value for the stroke attribute 
   * is none which means that the outline is never drawn.
   * 
   * As a presentation attribute, it also can be used as a property directly inside a 
   * CSS stylesheet.
   * 
   * MDN
   */
  val stroke = "stroke".attr

  /**
   * the stroke-width attribute specifies the width of the outline on the current 
   * object. Its default value is 1. If a <percentage> is used, the value represents 
   * a percentage of the current viewport. If a value of 0 is used the outline will 
   * never be drawn.
   *
   * As a presentation attribute, it also can be used as a property directly inside a 
   * CSS stylesheet.
   * 
   * MDN
   */
  val strokeWidth = "stroke-width".attr

  /**
   * The color attribute is used to provide a potential indirect value (currentColor) 
   * for the fill, stroke, stop-color, flood-color and lighting-color attributes.
   * 
   * As a presentation attribute, it also can be used as a property directly inside 
   * a CSS stylesheet, see css color for further information.
   * 
   * MDN
   */
  val color = "color".attr

  /**
   * The clip attribute has the same parameter values as defined for the css 
   * clip property. Unitless values, which indicate current user coordinates, 
   * are permitted on the coordinate values on the <shape>. The value of 
   * auto defines a clipping path along the bounds of the viewport created 
   * by the given element.
   * 
   * As a presentation attribute, it also can be used as a property directly 
   * inside a CSS stylesheet, see css clip for further information.
   * 
   * MDN
   */
  val clip = "clip".attr

  /**
   * The clip-path attribute bind the element is applied to with a given 
   * <clippath> element.
   * 
   * As a presentation attribute, it also can be used as a property directly 
   * inside a CSS stylesheet.
   * 
   * MDN
   */
  val clipPath = "clip-path".attr

  /**
   * The clipPathUnits attribute defines the coordinate system for the 
   * contents of the <clippath> element.
   * 
   * If the clipPathUnits attribute is not specified, then the effect is as 
   * if a value of userSpaceOnUse were specified.
   * 
   * Note that values defined as a percentage inside the content of 
   * the <clippath> are not affected by this attribute. It means that 
   * even if you set the value of maskContentUnits to 
   * objectBoundingBox, percentage values will be calculated as if 
   * the value of the attribute were userSpaceOnUse.
   * 
   * MDN
   */
  val clipPathUnits = "clipPathUnits".attr

  /**
   * The mask attribute bind the element is applied to with a given 
   * <mask> element.
   * 
   * As a presentation attribute, it also can be used as a property directly 
   * inside a CSS stylesheet.
   * 
   * MDN
   */
  val mask = "mask".attr

  /**
   * The maskUnits attribute defines the coordinate system for 
   * attributes x, y, width and height.
   * 
   * If the maskUnits attribute isn't specified, then the effect is as if 
   * a value of objectBoundingBox were specified.
   * 
   * MDN
   */
  val maskUnits = "maskUnits".attr
}

