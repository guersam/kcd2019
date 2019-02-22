package com.guersam.kcd2019.domain

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._

trait ArbitraryInstances {

  final val AchievementGen: Gen[Achievement] = for {
    teamId <- nonEmptyListOf(alphaNumChar).map(_.mkString)
    userId <- nonEmptyListOf(alphaNumChar).map(_.mkString)
    text <- nonEmptyListOf(alphaNumChar).map(_.mkString)
  } yield Achievement(teamId, userId, text)

  implicit final val ArbitraryAchievement: Arbitrary[Achievement] = Arbitrary(AchievementGen)
}
