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
import acyclic.file
import scala.language.dynamics

/**
 * A trait for global attributes that are applicable to any HTML5 element. All traits that define Attrs should
 * derive from this trait since all groupings of attributes should include these global ones.
 */
trait GlobalAttrs[Builder, Output <: FragT, FragT] extends Util[Builder, Output, FragT] {

  /**
   * Specifies a shortcut key to activate/focus an element
   */
  val accesskey	= "accesskey".attr
  /**
   * This attribute is a space-separated list of the classes of the element.
   * Classes allows CSS and Javascript to select and access specific elements
   * via the class selectors or functions like the DOM method
   * document.getElementsByClassName. You can use cls as an alias for this
   * attribute so you don't have to backtick-escape this attribute.
   *
   * MDN
   */
  val `class` = "class".attr
  /**
   * Shorthand for the `class` attribute
   */
  val cls = `class`
  val contenteditable	= "contenteditable".attr // Specifies whether the content of an element is editable or not
  val contextmenu	= "contextmenu".attr // Specifies a context menu for an element. The context menu appears when a user right-clicks on the element
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
      AttrPair(sections.reverse.mkString("-").attr, v, ev)
  }
  def data(suffix: String) = ("data-" + suffix).attr
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
  val dir	= "dir".attr
  /**
   * A Boolean attribute that specifies whether an element is draggable or not
   */
  val draggable	= "draggable".attr
  /**
   * Specifies whether the dragged data is copied, moved, or linked, when dropped
   */
  val dropzone = "dropzone".attr
  /**
   * Specifies that an element is not yet, or is no longer, relevant and
   * consequently hidden from view of the user.
   */
  val hidden = "hidden".attr
  /**
   * This attribute defines a unique identifier (ID) which must be unique in
   * the whole document. Its purpose is to identify the element when linking
   * (using a fragment identifier), scripting, or styling (with CSS).
   *
   * MDN
   */
  val id	= "id".attr
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
  val lang = "lang".attr
  /**
   * This enumerated attribute defines whether the element may be checked for
   * spelling errors.
   *
   * MDN
   */
  val spellcheck = "spellcheck".emptyAttr
  /**
   * This attribute contains CSS styling declarations to be applied to the
   * element. Note that it is recommended for styles to be defined in a separate
   * file or files. This attribute and the style element have mainly the
   * purpose of allowing for quick styling, for example for testing purposes.
   *
   * MDN
   */
  val style	= "style".attr
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
  val tabindex = "tabindex".attr
  /**
   * This attribute contains a text representing advisory information related to
   * the element it belongs too. Such information can typically, but not
   * necessarily, be presented to the user as a tooltip.
   *
   * MDN
   */
  val title	= "title". attr
  /**
   * Specifies whether the content of an element should be translated or not
   */
  val translate	= "translate".attr
}

trait SharedEventAttrs[Builder, Output<:FragT, FragT] extends Util[Builder, Output, FragT] {
  /**
   * Script to be run when an error occurs when the file is being loaded
   */
  val onerror = "onerror".attr
}

/**
 * Clipboard Events
 */
