package com.guersam.kcd2019.interpreter.achievement

import cats.effect.Sync
import cats.effect.concurrent.Ref
import cats.implicits._
import com.guersam.kcd2019.domain.{Achievement, AchievementRegistry, TeamId}

class InMemoryAchievementRegistry[F[_]: Sync](ref: Ref[F, Map[TeamId, List[Achievement]]])
  extends AchievementRegistry[F] {

  def registerAchievement(achievement: Achievement): F[Unit] =
    ref.update { map =>
      val teamId = achievement.teamId
      val as = map.getOrElse(teamId, Nil)
      map.updated(teamId, achievement :: as)
    }

  def findAllAchievementsByTeam(teamId: TeamId): F[List[Achievement]] =
    ref.get.map(_.getOrElse(teamId, Nil))
}

object InMemoryAchievementRegistry {

  def make[F[_]: Sync]: F[AchievementRegistry[F]] =
    Ref[F].of(EmptyMap)
      .map(new InMemoryAchievementRegistry[F](_))

  final private val EmptyMap = Map.empty[TeamId, List[Achievement]]
}
