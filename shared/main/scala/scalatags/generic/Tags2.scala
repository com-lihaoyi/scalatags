package scalatags.generic

import scalatags.generic.Util

/**
  * Contains HTML tags which are used less frequently. These are generally
  * imported individually as needed.
  */
trait Tags2[Builder, Output] extends Util[Builder, Output]{

   // Document Metadata
   /**
    * Defines the title of the document, shown in a browser's title bar or on the
    * page's tab. It can only contain text and any contained tags are not
    * interpreted.
    *
    * MDN
    */
   val title: TypedTag[Output, Builder]

   /**
    * Used to write inline CSS.
    *
    *  MDN
    */
   val style: TypedTag[Output, Builder]
   // Scripting
   /**
    * Defines alternative content to display when the browser doesn't support
    * scripting.
    *
    *  MDN
    */
   val noscript: TypedTag[Output, Builder]

   // Sections
   /**
    * Represents a generic section of a document, i.e., a thematic grouping of
    * content, typically with a heading.
    *
    *  MDN
    */
   val section: TypedTag[Output, Builder]
   /**
    * Represents a section of a page that links to other pages or to parts within
    * the page: a section with navigation links.
    *
    *  MDN
    */
   val nav: TypedTag[Output, Builder]
   /**
    * Defines self-contained content that could exist independently of the rest
    * of the content.
    *
    *  MDN
    */
   val article: TypedTag[Output, Builder]
   /**
    * Defines some content loosely related to the page content. If it is removed,
    * the remaining content still makes sense.
    *
    *  MDN
    */
   val aside: TypedTag[Output, Builder]
   /**
    * Defines a section containing contact information.
    *
    *  MDN
    */
   val address: TypedTag[Output, Builder]

   /**
    * Defines the main or important content in the document. There is only one
    * main element in the document.
    *
    *  MDN
    */
   val main: TypedTag[Output, Builder]

   // Text level semantics

   /**
    * An inline quotation.
    *
    *  MDN
    */
   val q: TypedTag[Output, Builder]
   /**
    * Represents a term whose definition is contained in its nearest ancestor
    * content.
    *
    *  MDN
    */
   val dfn: TypedTag[Output, Builder]
   /**
    * An abbreviation or acronym; the expansion of the abbreviation can be
    * represented in the title attribute.
    *
    *  MDN
    */
   val abbr: TypedTag[Output, Builder]
   /**
    * Associates to its content a machine-readable equivalent.
    *
    *  MDN
    */
   val data: TypedTag[Output, Builder]
   /**
    * Represents a date and time value; the machine-readable equivalent can be
    * represented in the datetime attribetu
    *
    *  MDN
    */
   val time: TypedTag[Output, Builder]
   /**
    * Represents a variable.
    *
    *  MDN
    */
   val `var`: TypedTag[Output, Builder]
   /**
    * Represents the output of a program or a computer.
    *
    *  MDN
    */
   val samp: TypedTag[Output, Builder]
   /**
    * Represents user input, often from a keyboard, but not necessarily.
    *
    *  MDN
    */
   val kbd: TypedTag[Output, Builder]

   /**
    * Defines a mathematical formula.
    *
    *  MDN
    */
   val math: TypedTag[Output, Builder]
   /**
    * Represents text highlighted for reference purposes, that is for its
    * relevance in another context.
    *
    *  MDN
    */
   val mark: TypedTag[Output, Builder]
   /**
    * Represents content to be marked with ruby annotations, short runs of text
    * presented alongside the text. This is often used in conjunction with East
    * Asian language where the annotations act as a guide for pronunciation, like
    * the Japanese furigana .
    *
    *  MDN
    */
   val ruby: TypedTag[Output, Builder]
   /**
    * Represents the text of a ruby annotation.
    *
    *  MDN
    */
   val rt: TypedTag[Output, Builder]
   /**
    * Represents parenthesis around a ruby annotation, used to display the
    * annotation in an alternate way by browsers not supporting the standard
    * display for annotations.
    *
    *  MDN
    */
   val rp: TypedTag[Output, Builder]
   /**
    * Represents text that must be isolated from its surrounding for bidirectional
    * text formatting. It allows embedding a span of text with a different, or
    * unknown, directionality.
    *
    *  MDN
    */
   val bdi: TypedTag[Output, Builder]
   /**
    * Represents the directionality of its children, in order to explicitly
    * override the Unicode bidirectional algorithm.
    *
    *  MDN
    */
   val bdo: TypedTag[Output, Builder]

   // Forms

   /**
    * A key-pair generator control.
    *
    *  MDN
    */
   val keygen: TypedTag[Output, Builder]
   /**
    * The result of a calculation
    *
    *  MDN
    */
   val output: TypedTag[Output, Builder]
   /**
    * A progress completion bar
    *
    *  MDN
    */
   val progress: TypedTag[Output, Builder]
   /**
    * A scalar measurement within a known range.
    *
    *  MDN
    */
   val meter: TypedTag[Output, Builder]


   // Interactive elements
   /**
    * A widget from which the user can obtain additional information
    * or controls.
    *
    *  MDN
    */
   val details: TypedTag[Output, Builder]
   /**
    * A summary, caption, or legend for a given details.
    *
    *  MDN
    */
   val summary: TypedTag[Output, Builder]
   /**
    * A command that the user can invoke.
    *
    *  MDN
    */
   val command: TypedTag[Output, Builder]
   /**
    * A list of commands
    *
    *  MDN
    */
   val menu: TypedTag[Output, Builder]
 }
