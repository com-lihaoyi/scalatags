/**
 * This file contains static bindings for the bulk of standardized HTML tags.
 * The commonly used tags get imported by default, while the less commonly used
 * tags are placed inside the `tag` object.
 *
 * Documentation marked "MDN" is thanks to Mozilla Contributors
 * at https://developer.mozilla.org/en-US/docs/Web/API and available
 * under the Creative Commons Attribution-ShareAlike v2.5 or later.
 * http://creativecommons.org/licenses/by-sa/2.5/
 *
 * Everything else is under the MIT License
 * http://opensource.org/licenses/MIT
 *
 * according to the spec (http://www.w3.org/TR/html5/syntax.html#void-elements),
 * tags that cannot have any contents can be self closing. This means:
 *
 * area, base, br, col, command, embed, hr, img, input,
 * keygen, link, meta, param, source, track, wbr
 *
 * Other tags cannot, and must have a separate closing tag even when empty.
 */
package scalatags.generic

import org.scalajs.dom



/**
 * Trait that contains the contents of the `Tags` object, so they can be mixed
 * in to other objects if needed.
 */
trait Tags[Target] extends Util[Target]{



  // Root Element
  /**
   * Represents the root of an HTML or XHTML document. All other elements must
   * be descendants of this element.
   *
   *  MDN
   */
  val html = "html".tag[dom.HTMLHtmlElement]

  // Document Metadata
  /**
   * Represents a collection of metadata about the document, including links to,
   * or definitions of, scripts and style sheets.
   *
   *  MDN
   */
  val head = "head".tag[dom.HTMLHeadElement]

  /**
   * Defines the base URL for relative URLs in the page.
   *
   *  MDN
   */
  val base = "base".voidTag[dom.HTMLBaseElement]
  /**
   * Used to link JavaScript and external CSS with the current HTML document.
   *
   *  MDN
   */
  val link = "link".voidTag[dom.HTMLLinkElement]
  /**
   * Defines metadata that can't be defined using another HTML element.
   *
   *  MDN
   */
  val meta = "meta".voidTag[dom.HTMLMetaElement]


  // Scripting
  /**
   * Defines either an internal script or a link to an external script. The
   * script language is JavaScript.
   *
   *  MDN
   */
  val script = "script".tag[dom.HTMLScriptElement]


  // Sections
  /**
   * Represents the content of an HTML document. There is only one body
   *   element in a document.
   *
   *  MDN
   */
  val body = "body".tag[dom.HTMLBodyElement]

  /**
   * Heading level 1
   *
   *  MDN
   */
  val h1 = "h1".tag[dom.HTMLHeadingElement]
  /**
   * Heading level 2
   *
   *  MDN
   */
  val h2 = "h2".tag[dom.HTMLHeadingElement]
  /**
   * Heading level 3
   *
   *  MDN
   */
  val h3 = "h3".tag[dom.HTMLHeadingElement]
  /**
   * Heading level 4
   *
   *  MDN
   */
  val h4 = "h4".tag[dom.HTMLHeadingElement]
  /**
   * Heading level 5
   *
   *  MDN
   */
  val h5 = "h5".tag[dom.HTMLHeadingElement]
  /**
   * Heading level 6
   *
   *  MDN
   */
  val h6 = "h6".tag[dom.HTMLHeadingElement]
  /**
   * Defines the header of a page or section. It often contains a logo, the
   * title of the Web site, and a navigational table of content.
   *
   *  MDN
   */
  val header = "header".tag[dom.HTMLElement]
  /**
   * Defines the footer for a page or section. It often contains a copyright
   * notice, some links to legal information, or addresses to give feedback.
   *
   *  MDN
   */
  val footer = "footer".tag[dom.HTMLElement]


