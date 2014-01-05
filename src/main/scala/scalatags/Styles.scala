package scalatags

/**
 * A Style that only has a fixed set of possible values, provided by its members.
 */
class FixedStyle(jsName: String, cssName: String){
  def ~~(value: String) = new StyleValue(this, value)
}

/**
 * A Style that takes any value of type T as a parameter
 */
class OpenStyle[T](jsName: String, cssName: String) extends FixedStyle(jsName, cssName){

  def ~(value: T) = new StyleValue(this, value)

}
class StyleValue[T](s: FixedStyle, value: T)
trait Styles {
  type Color = String
  type Length = String
  type Number = String
  type Time = String
  /**
   * If a background-image is specified, the background-attachment CSS
   * property determines whether that image's position is fixed within
   * the viewport, or scrolls along with its containing block.
   */
  object backgroundAttachment extends FixedStyle("backgroundAttachment", "background-attachment"){
    /**
     * This keyword means that the background image will scroll within the
     * viewport along with the block the image is contained within.
     */
    val scroll = this ~~ "scroll"
    /**
     * This keyword means that the background image will not scroll with its
     * containing element, instead remaining stationary within the viewport.
     */
    val fixed = this ~~ "fixed"
    /**
     * This keyword means that the background image will not scroll with its
     * containing element, but will scroll when the element's content scrolls:
     * it is fixed regarding the element's content.
     */
    val local = this ~~ "local"
  }

  object visibility extends FixedStyle("visibility", "visibility"){
    /**
     * Default value, the box is visible
     */
    val visible = this ~~ "visible"
    /**
     * The box is invisible (fully transparent, nothing is drawn), but still
     * affects layout.  Descendants of the element will be visible if they have
     * visibility:visible
     */
    val hidden = this ~~ "hidden"
    /**
     * For table rows, columns, column groups, and row groups the row(s) or
     * column(s) are hidden and the space they would have occupied is (as if
     * display: none were applied to the column/row of the table)
     */
    val collapse = this ~~ "collapse"
  }

  /**
   * The text-align-last CSS property describes how the last line of a block or
   * a line, right before a forced line break, is aligned.
   */
  object textAlignLast extends FixedStyle("textAlignLast", "text-align-last"){
    /**
     * The affected line is aligned per the value of text-align, unless
     * text-align is justify, in which case the effect is the same as setting
     * text-align-last to left.
     */
    val auto = this ~~ "auto"
    /**
     * The same as left if direction is left-to-right and right if direction
     * is right-to-left.
     */
    val start = this ~~ "start"
    /**
     * The same as right if direction is left-to-right and left if direction
     * is right-to-left.
     */
    val end = this ~~ "end"
    /**
     * The inline contents are aligned to the left edge of the line box.
     */
    val left = this ~~ "left"
    /**
     * The inline contents are aligned to the right edge of the line box.
     */
    val right = this ~~ "right"
    /**
     * The inline contents are centered within the line box.
     */
    val center = this ~~ "center"
    /**
     * The text is justified. Text should line up their left and right edges to
     * the left and right content edges of the paragraph.
     */
    val justify = this ~~ "justify"
  }
  /**
   * The border-right-style CSS property sets the line style of the right border
   * of a box.
   */
  object borderRightStyle
    extends FixedStyle("bocrderRightStyle", "border-right-style")
    with BorderStyle

  trait Outline extends FixedStyle{
    /**
     * Displays a series of rounded dots. The spacing of the dots are not
     * defined by the specification and are implementation-specific. The radius
     * of the dots is half the calculated border-right-width.
     */
    val dotted = this ~~ "dotted"
    /**
     * Displays a series of short square-ended dashes or line segments. The exact
     * size and length of the segments are not defined by the specification and
     * are implementation-specific.
     */
    val dashed = this ~~ "dashed"
    /**
     * Displays a single, straight, solid line.
     */
    val solid = this ~~ "solid"
    /**
     * Displays two straight lines that add up to the pixel amount defined as
     * border-width or border-right-width.
     */
    val double = this ~~ "double"
    /**
     * Displays a border leading to a carved effect. It is the opposite of ridge.
     */
    val groove = this ~~ "groove"
    /**
     * Displays a border with a 3D effect, like if it is coming out of the page.
     * It is the opposite of groove.
     */
    val ridge = this ~~ "ridge"
    /**
     * Displays a border that makes the box appear embedded. It is the opposite
     * of outset. When applied to a table cell with border-collapse set to
     * collapsed, this value behaves like groove.
     */
    val inset = this ~~ "inset"
    /**
     * Displays a border that makes the box appear in 3D, embossed. It is the
     * opposite of inset. When applied to a table cell with border-collapse set
     * to collapsed, this value behaves like ridge.
     */
    val outset = this ~~ "outset"
  }
  trait BorderStyle extends FixedStyle with Outline{
    /**
     * Like for the hidden keyword, displays no border. In that case, except if
     * a background image is set, the calculated values of border-right-width
     * will be 0, even if specified otherwise through the property. In case of
     * table cell and border collapsing, the none value has the lowest priority:
     * it means that if any other conflicting border is set, it will be
     * displayed.
     */
    val none = this ~~ "none"
    /**
     * Like for the none keyword, displays no border. In that case, except if a
     * background image is set, the calculated values of border-right-width will
     * be 0, even if specified otherwise through the property. In case of table
     * cell and border collapsing, the hidden value has the highest priority: it
     * means that if any other conflicting border is set, it won't be displayed.
     */
    val hidden = this ~~ "hidden"

  }

  /**
   * The counter-increment CSS property is used to increase the value of CSS
   * Counters by a given value. The counter's value can be reset using the
   * counter-reset CSS property.
   */
  object counterIncrement extends OpenStyle[String]("counterIncrement", "counter-increment")

  /**
   * The orphans CSS property refers to the minimum number of lines in a block
   * container that must be left at the bottom of the page. This property is
   * normally used to control how page breaks occur.
   */
  object orphans extends OpenStyle[Int]("orphans", "orphans")

  /**
   * The border-style CSS property is a shorthand property for setting the line
   * style for all four sides of the elements border.
   */
  object borderStyle extends OpenStyle[String]("borderStyle", "border-style")

  /**
   * The CSS property pointer-events allows authors to control under what
   * circumstances (if any) a particular graphic element can become the target
   * of mouse events. When this property is unspecified, the same characteristics
   * of the visiblePainted value apply to SVG content.
   *
   * In addition to indicating that the element is not the target of mouse events,
   * the value none instructs the mouse event to go "through" the element and
   * target whatever is "underneath" that element instead.
   */
  object pointerEvents extends OpenStyle("pointerEvents", "pointer-events"){
    /**
     * The element behaves as it would if the pointer-events property was not
     * specified. In SVG content, this value and the value visiblePainted have
     * the same effect.
     */
    val auto = this ~~ "auto"
    /**
     * The element is never the target of mouse events; however, mouse events
     * may target its descendant elements if those descendants have pointer-events
     * set to some other value. In these circumstances, mouse events will trigger
     * event listeners on this parent element as appropriate on their way to/from
     * the descendant during the event capture/bubble phases.
     */
    val none = this ~~ "none"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * visibility property is set to visible and when the mouse cursor is over
     * the interior (i.e., 'fill') of the element and the fill property is set
     * to a value other than none, or when the mouse cursor is over the perimeter
     * (i.e., 'stroke') of the element and the stroke property is set to a value
     * other than none.
     */
    val visiblePainted = this ~~ "visiblePainted"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * visibility property is set to visible and when the mouse cursor is over
     * the interior (i.e., fill) of the element. The value of the fill property
     * does not effect event processing.
     */
    val visibleFill = this ~~ "visibleFill"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * visibility property is set to visible and when the mouse cursor is over
     * the perimeter (i.e., stroke) of the element. The value of the stroke
     * property does not effect event processing.
     */
    val visibleStroke = this ~~ "visibleStroke"
    /**
     * SVG only. The element can be the target of a mouse event when the
     * visibility property is set to visible and the mouse cursor is over either
     * the interior (i.e., fill) or the perimeter (i.e., stroke) of the element.
     * The values of the fill and stroke do not effect event processing.
     */
    val visible = this ~~ "visible"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * mouse cursor is over the interior (i.e., 'fill') of the element and the
     * fill property is set to a value other than none, or when the mouse cursor
     * is over the perimeter (i.e., 'stroke') of the element and the stroke
     * property is set to a value other than none. The value of the visibility
     * property does not effect event processing.
     */
    val painted = this ~~ "painted"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * pointer is over the interior (i.e., fill) of the element. The values of
     * the fill and visibility properties do not effect event processing.
     */
    val fill = this ~~ "fill"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * pointer is over the perimeter (i.e., stroke) of the element. The values
     * of the stroke and visibility properties do not effect event processing.
     */
    val stroke = this ~~ "stroke"
    /**
     * SVG only. The element can only be the target of a mouse event when the
     * pointer is over the interior (i.e., fill) or the perimeter (i.e., stroke)
     * of the element. The values of the fill, stroke and visibility properties
     * do not effect event processing.
     */
    val all = this ~~ "all"
  }

