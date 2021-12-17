package scalatags
package text
import scalatags.generic


case object ScalatagsPerf extends generic.ScalatagsPerf(scalatags.Text)

//
//case object ScalaXMLPerf extends generic.PerfTests{
//  import generic.PerfTests._
//
//  def calc() = {
//    def para(i: Int) = {
//      val color = if (i % 2 == 0) "red" else "green"
//      <p class={contentpara}
//         style={s"color: $color;"}
//         title={s"this is paragraph $i"}>Paragraph {i}</p>
//    }
//    <html>
//      <head>
//        <script>console.log(1)</script>
//      </head>
//      <body>
//        <h1 style="color: red;">{titleString}</h1>
//        <div style="background-color: blue;">
//          <p class={contentpara + " " + first} title="this is paragraph 0">{firstParaString}</p>
//          <a href="www.google.com">
//            <p>Goooogle</p>
//          </a>
//          {0 until 5 map para}
//        </div>
//      </body>
//    </html>
//  }.toString
//}
