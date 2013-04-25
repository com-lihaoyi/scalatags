package scalatags


/**
 * Module containing static definitions for all the common HTML5 attributes,
 * giving you nice autocomplete and error-checking by the compiler. attributes
 * which aren't on this list can be easily be created by passing in a string
 * tuple to the `attr` function.
 *
 */
trait HtmlTrait{this: HtmlTag => 

  def href(v: Any) = attr("href" -> v)
  def action(v: Any) = attr("action" -> v)
  def id(v: Any) = attr("id" -> v)
  def link(v: Any) = attr("link" -> v)
  def target (v: Any) = attr("target" -> v)
  def name(v: Any) = attr("name" -> v)

  def onblur(v: Any) = attr("onblur" -> v)
  def onchange(v: Any) = attr("onchange" -> v)
  def onclick(v: Any) = attr("onclick" -> v)
  def onfocus(v: Any) = attr("onfocus" -> v)
  def onkeydown(v: Any) = attr("onkeypress" -> v)
  def onkeyup(v: Any) = attr("onkeyup" -> v)
  def onkeypress(v: Any) = attr("onkeypress" -> v)
  def onload(v: Any) = attr("onload" -> v)
  def onmousedown(v: Any) = attr("onmousedown" -> v)
  def onmousemove(v: Any) = attr("onmousemove" -> v)
  def onmouseout(v: Any) = attr("onmouseout" -> v)
  def onmouseover(v: Any) = attr("onmouseover" -> v)
  def onmouseup(v: Any) = attr("onmouseup" -> v)
  def onreset(v: Any) = attr("onreset" -> v)
  def onselect(v: Any) = attr("onselect" -> v)
  def onsubmit(v: Any) = attr("onsubmit" -> v)

  def rel(v: Any) = attr("rel" -> v)
  def rev(v: Any) = attr("rev" -> v)
  def src(v: Any) = attr("src" -> v)
  def style(v: Any) = attr("style" -> v)
  def title(v: Any) = attr("title" -> v)
  def ctype(v: Any) = attr("type" -> v)
  def xmlns(v: Any) = attr("xmlns" -> v)
  def cls(k: Any*) = copy(classes = classes ++ k)
  def placeholder(v: Any) = attr("placeholder" -> v)
  def value(v: Any) = attr("value" -> v)

  def css(kv: (String, Any)*)= copy(styles = styles ++ kv)

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

  def top(v: Any) = css("top" -> v)
  def left(v: Any) = css("left" -> v)
  def bottom(v: Any) = css("bottom" -> v)
  def right(v: Any) = css("right" -> v)

  def height(v: Any) = css("height" -> v)
  def width(v: Any) = css("width" -> v)

  def max_height(v: Any) = css("max-height" -> v)
  def max_width(v: Any) = css("max-width" -> v)

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
  def border_width(v: Any) = css("border-width" -> v)


  def margin_top(v: Any) = css("margin-top" -> v)
  def margin_bottom(v: Any) = css("margin-bottom" -> v)
  def margin_left(v: Any) = css("margin-left" -> v)
  def margin_right(v: Any) = css("margin-right" -> v)
  def margin(v: Any, x: Any, y: Any, z: Any) = css("margin" -> (s"$v $x $y $z"))


  def padding_top(v: Any) = css("padding-top" -> v)
  def padding_bottom(v: Any) = css("padding-bottom" -> v)
  def padding_left(v: Any) = css("padding-left" -> v)
  def padding_right(v: Any) = css("padding-right" -> v)
  def padding(v: Any, x: Any, y: Any, z: Any) = css("padding" -> (s"$v $x $y $z"))

  def fL = float("left")
  def fR = float("right")

  def mL(v: Any) = margin_left(v)
  def mR(v: Any) = margin_right(v)
  def mT(v: Any) = margin_top(v)
  def mB(v: Any) = margin_bottom(v)
  def h(v: Any) = height(v)
  def w(v: Any) = width(v)

  def center = this.margin_left("auto").margin_right("auto")  
}


