package scalatags
import acyclic.file

/**
 * Convenience object to help import all [[Tags]], [[Attrs]], [[Styles]] and
 * [[Datatypes]] into the global namespace via `import scalatags.all._`
 */
object all extends Attrs with Styles with Tags with DataConverters
/**
 * Convenience object to help import all [[Tags]], and [[DataConverters]], while
 * aliases [[Attrs]] as `attr` and [[Styles]] as `css`. Used via
 * `import scalatags.short._`
 */
object short extends Tags with DataConverters{
  object * extends Attrs with Styles
}