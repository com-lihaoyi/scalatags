package scalatags.generic


/**
  * Trait that contains the contents of the `Tags` object, so they can be mixed
  * in to other objects if needed.
  */
trait Tags[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT]{



   // Root Element
   /**
    * Represents the root of an HTML or XHTML document. All other elements must
    * be descendants of this element.
    *
    *  MDN
    */
   val html: TypedTag[Builder, Output, FragT]

   // Document Metadata
   /**
    * Represents a collection of metadata about the document, including links to,
    * or definitions of, scripts and style sheets.
    *
    *  MDN
    */
   val head: TypedTag[Builder, Output, FragT]

   /**
    * Defines the base URL for relative URLs in the page.
    *
    *  MDN
    */
   val base: TypedTag[Builder, Output, FragT]
   /**
    * Used to link JavaScript and external CSS with the current HTML document.
    *
    *  MDN
    */
   val link: TypedTag[Builder, Output, FragT]
   /**
    * Defines metadata that can't be defined using another HTML element.
    *
    *  MDN
    */
   val meta: TypedTag[Builder, Output, FragT]


   // Scripting
   /**
    * Defines either an internal script or a link to an external script. The
    * script language is JavaScript.
    *
    *  MDN
    */
   val script: TypedTag[Builder, Output, FragT]


   // Sections
   /**
    * Represents the content of an HTML document. There is only one body
    *   element in a document.
    *
    *  MDN
    */
   val body: TypedTag[Builder, Output, FragT]

   /**
    * Heading level 1
    *
    *  MDN
    */
   val h1: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 2
    *
    *  MDN
    */
   val h2: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 3
    *
    *  MDN
    */
   val h3: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 4
    *
    *  MDN
    */
   val h4: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 5
    *
    *  MDN
    */
   val h5: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 6
    *
    *  MDN
    */
   val h6: TypedTag[Builder, Output, FragT]
   /**
    * Defines the header of a page or section. It often contains a logo, the
    * title of the Web site, and a navigational table of content.
    *
    *  MDN
    */
   val header: TypedTag[Builder, Output, FragT]
   /**
    * Defines the footer for a page or section. It often contains a copyright
    * notice, some links to legal information, or addresses to give feedback.
    *
    *  MDN
    */
   val footer: TypedTag[Builder, Output, FragT]


   // Grouping content
   /**
    * Defines a portion that should be displayed as a paragraph.
    *
    *  MDN
    */
   val p: TypedTag[Builder, Output, FragT]
   /**
    * Represents a thematic break between paragraphs of a section or article or
    * any longer content.
    *
    *  MDN
    */
   val hr: TypedTag[Builder, Output, FragT]
   /**
    * Indicates that its content is preformatted and that this format must be
    * preserved.
    *
    *  MDN
    */
   val pre: TypedTag[Builder, Output, FragT]
   /**
    * Represents a content that is quoted from another source.
    *
    *  MDN
    */
   val blockquote: TypedTag[Builder, Output, FragT]
   /**
    * Defines an ordered list of items.
    *
    *  MDN
    */
   val ol: TypedTag[Builder, Output, FragT]
   /**
    * Defines an unordered list of items.
    *
    *  MDN
    */
   val ul: TypedTag[Builder, Output, FragT]
   /**
    * Defines an item of an list.
    *
    *  MDN
    */
   val li: TypedTag[Builder, Output, FragT]
   /**
    * Defines a definition list; a list of terms and their associated definitions.
    *
    *  MDN
    */
   val dl: TypedTag[Builder, Output, FragT]
   /**
    * Represents a term defined by the next dd
    *
    *  MDN
    */
   val dt: TypedTag[Builder, Output, FragT]
   /**
    * Represents the definition of the terms immediately listed before it.
    *
    *  MDN
    */
   val dd: TypedTag[Builder, Output, FragT]
   /**
    * Represents a figure illustrated as part of the document.
    *
    *  MDN
    */
   val figure: TypedTag[Builder, Output, FragT]
   /**
    * Represents the legend of a figure.
    *
    *  MDN
    */
   val figcaption: TypedTag[Builder, Output, FragT]
   /**
    * Represents a generic container with no special meaning.
    *
    *  MDN
    */
   val div: TypedTag[Builder, Output, FragT]

