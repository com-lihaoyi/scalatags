package scalatags
import acyclic.file
/**
 * Module containing convenient ways of constructing CSS data types
 */
object DataConverters extends DataConverters


/**
 * Trait containing the contents of the [[DataConverters]] module, so it can be
 * mixed in to other objects as needed.
 */
trait DataConverters{
  implicit def Int2CssNumber(x: Int): CssNumber[Int] = new CssNumber(x)
  implicit def Double2CssNumber(x: Double): CssNumber[Double] = new CssNumber(x)
  implicit def Float2CssNumber(x: Float): CssNumber[Float] = new CssNumber(x)
  implicit def Long2CssNumber(x: Long): CssNumber[Long] = new CssNumber(x)
  implicit def Short2CssNumber(x: Short): CssNumber[Short] = new CssNumber(x)
  implicit def Byte2CssNumber(x: Byte): CssNumber[Byte] = new CssNumber(x)
  /**
   * Extends numbers to provide a bunch of useful methods, allowing you to write
   * CSS-lengths in a nice syntax without resorting to strings.
   */
  class CssNumber[T: Numeric](x: T){

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
    def px = x + "px"

    /**
     * One point which is 1/72 of an inch.
     *
     * MDN
     */
    def pt = x + "pt"

    /**
     * One millimeter.
     *
     * MDN
     */
    def mm = x + "mm"

    /**
     * One centimeter 10 millimeters.
     *
     * MDN
     */
    def cm = x + "cm"

    /**
     * One inch 2.54 centimeters.
     *
     * MDN
     */
    def in = x + "in"

    /**
     * One pica which is 12 points.
     *
     * MDN
     */
    def pc = x + "pc"
    /**
     * This unit represents the calculated font-size of the element. If used on
     * the font-size property itself, it represents the inherited font-size
     * of the element.
     *
     * MDN
     */
    def em = x + "em"

    /**
     * This unit represents the width, or more precisely the advance measure, of
     * the glyph '0' zero, the Unicode character U+0030 in the element's font.
     *
     * MDN
     */
    def ch = x + "ch"

    /**
     * This unit represents the x-height of the element's font. On fonts with the
     * 'x' letter, this is generally the height of lowercase letters in the font;
     * 1ex ≈ 0.5em in many fonts.
     *
     * MDN
     */
    def ex = x + "ex"

    /**
     * This unit represents the font-size of the root element e.g. the font-size
     * of the html element. When used on the font-size on this root element,
     * it represents its initial value.
     *
     * MDN
     */
    def rem = x + "rem"

    /**
     * An angle in degrees. One full circle is 360deg. E.g. 0deg, 90deg, 360deg.
     */
    def deg = x + "deg"

    /**
     * An angle in gradians. One full circle is 400grad. E.g. 0grad, 100grad,
     * 400grad.
     *
     * MDN
     */
    def grad = x + "grad"

    /**
     * An angle in radians.  One full circle is 2π radians which approximates
     * to 6.2832rad. 1rad is 180/π degrees. E.g. 0rad, 1.0708rad, 6.2832rad.
     *
     * MDN
     */
    def rad = x + "rad"

    /**
     * The number of turns the angle is. One full circle is 1turn. E.g. 0turn,
     * 0.25turn, 1turn.
     *
     * MDN
     */
    def turn = x + "turn"

    /**
     * A percentage value
     */
    def pct = x + "%"
  }
}



