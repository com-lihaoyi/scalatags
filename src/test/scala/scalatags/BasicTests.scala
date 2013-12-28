package scalatags

import org.scalatest._
import Util._

class BasicTests extends FreeSpec{


  /**
   * Tests the usage of the pre-defined tags, as well as creating
   * the tags on the fly from Symbols and Strings
   */
  "basic tag creation" in {
    assert(a.toString === "<a/>")
    assert(html.toString === "<html/>")
    assert("this_is_an_unusual_tag".x.toString === "<this_is_an_unusual_tag/>")
    assert("this-is-a-string-with-dashes".x.toString === "<this-is-a-string-with-dashes/>")
  }

  /**
   * Tests nesting tags in a simple hierarchy
   */
  "structured tags" in {
    strCheck(
      html(
        head(
          script(),
          "string-tag".x()
        ),
        body(
          div(
            p()
          )
        )
      ), """<html>
              <head>
                <script/>
                <string-tag/>
              </head>
              <body>
                <div>
                  <p/>
                </div>
              </body>
            </html>"""
    )
  }


  "css chaining" in {
    strCheck(
      div.fL.color("red"), "<div style=\"float:left;color:red;\"/>"
    )
  }

  /**
   * This test uses ScalaTags to generate a large block of HTML. The
   * target HTML was taken from the Dropbox Developers API Reference
   * at:
   *
   * https://www.dropbox.com/developers/reference/api
   */
  /*"large sample" in {

    strCheck(
      div.cls("section").id("api-specification")(
        h1.margin_top("9px")("REST API"),
        p(
          "The REST API is the underlying interface for all of our official " ,
          a.href("/mobile").target("_blank")("Dropbox mobile apps"),
          " and our ",
          a.href("/developers/reference/sdk")("SDKs"),
          ". It's the most direct way to access the API. This reference document is ",
          "designed for those interested in developing for platforms not supported by ",
          "the SDKs or for those interested in exploring API features in detail."
        ),
        div.cls("api-method").id("general-notes"),
        h2.margin_top("28px").id("general-notes")("General notes"), a.cls("headerlink").href("#general-notes"),

        h3.margin_top("9px")("SSL only"),
        p("We require that all requests are done over SSL."),

        h3("App folder access type"),
        p(
          "The default root level access type, ",
          strong("app folder"), " (as described in ",
          a.href("/developers/start/core")("core concepts"),
          "), is referenced in API URLs by its codename ",
          code("sandbox"), " . This is the only place where such a distinction is made."
        ),

        h3("UTF-8 encoding"),
        p(
          "Every string passed to and from the Dropbox API needs to be ",
          "UTF-8 encoded.  For maximum compatibility, normalize to ",
          a.href("http://unicode.org/reports/tr15/").target("_blank")("Unicode Normalization Form C"),
          " (NFC) before UTF-8 encoding."
        ),

        h3("Version numbers"),
        p(
          "The current version of our API is version 1. Most ",
          a.href("/developers/reference/oldapi")("version 0 methods"),
          "will work for the time being, but some of its methods risk being ",
          "removed (most notably, the version 0 API methods ", code("/token"), " and ",
          code("/account"), ")."
        ),

        div.id("date-format")(
          h3("Date format"),
          p("All dates in the API are strings in the following format:"),
          pre.cls("literal-block")("\"Sat, 21 Aug 2010 22:31:20 +0000\""),
          p("In code format, which can be used in all programming languages that support ",
          code("strftime"), " or ", code("strptime"), ":"),
          pre.cls("literal-block")("\"%a, %d %b %Y %H:%M:%S %z\"")
        ),

        div.cls("api-method").id("param.locale"),

        h3("Locale global request parameter"),
        p(
          "Dropbox uses the ", code("locale"), " parameter to specify",
          " language settings of content responses. If your app supports",
          " any language other than English, insert the appropriate",
          a.href("http://en.wikipedia.org/wiki/IETF_language_tag").target("_blank")("IETF language tag"),
          ". When a ", a.href("/help/31").target("_blank")("supported language"),
          " is specified, Dropbox will returned translated ", code("size"),
          " and/or ", code("user_error"), " fields (where applicable)."
        )
      ),
      <div class="section " id="api-specification">
        <h1 style="margin-top: 9px; ">REST API</h1>
        <p>The REST API is the underlying interface for all of our official
          <a target="_blank" href="/mobile">Dropbox mobile apps</a> and our
          <a href="/developers/reference/sdk">SDKs</a>. It's the most direct
          way to access the API. This reference document is designed for those
          interested in developing for platforms not supported by the SDKs or
          for those interested in exploring API features in detail.</p>

        <div class="api-method " id="general-notes"></div>
        <h2 style="margin-top: 28px; " id="general-notes">General notes</h2>
        <a class="headerlink " href="#general-notes" ></a>

        <h3 style="margin-top: 9px; ">SSL only</h3>
        <p>We require that all requests are done over SSL.</p>

        <h3>App folder access type</h3>
        <p>The default root level access type, <strong>app folder</strong> (as
          described in <a href="/developers/start/core">core concepts</a>), is
          referenced in API URLs by its codename <code>sandbox</code>. This
          is the only place where such a distinction is made.</p>

        <h3>UTF-8 encoding</h3>
        <p>Every string passed to and from the Dropbox API needs to be UTF-8
          encoded.  For maximum compatibility, normalize to
          <a target="_blank" href="http://unicode.org/reports/tr15/">Unicode
            Normalization Form C</a> (NFC) before UTF-8 encoding.</p>

        <h3>Version numbers</h3>
        <p>The current version of our API is version 1. Most
          <a href="/developers/reference/oldapi">version 0 methods</a> will work
          for the time being, but some of its methods risk being removed (most
          notably, the version 0 API methods <code>/token</code> and
          <code>/account</code>).</p>

        <div id="date-format">
          <h3>Date format</h3>
          <p>All dates in the API are strings in the following format:</p>
          <pre class="literal-block ">"Sat, 21 Aug 2010 22:31:20 +0000"</pre>
          <p>In code format, which can be used in all programming languages
            that support <code>strftime</code> or <code>strptime</code>:</p>
          <pre class="literal-block ">"%a, %d %b %Y %H:%M:%S %z"</pre>
        </div>

        <div class="api-method " id="param.locale"></div>
        <h3>Locale global request parameter</h3>
        <p>Dropbox uses the <code>locale</code> parameter to specify language
          settings of content responses. If your app supports any language
          other than English, insert the appropriate
          <a target="_blank" href="http://en.wikipedia.org/wiki/IETF_language_tag">IETF
          language tag</a>. When a <a target="_blank" href="/help/31">supported
          language</a> is specified, Dropbox will returned translated
          <code>size</code> and/or <code>user_error</code> fields (where applicable).</p>
      </div>)
  }*/

}
