package com.guersam.kcd2019

import cats.effect._
import cats.implicits._
import com.guersam.kcd2019.config.ApplicationConfig
import com.guersam.kcd2019.domain.{CongratulationService, MemeFinder}
import com.guersam.kcd2019.interpreter.meme.GiphyMemeFinder

// https://github.com/vpavkin/ticket-booking-aecor
class AppF[F[_]: Timer: ContextShift](implicit F: ConcurrentEffect[F]) {

  case class Resources(
                        appConfig: ApplicationConfig,
                        postgresWirings: PostgresWirings[F],
                        memeFinder: MemeFinder[F],
                      )

  def resources: Resource[F, Resources] =
    for {
      appConfig <- Resource.liftF(ApplicationConfig.load[F])
      postgresWirings <- PostgresWirings[F](appConfig.db)
      giphyMemeFinder <- GiphyMemeFinder.resource[F](appConfig.giphy)
    } yield Resources(appConfig, postgresWirings, giphyMemeFinder)

  def launch(r: Resources): F[Unit] = {
    val achievementRegistry = r.postgresWirings.achievementRegistry
    val congratsService = CongratulationService[F](achievementRegistry, r.memeFinder)
    val endpointsWirings = new EndpointWirings(r.appConfig.http, congratsService, achievementRegistry)
    endpointsWirings.launchHttpService
  }

  def run: Resource[F, Unit] =
    resources.flatMap(r => Resource.liftF(launch(r)))
}
