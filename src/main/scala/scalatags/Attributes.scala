package scalatags

class Attr(k: String){
  def ~(v: String) = this -> v
  override def toString = k
}
trait Attributes{
  implicit class AttrTagger(kv: (Attr, String)) extends Mods{
    def modify(tag: HtmlTag) = {
      val (k, v) = kv
      tag.copy(attrs = tag.attrs.updated(k.toString, v))
    }
  }

  def attr(s: String): Attr = new Attr(s)

  val href = attr("href")
  val action = attr("action")
  val id = attr("id")
  val target = attr("target")
  val name = attr("name")
  val alt = attr("alt")

  val onblur = attr("onblur")
  val onchange = attr("onchange")
  val onclick = attr("onclick")
  val onfocus = attr("onfocus")
  val onkeydown = attr("onkeydown")
  val onload = attr("onload")
  val onmousedown = attr("onmousedown")
  val onmousemove = attr("onmousemove")
  val onmouseout = attr("onmouseout")
  val onmouseover = attr("onmouseover")
  val onmouseup = attr("onmouseup")
  val onreset = attr("onreset")
  val onselect = attr("onselect")
  val onsubmit = attr("onsubmit")

  val rel = attr("rel")
  val rev = attr("rev")
  val src = attr("src")
  val style = attr("style")
  val title = attr("title")
  val `type` = attr("type")
  val xmlns = attr("xmlns")
  val `class` = attr("class")
  val placeholder = attr("placeholder")
  val value = attr("value")

  val accept = attr("accept")
  val autocomplete = attr("autocomplete")
  val autofocus = attr("autofocus")
  val charset = attr("charset")
  val disabled = attr("disabled")
  val `for` = attr("for")
  val readonly = attr("readonly")
  val required = attr("required")
  val rows = attr("rows")
  val cols = attr("cols")
  val size = attr("size")
  val tabindex = attr("tabindex")
}

