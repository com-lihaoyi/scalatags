package scalatags.stylesheet

import scala.quoted.*

class SourceClasses[T](val value: T => Seq[Cls])

object SourceClasses {
  
  inline implicit def apply[T]: SourceClasses[T] = ${ manglerImpl[T] }

  def manglerImpl[T: Type](using Quotes): Expr[SourceClasses[T]] = {
    '{ new SourceClasses[T]((t: T) => ${ Expr.ofSeq(terms('t)) }) }
  }

  private def terms[T: Type](t: Expr[T])(using ctx: Quotes): Seq[Expr[Cls]] = {
    import ctx.reflect._
    val valdefs = TypeRepr.of[T].typeSymbol.declaredFields.map(_.tree.asInstanceOf[ValDef])
    val clsesOnly = valdefs.filter(_.tpt.tpe =:= TypeRepr.of[Cls])
    clsesOnly.map(valdef => t.asTerm.select(valdef.symbol).asExprOf[Cls])
  }
}