  /**
   * The border-top-color CSS property sets the color of the top border of an
   * element. Note that in many cases the shorthand CSS properties border-color
   * or border-top are more convenient and preferable.
   */
  object borderTopColor extends OpenStyle[Color]("borderTopColor", "border-top-color")



  /**
   * The text-indent CSS property specifies how much horizontal space should be
   * left before the beginning of the first line of the text content of an element. Horizontal spacing is with respect to the left (or right, for right-to-left layout) edge of the containing block element's box.
   */
  object textIndent extends OpenStyle[Length]("textIndent", "text-indent")

  /**
   * The list-style-image CSS property sets the image that will be used as the
   * list item marker. It is often more convenient to use the shorthand
   * list-style.
   */
  object listStyleImage extends OpenStyle[String]("listStyleImage", "list-style-image")

  /**
   * The cursor CSS property specifies the mouse cursor displayed when the mouse
   * pointer is over an element.
   */
  object cursor extends FixedStyle("cursor", "cursor"){
    /**
     * The browser determines the cursor to display based on the current context.
     * E.g. equivalent to text when hovering text.
     */
    val auto = this ~~ "auto"
    /**
     * Default cursor, typically an arrow.
     */
    val default = this ~~ "default"
    /**
     * No cursor is rendered.
     */
    val none = this ~~ "none"
    /**
     * A context menu is available under the cursor.
     */
    val `context-menu` = this ~~ "context-menu"
    /**
     * Indicating help is available.
     */
    val help = this ~~ "help"
    /**
     * E.g. used when hovering over links, typically a hand.
     */
    val pointer = this ~~ "pointer"
    /**
     * The program is busy in the background but the user can still interact
     * with the interface (unlike for wait).
     */
    val progress = this ~~ "progress"
    /**
     * The program is busy (sometimes an hourglass or a watch).
     */
    val cssWait = this ~~ "wait"
    /**
     * Indicating that cells can be selected.
     */
    val cell = this ~~ "cell"
    /**
     * Cross cursor, often used to indicate selection in a bitmap.
     */
    val crosshair = this ~~ "crosshair"
    /**
     * Indicating text can be selected, typically an I-beam.
     */
    val text = this ~~ "text"
    /**
     * Indicating that vertical text can be selected, typically a sideways I-beam
     */
    val `vertical-text` = this ~~ "vertical-text"
    /**
     * Indicating an alias or shortcut is to be created.
     */
    val alias = this ~~ "alias"
    /**
     * Indicating that something can be copied
     */
    val copy = this ~~ "copy"
    /**
     * The hoevered object may be moved.
     */
    val move = this ~~ "move"
    /**
     * Cursor showing that a drop is not allowed at the current location.
     */
    val `no-drop` = this ~~ "no-drop"
    /**
     * Cursor showing that something cannot be done.
     */
    val `not-allowed` = this ~~ "not-allowed"
    /**
     * Cursor showing that something can be scrolled in any direction (panned).
     */
    val `all-scroll` = this ~~ "all-scroll"
    /**
     * The item/column can be resized horizontally. Often rendered as arrows
     * pointing left and right with a vertical separating.
     */
    val `col-resize` = this ~~ "col-resize"
    /**
     * The item/row can be resized vertically. Often rendered as arrows pointing
     * up and down with a horizontal bar separating them.
     */
    val `row-resize` = this ~~ "row-resize"
    /**
     * The top edge is to be moved.
     */
    val `n-resize` = this ~~ "n-resize"
    /**
     * The right edge is to be moved.
     */
    val `e-resize` = this ~~ "e-resize"
    /**
     * The bottom edge is to be moved.
     */
    val `s-resize` = this ~~ "s-resize"
    /**
     * The left edge is to be moved.
     */
    val `w-resize` = this ~~ "w-resize"
    /**
     * The top-right corner is to be moved.
     */
    val `ne-resize` = this ~~ "ne-resize"
    /**
     * The top-left corner is to be moved.
     */
    val `nw-resize` = this ~~ "nw-resize"
    /**
     * The bottom-right corner is to be moved.
     */
    val `se-resize` = this ~~ "se-resize"
    /**
     * The bottom-left corner is to be moved.
     */
    val `sw-resize` = this ~~ "sw-resize"

    val `ew-resize` = this ~~ "ew-resize"
    val `ns-resize` = this ~~ "ns-resize"
    val `nesw-resize` = this ~~ "nesw-resize"
    val `nwse-resize` = this ~~ "nwse-resize"

    /**
     * Indicates that something can be zoomed (magnified) in.
     */
    val `zoom-in` = this ~~ "zoom-in"
    /**
     * Indicates that something can be zoomed (magnified) out.
     */
    val `zoom-out` = this ~~ "zoom-out"
    /**
     * Indicates that something can be grabbed (dragged to be moved).
     */
    val grab = this ~~ "grab"
    /**
     * Indicates that something can be grabbed (dragged to be moved).
     */
    val grabbing = this ~~ "grabbing"
  }

  /**
   * The list-style-position CSS property specifies the position of the marker
   * box in the principal block box. It is often more convenient to use the
   * shortcut list-style.
   */
  object listStylePosition extends FixedStyle("listStylePosition", "list-style-position"){
    /**
     * The marker box is outside the principal block box.
     */
    val outside= this ~~ "outside"
    /**
     * The marker box is the first inline box in the principal block box, after
     * which the element's content flows.
     */
    val inside = this ~~ "inside"
  }
  object wordWrap extends FixedStyle("wordWrap", "word-wrap"){
    /**
     * Indicates that lines may only break at normal word break points.
     */
    val normal = this ~~ "normal"
    /**
     * Indicates that normally unbreakable words may be broken at arbitrary
     * points if there are no otherwise acceptable break points in the line.
     */
    val `break-word` = this ~~ "break-word"
  }

  /**
   * The border-top-style CSS property sets the line style of the top border of a box.
   */
  object borderTopStyle
    extends FixedStyle("borderTopStyle", "border-top-style")
    with BorderStyle



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
   */
  object opacity extends OpenStyle[Number]("opacity", "opacity")

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
   */
  object direction extends FixedStyle("direction", "direction"){
    /**
     * The initial value of direction (that is, if not otherwise specified). Text
     * and other elements go from left to right.
     */
    val ltr = this ~~ "ltr"
    /**
     * Text and other elements go from right to left
     */
    val rtl = this ~~ "rtl"
  }



  /**
   * The max-width CSS property is used to set the maximum width of a given
   * element. It prevents the used value of the width property from becoming
   * larger than the value specified for max-width.
   *
   * max-width overrides width, but min-width overrides max-width.
   */
  object maxWidth extends OpenStyle[Length]("maxWidth", "max-width")

  /**
   * The CSS color property sets the foreground color of an element's text
   * content, and its decorations. It doesn't affect any other characteristic of
   * the element; it should really be called text-color and would have been
   * named so, save for historical reasons and its appearance in CSS Level 1.
   */
  object color extends OpenStyle[Color]("color", "color"){
    val currentColor = this ~~ "currentColor"
  }

  /**
   * The clip CSS property defines what portion of an element is visible. The
   * clip property applies only to elements with position:absolute.
   */
  object clip extends FixedStyle("clip", "clip"){
    def ~(top: Length, right: Length, bottom: Length, left: Length) =
      this ~~ s"rect($top, $right, $bottom, $left)"

    def auto = this ~~ "auto"
  }

  /**
   * The border-right-width CSS property sets the width of the right border of
   * a box.
   */
  object borderRightWidth extends OpenStyle[Length]("borderRightWidth", "border-right-width"){
    val thin = this ~~ "thin"
    val medium = this ~~ "medium"
    val thick = this ~~ "thick"
  }

  /**
   * The vertical-align CSS property specifies the vertical alignment of an
   * inline or table-cell box.
   */
  object verticalAlign extends OpenStyle[Length]("verticalAlign", "vertical-align"){
    /**
     * Aligns the baseline of the element with the baseline of its parent. The
     * baseline of some replaced elements, like <textarea> is not specified by
     * the HTML specification, meaning that their behavior with this keyword may
     * change from one browser to the other.
     */
    val baseline = this ~~ "baseline"
    /**
     * Aligns the baseline of the element with the subscript-baseline of its
     * parent.
     */
    val sub = this ~~ "sub"
    /**
     * Aligns the baseline of the element with the superscript-baseline of its
     * parent.
     */
    val `super` = this ~~ "super"
    /**
     * Aligns the top of the element with the top of the parent element's font.
     */
    val `text-top` = this ~~ "text-top"
    /**
     * Aligns the bottom of the element with the bottom of the parent element's
     * font.
     */
    val `text-bottom` = this ~~ "text-bottom"
    /**
     * Aligns the middle of the element with the middle of lowercase letters in
     * the parent.
     */
    val middle = this ~~ "middle"
  }

