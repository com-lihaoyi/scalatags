package scalatags

/**
 * Module containing static definitions for all the common HTML5 tags,
 * giving you nice autocomplete and error-checking by the compiler. Tags
 * which aren't on this list can be easily be created using Symbols, although
 * the syntax is slightly less nice (`"head".x` instead of just `head`).
 *
 * The list was taken from:
 *
 * https://developer.mozilla.org/en-US/docs/HTML/HTML5/HTML5_element_list
 * 
 * Documentation marked "MDN" is thanks to Mozilla Contributors
 * at https://developer.mozilla.org/en-US/docs/Web/API and available
 * under the Creative Commons Attribution-ShareAlike v2.5 or later.
 * http://creativecommons.org/licenses/by-sa/2.5/
 */
private[scalatags] trait Tags{

  /**
   * Less-often used HTML tags. These are not imported by default in order to
   * avoid polluting your namespace with mostly-unused tags. They can be accessed
   * through `tags.XXX`, or via `import tags._` if you really want them and don't
   * mind the extra namespace pollution.
   */
  object tag{
    // Document Metadata
    /**
     * Defines the title of the document, shown in a browser's title bar or on the
     * page's tab. It can only contain text and any contained tags are not
     * interpreted.
     * 
     * MDN
     */
    val title = "title".x

    /**
     * Used to write inline CSS.
     *
     *  MDN
     */
    val style = "style".x
    // Scripting
    /**
     * Defines alternative content to display when the browser doesn't support
     * scripting.
     *
     *  MDN
     */
    val noscript = "noscript".x

    // Sections
    /**
     * Represents a generic section of a document, i.e., a thematic grouping of
     * content, typically with a heading.
     *
     *  MDN
     */
    val section = "section".x
    /**
     * Represents a section of a page that links to other pages or to parts within
     * the page: a section with navigation links.
     *
     *  MDN
     */
    val nav = "nav".x
    /**
     * Defines self-contained content that could exist independently of the rest
     * of the content.
     *
     *  MDN
     */
    val article = "article".x
    /**
     * Defines some content loosely related to the page content. If it is removed,
     * the remaining content still makes sense.
     *
     *  MDN
     */
    val aside = "aside".x
    /**
     * Defines a section containing contact information.
     *
     *  MDN
     */
    val address = "address".x

    /**
     * Defines the main or important content in the document. There is only one
     * main element in the document.
     *
     *  MDN
     */
    val main = "main".x

    // Text level semantics

    /**
     * An inline quotation.
     *
     *  MDN
     */
    val q = "q".x
    /**
     * Represents a term whose definition is contained in its nearest ancestor
     * content.
     *
     *  MDN
     */
    val dfn = "dfn".x
    /**
     * An abbreviation or acronym; the expansion of the abbreviation can be
     * represented in the title attribute.
     *
     *  MDN
     */
    val abbr = "abbr".x
    /**
     * Associates to its content a machine-readable equivalent.
     *
     *  MDN
     */
    val data = "data".x
    /**
     * Represents a date and time value; the machine-readable equivalent can be
     * represented in the datetime attribetu
     *
     *  MDN
     */
    val time = "time".x
    /**
     * Represents a variable.
     *
     *  MDN
     */
    val `var` = "var".x
    /**
     * Represents the output of a program or a computer.
     *
     *  MDN
     */
    val sampl = "samp".x
    /**
     * Represents user input, often from a keyboard, but not necessarily.
     *
     *  MDN
     */
    val kbd = "kbd".x

    /**
     * Represents text highlighted for reference purposes, that is for its
     * relevance in another context.
     *
     *  MDN
     */
    val mark = "mark".x
    /**
     * Represents content to be marked with ruby annotations , short runs of text
     * presented alongside the text. This is often used in conjunction with East
     * Asian language where the annotations act as a guide for pronunciation, like
     * the Japanese furigana .
     *
     *  MDN
     */
    val ruby = "ruby".x
    /**
     * Represents the text of a ruby annotation.
     *
     *  MDN
     */
    val rt = "rt".x
    /**
     * Represents parenthesis around a ruby annotation, used to display the
     * annotation in an alternate way by browsers not supporting the standard
     * display for annotations.
     *
     *  MDN
     */
    val rp = "rp".x
    /**
     * Represents text that must be isolated from its surrounding for bidirectional
     * text formatting. It allows embedding a span of text with a different, or
     * unknown, directionality.
     *
     *  MDN
     */
    val bdi = "bdi".x
    /**
     * Represents the directionality of its children, in order to explicitly
     * override the Unicode bidirectional algorithm.
     *
     *  MDN
     */
    val bdo = "bdo".x

    // Forms

    /**
     * A key-pair generator control.
     *
     *  MDN
     */
    val keygen = "keygen".x
    /**
     * The result of a calculation
     *
     *  MDN
     */
    val output = "output".x
    /**
     * A progress completion bar
     *
     *  MDN
     */
    val progress = "progress".x
    /**
     * A scalar measurement within a known range.
     *
     *  MDN
     */
    val meter = "meter".x


    // Interactive elements
    /**
     * A widget from which the user can obtain additional information
     * or controls.
     *
     *  MDN
     */
    val details = "details".x
    /**
     * A summary , caption , or legend for a given <details>.
     *
     *  MDN
     */
    val summary = "summary".x
    /**
     * A command that the user can invoke.
     *
     *  MDN
     */
    val command = "command".x
    /**
     * A list of commands
     *
     *  MDN
     */
    val menu = "menu".x
  }
  // Root Element
  /**
   * Represents the root of an HTML or XHTML document. All other elements must
   * be descendants of this element.
   *
   *  MDN
   */
  val html = "html".x

  // Document Metadata
  /**
   * Represents a collection of metadata about the document, including links to,
   * or definitions of, scripts and style sheets.
   *
   *  MDN
   */
  val head = "head".x

  /**
   * Defines the base URL for relative URLs in the page.
   *
   *  MDN
   */
  val base = "base".x
  /**
   * Used to link JavaScript and external CSS with the current HTML document.
   *
   *  MDN
   */
  val link = "link".x
  /**
   * Defines metadata that can't be defined using another HTML element.
   *
   *  MDN
   */
  val meta = "meta".x


  // Scripting
  /**
   * Defines either an internal script or a link to an external script. The
   * script language is JavaScript.
   *
   *  MDN
   */
  val script = "script".x


  // Sections
  /**
   * Represents the content of an HTML document. There is only one <body>
   *   element in a document.
   *
   *  MDN
   */
  val body = "body".x

  /**
   * Heading level 1
   *
   *  MDN
   */
  val h1 = "h1".x
  /**
   * Heading level 2
   *
   *  MDN
   */
  val h2 = "h2".x
  /**
   * Heading level 3
   *
   *  MDN
   */
  val h3 = "h3".x
  /**
   * Heading level 4
   *
   *  MDN
   */
  val h4 = "h4".x
  /**
   * Heading level 5
   *
   *  MDN
   */
  val h5 = "h5".x
  /**
   * Heading level 6
   *
   *  MDN
   */
  val h6 = "h6".x
  /**
   * Defines the header of a page or section. It often contains a logo, the
   * title of the Web site, and a navigational table of content.
   *
   *  MDN
   */
  val header = "header".x
  /**
   * Defines the footer for a page or section. It often contains a copyright
   * notice, some links to legal information, or addresses to give feedback.
   *
   *  MDN
   */
  val footer = "footer".x


  // Grouping content
  /**
   * Defines a portion that should be displayed as a paragraph.
   *
   *  MDN
   */
  val p = "p".x
  /**
   * Represents a thematic break between paragraphs of a section or article or
   * any longer content.
   *
   *  MDN
   */
  val hr = "hr".x
  /**
   * Indicates that its content is preformatted and that this format must be
   * preserved.
   *
   *  MDN
   */
  val pre = "pre".x
  /**
   * Represents a content that is quoted from another source.
   *
   *  MDN
   */
  val blockquote = "blockquote".x
  /**
   * Defines an ordered list of items.
   *
   *  MDN
   */
  val ol = "ol".x
  /**
   * Defines an unordered list of items.
   *
   *  MDN
   */
  val ul = "ul".x
  /**
   * Defines an item of an list.
   *
   *  MDN
   */
  val li = "li".x
  /**
   * Defines a definition list; al ist of terms and their associated definitions.
   *
   *  MDN
   */
  val dl = "dl".x
  /**
   * Represents a term defined by the next dd
   *
   *  MDN
   */
  val dt = "dl".x
  /**
   * Represents the definition of the terms immediately listed before it.
   *
   *  MDN
   */
  val dd = "dd".x
  /**
   * Represents a figure illustrated as part of the document.
   *
   *  MDN
   */
  val figure = "figure".x
  /**
   * Represents the legend of a figure.
   *
   *  MDN
   */
  val figcaption = "figcaption".x
  /**
   * Represents a generic container with no special meaning.
   *
   *  MDN
   */
  val div = "div".x

  // Text-level semantics
  /**
   * Represents a hyperlink, linking to another resource.
   *
   *  MDN
   */
  val a = "a".x
  /**
   * Represents emphasized text.
   *
   *  MDN
   */
  val em = "em".x
  /**
   * Represents especially important text.
   *
   *  MDN
   */
  val strong = "strong".x
  /**
   * Represents a side comment; text like a disclaimer or copyright, which is not
   * essential to the comprehension of the document.
   *
   *  MDN
   */
  val small = "small".x
  /**
   * Strikethrough element, used for that is no longer accurate or relevant.
   *
   *  MDN
   */
  val s = "s".x
  /**
   * Represents the title of a work being cited.
   *
   *  MDN
   */
  val cite = "cite".x

  /**
   * Represents computer code.
   *
   *  MDN
   */
  val code = "code".x

  /**
   * Subscript tag
   *
   *  MDN
   */
  val sub = "sub".x
  /**
   * Superscript tag.
   *
   *  MDN
   */
  val sup = "sup".x
  /**
   * Italicized text.
   *
   *  MDN
   */
  val i = "i".x
  /**
   * Bold text.
   *
   *  MDN
   */
  val b = "b".x
  /**
   * Underlined text.
   *
   *  MDN
   */
  val u = "u".x

  /**
   * Represents text with no specific meaning. This has to be used when no other
   * text-semantic element conveys an adequate meaning, which, in this case, is
   * often brought by global attributes like class, lang, or dir.
   *
   *  MDN
   */
  val span = "span".x
  /**
   * Represents a line break.
   *
   *  MDN
   */
  val br = "br".x
  /**
   * Represents a line break opportunity , that is a suggested point for wrapping
   * text in order to improve readability of text split on several lines.
   *
   *  MDN
   */
  val wbr = "wbr".x

  // Edits
  /**
   * Defines an addition to the document.
   *
   *  MDN
   */
  val ins = "ins".x
  /**
   * Defines a removal from the document.
   *
   *  MDN
   */
  val del = "del".x

  // Embedded content
  /**
   * Represents an image.
   *
   *  MDN
   */
  val img = "img".x
  /**
   * Represents a nested browsing context , that is an embedded HTML document.
   *
   *  MDN
   */
  val iframe = "iframe".x
  /**
   * Represents a integration point for an external, often non-HTML, application
   * or interactive content.
   *
   *  MDN
   */
  val embed = "embed".x
  /**
   * Represents an external resource , which is treated as an image, an HTML
   * sub-document, or an external resource to be processed by a plug-in.
   *
   *  MDN
   */
  val `object` = "object".x
  /**
   * Defines parameters for use by plug-ins invoked by <object> elements.
   *
   *  MDN
   */
  val param = "param".x
  /**
   * Represents a video , and its associated audio files and captions, with the
   * necessary interface to play it.
   *
   *  MDN
   */
  val video = "video".x
  /**
   * Represents a sound or an audio stream.
   *
   *  MDN
   */
  val audio = "audio".x
  /**
   * Allows the authors to specify alternate media resources for media elements
   * like video or audio
   *
   *  MDN
   */
  val source = "source".x
  /**
   * Allows authors to specify timed text track for media elements like video or
   * audio
   *
   *  MDN
   */
  val track = "track".x
  /**
   * Represents a bitmap area that scripts can use to render graphics like graphs,
   * games or any visual images on the fly.
   *
   *  MDN
   */
  val canvas = "canvas".x
  /**
   * In conjunction with area, defines an image map.
   *
   *  MDN
   */
  val map = "map".x
  /**
   * In conjunction with map, defines an image map
   *
   *  MDN
   */
  val area = "area".x
  /**
   * Defines an embeded vector image.
   *
   *  MDN
   */
  val svg = "svg".x
  /**
   * Defines a mathematical formula.
   *
   *  MDN
   */
  val math = "math".x

  // Tabular data
  /**
   * Represents data with more than one dimension.
   *
   *  MDN
   */
  val table = "table".x
  /**
   * The title of a table.
   *
   *  MDN
   */
  val caption = "caption".x
  /**
   * A set of columns.
   *
   *  MDN
   */
  val colgroup = "colgroup".x
  /**
   * A single column.
   *
   *  MDN
   */
  val col = "col".x
  /**
   * The table body.
   *
   *  MDN
   */
  val tbody = "tbody".x
  /**
   * The table headers.
   *
   *  MDN
   */
  val thead = "thead".x
  /**
   * The table footer.
   *
   *  MDN
   */
  val tfoot = "tfoot".x
  /**
   * A single row in a table.
   *
   *  MDN
   */
  val tr = "tr".x
  /**
   * A single cell in a table.
   *
   *  MDN
   */
  val td = "td".x
  /**
   * A header cell in a table.
   *
   *  MDN
   */
  val th = "th".x

  // Forms
  /**
   * Represents a form , consisting of controls, that can be submitted to a
   * server for processing.
   *
   *  MDN
   */
  val form = "form".x
  /**
   * A set of fields.
   *
   *  MDN
   */
  val fieldset = "fieldset".x
  /**
   * The caption for a fieldset.
   *
   *  MDN
   */
  val legend = "legend".x
  /**
   * The caption of a single field
   *
   *  MDN
   */
  val label = "label".x
  /**
   * A typed data field allowing the user to input data.
   *
   *  MDN
   */
  val input = "input".x
  /**
   * A button
   *
   *  MDN
   */
  val button = "button".x
  /**
   * A control that allows the user to select one of a set of options.
   *
   *  MDN
   */
  val select = "select".x
  /**
   * A set of predefined options for other controls.
   *
   *  MDN
   */
  val datalist = "datalist".x
  /**
   * A set of options, logically grouped.
   *
   *  MDN
   */
  val optgroup = "optgroup".x
  /**
   * An option in a select element.
   *
   *  MDN
   */
  val option = "option".x
  /**
   * A multiline text edit control.
   *
   *  MDN
   */
  val textarea = "textarea".x

}
