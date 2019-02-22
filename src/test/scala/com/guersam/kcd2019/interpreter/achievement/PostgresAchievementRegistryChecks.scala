package com.guersam.kcd2019.interpreter.achievement

import cats.effect.IO
import com.guersam.kcd2019.interpreter.infra.database.TestTransactor
import doobie.scalatest._
import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext

class PostgresAchievementRegistryChecks extends FunSuite with IOChecker  {

  implicit val cs = IO.contextShift(ExecutionContext.global)

  val transactor = TestTransactor.autoRollback

  import PostgresAchievementRegistry.Statements._
  test("registerAchievement")    {
    check(registerAchievement("NTRYUNFYB", "SUTNYXKY", "testing"))
  }

  test("findAchievementsByTeam") {
    check(findAchievementsByTeam("NTRYUNFYB"))
  }

}
