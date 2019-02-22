package com.guersam.kcd2019.interpreter.achievement

import cats.Monad
import com.guersam.kcd2019.domain.{Achievement, AchievementRegistry, TeamId, UserId}
import cats.implicits._
import doobie._
import doobie.implicits._

object PostgresAchievementRegistry extends AchievementRegistry[ConnectionIO] {

  def registerAchievement(achievement: Achievement): ConnectionIO[Unit] =
    Statements
      .registerAchievement(achievement.teamId, achievement.userId, achievement.text)
      .run
      .void

  def findAllAchievementsByTeam(teamId: TeamId): ConnectionIO[List[Achievement]] =
    Statements
      .findAchievementsByTeam(teamId)
      .to[List]

  object Statements {

    def registerAchievement(teamId: TeamId, userId: UserId, achievement: String): Update0 =
      sql"""
            INSERT INTO achievements (team_id, user_id, text)
            VALUES ($teamId, $userId, $achievement)
      """.update

    def findAchievementsByTeam(teamId: TeamId): Query0[Achievement] =
      sql"""
            SELECT team_id, user_id, text
            FROM achievements
            WHERE team_id = $teamId
            ORDER BY id DESC
      """.query
  }

  // TODO remove boilerplate using cats-tagless
  def lift[F[_]: Monad](xa: Transactor[F]): AchievementRegistry[F] =
    new AchievementRegistry[F] {
      def registerAchievement(achievement: Achievement): F[Unit] =
        PostgresAchievementRegistry.registerAchievement(achievement).transact(xa)

      def findAllAchievementsByTeam(teamId: TeamId): F[List[Achievement]] =
        PostgresAchievementRegistry.findAllAchievementsByTeam(teamId).transact(xa)
    }
}