trait ClipboardEventAttrs[Builder, Output<:FragT, FragT] extends Util[Builder, Output, FragT] {
  /**
   * Fires when the user copies the content of an element
   */
  val oncopy = "oncopy".attr
  /**
   * Fires when the user cuts the content of an element
   */
  val oncut = "oncut".attr
  /**
   * Fires when the user pastes some content in an element
   */
  val onpaste = "onpaste".attr
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
  val onabort = "onabort".attr
  /**
   * Script to be run when a file is ready to start playing (when it has buffered enough to begin)
   */
  val oncanplay = "oncanplay".attr
  /**
   * Script to be run when a file can be played all the way to the end without pausing for buffering
   */
  val oncanplaythrough = "oncanplaythrough".attr
  /**
   * Script to be run when the cue changes in a <track> element
   */
  val oncuechange = "oncuechange".attr
  /**
   * Script to be run when the length of the media changes
   */
  val ondurationchange = "ondurationchange".attr
  /**
   * Script to be run when something bad happens and the file is suddenly unavailable (like unexpectedly disconnects)
   */
  val onemptied = "onemptied".attr
  /**
   * Script to be run when the media has reach the end (a useful event for messages like "thanks for listening")
   */
  val onended = "onended".attr
  /**
   * Script to be run when media data is loaded
   */
  val onloadeddata = "onloadeddata".attr
  /**
   * Script to be run when meta data (like dimensions and duration) are loaded
   */
  val onloadedmetadata = "onloadedmetadata".attr
  /**
   * Script to be run just as the file begins to load before anything is actually loaded
   */
  val onloadstart = "onloadstart".attr
  /**
   * Script to be run when the media is paused either by the user or programmatically
   */
  val onpause = "onpause".attr
  /**
   * Script to be run when the media is ready to start playing
   */
  val onplay = "onplay".attr
  /**
   * Script to be run when the media actually has started playing
   */
  val onplaying = "onplaying".attr
  /**
   * Script to be run when the browser is in the process of getting the media data
   */
  val onprogress = "onprogress".attr
  /**
   * Script to be run each time the playback rate changes (like when a user switches to a slow motion or fast forward mode)
   */
  val onratechange = "onratechange".attr
  /**
   * Script to be run when the seeking attribute is set to false indicating that seeking has ended
   */
  val onseeked = "onseeked".attr
  /**
   * Script to be run when the seeking attribute is set to true indicating that seeking is active
   */
  val onseeking = "onseeking".attr
  /**
   * Script to be run when the browser is unable to fetch the media data for whatever reason
   */
  val onstalled = "onstalled".attr
  /**
   * Script to be run when fetching the media data is stopped before it is completely loaded for whatever reason
   */
  val onsuspend = "onsuspend".attr
  /**
   * Script to be run when the playing position has changed (like when the user fast forwards to a different point in the media)
   */
  val ontimeupdate = "ontimeupdate".attr
  /**
   * Script to be run each time the volume is changed which (includes setting the volume to "mute")
   */
  val onvolumechange = "onvolumechange".attr
  /**
   * Script to be run when the media has paused but is expected to resume (like when the media pauses to buffer more data)
   */
  val onwaiting = "onwaiting".attr
}

/**
 * Miscellaneous Events
 */
trait MiscellaneousEventAttrs[Builder, Output<:FragT, FragT] extends SharedEventAttrs[Builder, Output, FragT] {
  /**
   * Fires when a <menu> element is shown as a context menu
   */
  val onshow = "onshow".attr
  /**
   * Fires when the user opens or closes the <details> element
   */
  val ontoggle = "ontoggle".attr
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
  val onload = "onload".attr
  /**
   * Script to be run after the document is printed
   */
  val onafterprint = "onafterprint".attr
  /**
   * Script to be run before the document is printed
   */
  val onbeforeprint = "onbeforeprint".attr
  /**
   * Script to be run when the document is about to be unloaded
   */
  val onbeforeunload = "onbeforeunload".attr
  /**
   * Script to be run when there has been changes to the anchor part of the a URL
   */
  val onhashchange = "onhashchange".attr
  /**
   * Script to be run when the message is triggered
   */
  val onmessage = "onmessage".attr
  /**
   * Script to be run when the browser starts to work offline
   */
  val onoffline = "onoffline".attr
  /**
   * Script to be run when the browser starts to work online
   */
  val ononline = "ononline".attr
  /**
   * Script to be run when a user navigates away from a page
   */
  val onpagehide = "onpagehide".attr
  /**
   * Script to be run when a user navigates to a page
   */
  val onpageshow = "onpageshow".attr
  /**
   * Script to be run when the window's history changes
   */
  val onpopstate = "onpopstate".attr
  /**
   * Fires when the browser window is resized
   */
  val onresize = "onresize".attr
  /**
   * Script to be run when a Web Storage area is updated
   */
  val onstorage = "onstorage".attr
  /**
   * Fires once a page has unloaded (or the browser window has been closed)
   */
  val onunload = "onunload".attr
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
  val onblur = "onblur".attr
  /**
   * The change event is fired for input, select, and textarea elements
   * when a change to the element's value is committed by the user.
   *
   * MDN
   */
  val onchange = "onchange".attr

