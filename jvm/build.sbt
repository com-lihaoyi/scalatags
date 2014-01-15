Build.sharedSettings

version := "0.2.2"

Twirl.settings

sourceDirectory in Twirl.twirlCompile <<= (sourceDirectory in Test) / "twirl"

target in Twirl.twirlCompile <<= (sourceManaged in Test) / "generated-twirl-sources"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "org.fusesource.scalate" % "scalate-core_2.10" % "1.6.1" % "test"
)
