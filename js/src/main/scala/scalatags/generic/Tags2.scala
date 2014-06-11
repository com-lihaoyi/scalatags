package scalatags.generic
import org.scalajs.dom

/**
   * Contains HTML tags which are used less frequently. These are generally
   * imported individually as needed.
   */
trait Tags2[Builder] extends Util[Builder]{

   // Document Metadata
   /**
   * Defines the title of the document, shown in a browser's title bar or on the
   * page's tab. It can only contain text and any contained tags are not
   * interpreted.
   *
   * MDN
   */
   val title = "title".tag[dom.HTMLTitleElement]

   /**
   * Used to write inline CSS.
   *
   *  MDN
   */
   val style = "style".tag[dom.HTMLStyleElement]
   // Scripting
   /**
   * Defines alternative content to display when the browser doesn't support
   * scripting.
   *
   *  MDN
   */
   val noscript = "noscript".tag[dom.HTMLElement]

   // Sections
   /**
   * Represents a generic section of a document, i.e., a thematic grouping of
   * content, typically with a heading.
   *
   *  MDN
   */
   val section = "section".tag[dom.HTMLElement]
   /**
   * Represents a section of a page that links to other pages or to parts within
   * the page: a section with navigation links.
   *
   *  MDN
   */
   val nav = "nav".tag[dom.HTMLElement]
   /**
   * Defines self-contained content that could exist independently of the rest
   * of the content.
   *
   *  MDN
   */
   val article = "article".tag[dom.HTMLElement]
   /**
   * Defines some content loosely related to the page content. If it is removed,
   * the remaining content still makes sense.
   *
   *  MDN
   */
   val aside = "aside".tag[dom.HTMLElement]
   /**
   * Defines a section containing contact information.
   *
   *  MDN
   */
   val address = "address".tag[dom.HTMLElement]

   /**
   * Defines the main or important content in the document. There is only one
   * main element in the document.
   *
   *  MDN
   */
   val main = "main".tag[dom.HTMLElement]

   // Text level semantics

   /**
   * An inline quotation.
   *
   *  MDN
   */
   val q = "q".tag[dom.HTMLQuoteElement]
   /**
   * Represents a term whose definition is contained in its nearest ancestor
   * content.
   *
   *  MDN
   */
   val dfn = "dfn".tag[dom.HTMLElement]
   /**
   * An abbreviation or acronym; the expansion of the abbreviation can be
   * represented in the title attribute.
   *
   *  MDN
   */
   val abbr = "abbr".tag[dom.HTMLElement]
   /**
   * Associates to its content a machine-readable equivalent.
   *
   *  MDN
   */
   val data = "data".tag[dom.HTMLElement]
   /**
   * Represents a date and time value; the machine-readable equivalent can be
   * represented in the datetime attribetu
   *
   *  MDN
   */
   val time = "time".tag[dom.HTMLElement]
   /**
   * Represents a variable.
   *
   *  MDN
   */
   val `var` = "var".tag[dom.HTMLElement]
   /**
   * Represents the output of a program or a computer.
   *
   *  MDN
   */
   val samp = "samp".tag[dom.HTMLElement]
   /**
   * Represents user input, often from a keyboard, but not necessarily.
   *
   *  MDN
   */
   val kbd = "kbd".tag[dom.HTMLElement]

   /**
   * Defines a mathematical formula.
   *
   *  MDN
   */
   val math = "math".tag[dom.HTMLElement]
   /**
   * Represents text highlighted for reference purposes, that is for its
   * relevance in another context.
   *
   *  MDN
   */
   val mark = "mark".tag[dom.HTMLElement]
   /**
   * Represents content to be marked with ruby annotations, short runs of text
   * presented alongside the text. This is often used in conjunction with East
   * Asian language where the annotations act as a guide for pronunciation, like
   * the Japanese furigana .
   *
   *  MDN
   */
   val ruby = "ruby".tag[dom.HTMLElement]
   /**
   * Represents the text of a ruby annotation.
   *
   *  MDN
   */
   val rt = "rt".tag[dom.HTMLElement]
   /**
   * Represents parenthesis around a ruby annotation, used to display the
   * annotation in an alternate way by browsers not supporting the standard
   * display for annotations.
   *
   *  MDN
   */
   val rp = "rp".tag[dom.HTMLElement]
   /**
   * Represents text that must be isolated from its surrounding for bidirectional
   * text formatting. It allows embedding a span of text with a different, or
   * unknown, directionality.
   *
   *  MDN
   */
   val bdi = "bdi".tag[dom.HTMLElement]
   /**
   * Represents the directionality of its children, in order to explicitly
   * override the Unicode bidirectional algorithm.
   *
   *  MDN
   */
   val bdo = "bdo".tag[dom.HTMLElement]

   // Forms

   /**
   * A key-pair generator control.
   *
   *  MDN
   */
   val keygen = "keygen".voidTag[dom.HTMLElement]
   /**
   * The result of a calculation
   *
   *  MDN
   */
   val output = "output".tag[dom.HTMLElement]
   /**
   * A progress completion bar
   *
   *  MDN
   */
   val progress = "progress".tag[dom.HTMLProgressElement]
   /**
   * A scalar measurement within a known range.
   *
   *  MDN
   */
   val meter = "meter".tag[dom.HTMLElement]


   // Interactive elements
   /**
   * A widget from which the user can obtain additional information
   * or controls.
   *
   *  MDN
   */
   val details = "details".tag[dom.HTMLElement]
   /**
   * A summary, caption, or legend for a given details.
   *
   *  MDN
   */
   val summary = "summary".tag[dom.HTMLElement]
   /**
   * A command that the user can invoke.
   *
   *  MDN
   */
   val command = "command".voidTag[dom.HTMLElement]
   /**
   * A list of commands
   *
   *  MDN
   */
   val menu = "menu".tag[dom.HTMLMenuElement]
 }
