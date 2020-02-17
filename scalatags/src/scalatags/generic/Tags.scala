package scalatags.generic


/**
  * Trait that contains the contents of the `Tags` object, so they can be mixed
  * in to other objects if needed.
  */
trait Tags[+TypedTag]{



   // Root Element
   /**
    * Represents the root of an HTML or XHTML document. All other elements must
    * be descendants of this element.
    *
    *  MDN
    */
   val html: TypedTag

   // Document Metadata
   /**
    * Represents a collection of metadata about the document, including links to,
    * or definitions of, scripts and style sheets.
    *
    *  MDN
    */
   val head: TypedTag

   /**
    * Defines the base URL for relative URLs in the page.
    *
    *  MDN
    */
   val base: TypedTag
   /**
    * Used to link JavaScript and external CSS with the current HTML document.
    *
    *  MDN
    */
   val link: TypedTag
   /**
    * Defines metadata that can't be defined using another HTML element.
    *
    *  MDN
    */
   val meta: TypedTag


   // Scripting
   /**
    * Defines either an internal script or a link to an external script. The
    * script language is JavaScript.
    *
    *  MDN
    */
   val script: TypedTag


   // Sections
   /**
    * Represents the content of an HTML document. There is only one body
    *   element in a document.
    *
    *  MDN
    */
   val body: TypedTag

   /**
    * Heading level 1
    *
    *  MDN
    */
   val h1: TypedTag
   /**
    * Heading level 2
    *
    *  MDN
    */
   val h2: TypedTag
   /**
    * Heading level 3
    *
    *  MDN
    */
   val h3: TypedTag
   /**
    * Heading level 4
    *
    *  MDN
    */
   val h4: TypedTag
   /**
    * Heading level 5
    *
    *  MDN
    */
   val h5: TypedTag
   /**
    * Heading level 6
    *
    *  MDN
    */
   val h6: TypedTag
   /**
    * Defines the header of a page or section. It often contains a logo, the
    * title of the Web site, and a navigational table of content.
    *
    *  MDN
    */
   val header: TypedTag
   /**
    * Defines the footer for a page or section. It often contains a copyright
    * notice, some links to legal information, or addresses to give feedback.
    *
    *  MDN
    */
   val footer: TypedTag


   // Grouping content
   /**
    * Defines a portion that should be displayed as a paragraph.
    *
    *  MDN
    */
   val p: TypedTag
   /**
    * Represents a thematic break between paragraphs of a section or article or
    * any longer content.
    *
    *  MDN
    */
   val hr: TypedTag
   /**
    * Indicates that its content is preformatted and that this format must be
    * preserved.
    *
    *  MDN
    */
   val pre: TypedTag
   /**
    * Represents a content that is quoted from another source.
    *
    *  MDN
    */
   val blockquote: TypedTag
   /**
    * Defines an ordered list of items.
    *
    *  MDN
    */
   val ol: TypedTag
   /**
    * Defines an unordered list of items.
    *
    *  MDN
    */
   val ul: TypedTag
   /**
    * Defines an item of an list.
    *
    *  MDN
    */
   val li: TypedTag
   /**
    * Defines a definition list; a list of terms and their associated definitions.
    *
    *  MDN
    */
   val dl: TypedTag
   /**
    * Represents a term defined by the next dd
    *
    *  MDN
    */
   val dt: TypedTag
   /**
    * Represents the definition of the terms immediately listed before it.
    *
    *  MDN
    */
   val dd: TypedTag
   /**
    * Represents a figure illustrated as part of the document.
    *
    *  MDN
    */
   val figure: TypedTag
   /**
    * Represents the legend of a figure.
    *
    *  MDN
    */
   val figcaption: TypedTag
   /**
    * Represents a generic container with no special meaning.
    *
    *  MDN
    */
   val div: TypedTag

   // Text-level semantics
   /**
    * Represents a hyperlink, linking to another resource.
    *
    *  MDN
    */
   val a: TypedTag
   /**
    * Represents emphasized text.
    *
    *  MDN
    */
   val em: TypedTag
   /**
    * Represents especially important text.
    *
    *  MDN
    */
   val strong: TypedTag
   /**
    * Represents a side comment; text like a disclaimer or copyright, which is not
    * essential to the comprehension of the document.
    *
    *  MDN
    */
   val small: TypedTag
   /**
    * Strikethrough element, used for that is no longer accurate or relevant.
    *
    *  MDN
    */
   val s: TypedTag
   /**
    * Represents the title of a work being cited.
    *
    *  MDN
    */
   val cite: TypedTag

   /**
    * Represents computer code.
    *
    *  MDN
    */
   val code: TypedTag

