import acyclic.file
import scala.collection.immutable.Queue
import scala.collection.{SortedMap, mutable}
import scalatags.generic.{Attr, Style}
import scalatags.Platform.Base

/**
 * ScalaTags is a small XML/HTML construction library for Scala. See
 * [[https://github.com/lihaoyi/scalatags the Github page]] for an introduction
 * and documentation.
 */
package object scalatags {
  private[scalatags] def camelCase(dashedString: String) = {
    val first :: rest = dashedString.split("-").toList

    (first :: rest.map(s => s(0).toUpper.toString + s.drop(1))).mkString
  }
}
