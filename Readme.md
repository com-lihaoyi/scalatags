ScalaTags
=========

ScalaTags is a small XML/HTML construction library for [Scala](http://www.scala-lang.org/) that takes fragments in plain Scala code that look like this:

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
        <script src="..." />
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

The core functionality of Scalatags is less than 500 lines of code, and yet it provides all the functionality of large frameworks like Python's [Jinja2](http://jinja.pocoo.org/docs/sandbox/) or C#'s [Razor](http://msdn.microsoft.com/en-us/vs2010trainingcourse_aspnetmvc3razor.aspx).

It does this by leveraging the functionality of the Scala language to do almost *everything*. A lot of different language constructs can be used to help keep your templates concise and [DRY](http://en.wikipedia.org/wiki/Don't_repeat_yourself), and why re-invent them all yourself when you have someone else who has done it before you.

Getting Started
===============

ScalaTags is hosted on [Maven Central](http://search.maven.org/#artifactdetails%7Ccom.scalatags%7Cscalatags_2.10%7C0.1.4%7Cjar); to get started, simply add the following to your `build.sbt`:

```scala
libraryDependencies += "com.scalatags" % "scalatags_2.10" % "0.2.0"
```

And you're good to go! simply open up a `sbt console`, and you can start working through the [Examples](#Examples), which should just work when copied and pasted into the console.

Why ScalaTags
=============

ScalaTags is inspired by the Play! Framework's [Twirl templates](https://github.com/spray/twirl), and ASP.NET's [Razor templates](http://msdn.microsoft.com/en-us/vs2010trainingcourse_aspnetmvc3razor.aspx). Like those, it takes the view that it is better to re-use the host language in your templates, rather than try to invent your own mini-language. Unlike Twirl and Razor, ScalaTags is an embedded DSL, not requiring any special parser or build step.

Since ScalaTags is pure Scala. This means that any IDE which understands Scala will understand ScalaTags. This means you get syntax highlighting:

![Syntax Highlighting]()

and Code Completion:

![Code Completion]()

and Error Highlighting:

![Error Highlighting]()

and in-editor documentation:

![In-Editor Documentation]()

And all the other good things (<em>jump to definition</em>, *extract method*, etc.) you're used to in a statically typed language. No more messing around in templates which mess up the highlighting of your HTML editor, or waiting months for the correct plugin to materialize.

Although other templating systems also perform static validation, Scalatags is able to statically check the templates to a much greater degree than any external templating engine. For example, we can apply static constraints to a number of HTML attributes and CSS rules:

![CSS Compilation Error]()

Making them fail to compile if you accidentally pass the wrong thing in:

![CSS Compilation Error 2]()

Basic Examples
==============

This is a bunch of simple examples to get you started using ScalaTags. These examples are all in the [unit tests](https://github.com/lihaoyi/scalatags/blob/master/src/test/scala/scalatags/ExampleTests.scala).

Hello World
-----------
The core of ScalaTags is a way to generate (X)HTML fragments using plain Scala. Below is a simple "hello world" example:

```scala
import scalatags._

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

Creates a ScalaTag fragment. We could do many things with a fragment: store it, return it, but eventually you will want to convert it into HTML. In order to render the fragment to HTML, simply use:

```scala
frag.toString
```

Which will give you a String containing the HTML representation:

```html
<html><head><script>some script</script></head><body><h1>This is my title</h1><div><p>This is my first paragraph</p><p>This is my second paragraph</p></div></body></html>
```

This representation omits unnecesary whitespace. To improve readability we could pretty print it using some XML processing:

```scala
val prettier = new scala.xml.PrettyPrinter(80, 4)
println(prettier.format(scala.xml.XML.loadString(frag.toString)))
```

executing that prints out:

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

The following examples will simply show the initial ScalaTag fragment and the final prettyprinted HTML, skipping the intermediate steps.

Attributes
----------

You may already have noticed some use of `id` and `src` methods in the previous code. In general, each attribute has an associated value which can be used to set it. This example shows you the `onclick` and `href` attributes:

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

The most common HTML attributes all have shortcut methods. This keeps things concise and statically checked. However, inevitably you'll want to set some attribtue which isn't in the initial list defined by Scalatags. This can be done with the `.attr` method:

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

Both of these print the same thing:

```html
<html>
    <head>
        <script>some script</script>
    </head>
    <body>
        <h1>This is my title</h1>
        <div>
            <p onclick="... do some js">This is my first paragraph</p>
            <a href="www.google.com">
                <p>Goooogle</p>
            </a>
        </div>
    </body>
</html>
```

CSS & Classes
-------------

In HTML, the `class` and `style` attributes are often thought of not as normal attributes (which contain strings), but as lists of strings (for `class`) and lists of key-value pairs (for `style`). Furthermore, there is a large but finite number of styles, and not any arbitrary string can be a style. The following example shows how CSS classes and inline-styles are typically set:

```scala
val contentpara = "contentpara".cls
val first = "first".cls
html(
  head(
    script("some script")
  ),
  body(
    h1(backgroundColor:="blue", color:="red")("This is my title"),
    div(backgroundColor:="blue", color:="red")(
      p(contentpara, first)(
        "This is my first paragraph"
      ),
      a(opacity:=0.9)(
        p("contentpara".cls)("Goooogle")
      )
    )
  )
)
```

Note that in this case, `backgroundColor`, `color`, `contentpara`, `first` and `opacity` are all statically typed identifiers. The two CSS classes `contentpara` and `first` are defined just before, while `backgroundColor`, `color` and `opacity` are [defined by Scalatags](https://github.com/lihaoyi/scalatags/blob/0.2/src/main/scala/scalatags/Styles.scala).

Scalatags also provides a way of setting styles dynamically as strings. This example shows how to define your own styles or css classes inline:

```
html(
  head(
    script("some script")
  ),
  body(
    h1("background-color".style:="blue", "color".style:="red")("This is my title"),
    div("background-color".style:="blue", "color".style:="red")(
      p("contentpara".cls, "first".cls)(
        "This is my first paragraph"
      ),
      a("opacity".style:="0.9")(
        p("contentpara".cls)("Goooogle")
      )
    )
  )
)
```

Both of these print the same thing:

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

A full list of the shortcut methods (for both attributes and styles) can be found in `HtmlAttributes.scala`

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

Variables can be inserted into the templates as Strings, simply by adding them to an element's children. This prints

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

Like most other XML templating languages, ScalaTags contains control flow statements like `if` and `for`. Unlike other templating languages which have their own [crufty little programming language embedded inside them for control flow](http://jinja.pocoo.org/docs/templates/#list-of-control-structures), you probably already know how to use ScalaTags' control flow syntax:

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

Many other templating systems define [incredibly](http://guides.rubyonrails.org/layouts_and_rendering.html#using-partials) [roundabout](http://jinja.pocoo.org/docs/templates/#macros) ways of creating re-usable parts of the template. In ScalaTags, we don't need to re-invent the wheel, because Scala has these amazing things called *functions*:

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

prints

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

By default, any text that's put into the Scalatags templates, whether as a attribute value or a text node, is properly escaped when it is rendered. Thus, when you run the following snippet:

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

You get this:

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

Again, this is something that many other templating languages have their own [special](http://guides.rubyonrails.org/layouts_and_rendering.html#using-nested-layouts) [implementations](http://jinja.pocoo.org/docs/templates/#template-inheritance) of. In ScalaTags, this can be done simply by just using functions:

```scala
def page(scripts: Seq[Node], content: Seq[Node]) =
  html(
    head(scripts),
    body(
      h1("This is my title"),
      div("content".cls)(content)
    )
  )


page(
  Seq(
    script("some script")
  ),
  Seq(
    p("This is the first ", b("image"), " displayed on the ", a("site")),
    img(src:=`"www.myImage.com/image.jpg"),
    p("blah blah blah i am text")
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

Although most of the time, functions are sufficient to keep things DRY, if you for some reason want to use inheritance to structure your code, you probably already know how to do so. Again, unlike [other](http://wsgiarea.pocoo.org/jinja/docs/inheritance.html) [frameworks](http://docs.makotemplates.org/en/latest/inheritance.html) that have implemented complex inheritance systems themselves, Scalatags is just Scala, and it behaves as you'd expect.

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

Child.toString
```

prints

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

ScalaJS
=======

ScalaTags now works out-of-the-box with [Scala.js](http://www.scala-js.org/). The folder ```scalatags-js``` includes an alternate SBT project that builds with ScalaJS.

To use Scalatags with a ScalaJS project, check out this project Just put a source dependency on the the `/scalatags-js` folder from your own project. This manual process should improve when ScalaJS's package management story is more mature.

Internals
=========

The bulk of Scalatag's ~5000 lines of code is static bindings (and inline documentation!) for the myriad of CSS rules and HTML tags and attributes that exist. The core of Scalatags lives in [Core.scala](), with most of the implicit extensions and conversions living in [package.scala]().

The rough architecture is as follows:

- Every node is effectively-immutable, and contains a list of `Nested` objects within it. Calling the node's apply method (e.g. `div(..., ...)`) creates a new node with the new `Nested` objects appended.
- A `Nested` can be a child-node, a attribute binding, a style binding, a css class, a `String`, or something that you define yourself.
- When a node is rendered, the list of `Nested` objects is traversed and used to populate a `children` list and `attrs` map, which are then used to render the HTML string. Each `Nested` has a `build` method that it uses to populate those data structures during rendering time. This also happens lazily when either `children` or `attrs` is called directly.
- After rendering, `children` and `attrs` are cached and the node remains effectively immutable after.

The goal of this is to avoid the performance penalty of building the data structure completely immutably, while also avoiding the correctness problems when the mutability is externally visible. By lazily deferring the actual construction until the value is needed, and then using mutation to speed things up, Scalatags optimizes for the most common use case where a tag is only rendered once, after it has finished construction, while still preserving the illusion of immutability to any external observers.

By writing custom `Nested` objects with your own `build` method, you can create "tags" that when placed into the contents of a tag, manipulates the `children` and `attrs` structures in interesting ways. Possible uses include validating that `attrs` only contains valid values for that element, or binding a [reactive variable](https://github.com/lihaoyi/scala.rx) to an attribute with enough scaffolding to propagate changes to the attribute after the page has rendered.

License
=======

The MIT License (MIT)

Copyright (c) 2013, Li Haoyi

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
