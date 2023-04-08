package scalatags.stylesheet

import scala.language.experimental.macros
import scala.reflect.macros.Context

class SourceClasses[T](val value: T => Seq[Cls])
object SourceClasses {
  implicit def apply[T]: SourceClasses[T] = macro manglerImpl[T]
  def manglerImpl[T: c.WeakTypeTag](c: Context) = {
    import c.universe._

    val weakType = weakTypeOf[T]

    val stylesheetName = newTermName("stylesheet")
    val names = for {
      member <- weakType.members.toSeq.reverse
      // Not sure if there's a better way to capture by-name types
      if member.typeSignature.toString == "=> scalatags.stylesheet.Cls" ||
        member.typeSignature.toString == "scalatags.stylesheet.Cls"
      if member.isPublic
    } yield q"$stylesheetName.${member.name.toTermName}"
    val res = q"""
    new scalatags.stylesheet.SourceClasses[$weakType](
      ($stylesheetName: $weakType) => Seq[scalatags.stylesheet.Cls](..$names)
    )
    """

    c.Expr[SourceClasses[T]](res)
  }
}
