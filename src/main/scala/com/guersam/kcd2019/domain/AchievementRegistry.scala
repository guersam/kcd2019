package com.guersam.kcd2019.domain

trait AchievementRegistry[F[_]] {

  def registerAchievement(achievement: Achievement): F[Unit]

  def findAllAchievementsByTeam(teamId: TeamId): F[List[Achievement]]
}
