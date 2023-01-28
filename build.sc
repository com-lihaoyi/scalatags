import mill._, scalalib._, scalajslib._, scalanativelib._, publish._
import $ivy.`com.lihaoyi::mill-contrib-buildinfo:$MILL_VERSION`
import mill.contrib.buildinfo._
import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.3.0`
import de.tobiasroeser.mill.vcs.version.VcsVersion
import $ivy.`com.github.lolgab::mill-mima::0.0.13`
import com.github.lolgab.mill.mima._
import mill.scalalib.api.Util.isScala3

val dottyVersions = sys.props.get("dottyVersion").toList

val scalaVersions = "2.12.16" :: "2.13.8" :: "2.11.12" :: "3.1.3" :: dottyVersions
val scala2Versions = scalaVersions.filter(_.startsWith("2."))

val scalaJSVersions = scalaVersions.map((_, "1.10.1"))
val scalaNativeVersions = scalaVersions.map((_, "0.4.5"))

trait MimaCheck extends Mima {
  def mimaPreviousVersions = Seq("0.11.0", "0.11.1", "0.12.0")
}

trait ScalatagsPublishModule extends PublishModule with MimaCheck {
  def artifactName = "scalatags"


  def publishVersion = VcsVersion.vcsState().format()

  def crossScalaVersion: String

  def pomSettings = PomSettings(
    description = artifactName(),
    organization = "com.lihaoyi",
    url = "https://github.com/com-lihaoyi/scalatags",
    licenses = Seq(License.MIT),
    versionControl = VersionControl(
      browsableRepository = Some("git://github.com/com-lihaoyi/scalatags.git"),
      connection = Some("scm:git://github.com/com-lihaoyi/scalatags.git")
    ),
    developers = Seq(
      Developer("lihaoyi", "Li Haoyi", "https://github.com/lihaoyi")
    )
  )
}

trait Common extends CrossScalaModule {
  def millSourcePath = super.millSourcePath / offset
  def ivyDeps = Agg(
    ivy"com.lihaoyi::sourcecode::0.3.0",
    ivy"com.lihaoyi::geny::1.0.0",
  )
  def compileIvyDeps = T { super.compileIvyDeps() ++
    (
      if (isScala3(crossScalaVersion)) Agg()
      else Agg(
        ivy"org.scala-lang:scala-reflect:${scalaVersion()}"
      )
    )
  }
  def offset: os.RelPath = os.rel
  def sources = T.sources(
    super.sources()
      .flatMap(source =>
        Seq(
          PathRef(source.path / os.up / source.path.last),
          PathRef(source.path / os.up / os.up / source.path.last),
        )
      )
  )
}

trait CommonTestModule extends ScalaModule with TestModule.Utest with BuildInfo {
  def millSourcePath = super.millSourcePath / os.up
  def crossScalaVersion: String
  val scalaXmlVersion = if(crossScalaVersion.startsWith("2.11.")) "1.3.0" else "2.1.0"
  def ivyDeps = Agg(
    ivy"com.lihaoyi::utest::0.8.1",
    ivy"org.scala-lang.modules::scala-xml:$scalaXmlVersion"
  )
  def offset: os.RelPath = os.rel
  def sources = T.sources(
    super.sources()
      .flatMap(source =>
        Seq(
          PathRef(source.path / os.up / "test" / source.path.last),
          PathRef(source.path / os.up / os.up / "test" / source.path.last),
        )
      )
      .distinct
  )
  override def buildInfoPackageName = Some("scalatags")
  override def buildInfoMembers = Map(
    "scalaMajorVersion" -> crossScalaVersion.split('.').head
  )
}


object scalatags extends Module {
  object jvm extends Cross[JvmScalatagsModule](scalaVersions:_*)
  class JvmScalatagsModule(val crossScalaVersion: String)
    extends Common with ScalaModule with ScalatagsPublishModule {

    object test extends Tests with CommonTestModule{
      def crossScalaVersion = JvmScalatagsModule.this.crossScalaVersion
    }
  }

  object js extends Cross[JSScalatagsModule](scalaJSVersions:_*)
  class JSScalatagsModule(val crossScalaVersion: String, crossJSVersion: String)
    extends Common with ScalaJSModule with ScalatagsPublishModule {
    def scalaJSVersion = crossJSVersion
    def ivyDeps = super.ivyDeps() ++ Agg(ivy"org.scala-js::scalajs-dom::2.3.0")
    def offset = os.up
    object test extends Tests with CommonTestModule{
      def offset = os.up
      def crossScalaVersion = JSScalatagsModule.this.crossScalaVersion
      def ivyDeps = super.ivyDeps() ++ Agg(ivy"org.scala-js::scalajs-env-jsdom-nodejs:1.1.0").map(_.withDottyCompat(crossScalaVersion))
      def jsEnvConfig = mill.scalajslib.api.JsEnvConfig.JsDom()
    }
  }

  object native extends Cross[NativeScalatagsModule](scalaNativeVersions:_*)
  class NativeScalatagsModule(val crossScalaVersion: String, crossScalaNativeVersion: String)
    extends Common with ScalaNativeModule with ScalatagsPublishModule {
    def scalaNativeVersion = crossScalaNativeVersion
    // No released Scala Native Scala 3 version yet
    def mimaPreviousArtifacts = if(isScala3(crossScalaVersion)) Agg.empty[Dep] else super.mimaPreviousArtifacts()
    def offset = os.up
    object test extends Tests with CommonTestModule{
      def offset = os.up
      def crossScalaVersion = NativeScalatagsModule.this.crossScalaVersion
    }
  }
}

object example extends ScalaJSModule{
  val (scalaV, scalaJSV) = scalaJSVersions.head
  def scalaVersion = scalaV
  def scalaJSVersion = scalaJSV
  def moduleDeps = Seq(scalatags.js(scalaV, scalaJSV))
}