  /**
   * The focus event is raised when the user sets focus on the given element.
   *
   * MDN
   */
  val onfocus = "onfocus".attr
  /**
   * The select event only fires when text inside a text input or textarea is
   * selected. The event is fired after the text has been selected.
   *
   * MDN
   */
  val onselect = "onselect".attr
  /**
   * The submit event is raised when the user clicks a submit button in a form
   * (<input type="submit"/>).
   *
   * MDN
   */
  val onsubmit = "onsubmit".attr
  /**
   * The reset event is fired when a form is reset.
   *
   * MDN
   */
  val onreset = "onreset".attr
  /**
    * Script to be run when a context menu is triggered
    */
  val oncontextmenu = "oncontextmenu".attr
  /**
   * Script to be run when an element gets user input
   */
  val oninput = "oninput".attr
  /**
   * Script to be run when an element is invalid
   */
  val oninvalid = "oninvalid".attr
  /**
   * Fires when the user writes something in a search field (for <input="search">)
   */
  val onsearch = "onsearch".attr

  /**
   * Indicates a selected option in an option list of a <select> element.
   */
  val selected = "selected".attr
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
  val onkeydown = "onkeydown".attr
  /**
   * The keyup event is raised when the user releases a key that's been pressed.
   *
   * MDN
   */
  val onkeyup = "onkeyup".attr
  /**
   * The keypress event should be raised when the user presses a key on the keyboard.
   * However, not all browsers fire keypress events for certain keys.
   *
   * Webkit-based browsers (Google Chrome and Safari, for example) do not fire keypress events on the arrow keys.
   * Firefox does not fire keypress events on modifier keys like SHIFT.
   *
   * MDN
   */
  val onkeypress = "onkeypress".attr
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
  val onclick = "onclick".attr
  /**
   * The dblclick event is fired when a pointing device button (usually a
   * mouse button) is clicked twice on a single element.
   *
   * MDN
   */
  val ondblclick = "ondblclick".attr
  /**
   * Script to be run when an element is dragged
   */
  val ondrag = "ondrag".attr
  /**
   * Script to be run at the end of a drag operation
   */
  val ondragend = "ondragend".attr
  /**
   * Script to be run when an element has been dragged to a valid drop target
   */
  val ondragenter = "ondragenter".attr
  /**
   * Script to be run when an element leaves a valid drop target
   */
  val ondragleave = "ondragleave".attr
  /**
   * Script to be run when an element is being dragged over a valid drop target
   */
  val ondragover = "ondragover".attr
  /**
   * Script to be run at the start of a drag operation
   */
  val ondragstart = "ondragstart".attr
  /**
   * Script to be run when dragged element is being dropped
   */
  val ondrop = "ondrop".attr
  /**
   * The mousedown event is raised when the user presses the mouse button.
   *
   * MDN
   */
  val onmousedown = "onmousedown".attr
  /**
   * The mousemove event is raised when the user moves the mouse.
   *
   * MDN
   */
  val onmousemove = "onmousemove".attr
  /**
   * The mouseout event is raised when the mouse leaves an element (e.g, when
   * the mouse moves off of an image in the web page, the mouseout event is
   * raised for that image element).
   *
   * MDN
   */
  val onmouseout = "onmouseout".attr
  /**
   * The mouseover event is raised when the user moves the mouse over a
   * particular element.
   *
   * MDN
   */
  val onmouseover = "onmouseover".attr
  /**
   * The mouseup event is raised when the user releases the mouse button.
   *
   * MDN
   */
  val onmouseup = "onmouseup".attr
  /**
   * Specifies the function to be called when the window is scrolled.
   *
   * MDN
   */
  val onscroll = "onscroll".attr
  /**
   * Fires when the mouse wheel rolls up or down over an element
   */
  val onwheel = "onwheel".attr
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
  val action = "action".attr
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
  val autocomplete = "autocomplete".attr
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
  val autofocus = "autofocus".emptyAttr
  /**
   * When the value of the type attribute is radio or checkbox, the presence of
   * this Boolean attribute indicates that the control is selected by default;
   * otherwise it is ignored.
   *
   * MDN
   */
  val checked = "checked".attr
  /**
   * The form attribute specifies one or more forms an `<input>` element belongs to.
   */
  val formA = "form".attr // TODO: Conflicts with "form" element
  /**
   * The `formaction` attribute provides the URL that will process the input control
   * when the form is submitted and overrides the default `action` attribute of the
   * `form` element. This should be used only with `input` elements of `type`
   * submit or image.
   */
  val formaction = "formaction".attr
  /**
   * The `formenctype` attribute provides the encoding type of the form when it is
   * submitted (for forms with a method of "POST") and overrides the default
   * `enctype` attribute of the `form` element. THis should be used only with the
   * `input` elements of `type` "submit" or "image"
   */
  val formenctype = "formenctype".attr
  /**
   * The `formmethod` attribute specifies the HTTP Method the form should use when
   * it is submitted and overrides the default `method` attribute of the `form`
   * element. This should be used only with the `input` elements of `type` "submit"
   * or "image".
   */
  val formmethod = "formmethod".attr
  /**
   * The `formnovalidate` Boolean attribute specifies that the input of the form
   * should not be validated upon submit and overrides the default `novalidate`
   * attribute of the `form`. This should only be used with `input` elements of
   * of `type` "submit".
   */
  val formnovalidate = "formnovalidate".attr
  /**
   * The `formtarget` provides a name or keyword that indicates where to display
   * the response that is received after submitting the form and overrides the
   * `target` attribute of them `form` element. This should only be used with
   * the `input` elements of `type` "submit" or "image"
   */
  val formtarget = "formtarget".attr
  /**
   * The `height` attribute specifies the height of an `input` element of
   * `type` "image".
   */
  val heightA = "height".attr // TODO: Conflicts with "height" in Styles -
  /**
   * The list attribute refers to a <datalist> element that contains the options
   * for an input element the presents a select list.
   */
  val list = "list".attr
  /**
   * The max attribute specifies the maximum value for an <input> element of type
   * number, range, date, datetime, datetime-local, month, time, or week.
   */
  val max = "max".attr
  /**
   * The min attribute specifies the minimum value for an <input> element of type
   * number, range, date, datetime, datetime-local, month, time, or week.
   */
  val min = "min".attr
  /**
   * This Boolean attribute specifies, when present/true, that the user is allowed
   * to enter more than one value for the <input> element for types "email" or "file".
   * It can also be provided to the <select> element to allow selecting more than one
   * option.
   */
  val multiple = "multiple".attr
  /**
   * The maximum allowed length for the input field. This attribute forces the input control
   * to accept no more than the allowed number of characters. It does not produce any
   * feedback to the user; you must write Javascript to make that happen.
   */
  val maxlength = "maxlength".attr
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
  val method = "method".attr
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
  val name = "name".attr
  /**
   * Specifies a regular expression to validate the input. The pattern attribute
   * works with the following input types: text, search, url, tel, email, and
   * password. Use the `title` attribute to describe the pattern to the user.
   */
  val pattern = "pattern".attr
  /**
   * A hint to the user of what can be entered in the control. The placeholder
   * text must not contain carriage returns or line-feeds. This attribute
   * applies when the value of the type attribute is text, search, tel, url or
   * email; otherwise it is ignored.
   *
   * MDN
   */
  val placeholder = "placeholder".attr
  /**
   * This Boolean attribute indicates that the user cannot modify the value of
   * the control. This attribute is ignored if the value of the type attribute
   * is hidden, range, color, checkbox, radio, file, or a button type.
   *
   * MDN
   */
  val readonly = "readonly".emptyAttr
  /**
   * This attribute specifies that the user must fill in a value before
   * submitting a form. It cannot be used when the type attribute is hidden,
   * image, or a button type (submit, reset, or button). The :optional and
   * :required CSS pseudo-classes will be applied to the field as appropriate.
   *
   * MDN
   */
  val required = "required".emptyAttr
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
  val size = "size".attr
  /**
   * The step attribute specifies the numeric intervals for an <input> element
   * that should be considered legal for the input. For example, if step is 2
   * on a number typed <input> then the legal numbers could be -2, 0, 2, 4, 6
   * etc. The step attribute should be used in conjunction with the min and
   * max attributes to specify the full range and interval of the legal values.
   * The step attribute is applicable to <input> elements of the following
   * types: number, range, date, datetime, datetime-local, month, time and week.
   */
  val step = "step".attr
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
  val target = "target".attr
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
  val `type` = "type".attr
  /**
   * Shorthand for the `type` attribute
   */
  val tpe = `type`
  /**
   * The initial value of the control. This attribute is optional except when
   * the value of the type attribute is radio or checkbox.
   *
   * MDN
   */
  val value = "value".attr
  /**
   * The `width` attribute specifies the width of an `input` element of
   * `type` "image".
   */
  val widthA = "width".attr // TODO: Conflicts with "width" in Styles
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
  val href = "href".attr
  /**
   * This attribute defines the alternative text describing the image. Users
   * will see this displayed if the image URL is wrong, the image is not in one
   * of the supported formats, or until the image is downloaded.
   *
   * MDN
   */
  val alt = "alt".attr
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
  val rel = "rel".attr
  /**
   * If the value of the type attribute is image, this attribute specifies a URI
   * for the location of an image to display on the graphical submit button;
   * otherwise it is ignored.
   *
   * MDN
   */
  val src = "src".attr
  /**
   *
   */
  val xmlns = "xmlns".attr
  /**
   * If the value of the type attribute is file, this attribute indicates the
   * types of files that the server accepts; otherwise it is ignored.
   *
   * MDN
   */
  val accept = "accept".attr
  /**
   * Declares the character encoding of the page or script. Used on meta and
   * script elements.
   *
   * MDN
   */
  val charset = "charset".attr
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
  val disabled = "disabled".emptyAttr
  /**
   * Describes elements which belongs to this one. Used on labels and output
   * elements.
   *
   * MDN
   */
  val `for` = "for".attr
  /**
   * The number of visible text lines for the control.
   *
   * MDN
   */
  val rows = "rows".attr
  /**
   * The visible width of the text control, in average character widths. If it
   * is specified, it must be a positive integer. If it is not specified, the
   * default value is 20 (HTML5).
   *
   * MDN
   */
  val cols = "cols".attr
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
  val role = "role".attr
  /**
   * This attribute gives the value associated with the http-equiv or name
   * attribute, depending of the context.
   *
   * MDN
   */
  val content = "content".attr
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
  val httpEquiv = "http-equiv".attr
  /**
   * This attribute specifies the media which the linked resource applies to.
   * Its value must be a media query. This attribute is mainly useful when
   * linking to external stylesheets by allowing the user agent to pick
   * the best adapted one for the device it runs on.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/HTML/Element/link#attr-media
   */
  val media = "media".attr
  /**

   * This attribute contains a non-negative integer value that indicates for 
   * how many columns the cell extends. Its default value is 1; if its value 
   * is set to 0, it extends until the end of the <colgroup>, even if implicitly 
   * defined, that the cell belongs to. Values higher than 1000 will be considered 
   * as incorrect and will be set to the default value (1). 
   *
   * MDN
   */
  val colspan = "colspan".attr
  /**
   * This attribute contains a non-negative integer value that indicates for how many 
   * rows the cell extends. Its default value is 1; if its value is set to 0, it extends 
   * until the end of the table section (<thead>, <tbody>, <tfoot>, even if implicitly 
   * defined, that the cell belongs to. Values higher than 65534 are clipped down to 65534.
   * 
   * MDN
   */
  val rowspan = "rowspan".attr

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
    def activedescendant = "aria-activedescendant".attr
    /**
     * Indicates whether assistive technologies will present all, or only parts of, the changed region based on the change notifications defined by the aria-relevant attribute. See related aria-relevant.
     */
    def atomic = "aria-atomic".attr