  // Grouping content
  /**
   * Defines a portion that should be displayed as a paragraph.
   *
   *  MDN
   */
  val p = "p".tag[dom.HTMLParagraphElement]
  /**
   * Represents a thematic break between paragraphs of a section or article or
   * any longer content.
   *
   *  MDN
   */
  val hr = "hr".voidTag[dom.HTMLHRElement]
  /**
   * Indicates that its content is preformatted and that this format must be
   * preserved.
   *
   *  MDN
   */
  val pre = "pre".tag[dom.HTMLPreElement]
  /**
   * Represents a content that is quoted from another source.
   *
   *  MDN
   */
  val blockquote = "blockquote".tag[dom.HTMLQuoteElement]
  /**
   * Defines an ordered list of items.
   *
   *  MDN
   */
  val ol = "ol".tag[dom.HTMLOListElement]
  /**
   * Defines an unordered list of items.
   *
   *  MDN
   */
  val ul = "ul".tag[dom.HTMLUListElement]
  /**
   * Defines an item of an list.
   *
   *  MDN
   */
  val li = "li".tag[dom.HTMLLIElement]
  /**
   * Defines a definition list; al ist of terms and their associated definitions.
   *
   *  MDN
   */
  val dl = "dl".tag[dom.HTMLDListElement]
  /**
   * Represents a term defined by the next dd
   *
   *  MDN
   */
  val dt = "dl".tag[dom.HTMLDTElement]
  /**
   * Represents the definition of the terms immediately listed before it.
   *
   *  MDN
   */
  val dd = "dd".tag[dom.HTMLDDElement]
  /**
   * Represents a figure illustrated as part of the document.
   *
   *  MDN
   */
  val figure = "figure".tag[dom.HTMLElement]
  /**
   * Represents the legend of a figure.
   *
   *  MDN
   */
  val figcaption = "figcaption".tag[dom.HTMLElement]
  /**
   * Represents a generic container with no special meaning.
   *
   *  MDN
   */
  val div = "div".tag[dom.HTMLDivElement]

  // Text-level semantics
  /**
   * Represents a hyperlink, linking to another resource.
   *
   *  MDN
   */
  val a = "a".tag[dom.HTMLAnchorElement]
  /**
   * Represents emphasized text.
   *
   *  MDN
   */
  val em = "em".tag[dom.HTMLElement]
  /**
   * Represents especially important text.
   *
   *  MDN
   */
  val strong = "strong".tag[dom.HTMLElement]
  /**
   * Represents a side comment; text like a disclaimer or copyright, which is not
   * essential to the comprehension of the document.
   *
   *  MDN
   */
  val small = "small".tag[dom.HTMLElement]
  /**
   * Strikethrough element, used for that is no longer accurate or relevant.
   *
   *  MDN
   */
  val s = "s".tag[dom.HTMLElement]
  /**
   * Represents the title of a work being cited.
   *
   *  MDN
   */
  val cite = "cite".tag[dom.HTMLElement]

  /**
   * Represents computer code.
   *
   *  MDN
   */
  val code = "code".tag[dom.HTMLElement]

  /**
   * Subscript tag
   *
   *  MDN
   */
  val sub = "sub".tag[dom.HTMLElement]
  /**
   * Superscript tag.
   *
   *  MDN
   */
  val sup = "sup".tag[dom.HTMLElement]
  /**
   * Italicized text.
   *
   *  MDN
   */
  val i = "i".tag[dom.HTMLElement]
  /**
   * Bold text.
   *
   *  MDN
   */
  val b = "b".tag[dom.HTMLElement]
  /**
   * Underlined text.
   *
   *  MDN
   */
  val u = "u".tag[dom.HTMLElement]

  /**
   * Represents text with no specific meaning. This has to be used when no other
   * text-semantic element conveys an adequate meaning, which, in this case, is
   * often brought by global attributes like class, lang, or dir.
   *
   *  MDN
   */
  val span = "span".tag[dom.HTMLSpanElement]
  /**
   * Represents a line break.
   *
   *  MDN
   */
  val br = "br".voidTag[dom.HTMLBRElement]
  /**
   * Represents a line break opportunity, that is a suggested point for wrapping
   * text in order to improve readability of text split on several lines.
   *
   *  MDN
   */
  val wbr = "wbr".voidTag[dom.HTMLElement]

  // Edits
  /**
   * Defines an addition to the document.
   *
   *  MDN
   */
  val ins = "ins".tag[dom.HTMLModElement]
  /**
   * Defines a removal from the document.
   *
   *  MDN
   */
  val del = "del".tag[dom.HTMLModElement]

