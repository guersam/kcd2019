package com.guersam.kcd2019.interpreter.database

import cats.effect.Sync
import com.guersam.kcd2019.config.DatabaseConfig
import org.flywaydb.core.Flyway

object Migration {

  def run[F[_]: Sync](dbConfig: DatabaseConfig): F[Unit] =
    Sync[F].delay {
      val flyway =
        Flyway
          .configure()
          .dataSource(dbConfig.url, dbConfig.user, dbConfig.pass)
          .load()

      flyway.migrate()
      () // To suppress discarded non-Unit value warning
    }
}