   /**
    * Subscript tag
    *
    *  MDN
    */
   val sub: TypedTag
   /**
    * Superscript tag.
    *
    *  MDN
    */
   val sup: TypedTag
   /**
    * Italicized text.
    *
    *  MDN
    */
   val i: TypedTag
   /**
    * Bold text.
    *
    *  MDN
    */
   val b: TypedTag
   /**
    * Underlined text.
    *
    *  MDN
    */
   val u: TypedTag

   /**
    * Represents text with no specific meaning. This has to be used when no other
    * text-semantic element conveys an adequate meaning, which, in this case, is
    * often brought by global attributes like class, lang, or dir.
    *
    *  MDN
    */
   val span: TypedTag
   /**
    * Represents a line break.
    *
    *  MDN
    */
   val br: TypedTag
   /**
    * Represents a line break opportunity, that is a suggested point for wrapping
    * text in order to improve readability of text split on several lines.
    *
    *  MDN
    */
   val wbr: TypedTag

   // Edits
   /**
    * Defines an addition to the document.
    *
    *  MDN
    */
   val ins: TypedTag
   /**
    * Defines a removal from the document.
    *
    *  MDN
    */
   val del: TypedTag

   // Embedded content
   /**
    * Represents an image.
    *
    *  MDN
    */
   val img: TypedTag
   /**
    * Represents a nested browsing context, that is an embedded HTML document.
    *
    *  MDN
    */
   val iframe: TypedTag
   /**
    * Represents a integration point for an external, often non-HTML, application
    * or interactive content.
    *
    *  MDN
    */
   val embed: TypedTag
   /**
    * Represents an external resource, which is treated as an image, an HTML
    * sub-document, or an external resource to be processed by a plug-in.
    *
    *  MDN
    */
   val `object`: TypedTag
   /**
    * Defines parameters for use by plug-ins invoked by object elements.
    *
    *  MDN
    */
   val param: TypedTag
   /**
    * Represents a video, and its associated audio files and captions, with the
    * necessary interface to play it.
    *
    *  MDN
    */
   val video: TypedTag
   /**
    * Represents a sound or an audio stream.
    *
    *  MDN
    */
   val audio: TypedTag
   /**
    * Allows the authors to specify alternate media resources for media elements
    * like video or audio
    *
    *  MDN
    */
   val source: TypedTag
   /**
    * Allows authors to specify timed text track for media elements like video or
    * audio
    *
    *  MDN
    */
   val track: TypedTag
   /**
    * Represents a bitmap area that scripts can use to render graphics like graphs,
    * games or any visual images on the fly.
    *
    *  MDN
    */
   val canvas: TypedTag
   /**
    * In conjunction with area, defines an image map.
    *
    *  MDN
    */
   val map: TypedTag
   /**
    * In conjunction with map, defines an image map
    *
    *  MDN
    */
   val area: TypedTag


   // Tabular data
   /**
    * Represents data with more than one dimension.
    *
    *  MDN
    */
   val table: TypedTag
   /**
    * The title of a table.
    *
    *  MDN
    */
   val caption: TypedTag
   /**
    * A set of columns.
    *
    *  MDN
    */
   val colgroup: TypedTag
   /**
    * A single column.
    *
    *  MDN
    */
   val col: TypedTag
   /**
    * The table body.
    *
    *  MDN
    */
   val tbody: TypedTag
   /**
    * The table headers.
    *
    *  MDN
    */
   val thead: TypedTag
   /**
    * The table footer.
    *
    *  MDN
    */
   val tfoot: TypedTag
   /**
    * A single row in a table.
    *
    *  MDN
    */
   val tr: TypedTag
   /**
    * A single cell in a table.
    *
    *  MDN
    */
   val td: TypedTag
   /**
    * A header cell in a table.
    *
    *  MDN
    */
   val th: TypedTag

   // Forms
   /**
    * Represents a form, consisting of controls, that can be submitted to a
    * server for processing.
    *
    *  MDN
    */
   val form: TypedTag
   /**
    * A set of fields.
    *
    *  MDN
    */
   val fieldset: TypedTag
   /**
    * The caption for a fieldset.
    *
    *  MDN
    */
   val legend: TypedTag
   /**
    * The caption of a single field
    *
    *  MDN
    */
   val label: TypedTag
   /**
    * A typed data field allowing the user to input data.
    *
    *  MDN
    */
   val input: TypedTag
   /**
    * A button
    *
    *  MDN
    */
   val button: TypedTag
   /**
    * A control that allows the user to select one of a set of options.
    *
    *  MDN
    */
   val select: TypedTag
   /**
    * A set of predefined options for other controls.
    *
    *  MDN
    */
   val datalist: TypedTag
   /**
    * A set of options, logically grouped.
    *
    *  MDN
    */
   val optgroup: TypedTag
   /**
    * An option in a select element.
    *
    *  MDN
    */
   val option: TypedTag
   /**
    * A multiline text edit control.
    *
    *  MDN
    */
   val textarea: TypedTag
 }
