package com.guersam.kcd2019.interpreter.meme

import cats.effect.IO
import cats.effect.laws.util.{TestContext, TestInstances}
import cats.implicits._
import com.guersam.kcd2019.domain.{AchievementRegistryTests, ArbitraryInstances}
import com.guersam.kcd2019.interpreter.achievement.InMemoryAchievementRegistry
import org.scalatest.FunSuite
import org.typelevel.discipline.scalatest.Discipline

class InMemoryAchievementRegistryTest
  extends FunSuite
  with Discipline
  with ArbitraryInstances
  with TestInstances {

  val registry = InMemoryAchievementRegistry.make[IO].unsafeRunSync

  implicit val context = TestContext()

  checkAll("InMemoryAchievementRegistry", AchievementRegistryTests(registry).algebra)

}
