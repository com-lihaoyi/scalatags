
package scalatags
package jsdom
import acyclic.file
import org.scalajs.dom
import scalatags.generic.Util



trait Tags extends generic.Tags[dom.Element, dom.Element, dom.Node]{

  // Root Element
  
  val html = "html".tag[dom.HTMLHtmlElement]

  // Document Metadata
  
  val head = "head".tag[dom.HTMLHeadElement]

  
  val base = "base".voidTag[dom.HTMLBaseElement]
  
  val link = "link".voidTag[dom.HTMLLinkElement]
  
  val meta = "meta".voidTag[dom.HTMLMetaElement]


  // Scripting
  
  val script = "script".tag[dom.HTMLScriptElement]


  // Sections
  
  val body = "body".tag[dom.HTMLBodyElement]

  
  val h1 = "h1".tag[dom.HTMLHeadingElement]
  
  val h2 = "h2".tag[dom.HTMLHeadingElement]
  
  val h3 = "h3".tag[dom.HTMLHeadingElement]
  
  val h4 = "h4".tag[dom.HTMLHeadingElement]
  
  val h5 = "h5".tag[dom.HTMLHeadingElement]
  
  val h6 = "h6".tag[dom.HTMLHeadingElement]
  
  val header = "header".tag[dom.HTMLElement]
  
  val footer = "footer".tag[dom.HTMLElement]


  // Grouping content
  
  val p = "p".tag[dom.HTMLParagraphElement]
  
  val hr = "hr".voidTag[dom.HTMLHRElement]
  
  val pre = "pre".tag[dom.HTMLPreElement]
  
  val blockquote = "blockquote".tag[dom.HTMLQuoteElement]
  
  val ol = "ol".tag[dom.HTMLOListElement]
  
  val ul = "ul".tag[dom.HTMLUListElement]
  
  val li = "li".tag[dom.HTMLLIElement]
  
  val dl = "dl".tag[dom.HTMLDListElement]
  
  val dt = "dl".tag[dom.HTMLDTElement]
  
  val dd = "dd".tag[dom.HTMLDDElement]
  
  val figure = "figure".tag[dom.HTMLElement]
  
  val figcaption = "figcaption".tag[dom.HTMLElement]
  
  val div = "div".tag[dom.HTMLDivElement]

  // Text-level semantics
  
  val a = "a".tag[dom.HTMLAnchorElement]
  
  val em = "em".tag[dom.HTMLElement]
  
  val strong = "strong".tag[dom.HTMLElement]
  
  val small = "small".tag[dom.HTMLElement]
  
  val s = "s".tag[dom.HTMLElement]
  
  val cite = "cite".tag[dom.HTMLElement]

  
  val code = "code".tag[dom.HTMLElement]

  
  val sub = "sub".tag[dom.HTMLElement]
  
  val sup = "sup".tag[dom.HTMLElement]
  
  val i = "i".tag[dom.HTMLElement]
  
  val b = "b".tag[dom.HTMLElement]
  
  val u = "u".tag[dom.HTMLElement]

  
  val span = "span".tag[dom.HTMLSpanElement]
  
  val br = "br".voidTag[dom.HTMLBRElement]
  
  val wbr = "wbr".voidTag[dom.HTMLElement]

  // Edits
  
  val ins = "ins".tag[dom.HTMLModElement]
  
  val del = "del".tag[dom.HTMLModElement]

  // Embedded content
  
  val img = "img".voidTag[dom.HTMLImageElement]
  
  val iframe = "iframe".tag[dom.HTMLIFrameElement]
  
  val embed = "embed".voidTag[dom.HTMLEmbedElement]
  
  val `object` = "object".tag[dom.HTMLObjectElement]
  
  val param = "param".voidTag[dom.HTMLParamElement]
  
  val video = "video".tag[dom.HTMLVideoElement]
  
  val audio = "audio".tag[dom.HTMLAudioElement]
  
  val source = "source".voidTag[dom.HTMLSourceElement]
  
  val track = "track".voidTag[dom.HTMLTrackElement]
  
  val canvas = "canvas".tag[dom.HTMLCanvasElement]
  
  val map = "map".tag[dom.HTMLMapElement]
  
  val area = "area".voidTag[dom.HTMLAreaElement]


  // Tabular data
  
  val table = "table".tag[dom.HTMLTableElement]
  
  val caption = "caption".tag[dom.HTMLTableCaptionElement]
  
  val colgroup = "colgroup".tag[dom.HTMLTableColElement]
  
  val col = "col".voidTag[dom.HTMLTableColElement]
  
  val tbody = "tbody".tag[dom.HTMLTableSectionElement]
  
  val thead = "thead".tag[dom.HTMLTableSectionElement]
  
  val tfoot = "tfoot".tag[dom.HTMLTableSectionElement]
  
  val tr = "tr".tag[dom.HTMLTableRowElement]
  
  val td = "td".tag[dom.HTMLTableCellElement]
  
  val th = "th".tag[dom.HTMLTableHeaderCellElement]

  // Forms
  
  val form = "form".tag[dom.HTMLFormElement]
  
  val fieldset = "fieldset".tag[dom.HTMLFieldSetElement]
  
  val legend = "legend".tag[dom.HTMLLegendElement]
  
  val label = "label".tag[dom.HTMLLabelElement]
  
  val input = "input".voidTag[dom.HTMLInputElement]
  
  val button = "button".tag[dom.HTMLButtonElement]
  
  val select = "select".tag[dom.HTMLSelectElement]
  
  val datalist = "datalist".tag[dom.HTMLDataListElement]
  
  val optgroup = "optgroup".tag[dom.HTMLOptGroupElement]
  
  val option = "option".tag[dom.HTMLOptionElement]
  
  val textarea = "textarea".tag[dom.HTMLTextAreaElement]
}
