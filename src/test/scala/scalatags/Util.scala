package scalatags

import scala.xml.Node

object Util extends org.scalatest.FreeSpec{
  val prettier = new scala.xml.PrettyPrinter(80, 4)
  def xmlCheck(x: HtmlTag, ns: Node) = {
    assert(
      prettier.format(x.toXML) === prettier.format(ns)
    )
  }
}
