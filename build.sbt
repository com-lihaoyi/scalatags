lazy val root = project.in(file("."))

lazy val js = project.in(file("js"))

Build.sharedSettings

unmanagedSourceDirectories in Compile <+= baseDirectory(_ / "shared" / "main" / "scala")

unmanagedSourceDirectories in Test <+= baseDirectory(_ / "shared" / "test" / "scala")

version := "0.2.2"

//Twirl.settings
//
//sourceDirectory in Twirl.twirlCompile <<= (sourceDirectory in Test) / "twirl"
//
//target in Twirl.twirlCompile <<= (sourceManaged in Test) / "generated-twirl-sources"

libraryDependencies ++= Seq(
  "org.fusesource.scalate" % "scalate-core_2.10" % "1.6.1" % "test",
  "com.lihaoyi" % "utest_2.10" % "0.1.0" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")