  trait Overflow extends FixedStyle{
    /**
     * Default value. Content is not clipped, it may be rendered outside the
     * content box.
     */
    val visible = this ~~ "visible"
    /**
     * The content is clipped and no scrollbars are provided.
     */
    val hidden = this ~~ "hidden"
    /**
     * The content is clipped and desktop browsers use scrollbars, whether or
     * not any content is clipped. This avoids any problem with scrollbars
     * appearing and disappearing in a dynamic environment. Printers may print
     * overflowing content.
     */
    val scroll = this ~~ "scroll"
    /**
     * Depends on the user agent. Desktop browsers like Firefox provide
     * scrollbars if content overflows.
     */
    val auto = this ~~ "auto"
  }
  /**
   * The overflow CSS property specifies whether to clip content, render scroll
   * bars or display overflow content of a block-level element.
   */
  object overflow extends FixedStyle("overflow", "overflow") with Overflow

  /**
   * If the value is a URI value, the element pointed to by the URI is used as
   * an SVG mask.
   */
  object mask extends FixedStyle("mask", "mask"){
    val none = this ~~ "none"
    def uri(s: String) = this ~~ s"uri($s)"
  }

  /**
   * The border-left-style CSS property sets the line style of the left border
   * of a box.
   */
  object borderLeftStyle
    extends FixedStyle("borderLeftStyle", "border-left-style")
    with BorderStyle

  /**
   * he empty-cells CSS property specifies how user agents should render borders
   * and backgrounds around cells that have no visible content.
   */
  object emptyCells extends FixedStyle("emptyCells", "empty-cells"){
    /**
     * Is a keyword indicating that borders and backgrounds should be drawn like
     * in a normal cells.
     */
    val show = this ~~ "show"
    /**
     * Is a keyword indicating that no border or backgrounds should be drawn.
     */
    val hide = this ~~ "hide"
  }


  /**
   * The padding-right CSS property of an element sets the padding space
   * required on the right side of an element. The padding area is the space
   * between the content of the element and its border. Negative values are not
   * allowed.
   */
  object paddingRight extends OpenStyle[Length]("paddingRight", "padding-right")

  /**
   * The background CSS property is a shorthand for setting the individual
   * background values in a single place in the style sheet. background can be
   * used to set the values for one or more of: background-clip, background-color,
   * background-image, background-origin, background-position, background-repeat,
   * background-size, and background-attachment.
   */
  object background extends OpenStyle[String]("background", "background"){

  }

  /**
   * The box-sizing CSS property is used to alter the default CSS box model used
   * to calculate widths and heights of elements. It is possible to use this
   * property to emulate the behavior of browsers that do not correctly support
   * the CSS box model specification.
   */
  object boxSizing extends FixedStyle("boxSizing", "box-sizing"){
    /**
     * This is the default style as specified by the CSS standard. The width and
     * height properties are measured including only the content, but not the
     * border, margin, or padding.
     */
    val `content-box` = this ~~ "content-box"
    /**
     * The width and height properties include the padding and border, but not
     * the margin. This is the box model used by Internet Explorer when the
     * document is in Quirks mode.
     */
    val `border-box` = this ~~ "border-box"
  }
  /**
   * The height CSS property specifies the height of the content area of an
   * element. The content area is inside the padding, border, and margin of the
   * element.
   *
   * The min-height and max-height properties override height.
   */
  object height extends OpenStyle[Length]("height", "height"){
    /**
     * The browser will calculate and select a width for the specified element.
     */
    val auto = this ~~ "auto"
  }

  /**
   * The padding-top CSS property of an element sets the padding space required
   * on the top of an element. The padding area is the space between the content
   * of the element and its border. Contrary to margin-top values, negative
   * values of padding-top are invalid.
   */
  object paddingTop extends OpenStyle[Length]("paddingTop", "padding-top")

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
   */
  object right extends OpenStyle[Length]("right", "right"){
    val auto = this ~~ "auto"
  }



  /**
   * The border-left CSS property is a shorthand that sets the values of
   * border-left-color, border-left-style, and border-left-width. These
   * properties describe the left border of elements.
   *
   * The three values of the shorthand property can be specified in any order,
   * and one or two of them may be omitted.
   */
  object borderLeft extends FixedStyle("borderLeft", "border-left"){
    def ~ (width: Length, style: String, color: Color) = this ~~ s"$width $style $color"
  }

  /**
   * The widows CSS property defines how many minimum lines must be left on top
   * of a new page, on a paged media. In typography, a widow is the last line of
   * a paragraph appearing alone at the top of a page. Setting the widows property
   * allows to prevent widows to be left.
   *
   * On a non-paged media, like screen, the widows CSS property has no effect.
   */
  object widows extends OpenStyle[Number]("widows", "widows")

  /**
   * On block level elements, the line-height CSS property specifies the minimal
   * height of line boxes within the element.
   *
   * On non-replaced inline elements, line-height specifies the height that is
   * used in the calculation of the line box height.
   *
   * On replaced inline elements, like buttons or other input element, line-height has no effect.
   */
  object lineHeight extends OpenStyle[Length]("lineHeight", "lineheight"){
    val normal = this ~~ "normal"
  }

  /**
   * The left CSS property specifies part of the position of positioned elements.
   *
   * For absolutely positioned elements (those with position: absolute or
   * position: fixed), it specifies the distance between the left margin edge of
   * the element and the left edge of its containing block.
   */
  object left extends OpenStyle[Length]("left", "left"){
    val auto = this ~~ "auto"
  }

  /**
   * The CSS text-underline-position property specifies the position of the
   * underline which is set using the text-decoration property underline value.
   *
   * This property inherits and is not reset by the text-decoration shorthand,
   * allowing to easily set it globally for a given document.
   */
  object textUnderlinePosition extends FixedStyle("textUnderlinePosition", "text-underline-position"){
    /**
     * This keyword allows the browser to use an algorithm to choose between
     * under and alphabetic.
     */
    val auto = this ~~ "auto"
    /**
     * This keyword forces the line to be set below the alphabetic baseline, at
     * a position where it won't cross any descender. This is useful to prevent
     * chemical or mathematical formulas, which make a large use of subscripts,
     * to be illegible.
     */
    val under = this ~~ "under"
    /**
     * In vertical writing-modes, this keyword forces the line to be placed on
     * the left of the characters. In horizontal writing-modes, it is a synonym
     * of under.
     */
    val left = this ~~ "left"
    /**
     * In vertical writing-modes, this keyword forces the line to be placed on
     * the right of the characters. In horizontal writing-modes, it is a synonym
     * of under.
     */
    val right = this ~~ "right"
    val `under left` = this ~~ "under left"
    val `under right` = this ~~ "under right"
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
   */
  object display extends FixedStyle("display", "display"){
    /**
     * Turns off the display of an element (it has no effect on layout); all
     * descendant elements also have their display turned off. The document is
     * rendered as though the element did not exist.
     *
     * To render an element box's dimensions, yet have its contents be invisible,
     * see the visibility property.
     */
    val none = this ~~ "none"
    /**
     * The element generates one or more inline element boxes.
     */
    val inline = this ~~ "inline"
    /**
     * The element generates a block element box.
     */
    val block = this ~~ "block"
    /**
     * The element generates a block box for the content and a separate
     * list-item inline box.
     */
    val `list-item` = this ~~ "list-item"
    /**
     * The element generates a block element box that will be flowed with
     * surrounding content as if it were a single inline box.
     */
    val `inline-block` = this ~~ "inline-block"
    /**
     * The inline-table value does not have a direct mapping in HTML. It behaves
     * like a <table> HTML element, but as an inline box, rather than a
     * block-level box. Inside the table box is a block-level context
     */
    val `inline-table` = this ~~ "inline-table"
    /**
     * Behaves like the <table> HTML element. It defines a block-level box.
     */
    val table = this ~~ "table"
    /**
     * Behaves like the <caption> HTML element.
     */
    val `table-caption` = this ~~ "table-caption"
    /**
     * Behaves like the <td> HTML element
     */
    val `table-cell` = this ~~ "table-cell"
    /**
     * These elements behave like the corresponding <col> HTML elements.
     */
    val `table-column` = this ~~ "table-column"
    /**
     * These elements behave like the corresponding <colgroup> HTML elements.
     */
    val `table-column-group` = this ~~ "table-column-group"
    /**
     * These elements behave like the corresponding <tfoot> HTML elements
     */
    val `table-footer-group` = this ~~ "table-footer-group"
    /**
     * These elements behave like the corresponding <thead> HTML elements
     */
    val `table-header-group` = this ~~ "table-header-group"
    /**
     * Behaves like the <tr> HTML element
     */
    val `table-row` = this ~~ "table-row"
    /**
     * These elements behave like the corresponding <tbody> HTML elements
     */
    val `table-row-group` = this ~~ "table-row-group"
    /**
     * The element behaves like a block element and lays out its content according
     * to the flexbox model.
     */
    val flex = this ~~ "flex"
    /**
     * The element behaves like an inline element and lays out its content
     * according to the flexbox model.
     */
    val `inline-flex` = this ~~ "inline-flex"
  }



