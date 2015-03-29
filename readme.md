ScalaTags 0.5.1
===============

ScalaTags is a small, [fast](#performance) XML/HTML/CSS construction library for [Scala](http://www.scala-lang.org/) that takes fragments in plain Scala code that look like this:

```scala
html(
  head(
    script(src:="..."),
    script(
      "alert('Hello World')"
    )
  ),
  body(
    div(
      h1(id:="title", "This is a title"),
      p("This is a big paragraph of text")
    )
  )
)
```

And turns them into HTML like this:

```html
<html>
    <head>
        <script src="..."></script>
        <script>alert('Hello World')</script>
    </head>
    <body>
        <div>
            <h1 id="title">This is a title</h1>
            <p>This is a big paragraph of text</p>
        </div>
    </body>
</html>
```
Contents
========

- [Mailing List](https://groups.google.com/forum/#!forum/scalatags)
- [Getting Started](#getting-started)
- [ScalaJS](#scalajs)
- [Why Scalatags](#why-scalatags)
- [Basic Examples](#basic-examples)
  - [Hello World](#hello-world)
  - [Attributes](#attributes)
  - [CSS and Classes](#css--classes)
  - [Non-String Attributes and Styles](#non-string-attributes-and-styles)
  - [Managing Imports](#managing-imports)
  - [Variables](#variables)
  - [Control Flow](#control-flow)
  - [Functions](#functions)
  - [Auto-escaping and unsanitized Input](#auto-escaping-and-unsanitized-input)
  - [Layouts](#layouts)
  - [Inheritance](#inheritance)
  - [Data and Aria](#data-and-aria)
- [DOM Backend](#dom-backend)
  - [Using the DOM](#using-the-dom)
- [CSS Stylesheets](#css)
- [Performance](#performance)
- [Internals](#internals)
  - [Architecture](#architecture)
  - [Extensibility](#extensibility)
  - [Cross-backend Code](#cross-backend-code)
- [Prior Work](#prior-work)
  - [Old-school Templates](#old-school-templates)
  - [Razor and Play Templates](#razor-and-play-templates)
  - [XHP and Pyxl](#xhp-and-pyxl)
  - [Scalatags](#scalatags-1)
- [License](#license)
- [Scaladoc](http://lihaoyi.github.io/scalatags/#package)


Getting Started
===============

ScalaTags is hosted on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cscalatags); to get started, simply add the following to your `build.sbt`:

```scala
libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.5.1"
```

And you're good to go! Open up a `sbt console` and you can start working through the [Examples](#Examples), which should just work when copied and pasted into the console.

ScalaJS
=======

To use Scalatags with a ScalaJS project, add the following to the `built.sbt` of your ScalaJS project:

```scala
libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.5.1"
```

And you should be good to go generating HTML fragments in the browser! Scalatags has no dependencies, and so all the examples should work right off the bat whether run in Chrome, Firefox or Rhino. Scalatags 0.5.1 is currently only compatibly with ScalaJS 0.5.3+.

Why Scalatags
=============

The core functionality of Scalatags is a [tiny amount of code](shared/main/scala/scalatags/generic/Core.scala), and yet it provides all the functionality of large frameworks like Python's [Jinja2](http://jinja.pocoo.org/docs/sandbox/) or C#'s [Razor](http://msdn.microsoft.com/en-us/vs2010trainingcourse_aspnetmvc3razor.aspx), and out-performs the competition by a [large margin](#performance). It does this by leveraging the functionality of the Scala language to do almost *everything*, meaning you don't need to learn a second template pseudo-language just to stitch your HTML fragments together

Since ScalaTags is pure Scala, any editor that understands Scala will understand scalatags.Text. Not only do you get syntax highlighting, you also get code completion:

![Autocomplete](docs/Autocomplete.png)

and Error Highlighting:

![Error Highlighting](docs/ErrorHighlighting.png)

and in-editor documentation:

![Inline Documentation](docs/InlineDocs.png)

And all the other good things (<em>jump to definition</em>, *extract method*, etc.) you're used to in a statically typed language. No more digging around in templates which mess up the highlighting of your HTML editor, or waiting months for the correct plugin to materialize.

Take a look at the [prior work](#prior-work) section for a more detailed analysis of Scalatags in comparison to other popular libraries.

Basic Examples
==============

This is a bunch of simple examples to get you started using Scalatags. These examples are all in the [unit tests](shared/test/scala/scalatags/ExampleTests.scala). For more examples, take a look at the [demo page](http://lihaoyi.github.io/scalatags/demo.html), backed by the code in [example/](https://github.com/lihaoyi/scalatags/blob/master/example/src/main/scala/example/ScalaJSExample.scala)

Hello World
-----------

```scala
import scalatags.Text.all._

val frag = html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p("This is my first paragraph"),
      p("This is my second paragraph")
    )
  )
)
```

The core of Scalatags is a way to generate (X)HTML fragments using plain Scala. This is done by values such as `head`, `script`, and so on, which are automatically imported into your program when you `import scalatags.Text.all._`. See [below](#managing-imports) if you want more fine-grained control over what's imported.

This example code creates a Scalatags fragment. We could do many things with a fragment: store it, return it, etc. since it's just a normal Scala value. Eventually, though, you will want to convert it into HTML. To do this, simply use:

```scala
frag.toString
```

Which will give you a String containing the HTML representation:

```html
<html><head><script>some script</script></head><body><h1>This is my title</h1><div><p>This is my first paragraph</p><p>This is my second paragraph</p></div></body></html>
```

This representation omits unnecesary whitespace. To improve readability we could pretty-print it using some XML processing:

```scala
val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(scala.xml.XML.loadString(frag.toString)))
```

Executing the above prints out:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <div>
            <p>This is my first paragraph</p>
            <p>This is my second paragraph</p>
        </div>
    </body>
</html>
```

If you want to add a doctype to your HTML fragment, simply add it as a string:

```scala
"<!DOCTYPE html>" + html(
  ...
)
```

The following examples will simply show the initial Scalatag fragment and the final pretty-printed HTML, skipping the intermediate steps.

Attributes
----------

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p(onclick:="... do some js")(
        "This is my first paragraph"
      ),
      a(href:="www.google.com")(
        p("Goooogle")
      )
    )
  )
)
```

In Scalatags, each attribute has an associated value which can be used to set it. This example shows how you set the `onclick` and `href` attributes with the `:=` operator. Attributes are all instances of the [Attr](http://lihaoyi.github.io/scalatags/#scalatags.generic.Attr) class.

The common HTML attributes all have static values to use in your fragments, and the list can be seen [here](http://lihaoyi.github.io/scalatags/#scalatags.generic.Attrs). This keeps things concise and statically checked. However, inevitably you'll want to set some attribute which isn't in the initial list defined by scalatags.Text. This can be done with the `.attr` method that Scalatags adds to Strings:

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p("onclick".attr:="... do some js")(
        "This is my first paragraph"
      ),
      a("href".attr:="www.google.com")(
        p("Goooogle")
      )
    )
  )
)
```

If you wish to, you can also take the result of the `.attr` call and assign it to a variable for you to use later in an identical way.

Both the above examples print the same thing:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <div>
            <p onclick="... do some js">
                This is my first paragraph
            </p>
            <a href="www.google.com">
                <p>Goooogle</p>
            </a>
        </div>
    </body>
</html>
```

CSS & Classes
-------------

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1(backgroundColor:="blue", color:="red")("This is my title"),
    div(backgroundColor:="blue", color:="red")(
      p(cls := "contentpara first")(
        "This is my first paragraph"
      ),
      a(opacity:=0.9)(
        p(cls := "contentpara")("Goooogle")
      )
    )
  )
)
```

In HTML, `style` attributes are often thought of not as normal attributes (which contain strings), but as lists of key-value pairs. Furthermore, there is a large but finite number of styles, and a style cannot simply be any arbitrary string. The above example shows how CSS classes and inline-styles are typically set.

Note that in this case, `backgroundColor`, `color` and `opacity` are all statically typed identifiers. These are all instances of [Style](http://lihaoyi.github.io/scalatags/#scalatags.generic.Style)) are [defined by Scalatags](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles).

Of course, `style` is in the end just an attribute, so you can treat it as such and bind them directly without any fuss:

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1(style:="background-color: blue; color: red;")("This is my title"),
    div(style:="background-color: blue; color: red;")(
      p(`class`:="contentpara first")(
        "This is my first paragraph"
      ),
      a(style:="opacity: 0.9;")(
        p(cls := "contentpara")("Goooogle")
      )
    )
  )
)
```

Both the above examples print the same thing:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1 style="background-color: blue; color: red;">This is my title</h1>
        <div style="background-color: blue; color: red;">
        <p class="contentpara first">This is my first paragraph</p>
        <a style="opacity: 0.9;">
            <p class="contentpara">Goooogle</p>
        </a>
        </div>
    </body>
</html>
```

A list of the attributes and styles provided by ScalaTags can be found in [Attrs](http://lihaoyi.github.io/scalatags/#scalatags.generic.Attrs) and [Styles](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles). This of course won't include any which you define yourself.


Non-String Attributes and Styles
================================

```scala
div(
  p(float.left)(
    "This is my first paragraph"
  ),
  a(tabindex:=10)(
    p("Goooogle")
  ),
  input(disabled:=true)
)
```

Not all attributes and styles take strings; some, like `float`, have an enumeration of valid values, and can be referenced by `float.left`, `float.right`, etc.. Others, like `tabindex` or `disabled`, take Ints and Booleans respectively. These are used directly as shown in the example above. 

Even for styles or attributes which take values other than strings (e.g. `tabindex`), the `:=` operator can still be used to force assignment:

```scala
div(
  p(float:="left")(
    "This is my first paragraph"
  ),
  a(tabindex:="10")(
    p("Goooogle")
  ),
  input(disabled:="true")
)
```

Both of the above examples print the same thing:

```html
<div>
    <p style="float: left;">This is my first paragraph</p>
    <a tabindex="10">
        <p>Goooogle</p>
    </a>
    <input disabled="true" />
</div>
```

Passing in numbers to the styles which require a `px` at the end results in the `px` being appended automatically:

```scala
div(width:=100, zIndex:=100, padding:=100)
```
```html
<div style="width: 100px; z-index: 100; padding: 100px;"></div>
```

Note how this doesn't affect other styles (e.g. `z-index`) which don't need this suffix. Generally, just pass in whatever you want and it'll do the right thing.

Boolean attributes such as `readonly` can be passed in alone:

```scala
div(input(readonly))
```
```html
<div><input readonly="readonly" /></div>
```

And will default to a value with the same name as the attribute. You can still set the value manually with `:=` if you wish

```scala
div(input(readonly:=1))
```
```html
<div><input readonly="1" /></div>
```
Managing Imports
================

```scala
import scalatags.Text.{attrs => a, styles => s, _}
import scalatags.Text.tags._
div(
  p(s.color:="red")("Red Text"),
  img(a.href:="www.imgur.com/picture.jpg")
)
```

Apart from using `import scalatags.Text.all._`, it is possible to perform the imports manually, renaming whatever you feel like renaming. The example above provides an example which imports all HTML tags, but imports [Attrs](http://lihaoyi.github.io/scalatags/#scalatags.generic.Attrs) and [Styles](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles) aliased rather than dumping their contents into your global namespace. This helps avoid polluting your namespace with lots of common names (e.g. `value`, `id`, etc.) that you may not use.

The main objects which you can import things from are:

- [tags](http://lihaoyi.github.io/scalatags/#scalatags.generic.Tags): common HTML tags
- [tags2](http://lihaoyi.github.io/scalatags/#scalatags.generic.Tags2): less common HTML tags
- [attrs](http://lihaoyi.github.io/scalatags/#scalatags.generic.Attrs): common HTML attributes
- [styles](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles): common CSS styles
- [styles2](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles2): less common CSS styles
- [svgTags](http://lihaoyi.github.io/scalatags/#scalatags.generic.Svg): SVG tags
- [svgAttrs](http://lihaoyi.github.io/scalatags/#scalatags.generic.SvgAttrs): attributes only associated with SVG elements
- [DataConverters](http://lihaoyi.github.io/scalatags/#scalatags.generic.DataConverters): convenient extensions (e.g. `10.px`) to create the CSS datatypes

You can pick and choose exactly which bits you want to import, or you can use one of the provided aggregates:

- [all](http://lihaoyi.github.io/scalatags/#scalatags.Text$$all$): this imports the contents of [Tags](http://lihaoyi.github.io/scalatags/#scalatags.generic.Tags), [Attrs](http://lihaoyi.github.io/scalatags/#scalatags.Text.Attrs), [Styles](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles) and [DataConverters](http://lihaoyi.github.io/scalatags/#scalatags.generic.DataConverters)
- [short](http://lihaoyi.github.io/scalatags/#scalatags.Text$$short$): this imports the contents of [Tags](http://lihaoyi.github.io/scalatags/#scalatags.generic.generic.Tags) and [DataConverters](http://lihaoyi.github.io/scalatags/#scalatags.generic.DataConverters), but aliases [Attrs](http://lihaoyi.github.io/scalatags/#scalatags.generic.Attrs) and [Styles](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles) as `*`

Thus, you can choose exactly what you want to import, and how:

```scala
import scalatags.Text.{attrs => attr, styles => css, _}
import scalatags.Text.tags._
div(
  p(css.color:="red")("Red Text"),
  img(attr.href:="www.imgur.com/picture.jpg")
)
```

Or you can rely on a aggregator like [all](http://lihaoyi.github.io/scalatags/#scalatags.Text$$all$) (which the rest of the examples use) or [short](http://lihaoyi.github.io/scalatags/#scalatags.Text$$short$). [short](http://lihaoyi.github.io/scalatags/#scalatags.Text$$short$) imports [Attrs](http://lihaoyi.github.io/scalatags/#scalatags.generic.Attrs) and [Styles](http://lihaoyi.github.io/scalatags/#scalatags.generic.Styles) as `*`, making them quick to access without cluttering the global namespace:

```scala
import scalatags.Text.short._
div(
  p(*.color:="red")("Red Text"),
  img(*.href:="www.imgur.com/picture.jpg")
)
```

Both these examples print:

```html
<div>
    <p style="color: red;">Red Text</p>
    <img href="www.imgur.com/picture.jpg" />
</div>
```

This custom import object also provides you a place to define your own custom tags that will be imported throughout your project e.g. a `js(s: String)` tag as shorthand for `script(src:=s)`. You can even override the default definitions, e.g. making `script` be a shorthand for `script(type:="javascript")` so that you can never forget to use your own custom version.

The lesser used tags or styles are generally not imported wholesale by default, but you can always import the ones you need:

```scala
import Styles2.pageBreakBefore
import Tags2.address
import scalatags.JsDom.svgTags._
import scalatags.JsDom.svgAttrs._
div(
  p(pageBreakBefore.always, "a long paragraph which should not be broken"),
  address("500 Memorial Drive, Cambridge MA"),
  svg(stroke:="blue")
)
```

This prints:

```scala
<div>
    <p style="page-break-before: always;">
        a long paragraph which should not be broken
    </p>
    <address>500 Memorial Drive, Cambridge MA</address>
    <svg stroke="blue" />
</div>
```

Custom Bundles
==============

```scala
object CustomBundle extends Text.Cap with text.Tags with text.Tags2 with Text.Aggregate{
  object st extends Text.Cap with Text.Styles with Text.Styles2
  object at extends Text.Cap with Text.Attrs
}

import CustomBundle._

html(
  head(
    script("some script")
  ),
  body(
    h1(st.backgroundColor:="blue", st.color:="red")("This is my title"),
    div(st.backgroundColor:="blue", st.color:="red")(
      p(at.cls := "contentpara first")(
        "This is my first paragraph"
      ),
      a(st.opacity:=0.9)(
        p(at.cls := "contentpara")("Goooogle")
      )
    )
  )
)
```

In addition to importing things piecemeal from various pre-defined namespaces, Scalatags allows you to build a custom bundle which can be used to provide a single-import syntax for whatever import convention you're using. For example, the above snippet sets up a custom bundle which dumps `Tags` and `Tags2` in the local namespace, assigns `Styles` and `Styles2` to the `st` object, `Attrs` to the `at` object, and ignores Svg-related styles and tags entirely. This lets you enforce a particular convention without having to duplicate the same import-renamings in multiple files in your application.

The above snippet prints the following:
```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1 style="background-color: blue; color: red;">This is my title</h1>
        <div style="background-color: blue; color: red;">
        <p class="contentpara first">This is my first paragraph</p>
        <a style="opacity: 0.9;">
            <p class="contentpara">Goooogle</p>
        </a>
        </div>
    </body>
</html>
```

Despite its usefulness in enforcing a particular import convention, custom bundles are completely interoperable with each other or with the default `all` and `short` bundles, and the above snippet could equally be written as:

```scala
import CustomBundle.{st, at}
import Text.all._

CustomBundle.html(
  head(
    script("some script")
  ),
  CustomBundle.body(
    h1(backgroundColor:="blue", st.color:="red")("This is my title"),
    div(st.backgroundColor:="blue", color:="red")(
      p(cls := "contentpara first")(
        "This is my first paragraph"
      ),
      CustomBundle.a(st.opacity:=0.9)(
        p(at.cls := "contentpara")("Goooogle")
      )
    )
  )
)
```

Mixing both things from the `all` bundle as well as `st` and `at` from our own `CustomBundle`. That's not to say you *should* do this, but if for some reason if e.g. you're using different conventions for different source files, you can be sure that they'll work together just fine.

Variables
=========

```scala
val title = "title"
val numVisitors = 1023

html(
  head(
    script("some script")
  ),
  body(
    h1("This is my ", title),
    div(
      p("This is my first paragraph"),
      p("you are the ", numVisitors.toString, "th visitor!")
    )
  )
)
```

Variables can be inserted into the templates as Strings, simply by adding them to an element's children. This prints:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <div>
            <p>This is my first paragraph</p>
            <p>you are the 1023th visitor!</p>
        </div>
    </body>
</html>
```

Control Flow
------------

```scala
val numVisitors = 1023
val posts = Seq(
  ("alice", "i like pie"),
  ("bob", "pie is evil i hate you"),
  ("charlie", "i like pie and pie is evil, i hat myself")
)

html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div("posts"),
    for ((name, text) <- posts) yield div(
        h2("Post by ", name),
        p(text)
    ),
    if(numVisitors > 100) p("No more posts!")
    else p("Please post below...")
  )
)
```

Like most other XML templating languages, ScalaTags contains control flow statements like `if` and `for`. Unlike other templating languages which have their own [crufty little programming language embedded inside them for control flow](http://jinja.pocoo.org/docs/templates/#list-of-control-structures), you probably already know how to use ScalaTags' control flow syntax. It's just Scala after all.

This prints out:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <div>posts</div>
        <div>
            <h2>Post by alice</h2>
            <p>i like pie</p>
        </div>
        <div>
            <h2>Post by bob</h2>
            <p>pie is evil i hate you</p>
        </div>
        <div>
            <h2>Post by charlie</h2>
            <p>i like pie and pie is evil, i hat myself</p>
        </div>
        <p>No more posts!</p>
    </body>
</html>
```

Functions
---------

```scala
def imgBox(source: String, text: String) = div(
  img(src:=source),
  div(
    p(text)
  )
)

html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    imgBox("www.mysite.com/imageOne.png", "This is the first image displayed on the site"),
    div(`class`:="content")(
      p("blah blah blah i am text"),
      imgBox("www.mysite.com/imageTwo.png", "This image is very interesting")
    )
  )
)
```

Many other templating systems define [incredibly](http://guides.rubyonrails.org/layouts_and_rendering.html#using-partials) [roundabout](http://jinja.pocoo.org/docs/templates/#macros) ways of creating re-usable parts of the template. In ScalaTags, we don't need to re-invent the wheel, because Scala has these amazing things called *functions*.

The above example prints:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <div>
            <img src="www.mysite.com/imageOne.png" />
            <div>
                <p>This is the first image displayed on the site</p>
            </div>
        </div>
        <div class="content">
            <p>blah blah blah i am text</p>
            <div>
                <img src="www.mysite.com/imageTwo.png" />
            <div>
                <p>This image is very interesting</p>
            </div>
            </div>
        </div>
    </body>
</html>
```

Auto-escaping and unsanitized Input
-----------------------------------

```scala
val evilInput1 = "\"><script>alert('hello!')</script>"
val evilInput2 = "<script>alert('hello!')</script>"

html(
  head(
    script("some script")
  ),
  body(
    h1(
      title:=evilInput1,
      "This is my title"
    ),
    evilInput2
  )
)
```

By default, any text that's put into the Scalatags templates, whether as a attribute value or a text node, is properly escaped when it is rendered. Thus, when you run the following snippet, you get this:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1 title="&quot;&gt;&lt;script&gt;alert('hello!')&lt;/script&gt;">
            This is my title
        </h1>
        &lt;script&gt;alert('hello!')&lt;/script&gt;
    </body>
</html>
```

As you can see, the contents of the variables `evilInput1` and `evilInput2` have been HTML-escaped, so you do not have to worry about un-escaped user input messing up your DOM or causing XSS injections. Furthermore, the names of the tags (e.g. "html") and attributes (e.g. "href") are themselves validated: passing in an invalid name to either of those (e.g. a tag or attribute name with a space inside) will throw an `IllegalArgumentException`).

If you *really* want, for whatever reason, to put unsanitized input into your HTML, simply surround the string with a `raw` tag:

```scala
val evilInput = "<script>alert('hello!')</script>"

html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    raw(evilInput)
  )
)
```

prints

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <script>alert('hello!')</script>
    </body>
</html>
```

As you can see, the `<script>` tags in `evilInput` have been passed through to the resultant HTML string unchanged. Although this makes it easy to open up XSS holes (as shown above!), if you know what you're doing, go ahead.

There isn't any way to put unescaped text inside tag names, attribute names, or attribute values.

Layouts
-------

```scala
def page(scripts: Seq[Frag], content: Seq[Frag]) =
  html(
    head(scripts),
    body(
      h1("This is my title"),
      div(cls:="content")(content)
    )
  )


page(
  Seq(
    script("some script")
  ),
  Seq(
    p("This is the first ", b("image"), " displayed on the ", a("site")),
    img(src:="www.myImage.com/image.jpg"),
    p("blah blah blah i am text")
  )
)
```

Again, this is something that many other templating languages have their own [special](http://guides.rubyonrails.org/layouts_and_rendering.html#using-nested-layouts) [implementations](http://jinja.pocoo.org/docs/templates/#template-inheritance) of. In ScalaTags, this can be done simply by just using functions! The above snippet gives you:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
            <div class="content">
            <p>This is the first <b>image</b> displayed on the <a>site</a></p>
                <img src="www.myImage.com/image.jpg" />
            <p>blah blah blah i am text</p>
        </div>
    </body>
</html>
```

Inheritance
-----------

```scala
class Parent{
  def render = html(
    headFrag,
    bodyFrag

  )
  def headFrag = head(
    script("some script")
  )
  def bodyFrag = body(
    h1("This is my title"),
    div(
      p("This is my first paragraph"),
      p("This is my second paragraph")
    )
  )
}

object Child extends Parent{
  override def headFrag = head(
    script("some other script")
  )
}

Child.render
```

Most of the time, functions are sufficient to keep things DRY, but if for some reason you want to use inheritance to structure your code, you probably already know how to do so. Again, unlike [other](http://wsgiarea.pocoo.org/jinja/docs/inheritance.html) [frameworks](http://docs.makotemplates.org/en/latest/inheritance.html) that have implemented complex inheritance systems themselves, Scalatags is just Scala, and it behaves as you'd expect.

```html
<html>
    <head>
        <script>some other script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <div>
            <p>This is my first paragraph</p>
            <p>This is my second paragraph</p>
        </div>
    </body>
</html>
```

Data and Aria
=============

Data attributes work by separating the sections of the attribute with `.` instead of `-`:

```scala
div(
  id:="electriccars",
  data.columns:="3",
  data.index.number:="12314",
  data.parent:="cars",
  "..."
)
```
```html
<div
  id="electriccars"
  data-columns="3"
  data-index-number="12314"
  data-parent="cars">
  ...
</div>
```
Aria attributes work too:

```scala
div(
  div(id:="ch1Panel", role:="tabpanel", aria.labelledby:="ch1Tab")(
    "Chapter 1 content goes here"
  ),
  div(id:="ch2Panel", role:="tabpanel", aria.labelledby:="ch2Tab")(
    "Chapter 2 content goes here"
  ),
  div(id:="quizPanel", role:="tabpanel", aria.labelledby:="quizTab")(
    "Quiz content goes here"
  )
)
```

```html
<div>
  <div id="ch1Panel" role="tabpanel" aria-labelledby="ch1Tab">
    Chapter 1 content goes here
  </div>
  <div id="ch2Panel" role="tabpanel" aria-labelledby="ch2Tab">
    Chapter 2 content goes here
  </div>
  <div id="quizPanel" role="tabpanel" aria-labelledby="quizTab">
    Quiz content goes here
  </div>
</div>
```

DOM Backend
===========
Although Scalatags was originally a HTML-String generation library, it now ships with an additional backend that runs only on ScalaJS, available by replacing 

```scala
import scalatags.Text._
```

with

```scala
import scalatags.JsDom._
```

This gives you a version of Scalatags that renders directly to `dom.Element`s, rather than spitting out strings. With the DOM backend, `toString` still works and generates a HTML string, but is implemented by constructing a tree of `dom.Element`s and calling `.outerHTML` on them, and the resultant string may have subtle differences (e.g. order of attributes, or whitespace between styles) from the Text backend.

Using the DOM
-------------

The DOM backend provides an additional method `.render` on all Scalatags fragments, which converts the fragment into a DOM tree:

```scala
val elem = div.render
println(elem.children.length) // 0
elem.appendChild(p("omg", "wtf", "bbq").render)
println(elem.children.length) // 1
val pElem = elem.children(0).asInstanceOf[dom.HTMLParagraphElement]
println(pElem.childNodes.length) // 3
println(pElem.textContent) // "omgwtfbbq"
```

As you can see, you can manipulate DOM elements directly, calling standard DOM APIs like `.children`, `.appendChild`, etc.

In addition to the default ability to splice `String`s, `Boolean`s and `Int`s into the Scalatags fragment, the DOM backend allows you to bind arbitrary `js.Any`-convertible objects, e.g. the function literal shown below:
  
```scala
var count = 0
val elem = div(
  onclick := {() => count += 1},
  tabindex := 1
).render

assert(count == 0)
elem.onclick(null)
assert(count == 1)
```

As you can see, the function literal is kept intact rather than being serialized (as you would expect in the Text backend). When called via the attribute of the element, you can see that it functions intact and properly increments the counter.

Use Cases
---------
Having direct access to the DOM allows a great deal of flexibility that you do not have when working with strings. For example, the example below defines an input-label pair which clears the label when you focus on the input, a common pattern:
 
```scala
val labelElem = label("Default").render

val inputElem = input(
  `type`:="text",
  onfocus := { () => labelElem.textContent = ""}
).render

val box = div(
  inputElem,
  labelElem
).render

println(labelElem.textContent) // "Default"
inputElem.onfocus(null)
println(labelElem.textContent) // ""
```

This allows a very convenient direct-binding of behaviors without having to trawl through the DOM with jquery selectors or creating lots of IDs and worrying about collisions. 

-----------

The DOM backend currently requires you to separately add a dependency on [scala-js-dom](https://github.com/scala-js/scala-js-dom) for it to function. 

CSS
===

Scalatags offsets an internal DSL for constructing CSS Stylesheets. The basic construction looks like:

```scala
import scalatags.stylesheet._
val Simple = Sheet[Simple]
trait Simple extends StyleSheet{
  def x = cls(
    backgroundColor := "red",
    height := 125
  )
  def y = cls.hover(
    opacity := 0.5
  )

  def z = cls(x.splice, y.splice)
}
```

This generates a stylesheet that looks like

```css
.$pkg-Simple-x{
  background-color: red;
  height: 125px;
}
.$pkg-Simple-y:hover{
  opacity: 0.5;
}
.$pkg-Simple-z{
  background-color: red;
  height: 125px;
  opacity: 0.5;
}
```

Note that you need to use a separate `trait` and `val` in order for this to work; you cannot just have an `object Simple` extend `StyleSheet` directly.
 
You can then access `Simple.styleSheetText` in order to do things with the generated stylesheet text. Exactly what you want to do is up to you: In Scala-JVM you will likely want to save it to a file (to be served later) or inlined into some HTML fragment, while in Scala.js you may insert it into the page directly via a `script` tag.

Only `cls`s defined on `trait Simple` are gathered up as part of the generated stylesheet. By default, the name of each class is constructed via the names `$pkg-$class-$def`. You can override `customSheetName` to replace the `$pkg-$class` part with something else.

Once the stylesheet is defined, you can then immediately start using styles within your Scalatags fragments, just like any other `Modifier`:


```scala
val x = div(
  Simple.x,
  Simple.y
)
```
```html
<div class="pkg-Simple-x pkg-Simple-y"></div>
```

By default, Scalatag's `StyleSheet`s have no cascading: you only can define styles for a single class (and it's pseudo-selectors) at a single time. If you want to define styles for multiple tags without a larger HTML fragment, you should define multiple classes. The fact that Scalatag's `cls` definitions are just normal `def`s makes managing these classes very easy: you can use standard IDE's or tools to jump-to-definitions, auto-rename them, etc.. Many common mistakes in CSS, such as accidentally mis-spelling a class-name or botching a renaming, become compilation errors.

Since Scalatags `StyleSheet`s are just Scala, you can feel free to use normal Scala techniques (constants, functions, traits, etc.) to DRY up your code without having to learn any special Scalatags-specific mechanisms.

Inline Modifiers
----------------

The above example contains a single `hover` pseudo-selector, but what if you want the same class to have a whole range of pseudo-selectors? To do that, simply nest the selectors inline inside the `cls` fragment:

```scala
val Inline = Sheet[Inline]
trait Inline extends StyleSheet{
  def w = cls(
    &.hover(
      backgroundColor := "red"
    ),
    &.active(
      backgroundColor := "blue"
    ),
    &.hover.active(
      backgroundColor := "yellow"
    ),
    opacity := 0.5
  )
}
```

This generates the stylesheet:

```css
.$pkg-Inline-w{
  opacity: 0.5;
}
.$pkg-Inline-w:hover{
  background-color: red;
}
.$pkg-Inline-w:active{
  background-color: blue;
}
.$pkg-Inline-w:hover:active{
  background-color: yellow;
}
```

Cascading
---------

Occassionally, you really do want your CSS stylesheets to cascade. This may be e.g. because you want to define styles for a large HTML document, and don't want to repeat the "default" classes throughout your HTML. In order to do this with Scalatags, you have to opt in by inheriting from `CascadingStyleSheet`:

```scala
val Cascade = Sheet[Cascade]
trait Cascade extends CascadingStyleSheet{
  def y = cls()
  def x = cls(
    a(
      backgroundColor := "red",
      textDecoration.none
    ),
    a.hover(
      backgroundColor := "blue",
      textDecoration.underline
    ),
    (a.hover ~ div ~ y)(
      opacity := 0
    ),
    div.hover(
      div(
        y(
          opacity := 0
        )
      )
    )
  )
}
```

This generates:

```css
.$pkg-Cascade-x a{
  background-color: red;
  text-decoration: none;
}
.$pkg-Cascade-x a:hover{
  background-color: blue;
  text-decoration: underline;
}
.$pkg-Cascade-x a:hover div .$pkg-Cascade-y{
  opacity: 0;
}
.$pkg-Cascade-x div:hover div .$pkg-Cascade-y{
  opacity: 0;
}
```

With Scalatags, even cascadin styles must start with a "root" class. However,inside that class, you then have two ways of defining cascades:

- Via nesting: you can nest Scalatags HTML tags such as `a` or `div`, or other pre-defined classes such as `Cascade.y`
- Via the `~` or `>` operators, for defining a chain with or without the `>` child-selector

You shouldn't need cascading often, but when you do, it's useful to have around. Scalatags gives you the best of both worlds by exposing it where necessary but preventing you from using it by accident.

Performance
===========

| Template Engine  | Renders     |
| ---------------- | -----------:|
| Scalatags        |   7436041   |
| scala-xml        |   3794707   |
| Twirl            |   1902274   |
| Scalate-Mustache |    500975   |
| Scalate-Jade     |    396224   |

These numbers are the number of times each template engine is able to render (to a String) a simple, dynamic HTML fragment in 60 seconds.

The fragment (shown below) is designed to exercise a bunch of different functionality in each template engine: functions/partials, loops, value-interpolation, etc.. The templates were structured identically despite the different languages used by the various engines. All templates were loaded and rendered once before the benchmarking begun, to allow for any file-operations/pre-compilation to happen.

The numbers speak for themselves; Scalatags is almost twice as fast as splicing/serializing `scala-xml` literals, almost four times as fast as [Twirl](http://www.playframework.com/documentation/2.2.x/ScalaTemplates), and 10-15 times as fast as the various [Scalate](http://scalate.fusesource.org/) alternatives. This is likely due to overhead from the somewhat bloated data structures used by `scala-xml` (which Twirl also uses) and the heavy-use of dictionaries used to implement the custom scoping in the Scalate templates. Although this is a microbenchmark, and probably does not perfectly match real-world usage patterns, the conclusion is pretty clear: Scalatags is fast!

This is the Scalatags fragment that was rendered:

```scala

def para(n: Int) = p(
  cls:="contentpara",
  title:=s"this is paragraph $n"
)

val titleString = "This is my title"
val firstParaString = "This is my first paragraph"

html(
  head(
    script("console.log(1)")
  ),
  body(
    h1(color:="red")(titleString),
    div(backgroundColor:="blue")(
      para(0)(
        cls := "contentpara first",
        firstParaString
      ),
      a(href:="www.google.com")(
        p("Goooogle")
      ),
      for(i <- 0 until 5) yield para(i)(
        s"Paragraph $i",
        color:=(if (i % 2 == 0) "red" else "green")
      )
    )
  )
).toString()
```

The rest of the code involved in this micro-benchmark can be found in [PerfTests.scala](shared/test/scala/scalatags/PerfTests.scala)


Internals
=========

The primary data structure Scalatags uses are the `TypedTag`:

```scala
trait TypedTag[Builder, +Output, FragT] extends Frag[Builder, FragT]{
  def tag: String

  /**
   * The modifiers that are applied to a TypedTag are kept in this linked-Seq
   * (which are actually WrappedArrays) data-structure in order for maximum
   * performance.
   */
  def modifiers: List[Seq[Modifier[Builder]]]

  /**
   * Add the given modifications (e.g. additional children, or new attributes)
   * to the [[TypedTag]].
   */
  def apply(xs: Modifier[Builder]*): Self

  /**
   * Collapses this scalatags tag tree and returns an [[Output]]
   */
  def render: Output
}
```

The `Modifier`:

```scala
trait Modifier[Builder] {
  /**
   * Applies this modifier to the specified [[Builder]], such that when
   * rendering is complete the effect of adding this modifier can be seen.
   */
  def applyTo(t: Builder): Unit
}
```

And the `Frag`:

```scala
trait Frag[Builder, FragT] extends Modifier[Builder]{
  def render: FragT
}
```


A `TypedTag` is basically a tag-name together with a loose bag of `Modifier`s, and is itself a `Modifier` so it can be nested within other `TypedTag`s. A `Modifier` is a tag, a sequence of tags, an attribute binding, a style binding, or anything else that can be used to modify how a tag will be rendered. Lastly, a `Frag` represents the smallest standalone atom, which includes tags, loose strings, numbers, and other things. 

In the text backend you have the alias

```scala
type Tag = TypedTag[String]
```

Since everything renders to a string. In the DOM backend you have:

```scala
type HtmlTag = JsDom.TypedTag[dom.HTMLElement]
type SvgTag = JsDom.TypedTag[dom.SVGElement]
type Tag = JsDom.TypedTag[dom.Element]
```

These aliases help you keep your code short by letting you refer to the most common versions of `TypedTag` via concise names.

Each Scalatags backend has its own refinements, e.g. `Text.TypedTag`, `Text.Frag` and `Text.Modifier` have the `Builder` type-parameter fixed as `text.Builder`, and the `Output` type-parameter fixed as `String`. Their `JsDom.*` counterparts have `Builder` fixed as `dom.Element`, and `Output`fixed to various subclasses of `dom.Element`. The various other classes/traits (e.g. `Attr`, `AttrPair`, StylePair`, etc.) are similarly abstract with concrete versions in each backend. 

The current selection of `Modifier` (or implicitly convertable) types include

- `TypedTag`s and `String`s: appends itself to the parent's `children` list.
- `AttrPair`s: sets a key in the parent's `attrs` list.
- `StylePair`s: appends the inline `style: value;` to the parent's `style` attribute.
- `StringFrag`s: append the string as a child.
- `RawFrag`s: append the string to the parent as unescaped HTML.

The bulk of Scalatag's ~5000 lines of code is static bindings (and inline documentation!) for the myriad of CSS rules and HTML tags and attributes that exist. The core of Scalatags lives in [Core.scala](shared/main/scala/scalatags/Core.scala), with most of the implicit extensions and conversions living in [package.scala](shared/main/scala/scalatags/package.scala).

Architecture
------------

Scalatags has pretty odd internals in order to support code re-use between the Text and Dom packages. Essentially, each Scalatags package is an instance of 
 
```scala
trait Bundle[Builder, Output <: FragT, FragT]{
```

Which is parametrized on the `Builder` used to generate the output, the type generated by rendering a `Frag`, as well as the final `Output` type generated by rendering a `TypedTag`. The Text package is defined as 
  
```scala
object Text extends Bundle[text.Builder, String, String] {
```

Since it uses a custom `text.Builder` object for maximum performance and spits out a `String`, while the Dom package is defined as as 

```scala
object JsDom extends generic.Bundle[dom.Element, dom.Element, dom.Node]
```

Since it uses `dom.Element`s both as the intermediate builder as well as the final result.

This layout allows Scalatags to specify formally which types are common between the two backends (i.e. those in `generic.Bundle`) and which can vary. For example, both backends have a concept of `TypedTag`s, `Frag`s and `Modifier`s. On the other hand, The Text backend has `TypedTag[String]` aliased as `Tag` since it will always be `String`, while the Dom backend has it left as

```scala
TypedTag[+Output <: dom.Element]
```

With helper types bound as:

```scala
type HtmlTag = TypedTag[dom.HTMLElement]
type SvgTag = TypedTag[dom.SVGElement]
type Tag = TypedTag[dom.Element]
```

Since it is likely that working with the Dom backend you will want to distinguish these.

Extensibility
-------------

A mix of typeclasses and implicit conversions is used to control what you can put into a Scalatags fragment. For example, the typeclasses

```scala
trait AttrValue[Builder, T]{
  def apply(t: Builder, a: Attr, v: T)
}
trait StyleValue[Builder, T]{
  def apply(t: Builder, s: Style, v: T)
}
```

Allow you to specify what (and how) types can be used as attributes and styles respectively, while implicit conversions to `Modifier` or `Frag` are used to allow you to use arbitrary types as children. The use of implicit conversions in this case is to allow it to work with variable length argument lists (i.e. `(mods: *Modifier)`), which is difficult to do with typeclasses.

Due to this design, and the parametrization of the bundles described earlier, it is possible to define behavior for a particular type only where it makes sense. e.g. there is a pair of typeclass instances
 
```scala
implicit object bindJsAny extends generic.AttrValue[dom.Element, js.Any]
implicit def bindJsAnyLike[T <% js.Any] = new generic.AttrValue[dom.Element, T]
```

Which allows you to bind anything convertible to a `js.Any` into the JsDom fragments, since they can just be assigned directly to the attributes of the `dom.Element` objects. Doing the same thing for Text fragments doesn't make sense, and would correctly fail to compile. 

You can easier add other typeclass instances to handle binding e.g. `Future`s (which will add a child or set an attr/style on completion), or reactive variables (which would constantly update the child/attr/style every time it changes).

Cross-backend Code
------------------

If you wish to, it is possible to write code that is generic against the Scalatags backend used, and can be compiled and run on both Text and JsDom backends at the same time! This is done by adding an explicit dependency on `generic.Bundle[Builder, Output, FragT]`, e.g. how it is done in the unit tests:
 
```scala
class ExampleTests[Builder, Output, FragT](bundle: Bundle[Builder, Output, FragT]) extends TestSuite{
  import bundle._
  ...
}
```

Inside this scope, you are limited to only using the common functionality defined in `generic.Bundle`, and can't use any Text or JsDom specific APIs. However, in exchange you can write code that works in either backend, by instantiating it with the respective bundle:
 
```scala
object ExampleTests extends generic.ExampleTests(scalatags.Text)
object ExampleTests extends generic.ExampleTests(scalatags.JsDom)
```

This is currently used to shared the bulk of unit tests between the Text and JsDom backends, and could be useful in other scenarios where you may want to swap between them (e.g. using Text on the server, and JsDom on the client where it's available) while sharing as much code as possible. 

For a concrete, self-contained example of this, look at the [demo page](https://github.com/lihaoyi/scalatags/blob/master/example/src/main/scala/example/ScalaJSExample.scala).

Prior Work
==========

Scalatags was made after experience with a broad range of HTML generation systems. This experience (with both the pros and cons of existing systems) informed the design of scalatags.Text.

Old-school Templates
--------------------

[Jinja2](http://jinja.pocoo.org/docs/) is the templating engine that comes bundled with [Flask](http://flask.pocoo.org/), a similar (but somewhat weaker) system comes bundled with [Django](https://docs.djangoproject.com/en/dev/topics/templates/), and another system in a similar vein is [Ruby on Rail's ERB](http://guides.rubyonrails.org/layouts_and_rendering.html) rendering engine. This spread more-or-less represents the old-school way of rendering HTML, in that they:

- Are effectively string-based
- Use special syntax for both interpolating variables as well as for basic control flow logic
- Have a ruby/python-like (but not quite!) language for logic within the template
- Are generally with one template per file.

They also showcase many of the weaknesses of this style of templating system:

- The fact that it's string based means it's vulnerable to XSS injections, or plain-old malformed HTML output.
- The one-template-per-file rule discourages you from building your page from small re-usable fragments, because who wants to keep track of hundreds of individual files. People are reluctant to make a file with 3-5 lines in it, which is understandable but unfortunate because factoring templates into re-usable 3-5 line snippets is a good way of staying sane.
- The API is complex and novel: Jinja2 for example contains logic around [file-loading](http://jinja.pocoo.org/docs/api/#loaders) & [caching](http://jinja.pocoo.org/docs/api/#bytecode-cache), as well as custom Jinja2-specific ways of doing [loops](http://jinja.pocoo.org/docs/templates/#for), [conditionals](http://jinja.pocoo.org/docs/templates/#if), [functions](http://jinja.pocoo.org/docs/templates/#macros), [comments](http://jinja.pocoo.org/docs/templates/#comments), [inheritance](http://jinja.pocoo.org/docs/templates/#template-inheritance), [scoping](http://jinja.pocoo.org/docs/templates/#block-nesting-and-scope), [imports](http://jinja.pocoo.org/docs/templates/#import-context-behavior) and other things. All these are things you would have to learn.
- The syntax is completely new; finding an editor that properly supports all the quirks and semantics (or even simply highlighting things properly) is hard. You could hack together something quick and extremely fragile, or you could wait ages for a solid plugin to materialize.
- Abstraction is clunky: inbuilt tags are used via `<div />`, while user-defined components are called e.g. by `{{ component() }}`. You're left with a choice between not using much abstraction and mainly sticking to inbuilt tags, or creating components and accepting the fact that your templates will basically be totally composed of `{{ curly braces }}`. Neither choice is satisfying.
- Have no sort of static checking at all; you're just passing dicts around, and waiting for silly mistakes to blow up at run-time so you can fix them.

Razor and Play Templates
------------------------
[Razor](https://github.com/Antaris/RazorEngine) (the ASP.NET MVC template engine) and the Play framework's [template engine](http://www.playframework.com/documentation/2.3-SNAPSHOT/ScalaTemplates) go in a new direction. Their templates generally:

- Are statically-compiled
- Re-use large chunks of the host language

Both templating systems generally use `@` to delimit "code"; e.g. `@for(...){...}` declares a for-loop. Nice things are:

- The API is far simpler: all the custom control-flow/logic/syntax basically collapses into the simple statement "do it the way C#/Scala does it".
- Static checking in templates is nice.

However, they still have their downsides:

- Abstractions are less clunky to use than in old-school templates, e.g. `@component()` rather than `{{ component() }}`, but still not ideal.
- The one-template-per-file rule is still there, making abstractions clunky to define.
- They still require their own special build-system-integration, run-time compilation, caching, and other things, which a templating language really shouldn't need. [Scalate](http://scalate.fusesource.org/index.html), which has a runtime dependency on the entire Scala compiler, suffers from the same problem.
- The syntax still poses a problem for editors; both HTML editors and C#/Scala editors won't want to work with these templates, so you still end up with sub-par support or waiting for plugins.
- You still end up with weird [edge](http://stackoverflow.com/questions/13973009/complex-pattern-matching-on-templace-using-scala-play) [cases](http://stackoverflow.com/questions/12070625/compilation-error-of-play-framework-templates) due to the fact that you're squashing together two completely unrelated syntaxes.

XHP and Pyxl
------------
[XHP](https://github.com/facebook/xhp) and [Pyxl](https://github.com/dropbox/pyxl) are HTML generation systems used at Facebook and Dropbox. In short, they allow you to:

- Embed sections of your HTML as literals within your PHP and Python code
- Reference them as objects (e.g. calling methods and modifying fields)
- Provide a way to interpolate values and combine them.

The Pyxl homepage provides this example:

```python
image_name = "bolton.png"
image = <img src="/static/images/{image_name}" />

text = "Michael Bolton"
block = <div>{image}{text}</div>

element_list = [image, text]
block2 = <div>{element_list}</div>
```

Which shows how you can generate HTML directly in your python code, using your python variables. These libraries are basically the same thing, and have some nice properties:

- The one-template-per-file rule is gone! This encourages you to make more, smaller fragments and then compose them, which is great.
- They're no longer string-based, so you won't have problems with XSS or malformed output.
- The API is really simple; maybe a dozen different things you need to remember, and the rest is "just use Python/PHP". E.g. they don't need to load templates from the filesystem any more (with all the associated discovery/loading/caching logic) since it's all just in your code.
- The syntax is completely familiar too; apart from maybe one new rule (using `{...}` to interpolate values) the rest of your templates and logic are bog-standard HTML/Python/PHP.
- Abstraction is almost seamless; both systems allow you to define custom components and have them called via `<component arg="..." />`

But they're not quite there:

- Although the syntax is familiar to you, it's not familiar to your editor, and probably will mess up your syntax highlighting (and other tooling) in your Python/PHP files.
- Defining custom components (e.g. in [Pyxl](https://github.com/dropbox/pyxl#ui-modules)) is much more verbose/tedious than it needs to be. Most of the time, all you want is a function; very rarely do you want a fragment that is long lived and has mutable state, which is where classes/objects are really necessary.
- Even using custom components gets tedious; at some scale, everything you pass into a component will be a structured Python value rather than a string, and you end up with code like `<component arg1="{value1}" arg2="{value2}" arg3="{value3}" arg4="{value4}" />`. It's nice to have inbuilt/custom components behave uniformly, but you wonder what the XML syntax is really buying you other than forcing you to only use keyword-arguments and to wrap arguments in `"{...}"`.
- It's still XML! People spend [a lot](https://github.com/visionmedia/jade) [of time](http://haml.info/) trying to get XML out of their templates; it seems odd to spend just as much time trying to put it into your source code.

Scalatags
---------

And that's why I created Scalatags:

- Structured and immune to malformed output/XSS.
- No more one-template-per-file rule; make small templates to your heart's content!
- Dead-simple API, which re-uses 100% of what you know about Scala's language and libraries.
- 100% Scala syntax; no more bumping into weird edge cases with the parser.
- Syntax highlighting, error-highlighting, jump-to-definition, in-editor-documentation, and all the other nice IDE features out of the box. No more waiting for plugins!
- Inbuilt and custom tags are uniformly just function calls (e.g. `div("hello world")`)
- Custom components are trivial to create (`def component(x: Int, y: Int) = ...`) and trivial to use (`component(value1, value2)`) because they're just functions.
- No dependencies! No need for SBT integration, filesystem loading, runtime compilation, caching, and all that jazz. You create a Scala object, call `.toString()`, and that's it!

On top of fixing all the old problems, Scalatags targets some new ones:

- Typesafe-ish access to HTML tags, attributes and CSS classes and styles. No more weird bugs due to typos like `flaot: left` or `<dvi>`.
- Cross compiles to run on both JVM and Javascript via [ScalaJS](https://github.com/scala-js/scala-js), which is a property few other engines (e.g. [Mustache](http://mustache.github.io/)) have.
- [Blazing fast](#performance)

Scalatags is still a work in progress, but I think I've hit most of the pain points I was feeling with the old systems, and hope to continually improve it over time. Pull requests are welcome!


Changelog
=========

0.5.1
-----

- Add `transition` style
- Add extension method `dom.Element#applyTags(mods: Modifier*)` to easily apply Scalatags modifiers to existing elements.

0.5.0
-----

- First implementation of Scalatags `StyleSheet`s

0.4.6
-----

- Attribute is now "min-width" instead of "minWidth"
- Fixed typo in borderRightStyle
- Published for Scala.js 0.6.1

0.4.5
-----

- Fixed regression in definition of `JsDom.HtmlTag` introduced in 0.4.4

0.4.4
-----

- Updated to scala-js-dom 0.8.0
- Add missing SVG `spreadMethod`
- `()` is now a `Frag` rather than a `Modifer`, rendering to `""`
- Added a ton of missing attribtues

0.4.2
-----

- Added Javascript source maps that point to the Github sources.

0.4.1
-----

- Added basic `Namespace` support, to allow for proper rendering of SVG elements in the `JsDom` backend 
- Replaced unmaintained/untested `SvgStyles` with a semi-tested `SvgAttrs`
- Added `example/` folder

0.4.0
-----

- Removed unused type param from `Frag`
- Changed `bindNode` from `Modifier[dom.Element]` to `Frag[dom.Element, dom.Node]`

0.3.9
-----

- Better error messages for missing implicits
- Added attribuets `method`, `role`, `content`, `httpEquiv`, `onkeyup`, `onkeypress`
- Added implicit conversion from `Seq[Frag]` to `Frag`

0.3.8
-----

- Added `Frag` alias in `Bundle`, to match all the other aliases per-bundle
  
0.3.7
-----

- Added `implicits` object, for people who want to import all the implicit conversions/typeclasses without importing tags or other things

0.3.6
-----

- Added unit test and readme-section to document making custom-bundles, to allow customizable single-import syntax to bring all the things you want into scope.
- Deleted dead code in `Platform`
- Enforce lack of cycles between internal files
- Removed `StyleValue[T: Numeric]` and `AttrValue[T: Numeric]` in favor of `Byte`/`Short`/`Int`/etc. versions, to work around bug in type-inference in 2.10

0.3.5
-----

- Fixed a bug in `Text` backend that blew up when you had more than 8 attributes

0.3.4
-----

- Added a separate type parameter for `Frag.render`, distinct from the type of `TypedTag.render`, to more accurately type the API and allow the usage of `Frag`s on their own.

License
=======

The MIT License (MIT)

Copyright (c) 2013, Li Haoyi

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
