package scalatags

/**
 * Created by haoyi on 6/6/14.
 */
object Main {
  def main(args: Array[String]): Unit = {
    type M = Int
    val x: M = ???
    object Z{
      def apply(xs: M*): Z.type = ???
    }
    lazy val q = Z(x)
  }
}
