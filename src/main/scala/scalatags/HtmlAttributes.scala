package scalatags


/**
 * Module containing static definitions for all the common HTML5 attributes,
 * giving you nice autocomplete and error-checking by the compiler. attributes
 * which aren't on this list can be easily be created by passing in a string
 * tuple to the `attr` function.
 *
 */
trait HtmlAttributes{ this: XTags.type =>
  class Dim(val s: String)
  implicit class StringAsDim(s: String) extends Dim(s){
    assert ("\\d+px|\\d+%".r.findFirstMatchIn(s).isDefined)
  }
  implicit class IntAsDim(i: Int) extends Dim(i + "px")

  implicit class HtmlTrait[T <% HtmlNode](x: T){

    def href(v: String) = x.attr("href" -> v)
    def action(v: String) = x.attr("action" -> v)
    def id(v: String) = x.attr("id" -> v)
    def link(v: String) = x.attr("link" -> v)
    def target (v: String) = x.attr("target" -> v)
    def name(v: String) = x.attr("name" -> v)

    def onblur(v: String) = x.attr("onblur" -> v)
    def onchange(v: String) = x.attr("onchange" -> v)
    def onclick(v: String) = x.attr("onclick" -> v)
    def onfocus(v: String) = x.attr("onfocus" -> v)
    def onkeydown(v: String) = x.attr("onkeypress" -> v)
    def onkeyup(v: String) = x.attr("onkeyup" -> v)
    def onkeypress(v: String) = x.attr("onkeypress" -> v)
    def onload(v: String) = x.attr("onload" -> v)
    def onmousedown(v: String) = x.attr("onmousedown" -> v)
    def onmousemove(v: String) = x.attr("onmousemove" -> v)
    def onmouseout(v: String) = x.attr("onmouseout" -> v)
    def onmouseover(v: String) = x.attr("onmouseover" -> v)
    def onmouseup(v: String) = x.attr("onmouseup" -> v)
    def onreset(v: String) = x.attr("onreset" -> v)
    def onselect(v: String) = x.attr("onselect" -> v)
    def onsubmit(v: String) = x.attr("onsubmit" -> v)

    def rel(v: String) = x.attr("rel" -> v)
    def rev(v: String) = x.attr("rev" -> v)
    def src(v: String) = x.attr("src" -> v)
    def style(v: String) = x.attr("style" -> v)
    def title(v: String) = x.attr("title" -> v)
    def ctype(v: String) = x.attr("type" -> v)
    def xmlns(v: String) = x.attr("xmlns" -> v)
    def cls(k: String*) = x.attr("class" -> k.toList.fold(x.attrs("class"))(_ + " " + _).trim)
    def placeholder(v: String) = x.attr("placeholder" -> v)
    def value(v: String) = x.attr("value" -> v)

    def css(kv: (String, String)*): HtmlNode = {
      x.style(
        x.attrs("style") + kv.map{case (k, v) => s"$k: $v; "}.mkString
      )
    }
    def cursor(v: String) = css("cursor" -> v)
    def display(v: String) = css("display" -> v)
    def float(v: String) = css("float" -> v)
    def opacity(v: Double) = css("opacity" -> v.toString)
    def opacity(v: String) = css("opacity" -> v)
    def z_index(v: String) = css("z-index" -> v)
    def clear(v: String) = css("clear" -> v)
    def color(v: String) = css("color" -> v)
    def position(v: String) = css("position" -> v)
    def overflow(v: String) = css("overflow" -> v)
    def vertical_align(v: String) = css("vertical-align" -> v)
    def visibility(v: String) = css("visibility" -> v)
    def white_space(v: String) = css("white-space" -> v)

    def top(v: Dim) = css("top" -> v.s)
    def left(v: Dim) = css("left" -> v.s)
    def bottom(v: Dim) = css("bottom" -> v.s)
    def right(v: Dim) = css("right" -> v.s)

    def height(v: Dim) = css("height" -> v.s)
    def width(v: Dim) = css("width" -> v.s)

    def max_height(v: Dim) = css("max-height" -> v.s)
    def max_width(v: Dim) = css("max-width" -> v.s)

    def background(v: String) = css("background" -> v)
    def background_attachment(v: String) = css("background-attachment" -> v)
    def background_color(v: String) = css("background-color" -> v)
    def background_image(v: String) = css("background-image" -> v)
    def background_position(v: String) = css("background_position" -> v)
    def background_repeat(v: String) = css("background-repeat" -> v)

    def border(v: String) = css("border" -> v)
    def border_collapse(v: String) = css("border-collapse" -> v)
    def bordercolor(v: String) = css("border-color" -> v)
    def border_spacing(v: String) = css("border-spacing" -> v)
    def border_style(v: String) = css("border-style" -> v)
    def border_width(v: Dim) = css("border-width" -> v.s)


    def margin_top(v: Dim) = css("margin-top" -> v.s)
    def margin_bottom(v: Dim) = css("margin-bottom" -> v.s)
    def margin_left(v: Dim) = css("margin-left" -> v.s)
    def margin_right(v: Dim) = css("margin-right" -> v.s)
    def margin[A <% Dim](v: A, x: A, y: A, z: A) = css("margin" -> (s"$v.s $x.s %y.s %.z.s"))


    def padding_top(v: Dim) = css("padding-top" -> v.s)
    def padding_bottom(v: Dim) = css("padding-bottom" -> v.s)
    def padding_left(v: Dim) = css("padding-left" -> v.s)
    def padding_right(v: Dim) = css("padding-right" -> v.s)
    def padding[A <% Dim](v: A, x: A, y: A, z: A) = css("padding" -> (s"$v.s $x.s %y.s %.z.s"))

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