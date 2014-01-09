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
 */
package scalatags

/**
 * Contains HTML tags which are used less frequently. These are not imported by
 * default to avoid namespace pollution.
 */
object Tags{
  // Document Metadata
  /**
   * Defines the title of the document, shown in a browser's title bar or on the
   * page's tab. It can only contain text and any contained tags are not
   * interpreted.
   *
   * MDN
   */
  val title = "title".tag

  /**
   * Used to write inline CSS.
   *
   *  MDN
   */
  val style = "style".tag
  // Scripting
  /**
   * Defines alternative content to display when the browser doesn't support
   * scripting.
   *
   *  MDN
   */
  val noscript = "noscript".tag

  // Sections
  /**
   * Represents a generic section of a document, i.e., a thematic grouping of
   * content, typically with a heading.
   *
   *  MDN
   */
  val section = "section".tag
  /**
   * Represents a section of a page that links to other pages or to parts within
   * the page: a section with navigation links.
   *
   *  MDN
   */
  val nav = "nav".tag
  /**
   * Defines self-contained content that could exist independently of the rest
   * of the content.
   *
   *  MDN
   */
  val article = "article".tag
  /**
   * Defines some content loosely related to the page content. If it is removed,
   * the remaining content still makes sense.
   *
   *  MDN
   */
  val aside = "aside".tag
  /**
   * Defines a section containing contact information.
   *
   *  MDN
   */
  val address = "address".tag

  /**
   * Defines the main or important content in the document. There is only one
   * main element in the document.
   *
   *  MDN
   */
  val main = "main".tag

  // Text level semantics

  /**
   * An inline quotation.
   *
   *  MDN
   */
  val q = "q".tag
  /**
   * Represents a term whose definition is contained in its nearest ancestor
   * content.
   *
   *  MDN
   */
  val dfn = "dfn".tag
  /**
   * An abbreviation or acronym; the expansion of the abbreviation can be
   * represented in the title attribute.
   *
   *  MDN
   */
  val abbr = "abbr".tag
  /**
   * Associates to its content a machine-readable equivalent.
   *
   *  MDN
   */
  val data = "data".tag
  /**
   * Represents a date and time value; the machine-readable equivalent can be
   * represented in the datetime attribetu
   *
   *  MDN
   */
  val time = "time".tag
  /**
   * Represents a variable.
   *
   *  MDN
   */
  val `var` = "var".tag
  /**
   * Represents the output of a program or a computer.
   *
   *  MDN
   */
  val sampl = "samp".tag
  /**
   * Represents user input, often from a keyboard, but not necessarily.
   *
   *  MDN
   */
  val kbd = "kbd".tag

  /**
   * Defines a mathematical formula.
   *
   *  MDN
   */
  val math = "math".tag
  /**
   * Represents text highlighted for reference purposes, that is for its
   * relevance in another context.
   *
   *  MDN
   */
  val mark = "mark".tag
  /**
   * Represents content to be marked with ruby annotations , short runs of text
   * presented alongside the text. This is often used in conjunction with East
   * Asian language where the annotations act as a guide for pronunciation, like
   * the Japanese furigana .
   *
   *  MDN
   */
  val ruby = "ruby".tag
  /**
   * Represents the text of a ruby annotation.
   *
   *  MDN
   */
  val rt = "rt".tag
  /**
   * Represents parenthesis around a ruby annotation, used to display the
   * annotation in an alternate way by browsers not supporting the standard
   * display for annotations.
   *
   *  MDN
   */
  val rp = "rp".tag
  /**
   * Represents text that must be isolated from its surrounding for bidirectional
   * text formatting. It allows embedding a span of text with a different, or
   * unknown, directionality.
   *
   *  MDN
   */
  val bdi = "bdi".tag
  /**
   * Represents the directionality of its children, in order to explicitly
   * override the Unicode bidirectional algorithm.
   *
   *  MDN
   */
  val bdo = "bdo".tag

  // Forms

  /**
   * A key-pair generator control.
   *
   *  MDN
   */
  val keygen = "keygen".tag
  /**
   * The result of a calculation
   *
   *  MDN
   */
  val output = "output".tag
  /**
   * A progress completion bar
   *
   *  MDN
   */
  val progress = "progress".tag
  /**
   * A scalar measurement within a known range.
   *
   *  MDN
   */
  val meter = "meter".tag


  // Interactive elements
  /**
   * A widget from which the user can obtain additional information
   * or controls.
   *
   *  MDN
   */
  val details = "details".tag
  /**
   * A summary , caption , or legend for a given `<details>`.
   *
   *  MDN
   */
  val summary = "summary".tag
  /**
   * A command that the user can invoke.
   *
   *  MDN
   */
  val command = "command".tag
  /**
   * A list of commands
   *
   *  MDN
   */
  val menu = "menu".tag
}

