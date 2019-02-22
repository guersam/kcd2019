package com.guersam.kcd2019.config

import cats.effect.Sync
import org.http4s.Uri
import pureconfig.generic.auto._
import pureconfig.module.catseffect._
import pureconfig.module.http4s._

case class HttpConfig(
                       host: String,
                       port: Int
                     )

case class DatabaseConfig(
                           driver: String,
                           url: String,
                           user: String,
                           pass: String,
                           connectionPoolSize: Int
                         )

case class GiphyConfig(
                        apiRoot: Uri,
                        apiKey: String,
                        rating: Option[String]
                      )

case class ApplicationConfig(http: HttpConfig, db: DatabaseConfig, giphy: GiphyConfig)

object ApplicationConfig {
  def load[F[_] : Sync]: F[ApplicationConfig] =
    loadConfigF[F, ApplicationConfig]("ihavedoneit")
}

