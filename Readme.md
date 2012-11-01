ScalaTags
=========

ScalaTags is a small XML/HTML construction library for [Scala](http://www.scala-lang.org/). The core functionality of Scalatags is less than 200 lines of code, and yet it provides all the functionality of large frameworks like Python's [Jinja2](http://jinja.pocoo.org/docs/sandbox/) or C#'s [Razor](http://msdn.microsoft.com/en-us/vs2010trainingcourse_aspnetmvc3razor.aspx).

It does this by leveraging the functionality of the Scala language to do almost *everything*. A lot of different language constructs can be used to help keep your templates concise and [DRY](http://en.wikipedia.org/wiki/Don't_repeat_yourself), and why re-invent them all yourself when you have someone else who has done it before you.

ScalaTags takes fragments looking like this:


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

And turns them into (X)HTML like this:

    <html>
        <head>
            <script src="..."/>
            <script>alert('Hello World')</script>
        </head>
        <body>
            <div>
                <h1>This is a title</h1>
                <p>This is a big paragraph of text</p>
            </div>
        </body>
    </html>


Why ScalaTags
=============

ScalaTags is inspired by the Play! Framework's [Twirl templates](https://github.com/spray/twirl), and ASP.NET's [Razor templates](http://msdn.microsoft.com/en-us/vs2010trainingcourse_aspnetmvc3razor.aspx). Like those, it takes the view that it is better to re-use the host language in your templates, rather than try to invent your own mini-language. Unlike Twirl and Razor, ScalaTags is an embedded DSL, not requiring any special parser or build step.

The core of ScalaTags is less than 200 lines of Scala code. If you look at the some tags from that fragment, it's actually really simple

    // div is a function-like object, whose apply() method taking
    // multiple XML Node children and returns an XML Node
    div(
        h1.id("title")("This is a title"),
    //	^	^		    ^
    //	|	|			| Strings have an implicit conversion to
    //	|	|			| XML Text() nodes
    //	|	|
    //  |	| .id() returns a new XML node, which is the same
    //	|	  as the old one but with the id attribute set
    //	|
    //	| h1 is also a function-like object, just like div
        p("This is a big paragraph of text")
    )

Anyone who understands Scala should already understand all the concepts involved. This is in contrast to [some](http://jinja.pocoo.org/docs/templates/#) [other](http://guides.rubyonrails.org/layouts_and_rendering.html#using-nested-layouts) templating systems, which have manuals dozens of pages long going over their own special implementations of concepts as [if statements](http://jinja.pocoo.org/docs/templates/#if), [loops](http://jinja.pocoo.org/docs/templates/#loop-controls) and [functions](http://jinja.pocoo.org/docs/templates/#macros).

The functionality of these templating languages don't just end there, but grow to encompass their own special implementations of [inheritance](http://jinja.pocoo.org/docs/templates/#template-inheritance), [imports](http://jinja.pocoo.org/docs/templates/#import), [file loaders](http://jinja.pocoo.org/docs/api/#loaders). By that point, you may aswell use a full-fledged programming language, and receive tool support and all the other benefits that using a standard programming language gives you.

Since ScalaTags is pure Scala. This means that any IDE which understands Scala will understand ScalaTags. This means you get syntax highlighting:

![Code Completion in IntelliJ IDEA](https://github.com/lihaoyi/scalatags/blob/master/SyntaxHighlighting.png?raw=true)

and Code Completion:

![Code Completion in IntelliJ IDEA](https://github.com/lihaoyi/scalatags/blob/master/CodeCompletion.png?raw=true)

and Error Highlighting:

![Error Highlighting in IntelliJ IDEA](https://github.com/lihaoyi/scalatags/blob/master/ErrorHighlighting.png?raw=true)

And all the other good things (<em>jump to definition</em>, *extract method*, etc.) you're used to in a statically typed language. No more messing around in templates which mess up the highlighting of your HTML editor, or waiting months for the correct plugin to materialize.

Examples
========

These are a bunch of simple examples to get you started using ScalaTags

Hello World
-----------

    import scalatags.XTags._

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

    val prettier = new scala.xml.PrettyPrinter(80, 4)
    println(prettier.format(frag.toXML))


Is a complete, executable Scala script. executing it prints out:

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


Variables
=========

    import scalatags.XTags._

    val title = "title"
    val numVisitors = 1023

    val frag = html(
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

    val prettier = new scala.xml.PrettyPrinter(80, 4)
    println(prettier.format(frag.toXML))


Variables can be inserted into the templates as Strings, simply by adding them to an element's children. This prints

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

Control Flow
------------

Like most other XML templating languages, ScalaTags contains control flow statements like `if` and `for`. Unlike other templating languages which have their own [crufty little programming language embedded inside them for control flow](http://jinja.pocoo.org/docs/templates/#list-of-control-structures), you probably already know how to use ScalaTags' control flow syntax:

    import scalatags.XTags._

    val numVisitors = 1023
    val posts = Seq(
      ("alice", "i like pie"),
      ("bob", "pie is evil i hate you"),
      ("charlie", "i like pie and pie is evil, i hat myself")
    )

    val frag = html(
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

    val prettier = new scala.xml.PrettyPrinter(80, 4)
    println(prettier.format(frag.toXML))


This prints out:

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

Functions
---------

Many other templating systems define [incredibly](http://guides.rubyonrails.org/layouts_and_rendering.html#using-partials) [roundabout](http://jinja.pocoo.org/docs/templates/#macros) ways of creating re-usable parts of the template. In ScalaTags, we don't need to re-invent the wheel, because Scala has these amazing things called *functions*:

    import scalatags.XTags._

    val numVisitors = 1023
    val posts = Seq(
      ("alice", "i like pie"),
      ("bob", "pie is evil i hate you"),
      ("charlie", "i like pie and pie is evil, i hat myself")
    )

    def imgBox(src: String, text: String) =
      div(
        img.src(src),
        div(
          p(text)
        )
      )

    val frag = html(
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

    val prettier = new scala.xml.PrettyPrinter(80, 4)
    println(prettier.format(frag.toXML))


prints

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


Layouts
-------

Again, this is something that many other templating languages have their own [special](http://guides.rubyonrails.org/layouts_and_rendering.html#using-nested-layouts) [implementations](http://jinja.pocoo.org/docs/templates/#template-inheritance) of. In ScalaTags, this can be done simply by just using functions:

    import scalatags.XTags._

    def page(scripts: Seq[XNode], content: Seq[XNode]) =
      html(
        head(scripts),
        body(
          h1("This is my title"),
          div.cls("content")(content)
        )
      )

    val frag =
      page(
        Seq(
          script("some script")
        ),
        Seq(
          p("This is the first ", b("image"), " displayed on the ", a("site")),
          img("www.myImage.com/image.jpg"),
          p("blah blah blah i am text")
        )
      )

    val prettier = new scala.xml.PrettyPrinter(80, 4)
    println(prettier.format(frag.toXML))


prints

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
                <img>www.myImage.com/image.jpg</img>
                <p>blah blah blah i am text</p>
            </div>
        </body>
    </html>

Unsanitized Input
-----------------

If you *really* want, for whatever reason, to put unsanitized input into your XML, simply use scala's Unparsed function:

    import scalatags.XTags._
    import xml.Unparsed

    val input = "<p>i am a cow</p>"

    val frag = html(
      head(
        script("some script")
      ),
      body(
        h1("This is my title"),
        Unparsed(input)
      )
    )

    val prettier = new scala.xml.PrettyPrinter(80, 4)
    println(prettier.format(frag.toXML))

prints

    <html>
        <head>
            <script>some script</script>
        </head>
        <body>
            <h1>This is my title</h1>
            <p>i am a cow</p>
        </body>
    </html>

Although this makes it easy to open up XSS holes, if you know what you're doing, go ahead.

Inheritance
-----------

Although most of the time, functions are sufficient to keep things DRY, if you for some reason want to use inheritance to structure your code, you probably already know how to do so. It's just Scala, and it behaves as you'd expect.

    import scalatags.XTags._

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

    class Child extends Parent{
      override def headFrag = head(
        script("some other script")
      )
    }

    val prettier = new scala.xml.PrettyPrinter(80, 4)
    println(prettier.format(new Child().render.toXML))

prints

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

What ScalaTags is Bad at
========================

ScalaTags is pretty terrible to use for marking up prose, for example to use in a blog:

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

It looks pretty terrible. This is due to two reasons:

- ScalaTags excels at use cases where there is a lot of repetitive structural XML/HTML tags to DRY up, since you have the full power of Scala's language constructs to refactor and extract common patterns in your code. However, in prose, you usually only have a minimal amount of tag duplication, so this benefit doesn't apply.
- Since it is implemented in pure Scala, it is the *text* which needs to be quoted, in contrast to XML where it's the *tags* which need to be marked out by `< >`. This means that the syntax is *lighter* when you have lots of tags, but *heavier* when you have lots of fragments of text.

This means that ScalaTags will naturally be more verbose when trying to express text-heavy things like blogs or wikis. Furthermore, because ScalaTags is pure Scala, running untrusted ScalaTags is a big security hole, and the need to compile it every time makes it unfeasible to use it for user-editable content.

The solution? Use [Markdown](http://en.wikipedia.org/wiki/Markdown) or [Textile](http://en.wikipedia.org/wiki/Textile_(markup_language)) or even plain XML to mark up those sections, and save ScalaTags for the heavy structural parts where you can really enjoy the benefits in static checking and DRY.
