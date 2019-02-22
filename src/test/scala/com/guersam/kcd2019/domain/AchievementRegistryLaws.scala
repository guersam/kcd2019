package com.guersam.kcd2019.domain

import cats.Monad
import cats.laws._
import cats.implicits._

// https://www.iteratorshq.com/blog/tagless-with-discipline-testing-scala-code-the-right-way/
trait AchievementRegistryLaws[F[_]] {
  def algebra: AchievementRegistry[F]
  implicit def M: Monad[F]

  def registerFindAllComposition(a: Achievement): IsEq[F[Option[Achievement]]] =
    algebra.registerAchievement(a) >> algebra.findAllAchievementsByTeam(a.teamId).map(_.headOption) <-> M.pure(Some(a))
}

object AchievementRegistryLaws {

  def apply[F[_]](instance: AchievementRegistry[F])
                 (implicit ev: Monad[F]): AchievementRegistryLaws[F] =
    new AchievementRegistryLaws[F] {
      val algebra: AchievementRegistry[F] = instance
      implicit val M: Monad[F] = ev
    }
}