  /**
   * The float CSS property specifies that an element should be taken from the
   * normal flow and placed along the left or right side of its container, where
   * text and inline elements will wrap around it. A floating element is one
   * where the computed value of float is not none.
   */
  object cssFloat extends FixedStyle("cssFloat", "float"){
    /**
     * Is a keyword indicating that the element must float on the left side of
     * its containing block.
     */
    val left = this ~~ "left"
    /**
     * Is a keyword indicating that the element must float on the right side of
     * its containing block.
     */
    val right = this ~~ "right"
    /**
     * Is a keyword indicating that the element must not float
     */
    val none = this ~~ "none"
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
   */
  object fontSizeAdjust extends OpenStyle[Number]("fontSizeAdjust", "font-size-adjust")

  /**
   * The border-right-color CSS property sets the color of the right border of
   * an element. Note that in many cases the shorthand CSS properties
   * border-color or border-right are more convenient and preferable.
   */
  object borderLeftColor extends OpenStyle[Color]("borderLeftColor", "border-left-color")

  /**
   * The CSS background-image property sets one or several background images for
   * an element. The images are drawn on successive stacking context layers, with
   * the first specified being drawn as if it is the closest to the user. The
   * borders of the element are then drawn on top of them, and the background-color
   * is drawn beneath them.
   */
  object backgroundImage extends OpenStyle[String]("backgroundImage", "background-image"){

  }

  /**
   * The list-style-type CSS property specifies appearance of a list item element.
   * As it is the only one who defaults to display:list-item, this is usually a
   * <li> element, but can be any element with this display value.
   */
  object listStyleType extends FixedStyle("listStyleType", "list-style-type"){
    /**
     * No item marker is shown
     */
    val none = this ~~ "none"
    /**
     * A filled circle (default value)
     */
    val disc = this ~~ "disc"
    /**
     * A hollow circle
     */
    val circle = this ~~ "circle"
    /**
     * A filled square
     */
    val square = this ~~ "square"
    /**
     * Decimal numbers begining with 1
     */
    val decimal = this ~~ "decimal"
    /**
     * Han decimal numbers
     */
    val `cjk-decimal` = this ~~ "cjk-decimal"
    /**
     * Decimal numbers padded by initial zeros
     */
    val `decimal-leading-zero` = this ~~ "decimal-leading-zero"
    /**
     * Lowercase roman numerals
     */
    val `lower-roman` = this ~~ "lower-roman"
    /**
     * Uppercase roman numerals
     */
    val `upper-roman` = this ~~ "upper-roman"
    /**
     * Lowercase classical greek
     */
    val `lower-greek` = this ~~ "lower-greek"
    /**
     * Lowercase ASCII letters
     */
    val `lower-alpha` = this ~~ "lower-alpha"
    /**
     * Lowercase ASCII letters
     */
    val `lower-latin` = this ~~ "lower-latin"
    /**
     * Uppercase ASCII letters
     */
    val `upper-alpha` = this ~~ "upper-alpha"
    /**
     * Uppercase ASCII letters
     */
    val `upper-latin` = this ~~ "upper-latin"
    /**
     * Traditional Armenian numbering
     */
    val armenian = this ~~ "armenian"
    /**
     * Traditional Georgian numbering
     */
    val georgian = this ~~ "georgian"
    /**
     * Traditional Hebrew numbering
     */
    val hebrew = this ~~ "hebrew"
    /**
     * Japanese Hiragana
     */
    val hiragana = this ~~ "hiragana"
    /**
     * Japanese Hiragana
     *
     * Iroha is the old japanese ordering of syllabs
     */
    val `hiragana-iroha` = this ~~ "hiragana-iroha"
    /**
     * Japanese Katakana
     */
    val katakana = this ~~ "katakana"
    /**
     * Japanese Katakana
     *
     * Iroha is the old japanese ordering of syllabs
     */
    val `katakana-iroha` = this ~~ "katakana-iroha"
  }



  /**
   * The text-overflow CSS property determines how overflowed content that is
   * not displayed is signaled to the users. It can be clipped, or display an
   * ellipsis ('…', U+2026 HORIZONTAL ELLIPSIS) or a Web author-defined string.
   */
  object textOverflow extends FixedStyle("textOverflow", "text-overflow"){
    /**
     * This keyword value indicates to truncate the text at the limit of the
     * content area, therefore the truncation can happen in the middle of a
     * character. To truncate at the transition between two characters, the
     * empty string value must be used. The value clip is the default for
     * this property.
     */
    val clip = this ~~ "clip"
    /**
     * This keyword value indicates to display an ellipsis ('…', U+2026 HORIZONTAL
     * ELLIPSIS) to represent clipped text. The ellipsis is displayed inside the
     * content area, decreasing the amount of text displayed. If there is not
     * enough space to display the ellipsis, it is clipped.
     */
    val ellipsis = this ~~ "ellipsis"
  }



  /**
   * The border-bottom-color CSS property sets the color of the bottom border of
   * an element. Note that in many cases the shorthand CSS properties border-color
   * or border-bottom are more convenient and preferable.
   */
  object borderBottomColor extends OpenStyle[Color]("borderBottomColor", "border-bottom-color")

  /**
   * The z-index CSS property specifies the z-order of an element and its
   * descendants. When elements overlap, z-order determines which one covers the
   * other. An element with a larger z-index generally covers an element with a
   * lower one.
   */
  object zIndex extends OpenStyle[Int]("zIndex", "z-index"){
    /**
     * The box does not establish a new local stacking context. The stack level
     * of the generated box in the current stacking context is the same as its
     * parent's box.
     */
    val auto = this ~~ "auto"
  }

  /**
   * The position CSS property chooses alternative rules for positioning elements,
   * designed to be useful for scripted animation effects.
   */
  object position extends FixedStyle("position", "position"){
    /**
     * This keyword let the element use the normal behavior, that is it is laid
     * out in its current position in the flow.  The top, right, bottom, and left
     * properties do not apply.
     */
    val static = this ~~ "static"
    /**
     * This keyword lays out all elements as though the element were not
     * positioned, and then adjust the element's position, without changing
     * layout (and thus leaving a gap for the element where it would have been
     * had it not been positioned). The effect of position:relative on
     * table-*-group, table-row, table-column, table-cell, and table-caption
     * elements is undefined.
     */
    val relative = this ~~ "relative"
    /**
     * Do not leave space for the element. Instead, position it at a specified
     * position relative to its closest positioned ancestor or to the containing
     * block. Absolutely positioned boxes can have margins, they do not collapse
     * with any other margins.
     */
    val absolute = this ~~ "absolute"
    /**
     * Do not leave space for the element. Instead, position it at a specified
     * position relative to the screen's viewport and doesn't move when scrolled.
     * When printing, position it at that fixed position on every page.
     */
    val fixed = this ~~ "fixed"
  }

  /**
   * The list-style CSS property is a shorthand property for setting
   * list-style-type, list-style-image and list-style-position.
   */
  object listStyle extends OpenStyle[String]("listStyle", "list-style")

  /**
   * The overflow-y CSS property specifies whether to clip content, render a
   * scroll bar, or display overflow content of a block-level element, when it
   * overflows at the top and bottom edges.
   */
  object overflowY extends FixedStyle("overflowY", "overflow-y") with Overflow

  /**
   * The caption-side CSS property positions the content of a table's <caption>
   * on the specified side.
   */
  object captionSide extends FixedStyle("captionSide", "caption-side"){
    /**
     * The caption box will be above the table.
     */
    val top = this ~~ "top"
    /**
     * The caption box will be below the table.
     */
    val bottom = this ~~ "bottom"
  }

  /**
   * The border-collapse CSS property selects a table's border model. This has
   * a big influence on the look and style of the table cells.
   */
  object borderCollapse extends OpenStyle("borderCollapse", "border-collapse"){
    /**
     * Is a keyword requesting the use of the separated-border table rendering
     * model. It is the default value.
     */
    val separate = this ~~ "separate"
    /**
     * Is a keyword requesting the use of the collapsed-border table rendering
     * model.
     */
    val collapse = this ~~ "collapse"
  }

  /**
   * The box-shadow CSS property describes one or more shadow effects as a
   * comma-separated list. It allows casting a drop shadow from the frame of
   * almost any element. If a border-radius is specified on the element with a
   * box shadow, the box shadow takes on the same rounded corners. The z-ordering
   * of multiple box shadows is the same as multiple text shadows (the first
   * specified shadow is on top).
   */
  object boxShadow extends OpenStyle[String]("boxShadow", "box-shadow")
  object quotes extends FixedStyle("quotes", "quotes"){
    /**
     * The open-quote and close-quote values of the content property produce no
     * quotation marks.
     */
    val none = this ~~ "none"

