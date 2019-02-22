package com.guersam.kcd2019.domain

import cats.Eq
import org.http4s.Uri

case class Achievement(
                        teamId: TeamId,
                        userId: UserId,
                        text: String,
                      )

object Achievement {
  implicit val AchievementEq: Eq[Achievement] = Eq.fromUniversalEquals
}

case class Meme(uri: Uri)

case class Congratulation(achievement: Achievement, text: String, meme: Option[Meme])
