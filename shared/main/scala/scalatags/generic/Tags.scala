package scalatags.generic

/**
  * Trait that contains the contents of the `Tags` object, so they can be mixed
  * in to other objects if needed.
  */
trait Tags[Builder, Output] extends Util[Builder, Output]{



   // Root Element
   /**
    * Represents the root of an HTML or XHTML document. All other elements must
    * be descendants of this element.
    *
    *  MDN
    */
   val html: TypedTag[Output, Builder]

   // Document Metadata
   /**
    * Represents a collection of metadata about the document, including links to,
    * or definitions of, scripts and style sheets.
    *
    *  MDN
    */
   val head: TypedTag[Output, Builder]

   /**
    * Defines the base URL for relative URLs in the page.
    *
    *  MDN
    */
   val base: TypedTag[Output, Builder]
   /**
    * Used to link JavaScript and external CSS with the current HTML document.
    *
    *  MDN
    */
   val link: TypedTag[Output, Builder]
   /**
    * Defines metadata that can't be defined using another HTML element.
    *
    *  MDN
    */
   val meta: TypedTag[Output, Builder]


   // Scripting
   /**
    * Defines either an internal script or a link to an external script. The
    * script language is JavaScript.
    *
    *  MDN
    */
   val script: TypedTag[Output, Builder]


   // Sections
   /**
    * Represents the content of an HTML document. There is only one body
    *   element in a document.
    *
    *  MDN
    */
   val body: TypedTag[Output, Builder]

   /**
    * Heading level 1
    *
    *  MDN
    */
   val h1: TypedTag[Output, Builder]
   /**
    * Heading level 2
    *
    *  MDN
    */
   val h2: TypedTag[Output, Builder]
   /**
    * Heading level 3
    *
    *  MDN
    */
   val h3: TypedTag[Output, Builder]
   /**
    * Heading level 4
    *
    *  MDN
    */
   val h4: TypedTag[Output, Builder]
   /**
    * Heading level 5
    *
    *  MDN
    */
   val h5: TypedTag[Output, Builder]
   /**
    * Heading level 6
    *
    *  MDN
    */
   val h6: TypedTag[Output, Builder]
   /**
    * Defines the header of a page or section. It often contains a logo, the
    * title of the Web site, and a navigational table of content.
    *
    *  MDN
    */
   val header: TypedTag[Output, Builder]
   /**
    * Defines the footer for a page or section. It often contains a copyright
    * notice, some links to legal information, or addresses to give feedback.
    *
    *  MDN
    */
   val footer: TypedTag[Output, Builder]


   // Grouping content
   /**
    * Defines a portion that should be displayed as a paragraph.
    *
    *  MDN
    */
   val p: TypedTag[Output, Builder]
   /**
    * Represents a thematic break between paragraphs of a section or article or
    * any longer content.
    *
    *  MDN
    */
   val hr: TypedTag[Output, Builder]
   /**
    * Indicates that its content is preformatted and that this format must be
    * preserved.
    *
    *  MDN
    */
   val pre: TypedTag[Output, Builder]
   /**
    * Represents a content that is quoted from another source.
    *
    *  MDN
    */
   val blockquote: TypedTag[Output, Builder]
   /**
    * Defines an ordered list of items.
    *
    *  MDN
    */
   val ol: TypedTag[Output, Builder]
   /**
    * Defines an unordered list of items.
    *
    *  MDN
    */
   val ul: TypedTag[Output, Builder]
   /**
    * Defines an item of an list.
    *
    *  MDN
    */
   val li: TypedTag[Output, Builder]
   /**
    * Defines a definition list; al ist of terms and their associated definitions.
    *
    *  MDN
    */
   val dl: TypedTag[Output, Builder]
   /**
    * Represents a term defined by the next dd
    *
    *  MDN
    */
   val dt: TypedTag[Output, Builder]
   /**
    * Represents the definition of the terms immediately listed before it.
    *
    *  MDN
    */
   val dd: TypedTag[Output, Builder]
   /**
    * Represents a figure illustrated as part of the document.
    *
    *  MDN
    */
   val figure: TypedTag[Output, Builder]
   /**
    * Represents the legend of a figure.
    *
    *  MDN
    */
   val figcaption: TypedTag[Output, Builder]
   /**
    * Represents a generic container with no special meaning.
    *
    *  MDN
    */
   val div: TypedTag[Output, Builder]

   // Text-level semantics
   /**
    * Represents a hyperlink, linking to another resource.
    *
    *  MDN
    */
   val a: TypedTag[Output, Builder]
   /**
    * Represents emphasized text.
    *
    *  MDN
    */
   val em: TypedTag[Output, Builder]
   /**
    * Represents especially important text.
    *
    *  MDN
    */
   val strong: TypedTag[Output, Builder]
   /**
    * Represents a side comment; text like a disclaimer or copyright, which is not
    * essential to the comprehension of the document.
    *
    *  MDN
    */
   val small: TypedTag[Output, Builder]
   /**
    * Strikethrough element, used for that is no longer accurate or relevant.
    *
    *  MDN
    */
   val s: TypedTag[Output, Builder]
   /**
    * Represents the title of a work being cited.
    *
    *  MDN
    */
   val cite: TypedTag[Output, Builder]

   /**
    * Represents computer code.
    *
    *  MDN
    */
   val code: TypedTag[Output, Builder]

