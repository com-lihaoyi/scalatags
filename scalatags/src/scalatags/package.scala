import scala.language.implicitConversions
import language.experimental.macros
/**
 * ScalaTags is a small XML/HTML construction library for Scala. See
 * [[https://github.com/lihaoyi/scalatags the Github page]] for an introduction
 * and documentation.
 */
package object scalatags {
  type Bundle[FragOutput0, TagOutput0 <: FragOutput0] = scalatags.generic.Bundle[FragOutput0, TagOutput0]
}
