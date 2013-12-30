ScalaTags
=========

ScalaTags is a small XML/HTML construction library for [Scala](http://www.scala-lang.org/). The core functionality of Scalatags is less than 200 lines of code, and yet it provides all the functionality of large frameworks like Python's [Jinja2](http://jinja.pocoo.org/docs/sandbox/) or C#'s [Razor](http://msdn.microsoft.com/en-us/vs2010trainingcourse_aspnetmvc3razor.aspx).

It does this by leveraging the functionality of the Scala language to do almost *everything*. A lot of different language constructs can be used to help keep your templates concise and [DRY](http://en.wikipedia.org/wiki/Don't_repeat_yourself), and why re-invent them all yourself when you have someone else who has done it before you.

ScalaTags takes fragments looking like this:

```scala
html(
    head(
        script.src("..."),
        script(
            "alert('Hello World')"
        )
    ),
    body(
        div(
            h1.id("title")("This is a title"),
            p("This is a big paragraph of text")
        )
    )
)
```

And turns them into (X)HTML like this:

```html
<html>
    <head>
        <script src="..."/>
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

Getting Started
===============

ScalaTags is hosted on [Maven Central](); to get started, simply add the following to your `build.sbt`:

```scala
libraryDependencies += "com.scalatags" % "scalatags_2.10" % "0.1.4"
```
(this is for old version which uses ```scala-xml``` -[here](https://github.com/lihaoyi/scalatags/tree/b505a303dc5cdf7664b11158dd2cbd371c39cf48)-, for the current version just clone the repo and reference it on your sbt project)

And you're good to go! simply open up a `sbt console`, and you can start working through the [Examples](#Examples), which should just work when copied and pasted into the console.

Why ScalaTags
=============

ScalaTags is inspired by the Play! Framework's [Twirl templates](https://github.com/spray/twirl), and ASP.NET's [Razor templates](http://msdn.microsoft.com/en-us/vs2010trainingcourse_aspnetmvc3razor.aspx). Like those, it takes the view that it is better to re-use the host language in your templates, rather than try to invent your own mini-language. Unlike Twirl and Razor, ScalaTags is an embedded DSL, not requiring any special parser or build step.

Since ScalaTags is pure Scala. This means that any IDE which understands Scala will understand ScalaTags. This means you get syntax highlighting:

![Code Completion in IntelliJ IDEA](https://github.com/lihaoyi/scalatags/blob/master/SyntaxHighlighting.png?raw=true)

and Code Completion:

![Code Completion in IntelliJ IDEA](https://github.com/lihaoyi/scalatags/blob/master/CodeCompletion.png?raw=true)

and Error Highlighting:

![Error Highlighting in IntelliJ IDEA](https://github.com/lihaoyi/scalatags/blob/master/ErrorHighlighting.png?raw=true)

And all the other good things (<em>jump to definition</em>, *extract method*, etc.) you're used to in a statically typed language. No more messing around in templates which mess up the highlighting of your HTML editor, or waiting months for the correct plugin to materialize.

Examples
========

This is a bunch of simple examples to get you started using ScalaTags. Essentially, each fragment has a `.toString` method, which turns it into a HTML string.

These examples are all in the [unit tests](https://github.com/lihaoyi/scalatags/blob/master/src/test/scala/scalatags/ExampleTests.scala).

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
    for ((name, text) <- posts) yield (
      div(
        h2("Post by ", name),
        p(text)
      )
    ),
    if(numVisitors > 100)(
      p("No more posts!")
    )else(
      p("Please post below...")
    )
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
def imgBox(src: String, text: String) =
  div(
    img.src(src),
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
    div.cls("content")(
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
            <img src="www.mysite.com/imageOne.png"/>
            <div>
                <p>This is the first image displayed on the site</p>
            </div>
        </div>
        <div class="content">
            <p>blah blah blah i am text</p>
            <div>
                <img src="www.mysite.com/imageTwo.png"/>
                <div>
                    <p>This image is very interesting</p>
                </div>
            </div>
        </div>
    </body>
</html>
```

Attributes
----------

You may already have noticed some use of `.id()` and `.src()` methods in the previous code. In general, any attribute can be set on a tag via the `.attr()` method:

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p.attr("onclick" -> "... do some js")(
        "This is my first paragraph"
      ),
      a.attr("href" -> "www.google.com")(
        p("Goooogle")
      )
    )
  )
)
```

However, the most common HTML attributes all have shortcut methods. For example, `.id(...)` is the shortcut for `.attr("id"->...)`, and helps reduce the verbosity and ensure correctness (typos won't compile) for the common use case. So the following is identical:

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p.onclick("... do some js")(
        "This is my first paragraph"
      ),
      a.href("www.google.com")(
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

In HTML, the `class` and `style` attributes are often thought of not as normal attributes (which contain strings), but as lists of strings (for `class`) and lists of key-value pairs (for `style). This means that it is handy to have more operations than just *set class to something*.