    def ~(pairs: (String, String)*) = {
      this ~~ pairs.flatMap(x => Seq(x._1, x._2)).map('"' + _ + '"').mkString(" ")
    }

  }
  object tableLayout extends FixedStyle("tableLayout", "table-layout"){
    /**
     * An automatic table layout algorithm is commonly used by most browsers for
     * table layout. The width of the table and its cells depends on the content
     * thereof.
     */
    val auto = this ~~ "auto"
    /**
     * Table and column widths are set by the widths of table and col elements
     * or by the width of the first row of cells. Cells in subsequent rows do
     * not affect column widths.
     */
    val fixed = this ~~ "fixed"
  }

  /**
   * The unicode-bidi CSS property together with the direction property relates
   * to the handling of bidirectional text in a document. For example, if a block
   * of text contains both left-to-right and right-to-left text then the
   * user-agent uses a complex Unicode algorithm to decide how to display the
   * text. This property overrides this algorithm and allows the developer to
   * control the text embedding.
   */
  object unicodeBidi extends FixedStyle("unicodeBidi", "unicode-bidi"){
    /**
     * The element does not offer a additional level of embedding with respect
     * to the bidirectional algorithm. For inline elements implicit reordering
     * works across element boundaries.
     */
    val normal = this ~~ "normal"
    /**
     * If the element is inline, this value opens an additional level of
     * embedding with respect to the bidirectional algorithm. The direction of
     * this embedding level is given by the direction property.
     */
    val embed = this ~~ "embed"
    /**
     * For inline elements this creates an override. For block container elements
     * this creates an override for inline-level descendants not within another
     * block container element. This means that inside the element, reordering
     * is strictly in sequence according to the direction property; the implicit
     * part of the bidirectional algorithm is ignored.
     */
    val `bidi-override` = this ~~ "bidi-override"
  }

  /**
   * The border-bottom-width CSS property sets the width of the bottom border of
   * a box.
   */
  object borderBottomWidth extends OpenStyle[Number]("borderBottomWidth", "border-bottom-width"){
    val thin = this ~~ "thin"
    val medium = this ~~ "medium"
    val thick = this ~~ "thick"
  }

  /**
   * The background-size CSS property specifies the size of the background
   * images. The size of the image can be fully constrained or only partially in
   * order to preserve its intrinsic ratio.
   */
  object backgroundSize extends OpenStyle[Length]("backgroundSize", "background-size"){
    /**
     * The auto keyword that scales the background image in the corresponding
     * direction such that its intrinsic proportion is maintained.
     */
    val auto = this ~~ "auto"
    /**
     * This keyword specifies that the background image should be scaled to be
     * as small as possible while ensuring both its dimensions are greater than
     * or equal to the corresponding dimensions of the background positioning
     * area.
     */
    val cover = this ~~ "cover"
    /**
     * This keyword specifies that the background image should be scaled to be
     * as large as possible while ensuring both its dimensions are less than or
     * equal to the corresponding dimensions of the background positioning area.
     */
    val contain = this ~~ "contain"
  }

  /**
   * The text-decoration CSS property is used to set the text formatting to
   * underline, overline, line-through or blink.
   */
  object textDecoration extends FixedStyle("textDecoration", "text-decoration") {
    /**
     * Produces no text decoration.
     */
    val none = this ~~ "none"
    /**
     * Each line of text is underlined.
     */
    val underline = this ~~ "underline"
    /**
     * Each line of text has a line above it.
     */
    val overline = this ~~ "overline"
    /**
     * Each line of text has a line through the middle.
     */
    val `line-through` = this ~~ "line-through"
  }

  /**
   * The font-size CSS property specifies the size of the font – specifically
   * the desired height of glyphs from the font. Setting the font size may, in
   * turn, change the size of other items, since it is used to compute the value
   * of em and ex length units.
   */
  object fontSize extends OpenStyle[Length]("fontSize", "font-size"){
    val `xx-small` = this ~~ "xx-small"
    val `x-small` = this ~~ "x-small"
    val small = this ~~ "small"
    val medium = this ~~ "medium"
    val large = this ~~ "large"
    val `x-large` = this ~~ "x-large"
    val `xx-large` = this ~~ "xx-large"
    /**
     * Larger than the parent element's font size, by roughly the ratio used to
     * separate the absolute size keywords above.
     */
    val larger = this ~~ "larger"
    /**
     * Smaller than the parent element's font size, by roughly the ratio used to
     * separate the absolute size keywords above.
     */
    val smaller = this ~~ "smaller"
  }

  /**
   * The border CSS property is a shorthand property for setting the individual
   * border property values in a single place in the style sheet. border can be
   * used to set the values for one or more of: border-width, border-style,
   * border-color.
   */
  object border extends OpenStyle[String]("border", "border")

  trait PageBreak extends FixedStyle{
    /**
     * Initial value. Automatic page breaks (neither forced nor forbidden).
     */
    val auto = this ~~ "auto"
    /**
     * Always force page breaks.
     */
    val always = this ~~ "always"
    /**
     * Avoid page breaks.
     */
    val avoid = this ~~ "avoid"
    /**
     * Force page breaks so that the next page is formatted
     * as a left page.
     */
    val left = this ~~ "left"
    /**
     * Force page breaks so that the next page is formatted
     * as a right page.
     */
    val right = this ~~ "right"
  }
  /**
   * The page-break-before CSS property adjusts page breaks before the current
   * element.
   *
   * This properties applies to block elements that generate a box. It won't
   * apply on an empty <div> that won't generate a box.
   */
  object pageBreakBefore
    extends FixedStyle("pageBreakBefore", "page-break-before")
    with PageBreak

  trait BorderRadius extends FixedStyle{
    def ~(r1: String, r2: String = "") = this ~~ s"$r1 $r2"
  }

  /**
   * The border-top-right-radius CSS property sets the rounding of the top-right
   * corner of the element. The rounding can be a circle or an ellipse, or if
   * one of the value is 0 no rounding is done and the corner is square.
   */
  object borderTopRightRadius
    extends FixedStyle("borderTopRightRadius", "border-top-right-radius")
    with BorderRadius

  /**
   * The border-bottom-left-radius CSS property sets the rounding of the
   * bottom-left corner of the element. The rounding can be a circle or an
   * ellipse, or if one of the value is 0 no rounding is done and the corner is
   * square.
   */
  object borderBottomLeftRadius
    extends OpenStyle("borderBottomLeftRadius", "border-bottom-left-radius")
    with BorderRadius

  /**
   * The text-transform CSS property specifies how to capitalize an element's
   * text. It can be used to make text appear in all-uppercase or all-lowercase,
   * or with each word capitalized.
   */
  object textTransform extends FixedStyle("textTransform", "text-transform"){
    /**
     * Forces the first letter of each word to be converted to
     * uppercase. Other characters are unchanged.
     */
    val capitalize = this ~~ "capitalize"
    /**
     * Forces all characters to be converted to uppercase.
     */
    val uppercase = this ~~ "uppercase"
    /**
     * Forces all characters to be converted to lowercase.
     */
    val lowercase = this ~~ "lowercase"
    /**
     * Prevents the case of all characters from being changed
     */
    val none = this ~~ "none"
  }

  /**
   * The border-right-color CSS property sets the color of the top border of an
   * element. Note that in many cases the shorthand CSS properties border-color
   * or border-right are more convenient and preferable.
   */
  object borderRightColor extends OpenStyle[Color]("borderRightColor", "border-top-color")

  /**
   * The font-family CSS property allows for a prioritized list of font family
   * names and/or generic family names to be specified for the selected element.
   * Unlike most other CSS properties, values are separated by a comma to indicate
   * that they are alternatives. The browser will select the first font on the
   * list that is installed on the computer, or that can be downloaded using the
   * information provided by a @font-face at-rule.
   */
  object fontFamily extends OpenStyle[String]("fontFamily", "font-family")

  /**
   * The clear CSS property specifies whether an element can be next to floating
   * elements that precede it or must be moved down (cleared) below them.
   *
   * The clear property applies to both floating and non-floating elements.
   */
  object clear extends FixedStyle("clear", "clear"){
    /**
     * The element is not moved down to clear past floating elements.
     */
    val none = this ~~ "none"
    /**
     * The element is moved down to clear past left floats.
     */
    val left = this ~~ "left"
    /**
     * The element is moved down to clear past right floats.
     */
    val right = this ~~ "right"
    /**
     * The element is moved down to clear past both left and right floats.
     */
    val both = this ~~ "both"
  }

  /**
   * The content CSS property is used with the ::before and ::after pseudo-elements
   * to generate content in an element. Objects inserted using the content
   * property are anonymous replaced elements.
   */
  object content extends OpenStyle[String]("content", "content")