   /**
    * Subscript tag
    *
    *  MDN
    */
   val sub: TypedTag[Output, Builder]
   /**
    * Superscript tag.
    *
    *  MDN
    */
   val sup: TypedTag[Output, Builder]
   /**
    * Italicized text.
    *
    *  MDN
    */
   val i: TypedTag[Output, Builder]
   /**
    * Bold text.
    *
    *  MDN
    */
   val b: TypedTag[Output, Builder]
   /**
    * Underlined text.
    *
    *  MDN
    */
   val u: TypedTag[Output, Builder]

   /**
    * Represents text with no specific meaning. This has to be used when no other
    * text-semantic element conveys an adequate meaning, which, in this case, is
    * often brought by global attributes like class, lang, or dir.
    *
    *  MDN
    */
   val span: TypedTag[Output, Builder]
   /**
    * Represents a line break.
    *
    *  MDN
    */
   val br: TypedTag[Output, Builder]
   /**
    * Represents a line break opportunity, that is a suggested point for wrapping
    * text in order to improve readability of text split on several lines.
    *
    *  MDN
    */
   val wbr: TypedTag[Output, Builder]

   // Edits
   /**
    * Defines an addition to the document.
    *
    *  MDN
    */
   val ins: TypedTag[Output, Builder]
   /**
    * Defines a removal from the document.
    *
    *  MDN
    */
   val del: TypedTag[Output, Builder]

   // Embedded content
   /**
    * Represents an image.
    *
    *  MDN
    */
   val img: TypedTag[Output, Builder]
   /**
    * Represents a nested browsing context, that is an embedded HTML document.
    *
    *  MDN
    */
   val iframe: TypedTag[Output, Builder]
   /**
    * Represents a integration point for an external, often non-HTML, application
    * or interactive content.
    *
    *  MDN
    */
   val embed: TypedTag[Output, Builder]
   /**
    * Represents an external resource, which is treated as an image, an HTML
    * sub-document, or an external resource to be processed by a plug-in.
    *
    *  MDN
    */
   val `object`: TypedTag[Output, Builder]
   /**
    * Defines parameters for use by plug-ins invoked by object elements.
    *
    *  MDN
    */
   val param: TypedTag[Output, Builder]
   /**
    * Represents a video, and its associated audio files and captions, with the
    * necessary interface to play it.
    *
    *  MDN
    */
   val video: TypedTag[Output, Builder]
   /**
    * Represents a sound or an audio stream.
    *
    *  MDN
    */
   val audio: TypedTag[Output, Builder]
   /**
    * Allows the authors to specify alternate media resources for media elements
    * like video or audio
    *
    *  MDN
    */
   val source: TypedTag[Output, Builder]
   /**
    * Allows authors to specify timed text track for media elements like video or
    * audio
    *
    *  MDN
    */
   val track: TypedTag[Output, Builder]
   /**
    * Represents a bitmap area that scripts can use to render graphics like graphs,
    * games or any visual images on the fly.
    *
    *  MDN
    */
   val canvas: TypedTag[Output, Builder]
   /**
    * In conjunction with area, defines an image map.
    *
    *  MDN
    */
   val map: TypedTag[Output, Builder]
   /**
    * In conjunction with map, defines an image map
    *
    *  MDN
    */
   val area: TypedTag[Output, Builder]


   // Tabular data
   /**
    * Represents data with more than one dimension.
    *
    *  MDN
    */
   val table: TypedTag[Output, Builder]
   /**
    * The title of a table.
    *
    *  MDN
    */
   val caption: TypedTag[Output, Builder]
   /**
    * A set of columns.
    *
    *  MDN
    */
   val colgroup: TypedTag[Output, Builder]
   /**
    * A single column.
    *
    *  MDN
    */
   val col: TypedTag[Output, Builder]
   /**
    * The table body.
    *
    *  MDN
    */
   val tbody: TypedTag[Output, Builder]
   /**
    * The table headers.
    *
    *  MDN
    */
   val thead: TypedTag[Output, Builder]
   /**
    * The table footer.
    *
    *  MDN
    */
   val tfoot: TypedTag[Output, Builder]
   /**
    * A single row in a table.
    *
    *  MDN
    */
   val tr: TypedTag[Output, Builder]
   /**
    * A single cell in a table.
    *
    *  MDN
    */
   val td: TypedTag[Output, Builder]
   /**
    * A header cell in a table.
    *
    *  MDN
    */
   val th: TypedTag[Output, Builder]

   // Forms
   /**
    * Represents a form, consisting of controls, that can be submitted to a
    * server for processing.
    *
    *  MDN
    */
   val form: TypedTag[Output, Builder]
   /**
    * A set of fields.
    *
    *  MDN
    */
   val fieldset: TypedTag[Output, Builder]
   /**
    * The caption for a fieldset.
    *
    *  MDN
    */
   val legend: TypedTag[Output, Builder]
   /**
    * The caption of a single field
    *
    *  MDN
    */
   val label: TypedTag[Output, Builder]
   /**
    * A typed data field allowing the user to input data.
    *
    *  MDN
    */
   val input: TypedTag[Output, Builder]
   /**
    * A button
    *
    *  MDN
    */
   val button: TypedTag[Output, Builder]
   /**
    * A control that allows the user to select one of a set of options.
    *
    *  MDN
    */
   val select: TypedTag[Output, Builder]
   /**
    * A set of predefined options for other controls.
    *
    *  MDN
    */
   val datalist: TypedTag[Output, Builder]
   /**
    * A set of options, logically grouped.
    *
    *  MDN
    */
   val optgroup: TypedTag[Output, Builder]
   /**
    * An option in a select element.
    *
    *  MDN
    */
   val option: TypedTag[Output, Builder]
   /**
    * A multiline text edit control.
    *
    *  MDN
    */
   val textarea: TypedTag[Output, Builder]
 }
