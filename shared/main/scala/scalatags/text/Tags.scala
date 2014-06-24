package scalatags
package text
import acyclic.file

trait Tags extends generic.Tags[Builder, String, String]{

  // Root Element
  
  val html = "html".tag

  // Document Metadata
  
  val head = "head".tag

  
  val base = "base".voidTag
  
  val link = "link".voidTag
  
  val meta = "meta".voidTag


  // Scripting
  
  val script = "script".tag


  // Sections
  
  val body = "body".tag

  
  val h1 = "h1".tag
  
  val h2 = "h2".tag
  
  val h3 = "h3".tag
  
  val h4 = "h4".tag
  
  val h5 = "h5".tag
  
  val h6 = "h6".tag
  
  val header = "header".tag
  
  val footer = "footer".tag


  // Grouping content
  
  val p = "p".tag
  
  val hr = "hr".voidTag
  
  val pre = "pre".tag
  
  val blockquote = "blockquote".tag
  
  val ol = "ol".tag
  
  val ul = "ul".tag
  
  val li = "li".tag
  
  val dl = "dl".tag
  
  val dt = "dl".tag
  
  val dd = "dd".tag
  
  val figure = "figure".tag
  
  val figcaption = "figcaption".tag
  
  val div = "div".tag

  // Text-level semantics
  
  val a = "a".tag
  
  val em = "em".tag
  
  val strong = "strong".tag
  
  val small = "small".tag
  
  val s = "s".tag
  
  val cite = "cite".tag

  
  val code = "code".tag

  
  val sub = "sub".tag
  
  val sup = "sup".tag
  
  val i = "i".tag
  
  val b = "b".tag
  
  val u = "u".tag

  
  val span = "span".tag
  
  val br = "br".voidTag
  
  val wbr = "wbr".voidTag

  // Edits
  
  val ins = "ins".tag
  
  val del = "del".tag

  // Embedded content
  
  val img = "img".voidTag
  
  val iframe = "iframe".tag
  
  val embed = "embed".voidTag
  
  val `object` = "object".tag
  
  val param = "param".voidTag
  
  val video = "video".tag
  
  val audio = "audio".tag
  
  val source = "source".voidTag
  
  val track = "track".voidTag
  
  val canvas = "canvas".tag
  
  val map = "map".tag
  
  val area = "area".voidTag


  // Tabular data
  
  val table = "table".tag
  
  val caption = "caption".tag
  
  val colgroup = "colgroup".tag
  
  val col = "col".voidTag
  
  val tbody = "tbody".tag
  
  val thead = "thead".tag
  
  val tfoot = "tfoot".tag
  
  val tr = "tr".tag
  
  val td = "td".tag
  
  val th = "th".tag

  // Forms
  
  val form = "form".tag
  
  val fieldset = "fieldset".tag
  
  val legend = "legend".tag
  
  val label = "label".tag
  
  val input = "input".voidTag
  
  val button = "button".tag
  
  val select = "select".tag
  
  val datalist = "datalist".tag
  
  val optgroup = "optgroup".tag
  
  val option = "option".tag
  
  val textarea = "textarea".tag
}