  /**
   * The background-clip CSS property specifies whether an element's background,
   * either the color or image, extends underneath its border.
   *
   * If there is no background image, this property has only visual effect when
   * the border has transparent regions (because of border-style) or partially
   * opaque regions; otherwise the border covers up the difference.
   */
  object backgroundClip extends FixedStyle("backgroundClip", "background-clip"){
    /**
     * The background extends to the outside edge of the border (but underneath
     * the border in z-ordering).
     */
    val `border-box` = this ~~ "border-box"
    /**
     * No background is drawn below the border (background extends to the
     * outside edge of the padding).
     */
    val `padding-box` = this ~~ "padding-box"
    /**
     * The background is painted within (clipped to) the content box.
     */
    val `content-box` = this ~~ "content-box"
  }

  /**
   * The margin-bottom CSS property of an element sets the margin space required
   * on the bottom of an element. A negative value is also allowed.
   */
  object marginBottom extends OpenStyle[Length]("marginBottom", "margin-bottom"){
    /**
     * auto is replaced by some suitable value, e.g. it can be used for
     * centering of blocks.
     */
    val auto = this ~~ "auto"
  }

  /**
   * The counter-reset CSS property is used to reset CSS Counters to a given
   * value.
   */
  object counterReset extends OpenStyle[String]("counterReset", "counter-reset")

  /**
   * The outline-width CSS property is used to set the width of the outline of
   * an element. An outline is a line that is drawn around elements, outside the
   * border edge, to make the element stand out.
   */
  object outlineWidth extends OpenStyle[Length]("outlineWidth", "outline-width"){
    /**
     * Typically 1px in desktop browsers like Firefox.
     */
    val thin = this ~~ "thin"
    /**
     * Typically 3px in desktop browsers like Firefox.
     */
    val medium = this ~~ "medium"
    /**
     * Typically 5px in desktop browsers like Firefox.
     */
    val thick = this ~~ "thick"
  }
  /**
   * The margin-right CSS property of an element sets the margin space required
   * on the bottom of an element. A negative value is also allowed.
   */
  object marginRight
    extends OpenStyle[Length]("marginRight", "margin-right")
    with MarginAuto

  trait MarginAuto extends FixedStyle{
    /**
     * auto is replaced by some suitable value, e.g. it can be used for
     * centering of blocks.
     */
    val auto = this ~~ "auto"

  }
  /**
   * The padding-left CSS property of an element sets the padding space required
   * on the left side of an element. The padding area is the space between the
   * content of the element and it's border. A negative value is not allowed.
   */
  object paddingLeft extends OpenStyle[Length]("paddingLeft", "padding-left")

  /**
   * The border-bottom CSS property is a shorthand that sets the values of
   * border-bottom-color, border-bottom-style, and border-bottom-width. These
   * properties describe the bottom border of elements.
   */
  object borderBottom extends OpenStyle[String]("borderBottom", "border-bottom")

  /**
   * The word-break CSS property is used to specify how (or if) to break lines
   * within words.
   */
  object wordBreak extends OpenStyle("wordBreak", "word-break"){
    /**
     * Use the default line break rule.
     */
    val normal = this ~~ "normal"
    /**
     * Word breaks may be inserted between any character for non-CJK
     * (Chinese/Japanese/Korean) text.
     */
    val `break-all` = this ~~ "break-all"
    /**
     * Don't allow word breaks for CJK text.  Non-CJK text behavior is same
     * as normal.
     */
    val `keep-all` = this ~~ "keep-all"
  }

  /**
   * The margin-top CSS property of an element sets the margin space required on
   * the top of an element. A negative value is also allowed.
   */
  object marginTop
    extends OpenStyle[Length]("marginTop", "margin-top")
    with MarginAuto

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
   */
  object top extends OpenStyle[Length]("top", "top"){
    /**
     * Is a keyword that represents:
     * - for absolutely positioned elements, the position the element based on
     *   the bottom property and treat height: auto as a height based on the
     *   content.
     * - for relatively positioned elements, the offset the element from its
     *   original position based on the bottom property, or if bottom is also
     *   auto, do not offset it at all.
     */
    val auto = this ~~ "auto"
  }

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
   */
  object fontWeight extends OpenStyle[Int]("fontWeight", "font-weight"){
    /**
     * Normal font weight. Same as 400.
     */
    val normal = this ~~ "normal"
    /**
     * Bold font weight. Same as 700.
     */
    val bold = this ~~ "bold"
    /**
     * One font weight lighter than the parent element (among the available
     * weights of the font).
     */
    val lighter = this ~~ "lighter"
    /**
     * One font weight darker than the parent element (among the available
     * weights of the font)
     */
    val bolder = this ~~ "bolder"

  }

  /**
   * The border-right CSS property is a shorthand that sets the values of
   * border-right-color, border-right-style, and border-right-width. These
   * properties describe the right border of elements.
   */
  object borderRight extends OpenStyle[String]("borderRight", "border-right")

  /**
   * The width CSS property specifies the width of the content area of an element.
   * The content area is inside the padding, border, and margin of the element.
   *
   * The min-width and max-width properties override width.
   */
  object width extends OpenStyle[Length]("width", "width"){
    /**
     * The browser will calculate and select a width for the specified element.
     */
    val auto = this ~~ "auto"


  }

  /**
   * The page-break-after CSS property adjusts page breaks after the current
   * element.
   */
  object pageBreakAfter
    extends FixedStyle("pageBreakAfter", "page-break-after")
    with PageBreak


  /**
   * The border-bottom-style CSS property sets the line style of the bottom
   * border of a box.
   */
  object borderBottomStyle
    extends FixedStyle("borderBottomStyle", "border-bottom-style")
    with BorderStyle

  /**
   * The padding CSS property sets the required padding space on all sides of an
   * element. The padding area is the space between the content of the element
   * and its border. Negative values are not allowed.
   *
   * The padding property is a shorthand to avoid setting each side separately
   * (padding-top, padding-right, padding-bottom, padding-left).
   */
  object padding extends OpenStyle[String]("padding", "padding")

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
   */
  object bottom extends OpenStyle[Length]("bottom", "bottom"){
    /**
     * Is a keyword that represents:
     * - for absolutely positioned elements, the position the element based on
     *   the top property and treat height: auto as a height based on the
     *   content.
     * - for relatively positioned elements, the offset the element from its
     *   original position based on the top property, or if top is also auto,
     *   do not offset it at
     */
    def auto = this ~~ "auto"
  }

  /**
   * The border-left-width CSS property sets the width of the left border of a box.
   */
  object borderLeftWidth extends OpenStyle[Length]("borderLeftWidth", "border-left-width")

  /**
   * The background-position CSS property sets the initial position, relative to
   * the background position layer defined by background-origin for each defined
   * background image.
   */
  object backgroundPosition extends OpenStyle[String]("backgroundPosition", "background-position")

  /**
   * The background-color CSS property sets the background color of an element,
   * either through a color value or the keyword transparent.
   */
  object backgroundColor extends OpenStyle[Color]("backgroundColor", "backgroundColor")

  /**
   * The page-break-inside CSS property adjusts page breaks inside the current
   * element.
   */
  object pageBreakInside extends FixedStyle("pageBreakInside", "page-break-inside"){
    /**
     * Initial value. Automatic page breaks (neither forced nor forbidden).
     */
    val auto = this ~~ "auto"
    /**
     * Avoid page breaks inside the element
     */
    val avoid = this ~~ "avoid"
  }

  /**
   * The background-origin CSS property determines the background positioning
   * area, that is the position of the origin of an image specified using the
   * background-image CSS property.
   *
   * Note that background-origin is ignored when background-attachment is fixed.
   */
  object backgroundOrigin extends OpenStyle("backgroundOrigin", "background-origin"){
    /**
     * The background extends to the outside edge of the border (but underneath
     * the border in z-ordering).
     */
    val `border-box` = this ~~ "border-box"
    /**
     * No background is drawn below the border (background extends to the
     * outside edge of the padding).
     */
    val `padding-box` = this ~~ "border-box"
    /**
     * The background is painted within (clipped to) the content box.
     */
    val `content-box` = this ~~ "content-box"
  }

  /**
   * The border-top-width CSS property sets the width of the top border of a box.
   */
  object borderTopWidth extends OpenStyle[Length]("borderTopWidth", "border-top-width")

  object outlineStyle
    extends FixedStyle("outlineStyle", "outlineStyle")
    with Outline

  /**
   * The border-top CSS property is a shorthand that sets the values of
   * border-top-color, border-top-style, and border-top-width. These
   * properties describe the top border of elements.
   */
  object borderTop extends OpenStyle[String]("borderTop", "border-top")

  /**
   * The outline-color CSS property sets the color of the outline of an element.
   * An outline is a line that is drawn around elements, outside the border edge,
   * to make the element stand out.
   */
  object outlineColor extends OpenStyle("outlineColor", "outline-color"){
    /**
     * To ensure the outline is visible, performs a color inversion of the
     * background. This makes the focus border more salient, regardless of the
     * color in the background. Note that browser are not required to support
     * it. If not, they just consider the statement as invalid
     */
    val invert = this ~~ "invert"
  }

