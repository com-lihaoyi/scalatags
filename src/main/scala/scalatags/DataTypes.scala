package scalatags

import DataTypes._


private[scalatags] trait DataTypes {
  implicit def Int2CssNumber(x: Int) = new CssNumber(x)
  implicit def Double2CssNumber(x: Double) = new CssNumber(x)
  implicit def Float2CssNumber(x: Float) = new CssNumber(x)
  implicit def Long2CssNumber(x: Long) = new CssNumber(x)
  implicit def Short2CssNumber(x: Short) = new CssNumber(x)
  implicit def Byte2CssNumber(x: Byte) = new CssNumber(x)
  /**
   * Extends numbers to provide a bunch of useful methods, allowing you to write
   * CSS-lengths in a nice syntax without resorting to strings.
   */
  class CssNumber(x: Double){

    /**
     * Relative to the viewing device. For screen display, typically one device
     * pixel (dot) of the display.
     *
     * For printers and very high resolution screens one CSS pixel implies
     * multiple device pixels, so that the number of pixel per inch stays around
     * 96.
     *
     * MDN
     */
    def px = Length(x, "px")

    /**
     * One point (which is 1/72 of an inch).
     *
     * MDN
     */
    def pt = Length(x, "pt")

    /**
     * One millimeter.
     *
     * MDN
     */
    def mm = Length(x, "mm")

    /**
     * One centimeter (10 millimeters).
     *
     * MDN
     */
    def cm = Length(x, "cm")

    /**
     * One inch (2.54 centimeters).
     *
     * MDN
     */
    def in = Length(x, "in")

    /**
     * One pica (which is 12 points).
     *
     * MDN
     */
    def pc = Length(x, "pc")
    /**
     * This unit represents the calculated font-size of the element. If used on
     * the font-size property itself, it represents the inherited font-size
     * of the element.
     *
     * MDN
     */
    def em = Length(x, "em")

    /**
     * This unit represents the width, or more precisely the advance measure, of
     * the glyph '0' (zero, the Unicode character U+0030) in the element's font.
     *
     * MDN
     */
    def ch = Length(x, "ch")

    /**
     * This unit represents the x-height of the element's font. On fonts with the
     * 'x' letter, this is generally the height of lowercase letters in the font;
     * 1ex ≈ 0.5em in many fonts.
     *
     * MDN
     */
    def ex = Length(x, "ex")

    /**
     * This unit represents the font-size of the root element (e.g. the font-size
     * of the `<html>` element). When used on the font-size on this root element,
     * it represents its initial value.
     *
     * MDN
     */
    def rem = Length(x, "rem")

    /**
     * An angle in degrees. One full circle is 360deg. E.g. 0deg, 90deg, 360deg.
     */
    def deg = Length(x, "deg")

    /**
     * An angle in gradians. One full circle is 400grad. E.g. 0grad, 100grad,
     * 400grad.
     *
     * MDN
     */
    def grad = Length(x, "grad")

    /**
     * An angle in radians.  One full circle is 2π radians which approximates
     * to 6.2832rad. 1rad is 180/π degrees. E.g. 0rad, 1.0708rad, 6.2832rad.
     *
     * MDN
     */
    def rad = Length(x, "rad")

    /**
     * The number of turns the angle is. One full circle is 1turn. E.g. 0turn,
     * 0.25turn, 1turn.
     *
     * MDN
     */
    def turn = Length(x, "turn")

    /**
     * A percentage value
     */
    def pct = Length(x, "%")
  }
  implicit class HexColorHelper(val sc: StringContext) {
    /**
     * A color represented as a hex string e.g. #fff or #ababab
     */
    def hex(args: Any*): Color = Hex(sc.parts.mkString)
  }

  def linearGradient(direction: String, colors: ColorStop*) = LinearGradient(direction, colors:_*)
  /**
   * The CSS radial-gradient() function creates an Image which represents a
   * gradient of colors radiating from an origin, the center of the gradient.
   * The result of this function is an object of the CSS Gradient data type.
   *
   * MDN
   */
  def radialGradient(x: Length, y: Length, style: String, colors: ColorStop*): RadialGradient = {
    RadialGradient(Seq(s"$x $y", style), colors:_*)
  }
  /**
   * The CSS radial-gradient() function creates an Image which represents a
   * gradient of colors radiating from an origin, the center of the gradient.
   * The result of this function is an object of the CSS Gradient data type.
   *
   * MDN
   */
  def radialGradient(style: String, colors: ColorStop*): RadialGradient = {
    RadialGradient(Seq(style), colors:_*)
  }
  /**
   * The CSS radial-gradient() function creates an Image which represents a
   * gradient of colors radiating from an origin, the center of the gradient.
   * The result of this function is an object of the CSS Gradient data type.
   *
   * MDN
   */
  def radialGradient(colors: ColorStop*): RadialGradient = {
    RadialGradient(Nil, colors:_*)
  }
  /**
   * An image found at a particular URL
   */
  def url(s: String) = Url(s)
  
  def element(s: String) = Element(s)
  /**
   * A color represented as its 3 primary components, from 0 to 255
   */
  def rgb(r: Int, g: Int, b: Int) = Rgb(r, g, b)

  /**
   * A color represented as its 3 primary components, from 0 to 255,
   * as well as an alpha (opacity) from 0.0 to 1.0
   */
  def rgba(r: Int, g: Int, b: Int, a: Double) = Rgba(r, g, b, a)
  /**
   * A color represented as Hue (0-360), Saturation (0-100) and Lightness (0-100)
   */
  def hsl(h: Int, s: Int, l: Int) = Hsl(h, s, l)
  /**
   * A color represented as Hue (0-360), Saturation (0-100) and Lightness (0-100)
   * as well as an alpha (opacity) from 0.0 to 1.0
   */
  def hsla(h: Int, s: Int, l: Int, a: Double) = Hsla(h, s, l, a)
}

