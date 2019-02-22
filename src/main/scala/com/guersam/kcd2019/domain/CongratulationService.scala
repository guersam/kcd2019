package com.guersam.kcd2019.domain

import cats.MonadError
import cats.implicits._

trait CongratulationService[F[_]] {
  def congratulate(achievement: Achievement): F[Congratulation]
}


object CongratulationService {

  def apply[F[_]: MonadError[?[_], Throwable]](
                                               achievementRegistry: AchievementRegistry[F],
                                               memeFinder: MemeFinder[F]
                                             ): CongratulationService[F] =
    new CongratulationService[F] {

      def congratulate(achievement: Achievement): F[Congratulation] = {
        val registrationF: F[Unit] =
          achievementRegistry
            .registerAchievement(achievement)

        val memeF: F[Option[Meme]] =
          memeFinder
            .findRandomMemeByKeyword("congratulations")
            .attempt
            .map(_.toOption)

        (registrationF >> memeF).map { maybeMeme =>
          Congratulation(achievement, "Congratulations!", maybeMeme)
        }
      }
    }
}
