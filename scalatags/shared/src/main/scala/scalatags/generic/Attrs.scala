/**
 * Documentation marked "MDN" is thanks to Mozilla Contributors
 * at https://developer.mozilla.org/en-US/docs/Web/API and available
 * under the Creative Commons Attribution-ShareAlike v2.5 or later.
 * http://creativecommons.org/licenses/by-sa/2.5/
 *
 * Everything else is under the MIT License
 * http://opensource.org/licenses/MIT
 */
package scalatags
package generic
import scala.language.dynamics

/**
 * A trait for global attributes that are applicable to any HTML5 element. All traits that define Attrs should
 * derive from this trait since all groupings of attributes should include these global ones.
 */
trait GlobalAttrs[Builder, Output <: FragT, FragT]
  extends Util[Builder, Output, FragT]{

  /**
   * Specifies a shortcut key to activate/focus an element
   */
  lazy val accesskey	= attr("accesskey")
  /**
   * This attribute is a space-separated list of the classes of the element.
   * Classes allows CSS and Javascript to select and access specific elements
   * via the class selectors or functions like the DOM method
   * document.getElementsByClassName. You can use cls as an alias for this
   * attribute so you don't have to backtick-escape this attribute.
   *
   * MDN
   */
  lazy val `class` = attr("class")
  /**
   * Shorthand for the `class` attribute
   */
  lazy val cls = `class`
  lazy val contenteditable	= attr("contenteditable") // Specifies whether the content of an element is editable or not
  lazy val contextmenu	= attr("contextmenu") // Specifies a context menu for an element. The context menu appears when a user right-clicks on the element
  /**
   * This class of attributes, called custom data attributes, allows proprietary
   * information to be exchanged between the HTML and its DOM representation that
   * may be used by scripts. All such custom data are available via the HTMLElement
   * interface of the element the attribute is set on. The HTMLElement.dataset
   * property gives access to them.
   *
   * The * may be replaced by any name following the production rule of xml names
   * with the following restrictions:
   *
   * the name must not start with xml, whatever case is used for these letters;
   * the name must not contain any semicolon (U+003A);
   * the name must not contain capital A to Z letters.
   *
   * Note that the HTMLElement.dataset attribute is a StringMap and the name of the
   * custom data attribute data-test-value will be accessible via
   * HTMLElement.dataset.testValue as any dash (U+002D) is replaced by the capitalization
   * of the next letter (camelcase).
   *
   * MDN
   */
  object data extends DataAttribute(List("data"))
  class DataAttribute(sections: List[String]) extends Dynamic{
    def selectDynamic(s: String) = new DataAttribute(s :: sections)
    def :=[T](v: T)(implicit ev: AttrValue[Builder, T]) =
      AttrPair(Attr(sections.reverse.mkString("-")), v, ev)
  }
  def data(suffix: String) = Attr("data-" + suffix)
  /**
   * Specifies the text direction for the content in an element. The valid values are:
   *
   * - `ltr`	Default. Left-to-right text direction
   *
   * - `rtl`	Right-to-left text direction
   *
   * - `auto`	Let the browser figure out the text direction, based on the content,
   *          (only recommended if the text direction is unknown)
   */
  lazy val dir	= attr("dir")
  /**
   * A Boolean attribute that specifies whether an element is draggable or not
   */
  lazy val draggable	= attr("draggable").empty
  /**
   * Specifies whether the dragged data is copied, moved, or linked, when dropped
   */
  lazy val dropzone = attr("dropzone")
  /**
   * Specifies that an element is not yet, or is no longer, relevant and
   * consequently hidden from view of the user.
   */
  lazy val hidden = attr("hidden").empty
  /**
   * This attribute defines a unique identifier (ID) which must be unique in
   * the whole document. Its purpose is to identify the element when linking
   * (using a fragment identifier), scripting, or styling (with CSS).
   *
   * MDN
   */
  lazy val id	= attr("id")
  /**
   * This attribute participates in defining the language of the element, the
   * language that non-editable elements are written in or the language that
   * editable elements should be written in. The tag contains one single entry
   * value in the format defines in the Tags for Identifying Languages (BCP47)
   * IETF document. If the tag content is the empty string the language is set
   * to unknown; if the tag content is not valid, regarding to BCP47, it is set
   * to invalid.
   *
   * MDN
   */
  lazy val lang = attr("lang")
  /**
   * This enumerated attribute defines whether the element may be checked for
   * spelling errors.
   *
   * MDN
   */
  lazy val spellcheck = attr("spellcheck").empty
  /**
   * This attribute contains CSS styling declarations to be applied to the
   * element. Note that it is recommended for styles to be defined in a separate
   * file or files. This attribute and the style element have mainly the
   * purpose of allowing for quick styling, for example for testing purposes.
   *
   * MDN
   */
  lazy val style	= attr("style")
  /**
   * This integer attribute indicates if the element can take input focus (is
   * focusable), if it should participate to sequential keyboard navigation, and
   * if so, at what position. It can takes several values:
   *
   * - a negative value means that the element should be focusable, but should
   *   not be reachable via sequential keyboard navigation;
   * - 0 means that the element should be focusable and reachable via sequential
   *   keyboard navigation, but its relative order is defined by the platform
   *   convention;
   * - a positive value which means should be focusable and reachable via
   *   sequential keyboard navigation; its relative order is defined by the value
   *   of the attribute: the sequential follow the increasing number of the
   *   tabindex. If several elements share the same tabindex, their relative order
   *   follows their relative position in the document).
   *
   * An element with a 0 value, an invalid value, or no tabindex value should be placed after elements with a positive tabindex in the sequential keyboard navigation order.
   */
  lazy val tabindex = attr("tabindex")
  /**
   * This attribute contains a text representing advisory information related to
   * the element it belongs too. Such information can typically, but not
   * necessarily, be presented to the user as a tooltip.
   *
   * MDN
   */
  lazy val title = attr("title")
  /**
   * Specifies whether the content of an element should be translated or not
   */
  lazy val translate	= attr("translate").empty
}

