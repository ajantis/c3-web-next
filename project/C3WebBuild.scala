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
  unmanagedResourceDirectories in Compile <+= (baseDirectory)

  excludeFilter in unmanagedResources := HiddenFileFilter || "node_modules*" || "project*" || "target*"
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
        "-encoding", "utf8", "-target:jvm-1.7", "-feature", "-language:implicitConversions", "-language:postfixOps", "-unchecked", "-deprecation",
        "-Ywarn-adapted-args", "-Xlog-reflective-calls"
      ))
  resolvers ++=
    Seq(
      "spray repo" at "http://repo.spray.io/",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Maven central" at "http://repo1.maven.org/maven2/"
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
  val ScalaVersion = "2.10.5"
}

object Dependencies {
  val akkaStreamV = "2.0.1"
  val akkaV = "2.2.5"
  val sprayV = "1.2.3"
  val scalaTestV = "2.0.M7"
  val c3webServer = Seq(
    Compile.akkaActor,
    Compile.sprayJSONNE,
    Compile.sprayServlet,
    Compile.sprayRouting,
    Compile.sprayClient,
    Compile.sprayUtils,
    Compile.sprayCaching,
    Compile.sprayCan,
    Compile.logger,
    Compile.logback,
    Test.scalatest, Test.spraytestkit, Test.specs2)

  object Compile {
    val sprayServlet = "io.spray" % "spray-servlet" % sprayV
    val sprayRouting = "io.spray" % "spray-routing" % sprayV
    val sprayClient = "io.spray" % "spray-client" % sprayV
    val sprayUtils = "io.spray" % "spray-util" % sprayV
    val sprayCaching = "io.spray" % "spray-caching" % sprayV
    val sprayCan = "io.spray" % "spray-can" % sprayV
    val sprayJSONNE = "io.spray" %%  "spray-json"  % "1.3.2"
    val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaV
    val logger = "com.typesafe.akka" %% "akka-slf4j" % akkaV
    val logback = "ch.qos.logback" % "logback-classic" % "1.0.13"
  }

  object Test {
    val scalatest = "org.scalatest" %% "scalatest" % scalaTestV % "test"
    val spraytestkit = "io.spray" % "spray-testkit" % sprayV % "test"
    val specs2 = "org.specs2" %% "specs2-core" % "2.5" % "test"
  }
}
