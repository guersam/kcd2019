package com.guersam.kcd2019

import cats.effect.{ConcurrentEffect, Timer}
import com.guersam.kcd2019.config.HttpConfig
import com.guersam.kcd2019.domain.{AchievementRegistry, CongratulationService}
import com.guersam.kcd2019.web.ApiRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.all._

final class EndpointWirings[F[_]: ConcurrentEffect: Timer](
                                                            conf: HttpConfig,
                                                            congratulationService: CongratulationService[F],
                                                            achievementRegistry: AchievementRegistry[F],
                                                          ) {

  val apiService = new ApiRoutes[F](congratulationService, achievementRegistry)

  val httpApp = Router(
    "/api" -> apiService.routes
  ).orNotFound

  def launchHttpService: F[Unit] =
    BlazeServerBuilder[F]
      .bindHttp(conf.port, conf.host)
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
}
