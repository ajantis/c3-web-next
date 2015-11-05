import sbt._
import Keys._
import sbtassembly.AssemblyPlugin.autoImport._

import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

import spray.revolver.RevolverPlugin._
import org.scalastyle.sbt.ScalastylePlugin

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
    .dependsOn(c3web_domain)

  lazy val c3web_domain = Project("c3web-domain", file("c3web-domain"))
    .settings(defaultSettings: _*)
    .settings(libraryDependencies ++= Dependencies.c3webDomain)

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
  val ProjectVersion = "1.0"
  val ScalaVersion = "2.11.5"

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

  lazy val c3webAssemblySettings = Seq(
    mainClass in assembly := Some("com.ifunsoftware.c3web.Boot"),
    jarName   in assembly := "c3web-server.jar")

  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formattingPreferences,
    ScalariformKeys.preferences in Test    := formattingPreferences
  )

  lazy val formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences()
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
  }
}

object Dependencies {
  val akkaV       = "2.4.0"
  val akkaStreamV = "1.0"
  val scalaTestV  = "2.2.4"
  val sprayJsonV  = "1.3.2"

  object Compile {
    val akkaActor         = "com.typesafe.akka" %% "akka-actor"                           % akkaV
    val akkaStream        = "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV
    val akkaHttpCore      = "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV
    val akkaHttp          = "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamV
    val sprayJson         = "io.spray"          %% "spray-json"                           % sprayJsonV
    val sprayJsonSupport  = "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV
    val akkaHttpTestkit   = "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamV
    val akkaHttpSession   = "com.softwaremill"  %% "akka-http-session"                    % "0.1.4"
  }

  object Test {
    val scalatest = "org.scalatest"     %% "scalatest"      % scalaTestV   % "test"    
  }

  val c3webServer = Seq(
    Compile.akkaActor,
    Compile.akkaStream,
    Compile.akkaHttpCore,
    Compile.akkaHttp,
    Compile.sprayJson,
    Compile.sprayJsonSupport,
    Compile.akkaHttpTestkit,
    Compile.akkaHttpSession,
    Test.scalatest)

  val c3webDomain = Seq(Compile.sprayJson, Test.scalatest)
}