trait SharedEventAttrs[Builder, Output<:FragT, FragT] extends Util[Builder, Output, FragT] {
  /**
   * Script to be run when an error occurs when the file is being loaded
   */
  lazy val onerror = attr("onerror")
}

/**
 * Clipboard Events
 */
trait ClipboardEventAttrs[Builder, Output<:FragT, FragT] extends Util[Builder, Output, FragT] {
  /**
   * Fires when the user copies the content of an element
   */
  lazy val oncopy = attr("oncopy")
  /**
   * Fires when the user cuts the content of an element
   */
  lazy val oncut = attr("oncut")
  /**
   * Fires when the user pastes some content in an element
   */
  lazy val onpaste = attr("onpaste")
}

/**
 * Media Events - triggered by media like videos, images and audio. These apply to
 * all HTML elements, but they are most common in media elements, like <audio>,
 * <embed>, <img>, <object>, and <video>.
 */
trait MediaEventAttrs[Builder, Output<:FragT, FragT] extends SharedEventAttrs[Builder, Output, FragT] {

  /**
   * Script to be run on abort
   */
  lazy val onabort = attr("onabort")
  /**
   * Script to be run when a file is ready to start playing (when it has buffered enough to begin)
   */
  lazy val oncanplay = attr("oncanplay")
  /**
   * Script to be run when a file can be played all the way to the end without pausing for buffering
   */
  lazy val oncanplaythrough = attr("oncanplaythrough")
  /**
   * Script to be run when the cue changes in a <track> element
   */
  lazy val oncuechange = attr("oncuechange")
  /**
   * Script to be run when the length of the media changes
   */
  lazy val ondurationchange = attr("ondurationchange")
  /**
   * Script to be run when something bad happens and the file is suddenly unavailable (like unexpectedly disconnects)
   */
  lazy val onemptied = attr("onemptied")
  /**
   * Script to be run when the media has reach the end (a useful event for messages like "thanks for listening")
   */
  lazy val onended = attr("onended")
  /**
   * Script to be run when media data is loaded
   */
  lazy val onloadeddata = attr("onloadeddata")
  /**
   * Script to be run when meta data (like dimensions and duration) are loaded
   */
  lazy val onloadedmetadata = attr("onloadedmetadata")
  /**
   * Script to be run just as the file begins to load before anything is actually loaded
   */
  lazy val onloadstart = attr("onloadstart")
  /**
   * Script to be run when the media is paused either by the user or programmatically
   */
  lazy val onpause = attr("onpause")
  /**
   * Script to be run when the media is ready to start playing
   */
  lazy val onplay = attr("onplay")
  /**
   * Script to be run when the media actually has started playing
   */
  lazy val onplaying = attr("onplaying")
  /**
   * Script to be run when the browser is in the process of getting the media data
   */
  lazy val onprogress = attr("onprogress")
  /**
   * Script to be run each time the playback rate changes (like when a user switches to a slow motion or fast forward mode)
   */
  lazy val onratechange = attr("onratechange")
  /**
   * Script to be run when the seeking attribute is set to false indicating that seeking has ended
   */
  lazy val onseeked = attr("onseeked")
  /**
   * Script to be run when the seeking attribute is set to true indicating that seeking is active
   */
  lazy val onseeking = attr("onseeking")
  /**
   * Script to be run when the browser is unable to fetch the media data for whatever reason
   */
  lazy val onstalled = attr("onstalled")
  /**
   * Script to be run when fetching the media data is stopped before it is completely loaded for whatever reason
   */
  lazy val onsuspend = attr("onsuspend")
  /**
   * Script to be run when the playing position has changed (like when the user fast forwards to a different point in the media)
   */
  lazy val ontimeupdate = attr("ontimeupdate")
  /**
   * Script to be run each time the volume is changed which (includes setting the volume to "mute")
   */
  lazy val onvolumechange = attr("onvolumechange")
  /**
   * Script to be run when the media has paused but is expected to resume (like when the media pauses to buffer more data)
   */
  lazy val onwaiting = attr("onwaiting")
}

