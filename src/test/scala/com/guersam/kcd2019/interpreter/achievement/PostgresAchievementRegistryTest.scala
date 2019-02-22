package com.guersam.kcd2019.interpreter.achievement

import cats.effect.IO
import cats.effect.Async
import cats.effect.laws.util.{TestContext, TestInstances}
import cats.Monad
import cats.implicits._
import com.guersam.kcd2019.domain.{AchievementRegistryTests, ArbitraryInstances}
import com.guersam.kcd2019.interpreter.infra.database.{ConnectionIOInstances, TestTransactor}
import doobie.free.connection.ConnectionIO
import org.scalatest.FunSuite
import org.typelevel.discipline.scalatest.Discipline

class PostgresAchievementRegistryTest
  extends FunSuite
  with Discipline
  with ArbitraryInstances
  with ConnectionIOInstances[IO]
  with TestInstances {

  implicit val context = TestContext()

  val transactor = TestTransactor.autoRollback

  implicit val M: Monad[ConnectionIO] = Async[ConnectionIO]

  checkAll("PostgresAchievementRegistry", AchievementRegistryTests(PostgresAchievementRegistry).algebra)

}
