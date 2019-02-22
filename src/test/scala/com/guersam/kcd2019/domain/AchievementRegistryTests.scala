package com.guersam.kcd2019.domain

import cats.{Eq, Monad}
import cats.laws.discipline._
import org.scalacheck.Arbitrary
import org.scalacheck.Prop.forAll
import org.typelevel.discipline.Laws

// https://www.iteratorshq.com/blog/tagless-with-discipline-testing-scala-code-the-right-way/
trait AchievementRegistryTests[F[_]] extends Laws {
  def laws: AchievementRegistryLaws[F]

  def algebra(implicit
              arbAchievement: Arbitrary[Achievement],
              eqFOptAchievement: Eq[F[Option[Achievement]]]) =
    new SimpleRuleSet(
      name = "Achievements",
      "register and findAll compose" -> forAll(laws.registerFindAllComposition _)
    )
}

object AchievementRegistryTests {
  def apply[F[_]: Monad](instance: AchievementRegistry[F]): AchievementRegistryTests[F] =
    new AchievementRegistryTests[F] {
      val laws: AchievementRegistryLaws[F] = AchievementRegistryLaws(instance)
    }
}
