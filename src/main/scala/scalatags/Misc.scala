package scalatags

/**
 * Represents a single CSS class.
 */
class Cls(val name: String)

/**
 * Misc helper functions
 */
private[scalatags] trait Misc extends Attributes{
  /**
   * Shorthand to generate a <script type="text/javascript" src="..."></script> tag
   */
  def javascript(origin: String = "") =
    script(`type`->"text/javascript", src->origin)

  /**
   * Shorthand to generate a <style rel="stylesheet" type="text/css" href="..."></style> tag
   */
  def stylesheet(origin: String = "") =
    link(rel->"stylesheet", `type`->"text/css", href->origin)

  /**
   * Extends numbers to provide a bunch of useful methods, allowing you to write
   * CSS-lengths in a nice syntax without resorting to strings.
   */
  implicit class CssNumber[T: Numeric](x: T){
    /**
     * Relative to the viewing device. For screen display, typically one device
     * pixel (dot) of the display.
     *
     * For printers and very high resolution screens one CSS pixel implies
     * multiple device pixels, so that the number of pixel per inch stays around
     * 96.
     */
    def px = x + "px"

    /**
     * One point (which is 1/72 of an inch).
     */
    def pt = x + "pt"

    /**
     * One millimeter.
     */
    def mm = x + "mm"

    /**
     * One centimeter (10 millimeters).
     */
    def cm = x + "cm"

    /**
     * One inch (2.54 centimeters).
     */
    def in = x + "in"

    /**
     * One pica (which is 12 points).
     */
    def pc = x + "pc"
    /**
     * This unit represents the calculated font-size of the element. If used on
     * the font-size property itself, it represents the inherited font-size
     * of the element.
     */
    def em = x + "em"

    /**
     * This unit represents the width, or more precisely the advance measure, of
     * the glyph '0' (zero, the Unicode character U+0030) in the element's font.
     */
    def ch = x + "ch"

    /**
     * This unit represents the x-height of the element's font. On fonts with the
     * 'x' letter, this is generally the height of lowercase letters in the font;
     * 1ex ≈ 0.5em in many fonts.
     */
    def ex = x + "ex"

    /**
     * This unit represents the font-size of the root element (e.g. the font-size
     * of the `<html>` element). When used on the font-size on this root element,
     * it represents its initial value.
     */
    def rem = x + "rem"
  }
}