  /**
   * The padding-bottom CSS property of an element sets the height of the padding
   * area at the bottom of an element. The padding area is the space between the
   * content of the element and it's border. Contrary to margin-bottom values,
   * negative values of padding-bottom are invalid.
   */
  object paddingBottom extends OpenStyle[Length]("paddingBottom", "padding-bottom")

  /**
   * The margin-left CSS property of an element sets the margin space required 
   * on the left side of a box associated with an element. A negative value is 
   * also allowed.
   * 
   * The vertical margins of two adjacent boxes may fuse. This is called margin 
   * collapsing.
   */
  object marginLeft
    extends OpenStyle[Length]("marginLeft", "margin-left")
    with MarginAuto

  /**
   * The font CSS property is either a shorthand property for setting font-style,
   * font-variant, font-weight, font-size, line-height and font-family, or a way
   * to set the element's font to a system font, using specific keywords.
   */
  object font extends OpenStyle[String]("font", "font")

  /**
   * The CSS outline property is a shorthand property for setting one or more of
   * the individual outline properties outline-style, outline-width and
   * outline-color in a single rule. In most cases the use of this shortcut is
   * preferable and more convenient.
   *
   * Outlines do not take up space, they are drawn above the content.
   */
  object outline extends OpenStyle[String]("outline", "outline")

  /**
   * The word-spacing CSS property specifies spacing behavior between tags and
   * words.
   */
  object wordSpacing extends OpenStyle[Length]("wordSpacing", "word-spacing"){
    /**
     * The normal inter-word space, as defined by the current font and/or the
     * browser.
     */
    val normal = this ~~ "normal"
  }

  /**
   * The max-height CSS property is used to set the maximum height of a given
   * element. It prevents the used value of the height property from becoming
   * larger than the value specified for max-height.
   *
   * max-height overrides height, but min-height overrides max-height.
   */
  object maxHeight extends OpenStyle("maxHeight", "max-height"){
    /**
     * No limit on the height of the box.
     */
    val none = this ~~ "none"
  }

  /**
   * The letter-spacing CSS property specifies spacing behavior between text
   * characters.
   */
  object letterSpacing extends OpenStyle[Length]("letterSpacing", "letter-spacing"){
    /**
     * The spacing is the normal spacing for the current font.
     */
    val normal = this ~~ "normal"
  }

  /**
   * The border-spacing CSS property specifies the distance between the borders
   * of adjacent cells (only for the separated borders model). This is equivalent
   * to the cellspacing attribute in presentational HTML, but an optional second
   * value can be used to set different horizontal and vertical spacing.
   */
  object borderSpacing extends FixedStyle("borderSpacing", "border-spacing"){
    def ~(length: Length) = this ~~ length
    def ~(horizontal: Length, vertical: Length) = this ~~ s"$horizontal $vertical"
  }

  /**
   * The background-repeat CSS property defines how background images are repeated.
   * A background image can be repeated along the horizontal axis, the vertical
   * axis, both, or not repeated at all. When the repetition of the image tiles
   * doesn't let them exactly cover the background, the way adjustments are done
   * can be controlled by the author: by default, the last image is clipped, but
   * the different tiles can instead be re-sized, or space can be inserted
   * between the tiles.
   */
  object backgroundRepeat extends OpenStyle[String]("backgroundRepeat", "background-repeat")

  /**
   * The border-radius CSS property allows Web authors to define how rounded
   * border corners are. The curve of each corner is defined using one or two
   * radii, defining its shape: circle or ellipse.
   */
  object borderRadius extends OpenStyle[String]("borderRadius", "border-radius")

  /**
   * The border-width CSS property sets the width of the border of a box. Using
   * the shorthand property border is often more convenient.
   */
  object borderWidth extends FixedStyle("borderWidth", "border-width"){

  }

  /**
   * The border-bottom-right-radius CSS property sets the rounding of the
   * bottom-right corner of the element. The rounding can be a circle or an
   * ellipse, or if one of the value is 0 no rounding is done and the corner is
   * square.
   */
  object borderBottomRightRadius
    extends OpenStyle("borderBottomRightRadius", "border-bottom-right-radius")
    with BorderRadius
  object whiteSpace extends OpenStyle("whiteSpace", "whiteSpace")
  object fontStyle extends OpenStyle("fontStyle", "fontStyle")
  object minWidth extends OpenStyle("minWidth", "minWidth")
  object stopColor extends OpenStyle("stopColor", "stopColor")

  /**
   * The border-top-left-radius CSS property sets the rounding of the
   * top-left corner of the element. The rounding can be a circle or an
   * ellipse, or if one of the value is 0 no rounding is done and the corner is
   * square.
   */
  object borderTopLeftRadius
    extends OpenStyle("borderTopLeftRadius", "border-top-left-radius")
    with BorderRadius

  /**
   * The border-color CSS property is a shorthand for setting the color of the
   * four sides of an element's border: border-top-color, border-right-color,
   * border-bottom-color, border-left-color
   */
  object borderColor extends FixedStyle("borderColor", "border-color"){
    def ~(color: Color) = this ~~ color
    def ~(horizontal: Color, vertical: Color) = this ~~ s"$horizontal $vertical"
    def ~(top: Color, vertical: Color, bottom: Color) = this ~~ s"$top $vertical $bottom"
    def ~(top: Color, right: Color, bottom: Color, left: Color) = this ~~ s"$top $right $bottom $left"
  }


  /**
   * The min-height CSS property is used to set the minimum height of a given
   * element. It prevents the used value of the height property from becoming
   * smaller than the value specified for min-height.
   *
   * The value of min-height overrides both max-height and height.
   */
  object minHeight extends OpenStyle[Length]("minHeight", "min-height")

  /**
   * The overflow-x CSS property specifies whether to clip content, render a
   * scroll bar or display overflow content of a block-level element, when it
   * overflows at the left and right edges.
   */
  object overflowX extends FixedStyle("overflowX", "overflow-x") with Overflow

  /**
   * The text-align CSS property describes how inline content like text is
   * aligned in its parent block element. text-align does not control the
   * alignment of block elements itself, only their inline content.
   */
  object textAlign extends OpenStyle("textAlign", "text-align"){
    /**
     * The same as left if direction is left-to-right and right if direction is
     * right-to-left.
     */
    val start = this ~~ "start"
    /**
     * The same as right if direction is left-to-right and left if direction is
     * right-to-left.
     */
    val end = this ~~ "end"
    /**
     * The inline contents are aligned to the left edge of the line box.
     */
    val left = this ~~ "left"
    /**
     * The inline contents are aligned to the right edge of the line box.
     */
    val right = this ~~ "right"
    /**
     * The inline contents are centered within the line box.
     */
    val center = this ~~ "center"
    /**
     * The text is justified. Text should line up their left and right edges to
     * the left and right content edges of the paragraph.
     */
    val justify = this ~~ "justify"
  }

  /**
   * The margin CSS property sets the margin for all four sides. It is a
   * shorthand to avoid setting each side separately with the other margin
   * properties: margin-top, margin-right, margin-bottom and margin-left.
   *
   * Negative values are also allowed.
   */
  object margin extends FixedStyle("margin", "margin"){
    /**
     * auto is replaced by some suitable value, e.g. it can be used for
     * centering of blocks.
     */
    val auto = this ~~ "auto"
    def ~(allSides: Length) = this ~~ allSides
    def ~(vertical: Length, horizontal: Length) = this ~~ s"$vertical $horizontal"
    def ~(top: Length, horizontal: Length, bottom: Length) = this ~~ s"$top $horizontal $bottom"
    def ~(top: Length, right: Length, bottom: Length, left: Length) = this ~~ s"$top $right $bottom $left"
  }

  /**
   * The animation-fill-mode CSS property specifies how a CSS animation should
   * apply styles to its target before and after it is executing.
   */
  object animationFillMode extends OpenStyle[String]("animationFillMode", "animation-fill-mode")

  /**
   * The animation-iteration-count CSS property defines the number of times an
   * animation cycle should be played before stopping.
   */
  object animationIterationCount extends OpenStyle[String]("animationIterationCount", "animation-iteration-count")

  /**
   * The text-shadow CSS property adds shadows to text. It accepts a comma-separated
   * list of shadows to be applied to the text and text-decorations of the element.
   *
   * Each shadow is specified as an offset from the text, along with optional
   * color and blur radius values.
   *
   * Multiple shadows are applied front-to-back, with the first-specified shadow
   * on top.
   */
  object textShadow extends OpenStyle[String]("textShadow", "text-shadow"){
    /**
     * No shadow; this is the default.
     */
    val none = this ~~ "none"
  }

  /**
   * The CSS backface-visibility property determines whether or not the back
   * face of the element is visible when facing the user. The back face of an
   * element always is a transparent background, letting, when visible, a mirror
   * image of the front face be displayed.
   */
  object backfaceVisibility extends OpenStyle("backfaceVisibility", "backface-visibility"){
    /**
     * The back face is visible.
     */
    val visible = this ~~ "visible"
    /**
     * The back face is not visible.
     */
    val hidden = this ~~ "hidden"
  }

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
   */
  object animationDelay extends FixedStyle("animationDelay", "animation-delay"){
    def ~(times: Time*) = this ~~ times.mkString(", ")
  }

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
   */
  object animationTimingFunction extends OpenStyle[String]("animationTimingFunction", "animation-timing-function")

