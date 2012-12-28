package scalatags

import xml.Unparsed

trait Misc {this: ScalaTags.type =>

/*
  def javascript(origin: String = "") =
    script.attr("type" -> "text/javascript").src(origin)

  def js(jcontents: String, args: Any*): XNode ={
    script.attr("type" -> "text/javascript")(
      Unparsed(
        "$(function(){" +
          jcontents.format(
            args.map( x =>
              x
            ):_*
          ) +
          "});"
      )
    )
  }

  def jsFor(jcontents: String, elemCls: String, args: Any*): XNode =
    js("(function(elem, elemCls){" + jcontents + "})($('.%s'), '%s')".format(elemCls, elemCls), args: _*)*/


  def stylesheet(origin: String = "") = link.rel("stylesheet").href(origin)

}
