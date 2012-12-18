package scalatags

/**
 * Module containing static definitions for all the common HTML5 tags,
 * giving you nice autocomplete and error-checking by the compiler. Tags
 * which aren't on this list can be easily be created using Symbols, although
 * the syntax is slightly less nice (`'head.x` instead of just `head`). The
 * list was taken from:
 *
 * https://developer.mozilla.org/en-US/docs/HTML/HTML5/HTML5_element_list
 */
trait HtmlTags{ this: XTags.type =>


  // Root Element
  val html = 'html.x

  // Document Metadata
  val head = 'head.x
  val title = 'title.x
  val base = 'base.x
  val link = 'link.x
  val meta = 'meta.x
  val style = 'style.x

  // Scripting
  val script = 'script.x
  val noscript = 'noscript.x

  // Sections
  val body = 'body.x
  val section = 'section.x
  val nav = 'nav.x
  val article = 'article.x
  val aside = 'aside.x
  val h1 = 'h1.x
  val h2 = 'h2.x
  val h3 = 'h3.x
  val h4 = 'h4.x
  val h5 = 'h5.x
  val h6 = 'h6.x
  val hgroup = 'hgroup.x
  val header = 'header.x
  val footer = 'footer.x
  val address = 'address.x

  // Grouping content
  val p = 'p.x
  val hr = 'hr.x
  val pre = 'pre.x
  val blockquote = 'blockquote.x
  val ol = 'ol.x
  val ul = 'ul.x
  val li = 'li.x
  val dl = 'dl.x
  val dt = 'dl.x
  val dd = 'dd.x
  val figure = 'figure.x
  val figcaption = 'figcaption.x
  val div = 'div.x

  // Text-level semantics
  val a = 'a.x
  val em = 'em.x
  val strong = 'strong.x
  val small = 'small.x
  val s = 's.x
  val cite = 'cite.x
  val q = 'q.x
  val dfn = 'dfn.x
  val abbr = 'abbr.x
  val data = 'data.x
  val time = 'time.x
  val code = 'code.x
  val `var` = 'var.x
  val sampl = 'samp.x
  val kbd = 'kbd.x
  val sub = 'sub.x
  val sup = 'sup.x
  val i = 'i.x
  val b = 'b.x
  val u = 'u.x
  val mark = 'mark.x
  val ruby = 'ruby.x
  val rt = 'rt.x
  val rp = 'rp.x
  val bdi = 'bdi.x
  val bdo = 'bdo.x
  val span = 'span.x
  val br = 'bar.x
  val wbr = 'wbr.x

  // Edits
  val ins = 'ins.x
  val del = 'del.x

  // Embedded content
  val img = 'img.x
  val iframe = 'iframe.x
  val embed = 'embed.x
  val `object` = 'object.x
  val param = 'param.x
  val video = 'video.x
  val audio = 'audio.x
  val source = 'source.x
  val track = 'track.x
  val canvas = 'canvas.x
  val map = 'map.x
  val area = 'area.x
  val svg = 'svg.x
  val math = 'math.x

  // Tabular data
  val table = 'table.x
  val caption = 'caption.x
  val colgroup = 'colgroup.x
  val col = 'col.x
  val tbody = 'tbody.x
  val thead = 'thead.x
  val tfoot = 'tfoot.x
  val tr = 'tr.x
  val td = 'td.x
  val th = 'th.x

  // Forms
  val form = 'form.x
  val fieldset = 'fieldset.x
  val legend = 'legend.x
  val label = 'label.x
  val input = 'input.x
  val button = 'button.x
  val select = 'select.x
  val datalist = 'datalist.x
  val optgroup = 'optgroup.x
  val option = 'option.x
  val textarea = 'textarea.x
  val keygen = 'keygen.x
  val output = 'output.x
  val progress = 'progress.x
  val meter = 'meter.x

  // Interactive elements
  val details = 'details.x
  val summary = 'summary.x
  val command = 'command.x
  val menu = 'menu.x
}