  // Embedded content
  /**
   * Represents an image.
   *
   *  MDN
   */
  val img = "img".voidTag[dom.HTMLImageElement]
  /**
   * Represents a nested browsing context, that is an embedded HTML document.
   *
   *  MDN
   */
  val iframe = "iframe".tag[dom.HTMLIFrameElement]
  /**
   * Represents a integration point for an external, often non-HTML, application
   * or interactive content.
   *
   *  MDN
   */
  val embed = "embed".voidTag[dom.HTMLEmbedElement]
  /**
   * Represents an external resource, which is treated as an image, an HTML
   * sub-document, or an external resource to be processed by a plug-in.
   *
   *  MDN
   */
  val `object` = "object".tag[dom.HTMLObjectElement]
  /**
   * Defines parameters for use by plug-ins invoked by object elements.
   *
   *  MDN
   */
  val param = "param".voidTag[dom.HTMLParamElement]
  /**
   * Represents a video, and its associated audio files and captions, with the
   * necessary interface to play it.
   *
   *  MDN
   */
  val video = "video".tag[dom.HTMLVideoElement]
  /**
   * Represents a sound or an audio stream.
   *
   *  MDN
   */
  val audio = "audio".tag[dom.HTMLAudioElement]
  /**
   * Allows the authors to specify alternate media resources for media elements
   * like video or audio
   *
   *  MDN
   */
  val source = "source".voidTag[dom.HTMLSourceElement]
  /**
   * Allows authors to specify timed text track for media elements like video or
   * audio
   *
   *  MDN
   */
  val track = "track".voidTag[dom.HTMLTrackElement]
  /**
   * Represents a bitmap area that scripts can use to render graphics like graphs,
   * games or any visual images on the fly.
   *
   *  MDN
   */
  val canvas = "canvas".tag[dom.HTMLCanvasElement]
  /**
   * In conjunction with area, defines an image map.
   *
   *  MDN
   */
  val map = "map".tag[dom.HTMLMapElement]
  /**
   * In conjunction with map, defines an image map
   *
   *  MDN
   */
  val area = "area".voidTag[dom.HTMLAreaElement]


  // Tabular data
  /**
   * Represents data with more than one dimension.
   *
   *  MDN
   */
  val table = "table".tag[dom.HTMLTableElement]
  /**
   * The title of a table.
   *
   *  MDN
   */
  val caption = "caption".tag[dom.HTMLTableCaptionElement]
  /**
   * A set of columns.
   *
   *  MDN
   */
  val colgroup = "colgroup".tag[dom.HTMLTableColElement]
  /**
   * A single column.
   *
   *  MDN
   */
  val col = "col".voidTag[dom.HTMLTableColElement]
  /**
   * The table body.
   *
   *  MDN
   */
  val tbody = "tbody".tag[dom.HTMLTableSectionElement]
  /**
   * The table headers.
   *
   *  MDN
   */
  val thead = "thead".tag[dom.HTMLTableSectionElement]
  /**
   * The table footer.
   *
   *  MDN
   */
  val tfoot = "tfoot".tag[dom.HTMLTableSectionElement]
  /**
   * A single row in a table.
   *
   *  MDN
   */
  val tr = "tr".tag[dom.HTMLTableRowElement]
  /**
   * A single cell in a table.
   *
   *  MDN
   */
  val td = "td".tag[dom.HTMLTableCellElement]
  /**
   * A header cell in a table.
   *
   *  MDN
   */
  val th = "th".tag[dom.HTMLTableHeaderCellElement]

  // Forms
  /**
   * Represents a form, consisting of controls, that can be submitted to a
   * server for processing.
   *
   *  MDN
   */
  val form = "form".tag[dom.HTMLFormElement]
  /**
   * A set of fields.
   *
   *  MDN
   */
  val fieldset = "fieldset".tag[dom.HTMLFieldSetElement]
  /**
   * The caption for a fieldset.
   *
   *  MDN
   */
  val legend = "legend".tag[dom.HTMLLegendElement]
  /**
   * The caption of a single field
   *
   *  MDN
   */
  val label = "label".tag[dom.HTMLLabelElement]
  /**
   * A typed data field allowing the user to input data.
   *
   *  MDN
   */
  val input = "input".voidTag[dom.HTMLInputElement]
  /**
   * A button
   *
   *  MDN
   */
  val button = "button".tag[dom.HTMLButtonElement]
  /**
   * A control that allows the user to select one of a set of options.
   *
   *  MDN
   */
  val select = "select".tag[dom.HTMLSelectElement]
  /**
   * A set of predefined options for other controls.
   *
   *  MDN
   */
  val datalist = "datalist".tag[dom.HTMLDataListElement]
  /**
   * A set of options, logically grouped.
   *
   *  MDN
   */
  val optgroup = "optgroup".tag[dom.HTMLOptGroupElement]
  /**
   * An option in a select element.
   *
   *  MDN
   */
  val option = "option".tag[dom.HTMLOptionElement]
  /**
   * A multiline text edit control.
   *
   *  MDN
   */
  val textarea = "textarea".tag[dom.HTMLTextAreaElement]
}
