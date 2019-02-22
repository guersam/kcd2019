name := "kcd2019"

organization := "com.guersam"

version := "0.1.0"

scalaVersion := "2.12.8"

val V = new {
  val cats = "1.6.0"
  val catsEffect = "1.2.0"
  val catsPar = "0.2.1"
  val circe = "0.11.1"
  val doobie = "0.6.0"
  val discipline = "0.11.0"
  val flyway = "5.2.4"
  val fs2 = "1.0.3"
  val logback = "1.2.3"
  val http4s = "0.20.0-M6"
  val pureconfig = "0.10.2"


  val scalatest = "3.0.6-SNAP6"
  val scalacheck = "1.14.0"

  val kindProjector = "0.9.9"
  val betterMonadicFor = "0.2.4"
  val scalametaParadise = "3.0.0-M11"
}

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % V.cats,
  "org.typelevel" %% "cats-testkit" % V.cats % Test,
  "org.typelevel" %% "cats-effect" % V.catsEffect,
  "org.typelevel" %% "cats-effect-laws" % V.catsEffect % Test,
  "io.chrisdavenport" %% "cats-par" % V.catsPar,
  "co.fs2" %% "fs2-core" % V.fs2,
  "org.http4s" %% "http4s-blaze-server" % V.http4s,
  "org.http4s" %% "http4s-blaze-client" % V.http4s,
  "org.http4s" %% "http4s-circe" % V.http4s,
  "org.http4s" %% "http4s-dsl" % V.http4s,
  "io.circe" %% "circe-core" % V.circe,
  "io.circe" %% "circe-generic" % V.circe,
  "io.circe" %% "circe-generic-extras" % V.circe,
  "io.circe" %% "circe-parser" % V.circe,
  "com.github.pureconfig" %% "pureconfig" % V.pureconfig,
  "com.github.pureconfig" %% "pureconfig-cats-effect" % V.pureconfig,
  "com.github.pureconfig" %% "pureconfig-http4s" % V.pureconfig,
  "org.flywaydb" % "flyway-core" % V.flyway,
  "ch.qos.logback" % "logback-classic" % V.logback,
  "org.tpolecat" %% "doobie-core" % V.doobie,
  "org.tpolecat" %% "doobie-postgres" % V.doobie,
  "org.tpolecat" %% "doobie-hikari" % V.doobie,
  "org.tpolecat" %% "doobie-scalatest" % V.doobie % Test,
  "org.typelevel" %% "discipline" % V.discipline % Test,
  "org.scalatest" %% "scalatest" % V.scalatest % Test,
  "org.scalacheck" %% "scalacheck" % V.scalacheck % Test,
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % V.kindProjector)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % V.betterMonadicFor)
addCompilerPlugin("org.scalameta" % "paradise" % V.scalametaParadise cross CrossVersion.patch)

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:higherKinds", // Allow higher-kinded types
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
//  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xfuture", // Turn on future language features.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match", // Pattern match may not be typesafe.
  "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  // "-Yno-imports",                      // No predef or default imports
  "-Ypartial-unification", // Enable partial unification in type constructor inference
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)