/**
 * Miscellaneous Events
 */
trait MiscellaneousEventAttrs[Builder, Output<:FragT, FragT] extends SharedEventAttrs[Builder, Output, FragT] {
  /**
   * Fires when a <menu> element is shown as a context menu
   */
  lazy val onshow = attr("onshow")
  /**
   * Fires when the user opens or closes the <details> element
   */
  lazy val ontoggle = attr("ontoggle")
}

/**
 * Window Events
 *
 */
trait WindowEventAttrs[Builder, Output<:FragT, FragT] extends SharedEventAttrs[Builder, Output, FragT] {
  /**
   * The load event fires at the end of the document loading process. At this
   * point, all of the objects in the document are in the DOM, and all the
   * images and sub-frames have finished loading.
   *
   * MDN
   */
  lazy val onload = attr("onload")
  /**
   * Script to be run after the document is printed
   */
  lazy val onafterprint = attr("onafterprint")
  /**
   * Script to be run before the document is printed
   */
  lazy val onbeforeprint = attr("onbeforeprint")
  /**
   * Script to be run when the document is about to be unloaded
   */
  lazy val onbeforeunload = attr("onbeforeunload")
  /**
   * Script to be run when there has been changes to the anchor part of the a URL
   */
  lazy val onhashchange = attr("onhashchange")
  /**
   * Script to be run when the message is triggered
   */
  lazy val onmessage = attr("onmessage")
  /**
   * Script to be run when the browser starts to work offline
   */
  lazy val onoffline = attr("onoffline")
  /**
   * Script to be run when the browser starts to work online
   */
  lazy val ononline = attr("ononline")
  /**
   * Script to be run when a user navigates away from a page
   */
  lazy val onpagehide = attr("onpagehide")
  /**
   * Script to be run when a user navigates to a page
   */
  lazy val onpageshow = attr("onpageshow")
  /**
   * Script to be run when the window's history changes
   */
  lazy val onpopstate = attr("onpopstate")
  /**
   * Fires when the browser window is resized
   */
  lazy val onresize = attr("onresize")
  /**
   * Script to be run when a Web Storage area is updated
   */
  lazy val onstorage = attr("onstorage")
  /**
   * Fires once a page has unloaded (or the browser window has been closed)
   */
  lazy val onunload = attr("onunload")
}

/**
 * Form Events that are triggered by actions inside an HTML form. However, these events apply to almost all HTML
 * elements but are most commonly used in form elements.
 */
trait FormEventAttrs[Builder, Output<:FragT, FragT] extends Util[Builder, Output, FragT] {
  /**
   * The blur event is raised when an element loses focus.
   *
   * MDN
   */
  lazy val onblur = attr("onblur")
  /**
   * The change event is fired for input, select, and textarea elements
   * when a change to the element's value is committed by the user.
   *
   * MDN
   */
  lazy val onchange = attr("onchange")

  /**
   * The focus event is raised when the user sets focus on the given element.
   *
   * MDN
   */
  lazy val onfocus = attr("onfocus")
  /**
   * The select event only fires when text inside a text input or textarea is
   * selected. The event is fired after the text has been selected.
   *
   * MDN
   */
  lazy val onselect = attr("onselect")
  /**
   * The submit event is raised when the user clicks a submit button in a form
   * (<input type="submit"/>).
   *
   * MDN
   */
  lazy val onsubmit = attr("onsubmit")
  /**
   * The reset event is fired when a form is reset.
   *
   * MDN
   */
  lazy val onreset = attr("onreset")
  /**
    * Script to be run when a context menu is triggered
    */
  lazy val oncontextmenu = attr("oncontextmenu")
  /**
   * Script to be run when an element gets user input
   */
  lazy val oninput = attr("oninput")
  /**
   * Script to be run when an element is invalid
   */
  lazy val oninvalid = attr("oninvalid")
  /**
   * Fires when the user writes something in a search field (for <input="search">)
   */
  lazy val onsearch = attr("onsearch")

  /**
   * Indicates a selected option in an option list of a <select> element.
   */
  lazy val selected = attr("selected").empty
}

/**
 * Keyboard Events - triggered by user action son the keyboard or similar user actions
 */
trait KeyboardEventAttrs[Builder, Output<:FragT, FragT] extends Util[Builder, Output, FragT] {
  /**
   * The keydown event is raised when the user presses a keyboard key.
   *
   * MDN
   */
  lazy val onkeydown = attr("onkeydown")
  /**
   * The keyup event is raised when the user releases a key that's been pressed.
   *
   * MDN
   */
  lazy val onkeyup = attr("onkeyup")
  /**
   * The keypress event should be raised when the user presses a key on the keyboard.
   * However, not all browsers fire keypress events for certain keys.
   *
   * Webkit-based browsers (Google Chrome and Safari, for example) do not fire keypress events on the arrow keys.
   * Firefox does not fire keypress events on modifier keys like SHIFT.
   *
   * MDN
   */
  lazy val onkeypress = attr("onkeypress")
}