   // Text-level semantics
   /**
    * Represents a hyperlink, linking to another resource.
    *
    *  MDN
    */
   val a: TypedTag[Builder, Output, FragT]
   /**
    * Represents emphasized text.
    *
    *  MDN
    */
   val em: TypedTag[Builder, Output, FragT]
   /**
    * Represents especially important text.
    *
    *  MDN
    */
   val strong: TypedTag[Builder, Output, FragT]
   /**
    * Represents a side comment; text like a disclaimer or copyright, which is not
    * essential to the comprehension of the document.
    *
    *  MDN
    */
   val small: TypedTag[Builder, Output, FragT]
   /**
    * Strikethrough element, used for that is no longer accurate or relevant.
    *
    *  MDN
    */
   val s: TypedTag[Builder, Output, FragT]
   /**
    * Represents the title of a work being cited.
    *
    *  MDN
    */
   val cite: TypedTag[Builder, Output, FragT]

   /**
    * Represents computer code.
    *
    *  MDN
    */
   val code: TypedTag[Builder, Output, FragT]

   /**
    * Subscript tag
    *
    *  MDN
    */
   val sub: TypedTag[Builder, Output, FragT]
   /**
    * Superscript tag.
    *
    *  MDN
    */
   val sup: TypedTag[Builder, Output, FragT]
   /**
    * Italicized text.
    *
    *  MDN
    */
   val i: TypedTag[Builder, Output, FragT]
   /**
    * Bold text.
    *
    *  MDN
    */
   val b: TypedTag[Builder, Output, FragT]
   /**
    * Underlined text.
    *
    *  MDN
    */
   val u: TypedTag[Builder, Output, FragT]

   /**
    * Represents text with no specific meaning. This has to be used when no other
    * text-semantic element conveys an adequate meaning, which, in this case, is
    * often brought by global attributes like class, lang, or dir.
    *
    *  MDN
    */
   val span: TypedTag[Builder, Output, FragT]
   /**
    * Represents a line break.
    *
    *  MDN
    */
   val br: TypedTag[Builder, Output, FragT]
   /**
    * Represents a line break opportunity, that is a suggested point for wrapping
    * text in order to improve readability of text split on several lines.
    *
    *  MDN
    */
   val wbr: TypedTag[Builder, Output, FragT]

   // Edits
   /**
    * Defines an addition to the document.
    *
    *  MDN
    */
   val ins: TypedTag[Builder, Output, FragT]
   /**
    * Defines a removal from the document.
    *
    *  MDN
    */
   val del: TypedTag[Builder, Output, FragT]