    /**
     * Indicates whether user input completion suggestions are provided.
     */
    def autocomplete = "aria-autocomplete".attr
    /**
     * Indicates whether an element, and its subtree, are currently being updated.
     */
    def busy = "aria-busy".attr

    /**
     * Indicates the current "checked" state of checkboxes, radio buttons, and other widgets. See related aria-pressed and aria-selected.
     */
    def checked = "aria-checked".attr

    /**
     * Identifies the element (or elements) whose contents or presence are controlled by the current element. See related aria-owns.
     */
    def controls = "aria-controls".attr

    /**
     * Identifies the element (or elements) that describes the object. See related aria-labelledby.
     */
    def describedby = "aria-describedby".attr

    /**
     * Indicates that the element is perceivable but disabled, so it is not editable or otherwise operable. See related aria-hidden and aria-readonly.
     */
    def disabled = "aria-disabled".attr

    /**
     * Indicates what functions can be performed when the dragged object is released on the drop target. This allows assistive technologies to convey the possible drag options available to users, including whether a pop-up menu of choices is provided by the application. Typically, drop effect functions can only be provided once an object has been grabbed for a drag operation as the drop effect functions available are dependent on the object being dragged.
     */
    def dropeffect = "aria-dropeffect".attr

    /**
     * Indicates whether the element, or another grouping element it controls, is currently expanded or collapsed.
     */
    def expanded = "aria-expanded".attr