/**
 * Mouse Events: triggered by a mouse, or similar user actions.
 */
trait MouseEventAttrs[Builder, Output<: FragT, FragT] extends Util[Builder, Output, FragT] {
  /**
   * The click event is raised when the user clicks on an element. The click
   * event will occur after the mousedown and mouseup events.
   *
   * MDN
   */
  lazy val onclick = attr("onclick")
  /**
   * The dblclick event is fired when a pointing device button (usually a
   * mouse button) is clicked twice on a single element.
   *
   * MDN
   */
  lazy val ondblclick = attr("ondblclick")
  /**
   * Script to be run when an element is dragged
   */
  val ondrag = attr("ondrag")
  /**
   * Script to be run at the end of a drag operation
   */
  lazy val ondragend = attr("ondragend")
  /**
   * Script to be run when an element has been dragged to a valid drop target
   */
  lazy val ondragenter = attr("ondragenter")
  /**
   * Script to be run when an element leaves a valid drop target
   */
  lazy val ondragleave = attr("ondragleave")
  /**
   * Script to be run when an element is being dragged over a valid drop target
   */
  lazy val ondragover = attr("ondragover")
  /**
   * Script to be run at the start of a drag operation
   */
  lazy val ondragstart = attr("ondragstart")
  /**
   * Script to be run when dragged element is being dropped
   */
  lazy val ondrop = attr("ondrop")
  /**
   * The mousedown event is raised when the user presses the mouse button.
   *
   * MDN
   */
  lazy val onmousedown = attr("onmousedown")
  /**
   * The mousemove event is raised when the user moves the mouse.
   *
   * MDN
   */
  lazy val onmousemove = attr("onmousemove")
  /**
   * The mouseout event is raised when the mouse leaves an element (e.g, when
   * the mouse moves off of an image in the web page, the mouseout event is
   * raised for that image element).
   *
   * MDN
   */
  lazy val onmouseout = attr("onmouseout")
  /**
   * The mouseover event is raised when the user moves the mouse over a
   * particular element.
   *
   * MDN
   */
  lazy val onmouseover = attr("onmouseover")
  /**
   * The mouseup event is raised when the user releases the mouse button.
   *
   * MDN
   */
  lazy val onmouseup = attr("onmouseup")
  /**
   * Specifies the function to be called when the window is scrolled.
   *
   * MDN
   */
  lazy val onscroll = attr("onscroll")
  /**
   * Fires when the mouse wheel rolls up or down over an element
   */
  lazy val onwheel = attr("onwheel")
}

/**
 * Attributes applicable only to the input element. This set is broken out because
 * it may be useful to identify the attributes of the input element separately
 * from other groupings. The attributes permitted by the input element are
 * likely the most complex of any element in HTML5.
 */
trait InputAttrs[Builder, Output <: FragT, FragT] extends GlobalAttrs[Builder, Output, FragT] {