/**
 * Module containing static definitions for all the common HTML5 tags,
 * giving you nice autocomplete and error-checking by the compiler. Tags
 * which aren't on this list can be easily be created using Symbols, although
 * the syntax is slightly less nice (`"head".x` instead of just `head`).
 */
private[scalatags] trait Tags{


  // Root Element
  /**
   * Represents the root of an HTML or XHTML document. All other elements must
   * be descendants of this element.
   *
   *  MDN
   */
  val html = "html".tag

  // Document Metadata
  /**
   * Represents a collection of metadata about the document, including links to,
   * or definitions of, scripts and style sheets.
   *
   *  MDN
   */
  val head = "head".tag

  /**
   * Defines the base URL for relative URLs in the page.
   *
   *  MDN
   */
  val base = "base".tag
  /**
   * Used to link JavaScript and external CSS with the current HTML document.
   *
   *  MDN
   */
  val link = "link".tag
  /**
   * Defines metadata that can't be defined using another HTML element.
   *
   *  MDN
   */
  val meta = "meta".tag


  // Scripting
  /**
   * Defines either an internal script or a link to an external script. The
   * script language is JavaScript.
   *
   *  MDN
   */
  val script = "script".tag


  // Sections
  /**
   * Represents the content of an HTML document. There is only one `<body>`
   *   element in a document.
   *
   *  MDN
   */
  val body = "body".tag

  /**
   * Heading level 1
   *
   *  MDN
   */
  val h1 = "h1".tag
  /**
   * Heading level 2
   *
   *  MDN
   */
  val h2 = "h2".tag
  /**
   * Heading level 3
   *
   *  MDN
   */
  val h3 = "h3".tag
  /**
   * Heading level 4
   *
   *  MDN
   */
  val h4 = "h4".tag
  /**
   * Heading level 5
   *
   *  MDN
   */
  val h5 = "h5".tag
  /**
   * Heading level 6
   *
   *  MDN
   */
  val h6 = "h6".tag
  /**
   * Defines the header of a page or section. It often contains a logo, the
   * title of the Web site, and a navigational table of content.
   *
   *  MDN
   */
  val header = "header".tag
  /**
   * Defines the footer for a page or section. It often contains a copyright
   * notice, some links to legal information, or addresses to give feedback.
   *
   *  MDN
   */
  val footer = "footer".tag


  // Grouping content
  /**
   * Defines a portion that should be displayed as a paragraph.
   *
   *  MDN
   */
  val p = "p".tag
  /**
   * Represents a thematic break between paragraphs of a section or article or
   * any longer content.
   *
   *  MDN
   */
  val hr = "hr".tag
  /**
   * Indicates that its content is preformatted and that this format must be
   * preserved.
   *
   *  MDN
   */
  val pre = "pre".tag
  /**
   * Represents a content that is quoted from another source.
   *
   *  MDN
   */
  val blockquote = "blockquote".tag
  /**
   * Defines an ordered list of items.
   *
   *  MDN
   */
  val ol = "ol".tag
  /**
   * Defines an unordered list of items.
   *
   *  MDN
   */
  val ul = "ul".tag
  /**
   * Defines an item of an list.
   *
   *  MDN
   */
  val li = "li".tag
  /**
   * Defines a definition list; al ist of terms and their associated definitions.
   *
   *  MDN
   */
  val dl = "dl".tag
  /**
   * Represents a term defined by the next dd
   *
   *  MDN
   */
  val dt = "dl".tag
  /**
   * Represents the definition of the terms immediately listed before it.
   *
   *  MDN
   */
  val dd = "dd".tag
  /**
   * Represents a figure illustrated as part of the document.
   *
   *  MDN
   */
  val figure = "figure".tag
  /**
   * Represents the legend of a figure.
   *
   *  MDN
   */
  val figcaption = "figcaption".tag
  /**
   * Represents a generic container with no special meaning.
   *
   *  MDN
   */
  val div = "div".tag

  // Text-level semantics
  /**
   * Represents a hyperlink, linking to another resource.
   *
   *  MDN
   */
  val a = "a".tag
  /**
   * Represents emphasized text.
   *
   *  MDN
   */
  val em = "em".tag
  /**
   * Represents especially important text.
   *
   *  MDN
   */
  val strong = "strong".tag
  /**
   * Represents a side comment; text like a disclaimer or copyright, which is not
   * essential to the comprehension of the document.
   *
   *  MDN
   */
  val small = "small".tag
  /**
   * Strikethrough element, used for that is no longer accurate or relevant.
   *
   *  MDN
   */
  val s = "s".tag
  /**
   * Represents the title of a work being cited.
   *
   *  MDN
   */
  val cite = "cite".tag

  /**
   * Represents computer code.
   *
   *  MDN
   */
  val code = "code".tag

  /**
   * Subscript tag
   *
   *  MDN
   */
  val sub = "sub".tag
  /**
   * Superscript tag.
   *
   *  MDN
   */
  val sup = "sup".tag
  /**
   * Italicized text.
   *
   *  MDN
   */
  val i = "i".tag
  /**
   * Bold text.
   *
   *  MDN
   */
  val b = "b".tag
  /**
   * Underlined text.
   *
   *  MDN
   */
  val u = "u".tag

  /**
   * Represents text with no specific meaning. This has to be used when no other
   * text-semantic element conveys an adequate meaning, which, in this case, is
   * often brought by global attributes like class, lang, or dir.
   *
   *  MDN
   */
  val span = "span".tag
  /**
   * Represents a line break.
   *
   *  MDN
   */
  val br = "br".tag
  /**
   * Represents a line break opportunity , that is a suggested point for wrapping
   * text in order to improve readability of text split on several lines.
   *
   *  MDN
   */
  val wbr = "wbr".tag

  // Edits
  /**
   * Defines an addition to the document.
   *
   *  MDN
   */
  val ins = "ins".tag
  /**
   * Defines a removal from the document.
   *
   *  MDN
   */
  val del = "del".tag

  // Embedded content
  /**
   * Represents an image.
   *
   *  MDN
   */
  val img = "img".tag
  /**
   * Represents a nested browsing context , that is an embedded HTML document.
   *
   *  MDN
   */
  val iframe = "iframe".tag
  /**
   * Represents a integration point for an external, often non-HTML, application
   * or interactive content.
   *
   *  MDN
   */
  val embed = "embed".tag
  /**
   * Represents an external resource , which is treated as an image, an HTML
   * sub-document, or an external resource to be processed by a plug-in.
   *
   *  MDN
   */
  val `object` = "object".tag
  /**
   * Defines parameters for use by plug-ins invoked by `<object>` elements.
   *
   *  MDN
   */
  val param = "param".tag
  /**
   * Represents a video , and its associated audio files and captions, with the
   * necessary interface to play it.
   *
   *  MDN
   */
  val video = "video".tag
  /**
   * Represents a sound or an audio stream.
   *
   *  MDN
   */
  val audio = "audio".tag
  /**
   * Allows the authors to specify alternate media resources for media elements
   * like video or audio
   *
   *  MDN
   */
  val source = "source".tag
  /**
   * Allows authors to specify timed text track for media elements like video or
   * audio
   *
   *  MDN
   */
  val track = "track".tag
  /**
   * Represents a bitmap area that scripts can use to render graphics like graphs,
   * games or any visual images on the fly.
   *
   *  MDN
   */
  val canvas = "canvas".tag
  /**
   * In conjunction with area, defines an image map.
   *
   *  MDN
   */
  val map = "map".tag
  /**
   * In conjunction with map, defines an image map
   *
   *  MDN
   */
  val area = "area".tag


  // Tabular data
  /**
   * Represents data with more than one dimension.
   *
   *  MDN
   */
  val table = "table".tag
  /**
   * The title of a table.
   *
   *  MDN
   */
  val caption = "caption".tag
  /**
   * A set of columns.
   *
   *  MDN
   */
  val colgroup = "colgroup".tag
  /**
   * A single column.
   *
   *  MDN
   */
  val col = "col".tag
  /**
   * The table body.
   *
   *  MDN
   */
  val tbody = "tbody".tag
  /**
   * The table headers.
   *
   *  MDN
   */
  val thead = "thead".tag
  /**
   * The table footer.
   *
   *  MDN
   */
  val tfoot = "tfoot".tag
  /**
   * A single row in a table.
   *
   *  MDN
   */
  val tr = "tr".tag
  /**
   * A single cell in a table.
   *
   *  MDN
   */
  val td = "td".tag
  /**
   * A header cell in a table.
   *
   *  MDN
   */
  val th = "th".tag

  // Forms
  /**
   * Represents a form , consisting of controls, that can be submitted to a
   * server for processing.
   *
   *  MDN
   */
  val form = "form".tag
  /**
   * A set of fields.
   *
   *  MDN
   */
  val fieldset = "fieldset".tag
  /**
   * The caption for a fieldset.
   *
   *  MDN
   */
  val legend = "legend".tag
  /**
   * The caption of a single field
   *
   *  MDN
   */
  val label = "label".tag
  /**
   * A typed data field allowing the user to input data.
   *
   *  MDN
   */
  val input = "input".tag
  /**
   * A button
   *
   *  MDN
   */
  val button = "button".tag
  /**
   * A control that allows the user to select one of a set of options.
   *
   *  MDN
   */
  val select = "select".tag
  /**
   * A set of predefined options for other controls.
   *
   *  MDN
   */
  val datalist = "datalist".tag
  /**
   * A set of options, logically grouped.
   *
   *  MDN
   */
  val optgroup = "optgroup".tag
  /**
   * An option in a select element.
   *
   *  MDN
   */
  val option = "option".tag
  /**
   * A multiline text edit control.
   *
   *  MDN
   */
  val textarea = "textarea".tag

}
