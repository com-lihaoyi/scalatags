package scalatags


/**
 * Module containing static definitions for all the common HTML5 attributes,
 * giving you nice autocomplete and error-checking by the compiler. attributes
 * which aren't on this list can be easily be created by passing in a string
 * tuple to the `attr` function.
 *
 */
trait Attributes{ this: ScalaTags.type =>

  class Dim(val s: String)

  implicit class StringAsDim(s: String) extends Dim(s){
    assert ("\\d+px|\\d+%".r.findFirstMatchIn(s).isDefined)
  }
  implicit class IntAsDim(i: Int) extends Dim(i + "px")

  implicit class HtmlTrait[T <% STag](x: T){

    def href(v: Any) = x.attr("href" -> v)
    def action(v: Any) = x.attr("action" -> v)
    def id(v: Any) = x.attr("id" -> v)
    def link(v: Any) = x.attr("link" -> v)
    def target (v: Any) = x.attr("target" -> v)
    def name(v: Any) = x.attr("name" -> v)

    def onblur(v: Any) = x.attr("onblur" -> v)
    def onchange(v: Any) = x.attr("onchange" -> v)
    def onclick(v: Any) = x.attr("onclick" -> v)
    def onfocus(v: Any) = x.attr("onfocus" -> v)
    def onkeydown(v: Any) = x.attr("onkeypress" -> v)
    def onkeyup(v: Any) = x.attr("onkeyup" -> v)
    def onkeypress(v: Any) = x.attr("onkeypress" -> v)
    def onload(v: Any) = x.attr("onload" -> v)
    def onmousedown(v: Any) = x.attr("onmousedown" -> v)
    def onmousemove(v: Any) = x.attr("onmousemove" -> v)
    def onmouseout(v: Any) = x.attr("onmouseout" -> v)
    def onmouseover(v: Any) = x.attr("onmouseover" -> v)
    def onmouseup(v: Any) = x.attr("onmouseup" -> v)
    def onreset(v: Any) = x.attr("onreset" -> v)
    def onselect(v: Any) = x.attr("onselect" -> v)
    def onsubmit(v: Any) = x.attr("onsubmit" -> v)

    def rel(v: Any) = x.attr("rel" -> v)
    def rev(v: Any) = x.attr("rev" -> v)
    def src(v: Any) = x.attr("src" -> v)
    def style(v: Any) = x.attr("style" -> v)
    def title(v: Any) = x.attr("title" -> v)
    def ctype(v: Any) = x.attr("type" -> v)
    def xmlns(v: Any) = x.attr("xmlns" -> v)
    def cls(k: String*) = x.copy(classes = x.classes ++ k)
    def placeholder(v: Any) = x.attr("placeholder" -> v)
    def value(v: Any) = x.attr("value" -> v)

    def css(kv: (String, Any)*)= x.copy(styles = x.styles ++ kv)

    def cursor(v: Any) = css("cursor" -> v)
    def display(v: Any) = css("display" -> v)
    def float(v: Any) = css("float" -> v)
    def opacity(v: Double) = css("opacity" -> v)
    def opacity(v: Any) = css("opacity" -> v)
    def z_index(v: Any) = css("z-index" -> v)
    def clear(v: Any) = css("clear" -> v)
    def color(v: Any) = css("color" -> v)
    def position(v: Any) = css("position" -> v)
    def overflow(v: Any) = css("overflow" -> v)
    def overflow_x(v: Any) = css("overflow-y" -> v)
    def overflow_y(v: Any) = css("overflow-x" -> v)
    def vertical_align(v: Any) = css("vertical-align" -> v)
    def visibility(v: Any) = css("visibility" -> v)
    def white_space(v: Any) = css("white-space" -> v)

    def top(v: Dim) = css("top" -> v.s)
    def left(v: Dim) = css("left" -> v.s)
    def bottom(v: Dim) = css("bottom" -> v.s)
    def right(v: Dim) = css("right" -> v.s)

    def height(v: Dim) = css("height" -> v.s)
    def width(v: Dim) = css("width" -> v.s)

    def max_height(v: Dim) = css("max-height" -> v.s)
    def max_width(v: Dim) = css("max-width" -> v.s)

    def background(v: Any) = css("background" -> v)
    def background_attachment(v: Any) = css("background-attachment" -> v)
    def background_color(v: Any) = css("background-color" -> v)
    def background_image(v: Any) = css("background-image" -> v)
    def background_position(v: Any) = css("background_position" -> v)
    def background_repeat(v: Any) = css("background-repeat" -> v)

    def border(v: Any) = css("border" -> v)
    def border_collapse(v: Any) = css("border-collapse" -> v)
    def bordercolor(v: Any) = css("border-color" -> v)
    def border_spacing(v: Any) = css("border-spacing" -> v)
    def border_style(v: Any) = css("border-style" -> v)
    def border_width(v: Dim) = css("border-width" -> v.s)


    def margin_top(v: Dim) = css("margin-top" -> v.s)
    def margin_bottom(v: Dim) = css("margin-bottom" -> v.s)
    def margin_left(v: Dim) = css("margin-left" -> v.s)
    def margin_right(v: Dim) = css("margin-right" -> v.s)
    def margin[A <% Dim](v: A, x: A, y: A, z: A) = css("margin" -> (s"${v.s} ${x.s} ${y.s} ${z.s}"))


    def padding_top(v: Dim) = css("padding-top" -> v.s)
    def padding_bottom(v: Dim) = css("padding-bottom" -> v.s)
    def padding_left(v: Dim) = css("padding-left" -> v.s)
    def padding_right(v: Dim) = css("padding-right" -> v.s)
    def padding[A <% Dim](v: A, x: A, y: A, z: A) = css("padding" -> (s"${v.s} ${x.s} ${y.s} ${z.s}"))

    def fL = float("left")
    def fR = float("right")

    def mL(v: Dim) = margin_left(v)
    def mR(v: Dim) = margin_right(v)
    def mT(v: Dim) = margin_top(v)
    def mB(v: Dim) = margin_bottom(v)
    def h(v: Dim) = height(v)
    def w(v: Dim) = width(v)

    def center = this.margin_left("auto").margin_right("auto")  }


}