/**
 * Contains the trait and case class definitions for the most commonly used CSS
 * data types. Not really necessary if you are declaring your CSS inline with
 * your fragments, but useful if you want to pass CSS values around and need a
 * type to put in a parameter list or collection.
 */
object DataTypes{

  /**
   * Something that can be used in as a color stop in a gradient. Generally is
   * either a Color or a ColorStopPair(Color, Length).
   */
  trait ColorStop

  /**
   * Pair of Color and Length to be used in a gradient
   */
  case class ColorStopPair(c: Color, p: Length) extends ColorStop{
    override def toString = s"$c $p"
  }

  /**
   * The Color CSS data type denotes a color in the sRGB color space. A color
   * can be described in any of these ways:
   *
   * - using a keyword
   * - using the RGB cubic-coordinate system (via the #-hexadecimal or the rgb()
   *   and rgba() functional notations)
   * - using the HSL cylindrical-coordinate system (via the hsl() and hsla()
   *   functional notations)
   *
   * MDN
   */
  trait Color extends ColorStop{
    def ~(d: Length) = ColorStopPair(this, d)
  }

  /**
   * A color represented as a hex string e.g. #fff or #ababab
   */
  case class Hex(s: String) extends Color{
    override def toString = s"#$s"
  }
  /**
   * A color represented as its 3 primary components, from 0 to 255
   */
  case class Rgb(r: Int, g: Int, b: Int) extends Color{
    override def toString = s"rgb($r, $g, $b)"
  }
  /**
   * A color represented as its 3 primary components, from 0 to 255,
   * as well as an alpha (opacity) from 0.0 to 1.0
   */
  case class Rgba(r: Int, g: Int, b: Int, a: Double) extends Color{
    override def toString = s"rgb($r, $g, $b, $a)"
  }

  /**
   * A color represented as Hue (0-360), Saturation (0-100) and Lightness (0-100)
   */
  case class Hsl(h: Int, s: Int, l: Int) extends Color{
    override def toString = s"hsl($h, $s, $l)"
  }
  /**
   * A color represented as Hue (0-360), Saturation (0-100) and Lightness (0-100)
   * as well as an alpha (opacity) from 0.0 to 1.0
   */
  case class Hsla(h: Int, s: Int, l: Int, a: Double) extends Color{
    override def toString = s"hsla($h, $s, $l, $a)"
  }


  /**
   * The Image CSS data type represents a 2D image. There are two kinds of
   * images in CSS: plain static images, often referenced using a URL, and
   * dynamically-generated images like gradients or representations of parts of
   * the tree.
   *
   * MDN
   */
  trait Image

  /**
   * An image created via a portion of the DOM, selected by a CSS selector
   */
  case class Element(selector: String) extends Image{
    override def toString = s"element(selector)"
  }

  /**
   * An image found at a particular URL
   */
  case class Url(url: String) extends Image{
    override def toString = s"url($url)"
  }

  /**
   * An image which is a gradient, either Linear or Radial
   */
  trait Gradient extends Image

  /**
   * The CSS linear-gradient() function creates an Image which represents a
   * linear gradient of colors. The result of this function is an object of the
   * CSS Gradient data type. Like any other gradient, a CSS linear gradient is
   * not a CSS Color but an image with no intrinsic dimensions; that is, it
   * has neither natural or preferred size, nor ratio. Its concrete size will
   * match the one of the element it applies to.
   *
   * MDN
   */
  case class LinearGradient(direction: String, colors: ColorStop*) extends Gradient{
    override def toString = {
      s"""linear-gradient($direction, ${colors.mkString(", ")})"""
    }
  }

  /**
   * The CSS radial-gradient() function creates an Image which represents a
   * gradient of colors radiating from an origin, the center of the gradient.
   * The result of this function is an object of the CSS Gradient data type.
   *
   * MDN
   */
  case class RadialGradient(sections: Seq[String], colors: ColorStop*) extends Gradient{
    override def toString = {
      s"""radial-gradient(${(sections ++ colors).mkString(", ")})"""
    }
  }

  private[scalatags] trait NumberUnit{
    def number: Double
    def unit: String
    override def toString = {
      if (number.toInt == number) number.toInt + unit
      else number + unit
    }
  }

  /**
   * The Length CSS data type denotes distance measurements. It is a Number
   * immediately followed by a length unit (px, em, pc, in, mm, …). Like for any
   * CSS dimension, there is no space between the unit literal and the number.
   */
  case class Length(number: Double, unit: String) extends NumberUnit
  case class Angle(number: Double, unit: String) extends NumberUnit
  case class Time(number: Double, unit: String) extends NumberUnit


  trait Position
  trait Ratio
}