Scalatags thus provides the following methods on all tags:

- `.cls(s: String*)`: append the given class(es) onto the existing list of classes
- `.css(s: (String, String)*)`: append the given tuple(s) onto the list of styles

Furthermore, just as ScalaTags provides shortcut methods for all common attributes, it also provides shortcut methods for all common css styles. For example

- .opacity(s: String)
- .position(s: String)

to save typing and add static checking in the common case. There are also some overloads for the numeric css styles (e.g. `.opacity(d: Double)`). So the following:

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1.css("color" -> "red", "background-color" -> "blue")("This is my title"),
    div.color("red").background_color("blue")(
      p.cls("contentpara", "first")(
        "This is my first paragraph"
      ),
      a.opacity(0.9)(
        p.cls("contentpara")("Goooogle")
      )
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
        <h1 style="background-color:blue;">This is my title</h1>
        <div style="color:red;background-color:blue;">
            <p class="contentpara first">This is my first paragraph</p>
            <a style="opacity:0.9;">
                <p class="contentpara">Goooogle</p>
            </a>
        </div>
    </body>
</html>
```

A full list of the shortcut methods (for both attributes and styles) can be found in `HtmlAttributes.scala`

Layouts
-------

Again, this is something that many other templating languages have their own [special](http://guides.rubyonrails.org/layouts_and_rendering.html#using-nested-layouts) [implementations](http://jinja.pocoo.org/docs/templates/#template-inheritance) of. In ScalaTags, this can be done simply by just using functions:

```scala
def page(scripts: Seq[STag], content: Seq[STag]) =
  html(
    head(scripts),
    body(
      h1("This is my title"),
      div.cls("content")(content)
    )
  )


page(
  Seq(
    script("some script")
  ),
  Seq(
    p("This is the first ", b("image"), " displayed on the ", a("site")),
    img.src("www.myImage.com/image.jpg"),
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
            <p>
                This is the first
                <b>image</b>
                displayed on the
                <a>site</a>
            </p>
            <img src="www.myImage.com/image.jpg"/>
            <p>blah blah blah i am text</p>
        </div>
    </body>
</html>
```



Inheritance
-----------

Although most of the time, functions are sufficient to keep things DRY, if you for some reason want to use inheritance to structure your code, you probably already know how to do so. It's just Scala, and it behaves as you'd expect.

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

Unsanitized Input
-----------------

If you *really* want, for whatever reason, to put unsanitized input into your HTML, simply put the string:

```scala
val input = "<p>i am a cow</p>"

html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    input
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
        <p>i am a cow</p>
    </body>
</html>
```

Although this makes it easy to open up XSS holes, if you know what you're doing, go ahead.

Design Considerations
=====================
Below are some of the design considerations that went into the ~400 lines of code that make up the ScalaTags library.

An Internal DSL
---------------
ScalaTags is an *internal* [DSL](http://en.wikipedia.org/wiki/Domain-specific_language). That means that rather than reading in the ScalaTags templates as a string, and parsing it into the XML data structure, ScalaTags uses the syntax and semantics of the host language in order to generate the data structure directly.

This means that ScalaTags is limited in what it can look like: it can only look like Scala. This means leaving out a few things I would have liked:

- **Significant Whitespace**: many other templating languages like [Jade](http://jade-lang.com/) or [Haml](http://haml.info/) use significant whitespace to great effect, making their HTML templates look extremely clean. ScalaTags can't have any of that.
- **Commas**: unlike languages like [F#](http://fsharp.org/) and [CoffeeScript](http://coffeescript.org/), Scala does not allow you to omit the `,` between elements of a list on separate lines. Thus, tags in a ScalaTag fragment have to be separated by commas.
- **Compilation**: ScalaTags templates have to be compiled before being executed, and the Scala compiler is really slow making the view-modify-reload feedback loop slower than I would have liked.

However, in return for these, ScalaTags gains a whole lot of things from being an internal DSL:

- **Syntax Highlighting**, **Code Completion**, **IDE Support**, all for free. Not to mention a compiler which spots common errors before the template has been executed.
- **Clean integration with the host language**: passing variables into a ScalaTags fragment is the easiest thing in the world, compared to other templating languages where you need to pass in a dictionary or jump through hoops.
- **Re-use of Scala constructs**: As shown above, ScalaTags does not need to invent its own if-statement, or loops, or partials, or inheritence system in order to provide code-reuse and keep things DRY. It can just use the normal Scala control-flow constructs that everyone is already used to. This means that you already know how everything works, including all the edge cases: want to put an anonymous inner class inside your ScalaTag fragment? Go ahead, it'll just work!
- **Limited Dependencies**: many other templating systems, which load their templates from external files, often end up doing things like directly loading the requested templates from the filesystem (for convenience), maintaining a global in-memory cache, and all sorts of other complex operations. One may ask: why should my templating library be able to grab at things all over my filesystem or maintain global state? ScalaTags doesn't do any of that; it just spits out HTML. This also means that ScalaTags can be easily used to generate HTML in places where you don't want to set up the global dependencies of a traditional templating system
- **Small footprint**: at ~400 lines of code, it'd be hard to find a HTML templating engine that does so much, for so little code!

Overall, I think that being an internal DSL has been a big win for ScalaTags, despite the limitations it brings.

What ScalaTags is bad at
------------------------

ScalaTags is pretty terrible to use for marking up prose, for example to use in a blog:

```scala
p(
  "A long ", b("long"), "time ago, in a galaxy", br(),
  "far far away...."
),
h1("STAR WARS"),
h2("Episode VI"),
p(
  "It is a period of ", em("civil war"), ". Rebel spaceships, striking",
  "from a hidden base, have won their first victory against",
  "the ", i("evil"), " galactic empire"
)
```

It looks pretty terrible. This is due to two reasons:

- ScalaTags excels at use cases where there is a lot of repetitive structural XML/HTML tags to DRY up, since you have the full power of Scala's language constructs to refactor and extract common patterns in your code. However, in prose, you usually only have a minimal amount of tag duplication, so this benefit doesn't apply.
- Since it is implemented in pure Scala, it is the *text* which needs to be quoted, in contrast to XML where it's the *tags* which need to be marked out by `< >`. This means that the syntax is *lighter* when you have lots of tags, but *heavier* when you have lots of fragments of text.

This means that ScalaTags will naturally be more verbose when trying to express text-heavy things like blogs or wikis. Furthermore, because ScalaTags is pure Scala, running untrusted ScalaTags is a big security hole, and the need to compile it every time makes it unfeasible to use it for user-editable content.

The solution? Use [Markdown](http://en.wikipedia.org/wiki/Markdown) or [Textile](http://redcloth.org/textile) or even plain XML to mark up those sections, and save ScalaTags for the heavy structural parts where you can really enjoy the benefits in static checking and DRY.

Scala.js
========

ScalaTags now works out-of-the-box with [Scala.js](http://www.scala-js.org/). The folder ```scalatags-js``` includes an alternate SBT project that builds with Scala.js. Just put a dependency on it from your own project.

Scala.js tools
--------------

The Scala.js version includes an additional object ```scalajs.JSUtils``` which adds two implicit methods to interact with the DOM:

- ```.toJSDynamic```: converts the fragment into a DOM node and returns it as a js.Dynamic object.
- ```.toDOMNode```: converts the fragment into a DOM node and returns it as a org.scalajs.dom.Node object.

License
=======

The MIT License (MIT)

Copyright (c) 2013, Li Haoyi

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