   // Embedded content
   /**
    * Represents an image.
    *
    *  MDN
    */
   val img: TypedTag[Builder, Output, FragT]
   /**
    * Represents a nested browsing context, that is an embedded HTML document.
    *
    *  MDN
    */
   val iframe: TypedTag[Builder, Output, FragT]
   /**
    * Represents a integration point for an external, often non-HTML, application
    * or interactive content.
    *
    *  MDN
    */
   val embed: TypedTag[Builder, Output, FragT]
   /**
    * Represents an external resource, which is treated as an image, an HTML
    * sub-document, or an external resource to be processed by a plug-in.
    *
    *  MDN
    */
   val `object`: TypedTag[Builder, Output, FragT]
   /**
    * Defines parameters for use by plug-ins invoked by object elements.
    *
    *  MDN
    */
   val param: TypedTag[Builder, Output, FragT]
   /**
    * Represents a video, and its associated audio files and captions, with the
    * necessary interface to play it.
    *
    *  MDN
    */
   val video: TypedTag[Builder, Output, FragT]
   /**
    * Represents a sound or an audio stream.
    *
    *  MDN
    */
   val audio: TypedTag[Builder, Output, FragT]
   /**
    * Allows the authors to specify alternate media resources for media elements
    * like video or audio
    *
    *  MDN
    */
   val source: TypedTag[Builder, Output, FragT]
   /**
    * Allows authors to specify timed text track for media elements like video or
    * audio
    *
    *  MDN
    */
   val track: TypedTag[Builder, Output, FragT]
   /**
    * Represents a bitmap area that scripts can use to render graphics like graphs,
    * games or any visual images on the fly.
    *
    *  MDN
    */
   val canvas: TypedTag[Builder, Output, FragT]
   /**
    * In conjunction with area, defines an image map.
    *
    *  MDN
    */
   val map: TypedTag[Builder, Output, FragT]
   /**
    * In conjunction with map, defines an image map
    *
    *  MDN
    */
   val area: TypedTag[Builder, Output, FragT]


   // Tabular data
   /**
    * Represents data with more than one dimension.
    *
    *  MDN
    */
   val table: TypedTag[Builder, Output, FragT]
   /**
    * The title of a table.
    *
    *  MDN
    */
   val caption: TypedTag[Builder, Output, FragT]
   /**
    * A set of columns.
    *
    *  MDN
    */
   val colgroup: TypedTag[Builder, Output, FragT]
   /**
    * A single column.
    *
    *  MDN
    */
   val col: TypedTag[Builder, Output, FragT]
   /**
    * The table body.
    *
    *  MDN
    */
   val tbody: TypedTag[Builder, Output, FragT]
   /**
    * The table headers.
    *
    *  MDN
    */
   val thead: TypedTag[Builder, Output, FragT]
   /**
    * The table footer.
    *
    *  MDN
    */
   val tfoot: TypedTag[Builder, Output, FragT]
   /**
    * A single row in a table.
    *
    *  MDN
    */
   val tr: TypedTag[Builder, Output, FragT]
   /**
    * A single cell in a table.
    *
    *  MDN
    */
   val td: TypedTag[Builder, Output, FragT]
   /**
    * A header cell in a table.
    *
    *  MDN
    */
   val th: TypedTag[Builder, Output, FragT]

   // Forms
   /**
    * Represents a form, consisting of controls, that can be submitted to a
    * server for processing.
    *
    *  MDN
    */
   val form: TypedTag[Builder, Output, FragT]
   /**
    * A set of fields.
    *
    *  MDN
    */
   val fieldset: TypedTag[Builder, Output, FragT]
   /**
    * The caption for a fieldset.
    *
    *  MDN
    */
   val legend: TypedTag[Builder, Output, FragT]
   /**
    * The caption of a single field
    *
    *  MDN
    */
   val label: TypedTag[Builder, Output, FragT]
   /**
    * A typed data field allowing the user to input data.
    *
    *  MDN
    */
   val input: TypedTag[Builder, Output, FragT]
   /**
    * A button
    *
    *  MDN
    */
   val button: TypedTag[Builder, Output, FragT]
   /**
    * A control that allows the user to select one of a set of options.
    *
    *  MDN
    */
   val select: TypedTag[Builder, Output, FragT]
   /**
    * A set of predefined options for other controls.
    *
    *  MDN
    */
   val datalist: TypedTag[Builder, Output, FragT]
   /**
    * A set of options, logically grouped.
    *
    *  MDN
    */
   val optgroup: TypedTag[Builder, Output, FragT]
   /**
    * An option in a select element.
    *
    *  MDN
    */
   val option: TypedTag[Builder, Output, FragT]
   /**
    * A multiline text edit control.
    *
    *  MDN
    */
   val textarea: TypedTag[Builder, Output, FragT]
 }
