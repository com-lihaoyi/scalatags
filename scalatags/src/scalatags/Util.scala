package scalatags
object Util{
  private[scalatags] def camelCase(dashedString: String) = {
    val first :: rest = dashedString.split("-").toList

    (first :: rest.map(s => s(0).toUpper.toString + s.drop(1))).mkString
  }
}