  /**
   * The column-width CSS property suggests an optimal column width. This is not
   * a absolute value but a mere hint. Browser will adjust the width of the
   * column around that suggested value, allowing to achieve scalable designs
   * that fit different screen size. Especially in presence of the column-count
   * CSS property which has precedence, to set an exact column width, all length
   * values must be specified. In horizontal text these are width, column-width,
   * column-gap, and column-rule-width
   */
  object columnWidth extends OpenStyle[Length]("columnWidth", "column-width"){
    /**
     * Is a keyword indicating that the width of the column should be determined
     * by other CSS properties, like column-count.
     */
    val auto = this ~~ "auto"
  }

  /**
   * The column-rule-color CSS property lets you set the color of the rule drawn
   * between columns in multi-column layouts.
   */
  object columnRuleColor extends OpenStyle[Color]("columnRuleColor", "column-rule-color")

  /**
   * The column-rule-width CSS property lets you set the width of the rule drawn
   * between columns in multi-column layouts.
   */
  object columnRuleWidth extends OpenStyle[Length]("columnRuleWidth", "column-rule-width"){
    val thin = this ~~ "thin"
    val medium = this ~~ "medium"
    val thick = this ~~ "thick"
  }

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
   */
  object transitionDelay extends FixedStyle("transitionDelay", "transition-delay"){
    def ~(times: Time*) = this ~~ times.mkString(", ")
  }

  /**
   * The CSS transition property is a shorthand property for transition-property,
   * transition-duration, transition-timing-function, and transition-delay. It
   * allows to define the transition between two states of an element. Different
   * states may be defined using pseudo-classes like :hover or :active or
   * dynamically set using JavaScript.
   */
  object transition extends OpenStyle[String]("transition", "transition")

  /**
   * The column-rule-style CSS property lets you set the style of the rule drawn
   * between columns in multi-column layouts.
   */
  object columnRuleStyle
    extends OpenStyle("columnRuleStyle", "column-rule-style")
    with Outline{
    val hidden = this ~~ "hidden"
  }

  /**
   * The animation CSS property is a shorthand property for animation-name,
   * animation-duration, animation-timing-function, animation-delay,
   * animation-iteration-count and animation-direction.
   */
  object animation extends OpenStyle[String]("animation", "animation")

  /**
   * The CSS transform property lets you modify the coordinate space of the CSS
   * visual formatting model. Using it, elements can be translated, rotated,
   * scaled, and skewed according to the values set.
   *
   * If the property has a value different than none, a stacking context will be
   * created. In that case the object will act as a containing block for
   * position: fixed elements that it contains.
   */
  object transform extends OpenStyle[String]("transform", "transform")

  /**
   * The CSS transition-timing-function property is used to describe how the
   * intermediate values of the CSS properties being affected by a transition
   * effect are calculated. This in essence lets you establish an acceleration
   * curve, so that the speed of the transition can vary over its duration.
   */
  object transitionTimingFunction extends OpenStyle[String]("transitionTimingFunction", "transition-timing-function")

  /**
   * The animation-play-state CSS property determines whether an animation is
   * running or paused. You can query this property's value to determine whether
   * or not the animation is currently running; in addition, you can set its
   * value to pause and resume playback of an animation.
   *
   * Resuming a paused animation will start the animation from where it left off
   * at the time it was paused, rather than starting over from the beginning of
   * the animation sequence.
   */
  object animationPlayState extends OpenStyle[String]("animationPlayState", "animation-play-state")

  /**
   * The transform-origin CSS property lets you modify the origin for
   * transformations of an element. For example, the transform-origin of the
   * rotate() function is the centre of rotation. (This property is applied by
   * first translating the element by the negated value of the property, then
   * applying the element's transform, then translating by the property value.)
   *
   * Not explicitely set values are reset to their corresponding values.
   */
  object transformOrigin extends OpenStyle[String]("transformOrigin", "transform-origin")

  /**
   * The column-gap CSS property sets the size of the gap between columns for
   * elements which are specified to display as a multi-column element.
   */
  object columnGap extends OpenStyle[Length]("columnGap", "column-gap"){
    /**
     * use the browser-defined default spacing between columns. The specification,
     * and most modern browsers follow it, recommends this keyword to be equal
     * to a length of 1em.
     */
    val normal = this ~~ "normal"
  }

  /**
   * The transition-property CSS property is used to specify the names of CSS
   * properties to which a transition effect should be applied.
   */
  object transitionProperty extends OpenStyle[String]("transitionProperty", "transition-property")

  /**
   * The font-feature-settings CSS property allows control over advanced
   * typographic features in OpenType fonts.
   */
  object fontFeatureSettings extends OpenStyle[String]("fontFeatureSettings", "font-feature-settings")

  /**
   * The perspective CSS property determines the distance between the z=0 plane
   * and the user in order to give to the 3D-positioned element some perspective.
   * Each 3D element with z>0 becomes larger; each 3D-element with z<0 becomes
   * smaller. The strength of the effect is determined by the value of this
   * property.
   */
  object perspective extends OpenStyle[Length]("perspective", "perspective"){
    /**
     * No perspective transform has to be appied
     */
    val none = this ~~ "none"
  }

  /**
   * The animation-direction CSS property indicates whether the animation should
   * play in reverse on alternate cycles.
   */
  object animationDirection extends OpenStyle[String]("animationDirection", "animation-direction")

  /**
   * The animation-duration CSS property specifies the length of time that an
   * animation should take to complete one cycle.
   *
   * A value of 0s, which is the default value, indicates that no animation should
   * occur.
   */
  object animationDuration extends OpenStyle("animationDuration", "animation-duration")

  /**
   * The animation-name CSS property specifies a list of animations that should
   * be applied to the selected element. Each name indicates a @keyframes at-rule
   * that defines the property values for the animation sequence.
   */
  object animationName extends OpenStyle[String]("animationName", "animation-name")

  /**
   * In multi-column layouts, the column-rule CSS property specifies a straight
   * line, or "rule", to be drawn between each column. It is a convenient
   * shorthand to avoid setting each of the individual column-rule-* properties
   * separately : column-rule-width, column-rule-style and column-rule-color.
   */
  object columnRule extends OpenStyle[String]("columnRule", "column-rule")

  /**
   * The column-fill CSS property controls how contents are partitioned into
   * columns. Contents are either balanced, which means that contents in all
   * columns will have the same height or, when using auto, just take up the
   * room the content needs.
   */
  object columnFill extends FixedStyle("columnFill", "column-fill"){
    /**
     * Is a keyword indicating that columns are filled sequentially.
     */
    val auto = this ~~ "auto"

    /**
     * Is a keyword indicating that content is equally divided between columns.
     */
    val balance = this ~~ "balance"
  }

  /**
   * The perspective-origin CSS property determines the position the viewer is
   * looking at. It is used as the vanishing point by the perspective property.
   */
  object perspectiveOrigin extends OpenStyle[String]("perspectiveOrigin", "perspective-origin")

  /**
   * The columns CSS property is a shorthand property allowing to set both the
   * column-width and the column-count properties at the same time.
   */
  object columns extends FixedStyle("columns", "columns"){
    def ~(number: Int) = this ~~ number.toString
    def ~(number: Int, width: Length) = this ~~ s"$number $width"
  }

  /**
   * The column-span CSS property makes it possible for an element to span across
   * all columns when its value is set to all. An element that spans more than
   * one column is called a spanning element.
   */
  object columnSpan extends OpenStyle("columnSpan", "column-span"){
    /**
     * The element does not span multiple columns.
     */
    val none = this ~~ "none"
    /**
     * The element spans across all columns. Content in the normal flow that
     * appears before the element is automatically balanced across all columns
     * before the element appears. The element establishes a new block formatting
     * context.
     */
    val all = this ~~ "all"
  }

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
   */
  object transitionDuration extends OpenStyle("transitionDuration", "transition-duration"){
    def ~(times: Time*) = this ~~ times.mkString(", ")
  }

  /**
   * The column-count CSS property describes the number of columns of the element.
   */
  object columnCount extends OpenStyle[Int]("columnCount", "column-count"){
    /**
     * Is a keyword indicating that the number of columns should be determined
     * by other CSS properties, like column-width.
     */
    val auto = this ~~ "auto"
  }

  /**
   * The transform-style CSS property determines if the children of the element
   * are positioned in the 3D-space or are flattened in the plane of the element.
   */
  object transformStyle extends FixedStyle("transformStyle", "transform-style"){
    /**
     * Indicates that the children of the element should be positioned in the
     * 3D-space.
     */
    val `preserve-3d` = this ~~ "preserve-3d"
    /**
     * Indicates that the children of the element are lying in the plane of the
     * element itself.
     */
    val flat = this ~~ "flat"
  }
}