  /**
   * The URI of a program that processes the information submitted via the form.
   * This value can be overridden by a formaction attribute on a button or
   * input element.
   *
   * MDN
   */
  lazy val action = attr("action")
  /**
   * This attribute indicates whether the value of the control can be
   * automatically completed by the browser. This attribute is ignored if the
   * value of the type attribute is hidden, checkbox, radio, file, or a button
   * type (button, submit, reset, image).
   *
   * Possible values are "off" and "on"
   *
   * MDN
   */
  lazy val autocomplete = attr("autocomplete")
  /**
   * This Boolean attribute lets you specify that a form control should have
   * input focus when the page loads, unless the user overrides it, for example
   * by typing in a different control. Only one form element in a document can
   * have the autofocus attribute, which is a Boolean. It cannot be applied if
   * the type attribute is set to hidden (that is, you cannot automatically set
   * focus to a hidden control).
   *
   * MDN
   */
  lazy val autofocus = attr("autofocus").empty
  /**
   * When the value of the type attribute is radio or checkbox, the presence of
   * this Boolean attribute indicates that the control is selected by default;
   * otherwise it is ignored.
   *
   * MDN
   */
  lazy val checked = attr("checked").empty
  /**
    * The `enctype` attribute provides the encoding type of the form when it is
    * submitted (for forms with a method of "POST").
    */
  lazy val enctype = attr("enctype")
  /**
   * The form attribute specifies one or more forms an `<input>` element belongs to.
   */
  lazy val formA = attr("form") // TODO: Conflicts with "form" element
  /**
   * The `formaction` attribute provides the URL that will process the input control
   * when the form is submitted and overrides the default `action` attribute of the
   * `form` element. This should be used only with `input` elements of `type`
   * submit or image.
   */
  lazy val formaction = attr("formaction")
  /**
   * The `formenctype` attribute provides the encoding type of the form when it is
   * submitted (for forms with a method of "POST") and overrides the default
   * `enctype` attribute of the `form` element. This should be used only with the
   * `input` elements of `type` "submit" or "image"
   */
  lazy val formenctype = attr("formenctype")
  /**
   * The `formmethod` attribute specifies the HTTP Method the form should use when
   * it is submitted and overrides the default `method` attribute of the `form`
   * element. This should be used only with the `input` elements of `type` "submit"
   * or "image".
   */
  lazy val formmethod = attr("formmethod")
  /**
   * The `formnovalidate` Boolean attribute specifies that the input of the form
   * should not be validated upon submit and overrides the default `novalidate`
   * attribute of the `form`. This should only be used with `input` elements of
   * of `type` "submit".
   */
  lazy val formnovalidate = attr("formnovalidate")
  /**
   * The `formtarget` provides a name or keyword that indicates where to display
   * the response that is received after submitting the form and overrides the
   * `target` attribute of them `form` element. This should only be used with
   * the `input` elements of `type` "submit" or "image"
   */
  lazy val formtarget = attr("formtarget")
  /**
   * The `height` attribute specifies the height of an `input` element of
   * `type` "image".
   */
  lazy val heightA = attr("height") // TODO: Conflicts with "height" in Styles -
  /**
   * The list attribute refers to a <datalist> element that contains the options
   * for an input element the presents a select list.
   */
  lazy val list = attr("list")
  /**
   * The max attribute specifies the maximum value for an <input> element of type
   * number, range, date, datetime, datetime-local, month, time, or week.
   */
  lazy val max = attr("max")
  /**
   * The min attribute specifies the minimum value for an <input> element of type
   * number, range, date, datetime, datetime-local, month, time, or week.
   */
  lazy val min = attr("min")
  /**
   * This Boolean attribute specifies, when present/true, that the user is allowed
   * to enter more than one value for the <input> element for types "email" or "file".
   * It can also be provided to the <select> element to allow selecting more than one
   * option.
   */
  lazy val multiple = attr("multiple").empty
  /**
   * The maximum allowed length for the input field. This attribute forces the input control
   * to accept no more than the allowed number of characters. It does not produce any
   * feedback to the user; you must write Javascript to make that happen.
   */
  lazy val maxlength = attr("maxlength")
  /**
   * The HTTP method that the browser uses to submit the form. Possible values are:
   *
   * - post: Corresponds to the HTTP POST method ; form data are included in the
   *   body of the form and sent to the server.
   *
   * - get: Corresponds to the HTTP GET method; form data are appended to the
   *   action attribute URI with a '?' as a separator, and the resulting URI is
   *   sent to the server. Use this method when the form has no side-effects and
   *   contains only ASCII characters.
   *
   * This value can be overridden by a formmethod attribute on a button or
   * input element.
   *
   * MDN
   */
  lazy val method = attr("method")
  /**
   * On form elements (input etc.):
   *   Name of the element. For example used by the server to identify the fields
   *   in form submits.
   *
   * On the meta tag:
   *   This attribute defines the name of a document-level metadata.
   *   This document-level metadata name is associated with a value, contained by
   *   the content attribute.
   *
   * MDN
   */
  lazy val name = attr("name")
  /**
   * Specifies a regular expression to validate the input. The pattern attribute
   * works with the following input types: text, search, url, tel, email, and
   * password. Use the `title` attribute to describe the pattern to the user.
   */
  lazy val pattern = attr("pattern")
  /**
   * A hint to the user of what can be entered in the control. The placeholder
   * text must not contain carriage returns or line-feeds. This attribute
   * applies when the value of the type attribute is text, search, tel, url or
   * email; otherwise it is ignored.
   *
   * MDN
   */
  lazy val placeholder = attr("placeholder")
  /**
   * This Boolean attribute indicates that the user cannot modify the value of
   * the control. This attribute is ignored if the value of the type attribute
   * is hidden, range, color, checkbox, radio, file, or a button type.
   *
   * MDN
   */
  lazy val readonly = attr("readonly").empty
  /**
   * This attribute specifies that the user must fill in a value before
   * submitting a form. It cannot be used when the type attribute is hidden,
   * image, or a button type (submit, reset, or button). The :optional and
   * :required CSS pseudo-classes will be applied to the field as appropriate.
   *
   * MDN
   */
  lazy val required = attr("required").empty
  /**
   * The initial size of the control. This value is in pixels unless the value
   * of the type attribute is text or password, in which case, it is an integer
   * number of characters. Starting in HTML5, this attribute applies only when
   * the type attribute is set to text, search, tel, url, email, or password;
   * otherwise it is ignored. In addition, the size must be greater than zero.
   * If you don't specify a size, a default value of 20 is used.
   *
   * MDN
   */
  lazy val size = attr("size")
  /**
   * The step attribute specifies the numeric intervals for an <input> element
   * that should be considered legal for the input. For example, if step is 2
   * on a number typed <input> then the legal numbers could be -2, 0, 2, 4, 6
   * etc. The step attribute should be used in conjunction with the min and
   * max attributes to specify the full range and interval of the legal values.
   * The step attribute is applicable to <input> elements of the following
   * types: number, range, date, datetime, datetime-local, month, time and week.
   */
  lazy val step = attr("step")
  /**
   * A name or keyword indicating where to display the response that is received
   * after submitting the form. In HTML 4, this is the name of, or a keyword
   * for, a frame. In HTML5, it is a name of, or keyword for, a browsing context
   * (for example, tab, window, or inline frame). The following keywords have
   * special meanings:
   *
   * - _self: Load the response into the same HTML 4 frame (or HTML5 browsing
   *   context) as the current one. This value is the default if the attribute
   *   is not specified.
   * - _blank: Load the response into a new unnamed HTML 4 window or HTML5
   *   browsing context.
   * - _parent: Load the response into the HTML 4 frameset parent of the current
   *   frame or HTML5 parent browsing context of the current one. If there is no
   *   parent, this option behaves the same way as _self.
   * - _top: HTML 4: Load the response into the full, original window, canceling
   *   all other frames. HTML5: Load the response into the top-level browsing
   *   context (that is, the browsing context that is an ancestor of the current
   *   one, and has no parent). If there is no parent, this option behaves the
   *   same way as _self.
   * - iframename: The response is displayed in a named iframe.
   */
  lazy val target = attr("target")
  /**
   * This attribute is used to define the type of the content linked to. The
   * value of the attribute should be a MIME type such as text/html, text/css,
   * and so on. The common use of this attribute is to define the type of style
   * sheet linked and the most common current value is text/css, which indicates
   * a Cascading Style Sheet format. You can use tpe as an alias for this
   * attribute so you don't have to backtick-escape this attribute.
   *
   * MDN
   */
  lazy val `type` = attr("type")
  /**
   * Shorthand for the `type` attribute
   */
  lazy val tpe = `type`
  /**
   * The initial value of the control. This attribute is optional except when
   * the value of the type attribute is radio or checkbox.
   *
   * MDN
   */
  lazy val value = attr("value")
  /**
   * The `width` attribute specifies the width of an `input` element of
   * `type` "image".
   */
  lazy val widthA = attr("width") // TODO: Conflicts with "width" in Styles
}

