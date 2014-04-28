package scalatags

import language.experimental.macros
import scala.reflect.macros.Context

object StyleSheets {
  implicit class PseudoSelectable[T](c: T){
    def active = c
    def checked = c
    def default = c
    def dir() = c
    def disabled = c
    def empty = c
    def enabled = c
    def first = c
    def `first-child` = c
    def `first-of-type` = c
    def fullscreen = c
    def focus = c
    def hover = c
    def indeterminate = c
    def `in-range` = c
    def invalid = c
    def lang() = c
    def `last-child` = c
    def `last-of-type` = c
    def left = c
    def link = c
    def not() = c
    def `nth-child`() = c
    def `nth-last-child`() = c
    def `nth-last-of-type`() = c
    def `nth-of-type`() = c
    def `only-child` = c
    def `only-of-type` = c
    def optional = c
    def `out-of-range` = c
    def `read-only` = c
    def `read-write` = c
    def required = c
    def right = c
    def root = c
    def scope = c
    def target = c
    def valid = c
    def visited = c
  }
}
