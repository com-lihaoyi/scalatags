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
  implicit class JsonHelper(val sc: StringContext) {
    def hex(args: Any*): Color = Hex(sc.parts.mkString)
  }

  def linearGradient(direction: String, colors: ColorStop*) = LinearGradient(direction, colors:_*)

  def radialGradient(x: Length, y: Length, style: String, colors: ColorStop*): RadialGradient = {
    RadialGradient(Seq(s"$x $y", style), colors:_*)
  }
  def radialGradient(style: String, colors: ColorStop*): RadialGradient = {
    RadialGradient(Seq(style), colors:_*)
  }
  def radialGradient(colors: ColorStop*): RadialGradient = {
    RadialGradient(Nil, colors:_*)
  }

  def url(s: String) = Url(s)
  
  def element(s: String) = Element(s)
  def rgb(r: Int, g: Int, b: Int) = Rgb(r, g, b)
  def rgba(r: Int, g: Int, b: Int, a: Double) = Rgba(r, g, b, a)
  def hsl(h: Int, s: Int, l: Int) = Hsl(h, s, l)
  def hsla(h: Int, s: Int, l: Int, a: Double) = Hsla(h, s, l, a)
}

/**
 * Contains the trait and case class definitions for the most commonly used CSS
 * data types. Not really necessary if you are declaring your CSS inline with
 * your fragments, but useful if you want to pass CSS values around and need a
 * type to put in a parameter list or collection.
 */
object DataTypes{

  trait ColorStop
  case class ColorStopPair(c: Color, p: Length) extends ColorStop{
    override def toString = s"$c $p"
  }
  trait Color extends ColorStop{
    def ~(d: Length) = ColorStopPair(this, d)
  }
  case class Hex(s: String) extends Color{
    override def toString = s"#$s"
  }
  case class Rgb(r: Int, g: Int, b: Int) extends Color{
    override def toString = s"rgb($r, $g, $b)"
  }
  case class Rgba(r: Int, g: Int, b: Int, a: Double) extends Color{
    override def toString = s"rgb($r, $g, $b, $a)"
  }
  case class Hsl(h: Int, s: Int, l: Int) extends Color{
    override def toString = s"hsl($h, $s, $l)"
  }

  case class Hsla(h: Int, s: Int, l: Int, a: Double) extends Color{
    override def toString = s"hsla($h, $s, $l, $a)"
  }



  trait Image
  case class Element(selector: String) extends Image{
    override def toString = s"element(selector)"
  }
  case class Url(url: String) extends Image{
    override def toString = s"url($url)"
  }
  trait Gradient extends Image
  case class LinearGradient(direction: String, colors: ColorStop*) extends Gradient{
    override def toString = {
      s"""linear-gradient($direction, ${colors.mkString(", ")})"""
    }
  }
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

  case class Length(number: Double, unit: String) extends NumberUnit
  case class Angle(number: Double, unit: String) extends NumberUnit
  case class Time(number: Double, unit: String) extends NumberUnit


  trait Position
  trait Ratio
}