/**
 * Trait containing the contents of the [[Attrs]] module, so they can be
 * mixed in to other objects if needed. This should contain "all" attributes
 * and mix in other traits (defined above) as needed to get full coverage.
 */
trait Attrs[Builder, Output <: FragT, FragT] extends InputAttrs[Builder, Output, FragT]
  with ClipboardEventAttrs[Builder, Output, FragT]
  with MediaEventAttrs[Builder, Output, FragT]
  with MiscellaneousEventAttrs[Builder, Output, FragT]
  with KeyboardEventAttrs[Builder, Output, FragT]
  with MouseEventAttrs[Builder, Output, FragT]
  with WindowEventAttrs[Builder, Output, FragT]
  with FormEventAttrs[Builder, Output, FragT]
{
  /**
   * This is the single required attribute for anchors defining a hypertext
   * source link. It indicates the link target, either a URL or a URL fragment.
   * A URL fragment is a name preceded by a hash mark (#), which specifies an
   * internal target location (an ID) within the current document. URLs are not
   * restricted to Web (HTTP)-based documents. URLs might use any protocol
   * supported by the browser. For example, file, ftp, and mailto work in most
   * user agents.
   *
   * MDN
   */
  lazy val href = attr("href")
  /**
   * This attribute defines the alternative text describing the image. Users
   * will see this displayed if the image URL is wrong, the image is not in one
   * of the supported formats, or until the image is downloaded.
   *
   * MDN
   */
  lazy val alt = attr("alt")
  /**
   * This attribute names a relationship of the linked document to the current
   * document. The attribute must be a space-separated list of the link types
   * values. The most common use of this attribute is to specify a link to an
   * external style sheet: the rel attribute is set to stylesheet, and the href
   * attribute is set to the URL of an external style sheet to format the page.
   *
   *
   * MDN
   */
  lazy val rel = attr("rel")
  /**
   * If the value of the type attribute is image, this attribute specifies a URI
   * for the location of an image to display on the graphical submit button;
   * otherwise it is ignored.
   *
   * MDN
   */
  lazy val src = attr("src")
  /**
   *
   */
  lazy val xmlns = attr("xmlns")
  /**
   * If the value of the type attribute is file, this attribute indicates the
   * types of files that the server accepts; otherwise it is ignored.
   *
   * MDN
   */
  lazy val accept = attr("accept")
  /**
   * Declares the character encoding of the page or script. Used on meta and
   * script elements.
   *
   * MDN
   */
  lazy val charset = attr("charset")
  /**
   * This Boolean attribute indicates that the form control is not available for
   * interaction. In particular, the click event will not be dispatched on
   * disabled controls. Also, a disabled control's value isn't submitted with
   * the form.
   *
   * This attribute is ignored if the value of the type attribute is hidden.
   *
   * MDN
   */
  lazy val disabled = attr("disabled").empty
  /**
   * Describes elements which belongs to this one. Used on labels and output
   * elements.
   *
   * MDN
   */
  lazy val `for` = attr("for")
  /**
   * The number of visible text lines for the control.
   *
   * MDN
   */
  lazy val rows = attr("rows")
  /**
   * The visible width of the text control, in average character widths. If it
   * is specified, it must be a positive integer. If it is not specified, the
   * default value is 20 (HTML5).
   *
   * MDN
   */
  lazy val cols = attr("cols")
  /**
   * The attribute describes the role(s) the current element plays in the
   * context of the document. This can be used, for example,
   * by applications and assistive technologies to determine the purpose of
   * an element. This could allow a user to make informed decisions on which
   * actions may be taken on an element and activate the selected action in a
   * device independent way. It could also be used as a mechanism for
   * annotating portions of a document in a domain specific way (e.g.,
   * a legal term taxonomy). Although the role attribute may be used to add
   * semantics to an element, authors should use elements with inherent
   * semantics, such as p, rather than layering semantics on semantically
   * neutral elements, such as div role="paragraph".
   *
   * See: [[http://www.w3.org/TR/role-attribute/#s_role_module_attributes]]
   */
  lazy val role = attr("role")
  /**
   * This attribute gives the value associated with the http-equiv or name
   * attribute, depending of the context.
   *
   * MDN
   */
  lazy val content = attr("content")
  /**
   * This enumerated attribute defines the pragma that can alter servers and
   * user-agents behavior. The value of the pragma is defined using the content
   * attribute and can be one of the following:
   *
   *   - content-language
   *   - content-type
   *   - default-style
   *   - refresh
   *   - set-cookie
   *
   * MDN
   */
  lazy val httpEquiv = attr("http-equiv")
  /**
   * This attribute specifies the media which the linked resource applies to.
   * Its value must be a media query. This attribute is mainly useful when
   * linking to external stylesheets by allowing the user agent to pick
   * the best adapted one for the device it runs on.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/link#attr-media
   */
  lazy val media = attr("media")
  /**

   * This attribute contains a non-negative integer value that indicates for 
   * how many columns the cell extends. Its default value is 1; if its value 
   * is set to 0, it extends until the end of the <colgroup>, even if implicitly 
   * defined, that the cell belongs to. Values higher than 1000 will be considered 
   * as incorrect and will be set to the default value (1). 
   *
   * MDN
   */
  lazy val colspan = attr("colspan")
  /**
   * This attribute contains a non-negative integer value that indicates for how many 
   * rows the cell extends. Its default value is 1; if its value is set to 0, it extends 
   * until the end of the table section (<thead>, <tbody>, <tfoot>, even if implicitly 
   * defined, that the cell belongs to. Values higher than 65534 are clipped down to 65534.
   * 
   * MDN
   */
  lazy val rowspan = attr("rowspan")

  /**
   * ARIA is a set of special accessibility attributes which can be added
   * to any markup, but is especially suited to HTML. The role attribute
   * defines what the general type of object is (such as an article, alert,
   * or slider). Additional ARIA attributes provide other useful properties,
   * such as a description for a form or the current value of a progressbar.
   *
   * MDN
   */
  object aria{
    /**
     * Identifies the currently active descendant of a composite widget.
     */
    lazy val activedescendant = attr("aria-activedescendant")
    /**
     * Indicates whether assistive technologies will present all, or only parts of, the changed region based on the change notifications defined by the aria-relevant attribute. See related aria-relevant.
     */
    lazy val atomic = attr("aria-atomic")

    /**
     * Indicates whether user input completion suggestions are provided.
     */
    lazy val autocomplete = attr("aria-autocomplete")
    /**
     * Indicates whether an element, and its subtree, are currently being updated.
     */
    lazy val busy = attr("aria-busy")

    /**
     * Indicates the current "checked" state of checkboxes, radio buttons, and other widgets. See related aria-pressed and aria-selected.
     */
    lazy val checked = attr("aria-checked")

    /**
     * Identifies the element (or elements) whose contents or presence are controlled by the current element. See related aria-owns.
     */
    lazy val controls = attr("aria-controls")

    /**
     * Identifies the element (or elements) that describes the object. See related aria-labelledby.
     */
    lazy val describedby = attr("aria-describedby")

    /**
     * Indicates that the element is perceivable but disabled, so it is not editable or otherwise operable. See related aria-hidden and aria-readonly.
     */
    lazy val disabled = attr("aria-disabled")

    /**
     * Indicates what functions can be performed when the dragged object is released on the drop target. This allows assistive technologies to convey the possible drag options available to users, including whether a pop-up menu of choices is provided by the application. Typically, drop effect functions can only be provided once an object has been grabbed for a drag operation as the drop effect functions available are dependent on the object being dragged.
     */
    lazy val dropeffect = attr("aria-dropeffect")

    /**
     * Indicates whether the element, or another grouping element it controls, is currently expanded or collapsed.
     */
    lazy val expanded = attr("aria-expanded")

    /**
     * Identifies the next element (or elements) in an alternate reading order of content which, at the user's discretion, allows assistive technology to override the general default of reading in document source order.
     */
    lazy val flowto = attr("aria-flowto")

    /**
     * Indicates an element's "grabbed" state in a drag-and-drop operation.
     */
    lazy val grabbed = attr("aria-grabbed")

    /**
     * Indicates that the element has a popup context menu or sub-level menu.
     */
    lazy val haspopup = attr("aria-haspopup")

    /**
     * Indicates that the element and all of its descendants are not visible or perceivable to any user as implemented by the author. See related aria-disabled.
     */
    lazy val hidden = attr("aria-hidden")

    /**
     * Indicates the entered value does not conform to the format expected by the application.
     */
    lazy val invalid = attr("aria-invalid")

    /**
     * Defines a string value that labels the current element. See related aria-labelledby.
     */
    lazy val label = attr("aria-label")

    /**
     * Identifies the element (or elements) that labels the current element. See related aria-label and aria-describedby.
     */
    lazy val labelledby = attr("aria-labelledby")

    /**
     * Defines the hierarchical level of an element within a structure.
     */
    lazy val level = attr("aria-level")

    /**
     * Indicates that an element will be updated, and describes the types of updates the user agents, assistive technologies, and user can expect from the live region.
     */
    lazy val live = attr("aria-live")

    /**
     * Indicates whether a text box accepts multiple lines of input or only a single line.
     */
    lazy val multiline = attr("aria-multiline")

    /**
     * Indicates that the user may select more than one item from the current selectable descendants.
     */
    lazy val multiselectable = attr("aria-multiselectable")

    /**
     * Indicates whether the element and orientation is horizontal or vertical.
     */
    lazy val orientation = attr("aria-orientation")

    /**
     * Identifies an element (or elements) in order to define a visual, functional, or contextual parent/child relationship between DOM elements where the DOM hierarchy cannot be used to represent the relationship. See related aria-controls.
     */
    lazy val owns = attr("aria-owns")

    /**
     * Defines an element's number or position in the current set of listitems or treeitems. Not required if all elements in the set are present in the DOM. See related aria-setsize.
     */
    lazy val posinset = attr("aria-posinset")

    /**
     * Indicates the current "pressed" state of toggle buttons. See related aria-checked and aria-selected.
     */
    lazy val pressed = attr("aria-pressed")

    /**
     * Indicates that the element is not editable, but is otherwise operable. See related aria-disabled.
     */
    lazy val readonly = attr("aria-readonly")

    /**
     * Indicates what user agent change notifications (additions, removals, etc.) assistive technologies will receive within a live region. See related aria-atomic.
     */
    lazy val relevant = attr("aria-relevant")

    /**
     * Indicates that user input is required on the element before a form may be submitted.
     */
    lazy val required = attr("aria-required")

    /**
     * Indicates the current "selected" state of various widgets. See related aria-checked and aria-pressed.
     */
    lazy val selected = attr("aria-selected")

    /**
     * Defines the number of items in the current set of listitems or treeitems. Not required if all elements in the set are present in the DOM. See related aria-posinset.
     */
    lazy val setsize = attr("aria-setsize")

    /**
     * Indicates if items in a table or grid are sorted in ascending or descending order.
     */
    lazy val sort = attr("aria-sort")

    /**
     * Defines the maximum allowed value for a range widget.
     */
    lazy val valuemax = attr("aria-valuemax")

    /**
     * Defines the minimum allowed value for a range widget.
     */
    lazy val valuemin = attr("aria-valuemin")

    /**
     * Defines the current value for a range widget. See related aria-valuetext.
     */
    lazy val valuenow = attr("aria-valuenow")

    /**
     * Defines the human readable text alternative of aria-valuenow for a range widget.
     */
    lazy val valuetext = attr("aria-valuetext")
  }

  /**
   * For use in &lt;style&gt; tags.
   *
   * If this attribute is present, then the style applies only to its parent element.
   * If absent, the style applies to the whole document.
   */
  lazy val scoped = attr("scoped")

  /**
   * For use in &lt;meter&gt; tags.
   *
   * @see https://css-tricks.com/html5-meter-element/
   */
  lazy val high = attr("high")

  /**
   * For use in &lt;meter&gt; tags.
   *
   * @see https://css-tricks.com/html5-meter-element/
   */
  lazy val low = attr("low")

  /**
   * For use in &lt;meter&gt; tags.
   *
   * @see https://css-tricks.com/html5-meter-element/
   */
  lazy val optimum = attr("optimum")

  /** IE-specific property to prevent user selection */
  lazy val unselectable = attr("unselectable")
}

