import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import org.scalastyle.sbt.ScalastylePlugin
import sbt.Keys._
import sbt._
import sbtassembly.AssemblyPlugin.autoImport._
import spray.revolver.RevolverPlugin._

object C3WebBuild extends Build {

  import ProjectSettings._

  /**
   * Parent C3-Web project
   */
  lazy val c3web = Project("c3web", file(".")).settings(defaultSettings: _*).aggregate(c3web_server)

  /**
   * Server module
   */
  lazy val c3web_server = Project("c3web-server", file("c3web-server"))
    .settings(defaultSettings: _*)
    .settings(c3webAssemblySettings: _*)
    .settings(Revolver.settings: _*)
    .settings(libraryDependencies ++= Dependencies.c3webServer)

  override lazy val settings = {
    super.settings ++
      buildSettings ++
      Seq(
        shellPrompt := {
          s => Project.extract(s).currentProject.id + " > "
        }
      )
  }
}

object ProjectSettings {
  lazy val buildSettings = Seq(
    organization := "com.ifunsoftware.c3web",
    version := ProjectVersion,
    scalaVersion := ScalaVersion
  )
  lazy val defaultSettings = Defaults.defaultSettings ++
    ScalastylePlugin.Settings ++
    formatSettings ++
    Seq(
      scalacOptions in Compile := Seq(
        "-encoding", "utf8", "-target:jvm-1.8", "-feature", "-language:implicitConversions", "-language:postfixOps", "-unchecked", "-deprecation",
        "-Ywarn-adapted-args", "-Xlog-reflective-calls"
      ))
  resolvers ++=
    Seq(
      "spray repo" at "http://repo.spray.io/"
    )
  lazy val c3webAssemblySettings = Seq(
    mainClass in assembly := Some("com.ifunsoftware.c3web.Boot"),
    jarName in assembly := "c3web-server.jar")
  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formattingPreferences,
    ScalariformKeys.preferences in Test := formattingPreferences
  )
  lazy val formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences()
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
  }
  val ProjectVersion = "1.0"
  val ScalaVersion = "2.11.5"
}

object Dependencies {
  val akkaV = "2.3.12"
  val akkaStreamV = "1.0"
  val scalaTestV = "2.2.4"
  val sprayV = "1.2.0"
  val c3webServer = Seq(
    Compile.akkaActor,
    Compile.akkaStream,
    Compile.akkaHttpCore,
    Compile.akkaHttp,
    Compile.sprayJson,
    Compile.akkaHttpTestkit,
    Compile.sprayServlet,
    Compile.sprayRouting,
    Compile.sprayClient,
    Compile.sprayUtils,
    Compile.sprayCaching,
    Compile.logger,
    Test.scalatest)

  object Compile {
    val sprayServlet = "io.spray" % "spray-servlet" % sprayV
    val sprayRouting = "io.spray" % "spray-routing" % sprayV
    val sprayClient = "io.spray" % "spray-client" % sprayV
    val sprayUtils = "io.spray" % "spray-util" % sprayV
    val sprayCaching = "io.spray" % "spray-caching" % sprayV
    val sprayCan = "io.spray" % "spray-can" % sprayV
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaV
    val logger = "com.typesafe.akka" %% "akka-slf4j" % akkaV
    val akkaStream = "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamV
    val akkaHttpCore = "com.typesafe.akka" %% "akka-http-core-experimental" % akkaStreamV
    val akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % akkaStreamV
    val sprayJson = "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamV
    val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit-experimental" % akkaStreamV
  }

  object Test {
    val scalatest = "org.scalatest" %% "scalatest" % scalaTestV % "test"
  }

}
