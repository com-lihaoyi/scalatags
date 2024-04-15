import mill._, scalalib._, scalajslib._, scalanativelib._, publish._
import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.4.0`
import $ivy.`com.github.lolgab::mill-mima::0.0.23`
import $ivy.`com.lihaoyi::mill-contrib-buildinfo:`
import mill.contrib.buildinfo._
import de.tobiasroeser.mill.vcs.version.VcsVersion

import com.github.lolgab.mill.mima._
import mill.scalalib.api.ZincWorkerUtil.isScala3

val dottyVersions = sys.props.get("dottyVersion").toList

val scalaVersions = List("2.12.18", "2.13.13", "3.3.1") ++ dottyVersions

trait ScalatagsPublishModule extends CrossScalaModule with PublishModule with PlatformScalaModule with Mima{
  def ivyDeps = Agg(
    ivy"com.lihaoyi::sourcecode::0.4.0",
    ivy"com.lihaoyi::geny::1.1.0",
  )

  def compileIvyDeps = T {
    super.compileIvyDeps() ++
      (
        if (isScala3(crossScalaVersion)) Agg()
        else Agg(
          ivy"org.scala-lang:scala-reflect:${scalaVersion()}"
        )
        )
  }

  def publishVersion = VcsVersion.vcsState().format()
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

  def mimaReportBinaryIssues() =
    if (crossScalaVersion.startsWith("3.") || this.isInstanceOf[ScalaNativeModule] || this.isInstanceOf[ScalaJSModule]) T.command()
    else super.mimaReportBinaryIssues()

  def mimaPreviousVersions = Seq("0.11.0", "0.11.1", "0.12.0")
}

trait CommonTestModule extends ScalaModule with TestModule.Utest with BuildInfo {
  def ivyDeps = Agg(
    ivy"com.lihaoyi::utest::0.8.3",
    ivy"org.scala-lang.modules::scala-xml:${if(scalaVersion().startsWith("2.11.")) "1.3.0" else "2.1.0"}"
  )

  override def buildInfoPackageName = "scalatags"
  override def buildInfoMembers = Seq(
    BuildInfo.Value("scalaMajorVersion", scalaVersion().split('.').head)
  )
}


object scalatags extends Module {
  object jvm extends Cross[JvmScalatagsModule](scalaVersions)
  trait JvmScalatagsModule
    extends ScalatagsPublishModule {

    object test extends ScalaTests with CommonTestModule
  }

  object js extends Cross[JSScalatagsModule](scalaVersions)
  trait JSScalatagsModule
    extends ScalaJSModule with ScalatagsPublishModule {
    def scalaJSVersion = "1.12.0"
    def ivyDeps = super.ivyDeps() ++ Agg(ivy"org.scala-js::scalajs-dom::2.3.0")
    private def sourceMapOptions = T.task {
      val vcsState = VcsVersion.vcsState()
      vcsState.lastTag.collect {
        case tag if vcsState.commitsSinceLastTag == 0 =>
          val baseUrl =
            pomSettings().url.replace("github.com", "raw.githubusercontent.com")
          val sourcesOptionName =
            if (isScala3(crossScalaVersion))
              "-scalajs-mapSourceURI"
            else "-P:scalajs:mapSourceURI"
          s"$sourcesOptionName:${T.workspace.toIO.toURI}->$baseUrl/$tag/"
      }
    }
    override def scalacOptions = super.scalacOptions() ++ sourceMapOptions()

    object test extends ScalaJSTests with CommonTestModule{
      def ivyDeps = super.ivyDeps() ++ Agg(ivy"org.scala-js::scalajs-env-jsdom-nodejs:1.1.0").map(_.withDottyCompat(crossScalaVersion))
      override def jsEnvConfig = mill.scalajslib.api.JsEnvConfig.JsDom()
    }
  }

  object native extends Cross[NativeScalatagsModule](scalaVersions)
  trait NativeScalatagsModule extends ScalaNativeModule with ScalatagsPublishModule {
    def scalaNativeVersion = "0.5.0"
    // No released Scala Native Scala 3 version yet
    def mimaPreviousArtifacts = if(isScala3(crossScalaVersion)) Agg.empty[Dep] else super.mimaPreviousArtifacts()
    object test extends ScalaNativeTests with CommonTestModule
  }
}

object example extends ScalaJSModule{
  def scalaVersion = scalaVersions.head
  def scalaJSVersion = "1.12.0"
  def moduleDeps = Seq(scalatags.js(scalaVersions.head))
}