    /**
     * Identifies the next element (or elements) in an alternate reading order of content which, at the user's discretion, allows assistive technology to override the general default of reading in document source order.
     */
    def flowto = "aria-flowto".attr

    /**
     * Indicates an element's "grabbed" state in a drag-and-drop operation.
     */
    def grabbed = "aria-grabbed".attr

    /**
     * Indicates that the element has a popup context menu or sub-level menu.
     */
    def haspopup = "aria-haspopup".attr

    /**
     * Indicates that the element and all of its descendants are not visible or perceivable to any user as implemented by the author. See related aria-disabled.
     */
    def hidden = "aria-hidden".attr

    /**
     * Indicates the entered value does not conform to the format expected by the application.
     */
    def invalid = "aria-invalid".attr

    /**
     * Defines a string value that labels the current element. See related aria-labelledby.
     */
    def label = "aria-label".attr

    /**
     * Identifies the element (or elements) that labels the current element. See related aria-label and aria-describedby.
     */
    def labelledby = "aria-labelledby".attr

    /**
     * Defines the hierarchical level of an element within a structure.
     */
    def level = "aria-level".attr

    /**
     * Indicates that an element will be updated, and describes the types of updates the user agents, assistive technologies, and user can expect from the live region.
     */
    def live = "aria-live".attr

