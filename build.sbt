lazy val root = project.in(file(".")).aggregate(js, jvm)

lazy val js = project.in(file("js"))

lazy val jvm = project.in(file("jvm"))

