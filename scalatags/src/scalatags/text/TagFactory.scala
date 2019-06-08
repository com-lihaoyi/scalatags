package scalatags.text

import scalatags.Escaping
import scalatags.generic.Namespace

/**
  * Created by haoyi on 7/9/16.
  */
trait TagFactory extends scalatags.generic.Util[Builder, String, String]{

  def tag(s: String, void: Boolean = false): ConcreteHtmlTag[String] = {
    if (!Escaping.validTag(s))
      throw new IllegalArgumentException(
        s"Illegal tag name: $s is not a valid XML tag name"
      )
    makeAbstractTypedTag[String](s, void, Namespace.htmlNamespaceConfig)
  }
}
