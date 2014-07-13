name := "doppelganger"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.mockito" % "mockito-core" % "1.9.5"
)

play.Project.playJavaSettings

compile in Test <<= PostCompile(Test)

