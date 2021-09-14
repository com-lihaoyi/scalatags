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
   def html: TypedTag[Builder, Output, FragT]

   // Document Metadata
   /**
    * Represents a collection of metadata about the document, including links to,
    * or definitions of, scripts and style sheets.
    *
    *  MDN
    */
   def head: TypedTag[Builder, Output, FragT]

   /**
    * Defines the base URL for relative URLs in the page.
    *
    *  MDN
    */
   def base: TypedTag[Builder, Output, FragT]
   /**
    * Used to link JavaScript and external CSS with the current HTML document.
    *
    *  MDN
    */
   def link: TypedTag[Builder, Output, FragT]
   /**
    * Defines metadata that can't be defined using another HTML element.
    *
    *  MDN
    */
   def meta: TypedTag[Builder, Output, FragT]


   // Scripting
   /**
    * Defines either an internal script or a link to an external script. The
    * script language is JavaScript.
    *
    *  MDN
    */
   def script: TypedTag[Builder, Output, FragT]


   // Sections
   /**
    * Represents the content of an HTML document. There is only one body
    *   element in a document.
    *
    *  MDN
    */
   def body: TypedTag[Builder, Output, FragT]

   /**
    * Heading level 1
    *
    *  MDN
    */
   def h1: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 2
    *
    *  MDN
    */
   def h2: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 3
    *
    *  MDN
    */
   def h3: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 4
    *
    *  MDN
    */
   def h4: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 5
    *
    *  MDN
    */
   def h5: TypedTag[Builder, Output, FragT]
   /**
    * Heading level 6
    *
    *  MDN
    */
   def h6: TypedTag[Builder, Output, FragT]
   /**
    * Defines the header of a page or section. It often contains a logo, the
    * title of the Web site, and a navigational table of content.
    *
    *  MDN
    */
   def header: TypedTag[Builder, Output, FragT]
   /**
    * Defines the footer for a page or section. It often contains a copyright
    * notice, some links to legal information, or addresses to give feedback.
    *
    *  MDN
    */
   def footer: TypedTag[Builder, Output, FragT]


   // Grouping content
   /**
    * Defines a portion that should be displayed as a paragraph.
    *
    *  MDN
    */
   def p: TypedTag[Builder, Output, FragT]
   /**
    * Represents a thematic break between paragraphs of a section or article or
    * any longer content.
    *
    *  MDN
    */
   def hr: TypedTag[Builder, Output, FragT]
   /**
    * Indicates that its content is preformatted and that this format must be
    * preserved.
    *
    *  MDN
    */
   def pre: TypedTag[Builder, Output, FragT]
   /**
    * Represents a content that is quoted from another source.
    *
    *  MDN
    */
   def blockquote: TypedTag[Builder, Output, FragT]
   /**
    * Defines an ordered list of items.
    *
    *  MDN
    */
   def ol: TypedTag[Builder, Output, FragT]
   /**
    * Defines an unordered list of items.
    *
    *  MDN
    */
   def ul: TypedTag[Builder, Output, FragT]
   /**
    * Defines an item of an list.
    *
    *  MDN
    */
   def li: TypedTag[Builder, Output, FragT]
   /**
    * Defines a definition list; a list of terms and their associated definitions.
    *
    *  MDN
    */
   def dl: TypedTag[Builder, Output, FragT]
   /**
    * Represents a term defined by the next dd
    *
    *  MDN
    */
   def dt: TypedTag[Builder, Output, FragT]
   /**
    * Represents the definition of the terms immediately listed before it.
    *
    *  MDN
    */
   def dd: TypedTag[Builder, Output, FragT]
   /**
    * Represents a figure illustrated as part of the document.
    *
    *  MDN
    */
   def figure: TypedTag[Builder, Output, FragT]
   /**
    * Represents the legend of a figure.
    *
    *  MDN
    */
   def figcaption: TypedTag[Builder, Output, FragT]
   /**
    * Represents a generic container with no special meaning.
    *
    *  MDN
    */
   def div: TypedTag[Builder, Output, FragT]

   // Text-level semantics
   /**
    * Represents a hyperlink, linking to another resource.
    *
    *  MDN
    */
   def a: TypedTag[Builder, Output, FragT]
   /**
    * Represents emphasized text.
    *
    *  MDN
    */
   def em: TypedTag[Builder, Output, FragT]
   /**
    * Represents especially important text.
    *
    *  MDN
    */
   def strong: TypedTag[Builder, Output, FragT]
   /**
    * Represents a side comment; text like a disclaimer or copyright, which is not
    * essential to the comprehension of the document.
    *
    *  MDN
    */
   def small: TypedTag[Builder, Output, FragT]
   /**
    * Strikethrough element, used for that is no longer accurate or relevant.
    *
    *  MDN
    */
   def s: TypedTag[Builder, Output, FragT]
   /**
    * Represents the title of a work being cited.
    *
    *  MDN
    */
   def cite: TypedTag[Builder, Output, FragT]

   /**
    * Represents computer code.
    *
    *  MDN
    */
   def code: TypedTag[Builder, Output, FragT]

   /**
    * Subscript tag
    *
    *  MDN
    */
   def sub: TypedTag[Builder, Output, FragT]
   /**
    * Superscript tag.
    *
    *  MDN
    */
   def sup: TypedTag[Builder, Output, FragT]
   /**
    * Italicized text.
    *
    *  MDN
    */
   def i: TypedTag[Builder, Output, FragT]
   /**
    * Bold text.
    *
    *  MDN
    */
   def b: TypedTag[Builder, Output, FragT]
   /**
    * Underlined text.
    *
    *  MDN
    */
   def u: TypedTag[Builder, Output, FragT]

   /**
    * Represents text with no specific meaning. This has to be used when no other
    * text-semantic element conveys an adequate meaning, which, in this case, is
    * often brought by global attributes like class, lang, or dir.
    *
    *  MDN
    */
   def span: TypedTag[Builder, Output, FragT]
   /**
    * Represents a line break.
    *
    *  MDN
    */
   def br: TypedTag[Builder, Output, FragT]
   /**
    * Represents a line break opportunity, that is a suggested point for wrapping
    * text in order to improve readability of text split on several lines.
    *
    *  MDN
    */
   def wbr: TypedTag[Builder, Output, FragT]

   // Edits
   /**
    * Defines an addition to the document.
    *
    *  MDN
    */
   def ins: TypedTag[Builder, Output, FragT]
   /**
    * Defines a removal from the document.
    *
    *  MDN
    */
   def del: TypedTag[Builder, Output, FragT]

   // Embedded content
   /**
    * Represents an image.
    *
    *  MDN
    */
   def img: TypedTag[Builder, Output, FragT]
   /**
    * Represents a nested browsing context, that is an embedded HTML document.
    *
    *  MDN
    */
   def iframe: TypedTag[Builder, Output, FragT]
   /**
    * Represents a integration point for an external, often non-HTML, application
    * or interactive content.
    *
    *  MDN
    */
   def embed: TypedTag[Builder, Output, FragT]
   /**
    * Represents an external resource, which is treated as an image, an HTML
    * sub-document, or an external resource to be processed by a plug-in.
    *
    *  MDN
    */
   def `object`: TypedTag[Builder, Output, FragT]
   /**
    * Defines parameters for use by plug-ins invoked by object elements.
    *
    *  MDN
    */
   def param: TypedTag[Builder, Output, FragT]
   /**
    * Represents a video, and its associated audio files and captions, with the
    * necessary interface to play it.
    *
    *  MDN
    */
   def video: TypedTag[Builder, Output, FragT]
   /**
    * Represents a sound or an audio stream.
    *
    *  MDN
    */
   def audio: TypedTag[Builder, Output, FragT]
   /**
    * Allows the authors to specify alternate media resources for media elements
    * like video or audio
    *
    *  MDN
    */
   def source: TypedTag[Builder, Output, FragT]
   /**
    * Allows authors to specify timed text track for media elements like video or
    * audio
    *
    *  MDN
    */
   def track: TypedTag[Builder, Output, FragT]
   /**
    * Represents a bitmap area that scripts can use to render graphics like graphs,
    * games or any visual images on the fly.
    *
    *  MDN
    */
   def canvas: TypedTag[Builder, Output, FragT]
   /**
    * In conjunction with area, defines an image map.
    *
    *  MDN
    */
   def map: TypedTag[Builder, Output, FragT]
   /**
    * In conjunction with map, defines an image map
    *
    *  MDN
    */
   def area: TypedTag[Builder, Output, FragT]


   // Tabular data
   /**
    * Represents data with more than one dimension.
    *
    *  MDN
    */
   def table: TypedTag[Builder, Output, FragT]
   /**
    * The title of a table.
    *
    *  MDN
    */
   def caption: TypedTag[Builder, Output, FragT]
   /**
    * A set of columns.
    *
    *  MDN
    */
   def colgroup: TypedTag[Builder, Output, FragT]
   /**
    * A single column.
    *
    *  MDN
    */
   def col: TypedTag[Builder, Output, FragT]
   /**
    * The table body.
    *
    *  MDN
    */
   def tbody: TypedTag[Builder, Output, FragT]
   /**
    * The table headers.
    *
    *  MDN
    */
   def thead: TypedTag[Builder, Output, FragT]
   /**
    * The table footer.
    *
    *  MDN
    */
   def tfoot: TypedTag[Builder, Output, FragT]
   /**
    * A single row in a table.
    *
    *  MDN
    */
   def tr: TypedTag[Builder, Output, FragT]
   /**
    * A single cell in a table.
    *
    *  MDN
    */
   def td: TypedTag[Builder, Output, FragT]
   /**
    * A header cell in a table.
    *
    *  MDN
    */
   def th: TypedTag[Builder, Output, FragT]

   // Forms
   /**
    * Represents a form, consisting of controls, that can be submitted to a
    * server for processing.
    *
    *  MDN
    */
   def form: TypedTag[Builder, Output, FragT]
   /**
    * A set of fields.
    *
    *  MDN
    */
   def fieldset: TypedTag[Builder, Output, FragT]
   /**
    * The caption for a fieldset.
    *
    *  MDN
    */
   def legend: TypedTag[Builder, Output, FragT]
   /**
    * The caption of a single field
    *
    *  MDN
    */
   def label: TypedTag[Builder, Output, FragT]
   /**
    * A typed data field allowing the user to input data.
    *
    *  MDN
    */
   def input: TypedTag[Builder, Output, FragT]
   /**
    * A button
    *
    *  MDN
    */
   def button: TypedTag[Builder, Output, FragT]
   /**
    * A control that allows the user to select one of a set of options.
    *
    *  MDN
    */
   def select: TypedTag[Builder, Output, FragT]
   /**
    * A set of predefined options for other controls.
    *
    *  MDN
    */
   def datalist: TypedTag[Builder, Output, FragT]
   /**
    * A set of options, logically grouped.
    *
    *  MDN
    */
   def optgroup: TypedTag[Builder, Output, FragT]
   /**
    * An option in a select element.
    *
    *  MDN
    */
   def option: TypedTag[Builder, Output, FragT]
   /**
    * A multiline text edit control.
    *
    *  MDN
    */
   def textarea: TypedTag[Builder, Output, FragT]
 }
