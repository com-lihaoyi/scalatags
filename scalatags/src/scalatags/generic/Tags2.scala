package scalatags.generic

/**
 * Contains HTML tags which are used less frequently. These are generally
 * imported individually as needed.
 */
trait Tags2[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT] {

  // Document Metadata
  /**
   * Defines the title of the document, shown in a browser's title bar or on the
   * page's tab. It can only contain text and any contained tags are not
   * interpreted.
   *
   * MDN
   */
  def title: TypedTag[Builder, Output, FragT]

  /**
   * Used to write inline CSS.
   *
   *  MDN
   */
  def style: TypedTag[Builder, Output, FragT]
  // Scripting
  /**
   * Defines alternative content to display when the browser doesn't support
   * scripting.
   *
   *  MDN
   */
  def noscript: TypedTag[Builder, Output, FragT]

  // Sections
  /**
   * Represents a generic section of a document, i.e., a thematic grouping of
   * content, typically with a heading.
   *
   *  MDN
   */
  def section: TypedTag[Builder, Output, FragT]

  /**
   * Represents a section of a page that links to other pages or to parts within
   * the page: a section with navigation links.
   *
   *  MDN
   */
  def nav: TypedTag[Builder, Output, FragT]

  /**
   * Defines self-contained content that could exist independently of the rest
   * of the content.
   *
   *  MDN
   */
  def article: TypedTag[Builder, Output, FragT]

  /**
   * Defines some content loosely related to the page content. If it is removed,
   * the remaining content still makes sense.
   *
   *  MDN
   */
  def aside: TypedTag[Builder, Output, FragT]

  /**
   * Defines a section containing contact information.
   *
   *  MDN
   */
  def address: TypedTag[Builder, Output, FragT]

  /**
   * Defines the main or important content in the document. There is only one
   * main element in the document.
   *
   *  MDN
   */
  def main: TypedTag[Builder, Output, FragT]

  // Text level semantics

  /**
   * An inline quotation.
   *
   *  MDN
   */
  def q: TypedTag[Builder, Output, FragT]

  /**
   * Represents a term whose definition is contained in its nearest ancestor
   * content.
   *
   *  MDN
   */
  def dfn: TypedTag[Builder, Output, FragT]

  /**
   * An abbreviation or acronym; the expansion of the abbreviation can be
   * represented in the title attribute.
   *
   *  MDN
   */
  def abbr: TypedTag[Builder, Output, FragT]

  /**
   * Associates to its content a machine-readable equivalent.
   *
   *  MDN
   */
  def data: TypedTag[Builder, Output, FragT]

  /**
   * Represents a date and time value; the machine-readable equivalent can be
   * represented in the datetime attribetu
   *
   *  MDN
   */
  def time: TypedTag[Builder, Output, FragT]

  /**
   * Represents a variable.
   *
   *  MDN
   */
  def `var`: TypedTag[Builder, Output, FragT]

  /**
   * Represents the output of a program or a computer.
   *
   *  MDN
   */
  def samp: TypedTag[Builder, Output, FragT]

  /**
   * Represents user input, often from a keyboard, but not necessarily.
   *
   *  MDN
   */
  def kbd: TypedTag[Builder, Output, FragT]

  /**
   * Defines a mathematical formula.
   *
   *  MDN
   */
  def math: TypedTag[Builder, Output, FragT]

  /**
   * Represents text highlighted for reference purposes, that is for its
   * relevance in another context.
   *
   *  MDN
   */
  def mark: TypedTag[Builder, Output, FragT]

  /**
   * Represents content to be marked with ruby annotations, short runs of text
   * presented alongside the text. This is often used in conjunction with East
   * Asian language where the annotations act as a guide for pronunciation, like
   * the Japanese furigana .
   *
   *  MDN
   */
  def ruby: TypedTag[Builder, Output, FragT]

  /**
   * Represents the text of a ruby annotation.
   *
   *  MDN
   */
  def rt: TypedTag[Builder, Output, FragT]

  /**
   * Represents parenthesis around a ruby annotation, used to display the
   * annotation in an alternate way by browsers not supporting the standard
   * display for annotations.
   *
   *  MDN
   */
  def rp: TypedTag[Builder, Output, FragT]

  /**
   * Represents text that must be isolated from its surrounding for bidirectional
   * text formatting. It allows embedding a span of text with a different, or
   * unknown, directionality.
   *
   *  MDN
   */
  def bdi: TypedTag[Builder, Output, FragT]

  /**
   * Represents the directionality of its children, in order to explicitly
   * override the Unicode bidirectional algorithm.
   *
   *  MDN
   */
  def bdo: TypedTag[Builder, Output, FragT]

  // Forms

  /**
   * A key-pair generator control.
   *
   *  MDN
   */
  def keygen: TypedTag[Builder, Output, FragT]

  /**
   * The result of a calculation
   *
   *  MDN
   */
  def output: TypedTag[Builder, Output, FragT]

  /**
   * A progress completion bar
   *
   *  MDN
   */
  def progress: TypedTag[Builder, Output, FragT]

  /**
   * A scalar measurement within a known range.
   *
   *  MDN
   */
  def meter: TypedTag[Builder, Output, FragT]

  // Interactive elements
  /**
   * A widget from which the user can obtain additional information
   * or controls.
   *
   *  MDN
   */
  def details: TypedTag[Builder, Output, FragT]

  /**
   * A summary, caption, or legend for a given details.
   *
   *  MDN
   */
  def summary: TypedTag[Builder, Output, FragT]

  /**
   * A command that the user can invoke.
   *
   *  MDN
   */
  def command: TypedTag[Builder, Output, FragT]

  /**
   * A list of commands
   *
   *  MDN
   */
  def menu: TypedTag[Builder, Output, FragT]
}