    /**
     * Indicates whether a text box accepts multiple lines of input or only a single line.
     */
    def multiline = "aria-multiline".attr

    /**
     * Indicates that the user may select more than one item from the current selectable descendants.
     */
    def multiselectable = "aria-multiselectable".attr

    /**
     * Indicates whether the element and orientation is horizontal or vertical.
     */
    def orientation = "aria-orientation".attr

    /**
     * Identifies an element (or elements) in order to define a visual, functional, or contextual parent/child relationship between DOM elements where the DOM hierarchy cannot be used to represent the relationship. See related aria-controls.
     */
    def owns = "aria-owns".attr

    /**
     * Defines an element's number or position in the current set of listitems or treeitems. Not required if all elements in the set are present in the DOM. See related aria-setsize.
     */
    def posinset = "aria-posinset".attr

    /**
     * Indicates the current "pressed" state of toggle buttons. See related aria-checked and aria-selected.
     */
    def pressed = "aria-pressed".attr

    /**
     * Indicates that the element is not editable, but is otherwise operable. See related aria-disabled.
     */
    def readonly = "aria-readonly".attr

    /**
     * Indicates what user agent change notifications (additions, removals, etc.) assistive technologies will receive within a live region. See related aria-atomic.
     */
    def relevant = "aria-relevant".attr

    /**
     * Indicates that user input is required on the element before a form may be submitted.
     */
    def required = "aria-required".attr

    /**
     * Indicates the current "selected" state of various widgets. See related aria-checked and aria-pressed.
     */
    def selected = "aria-selected".attr

    /**
     * Defines the number of items in the current set of listitems or treeitems. Not required if all elements in the set are present in the DOM. See related aria-posinset.
     */
    def setsize = "aria-setsize".attr

    /**
     * Indicates if items in a table or grid are sorted in ascending or descending order.
     */
    def sort = "aria-sort".attr

    /**
     * Defines the maximum allowed value for a range widget.
     */
    def valuemax = "aria-valuemax".attr

    /**
     * Defines the minimum allowed value for a range widget.
     */
    def valuemin = "aria-valuemin".attr

    /**
     * Defines the current value for a range widget. See related aria-valuetext.
     */
    def valuenow = "aria-valuenow".attr

    /**
     * Defines the human readable text alternative of aria-valuenow for a range widget.
     */
    def valuetext = "aria-valuetext".attr
  }
}

