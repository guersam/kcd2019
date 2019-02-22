package com.guersam.kcd2019

import cats.Monad
import cats.effect.{Async, ContextShift, Resource}
import com.guersam.kcd2019.config.DatabaseConfig
import com.guersam.kcd2019.domain.AchievementRegistry
import com.guersam.kcd2019.interpreter.database.Migration
import com.guersam.kcd2019.interpreter.achievement.PostgresAchievementRegistry
import doobie._
import doobie.hikari.HikariTransactor

final class PostgresWirings[F[_]: Monad] private (val transactor: Transactor[F]) {

  val achievementRegistry =
    PostgresAchievementRegistry.lift[F](transactor)
}

object PostgresWirings {

  def apply[F[_]: Async: ContextShift](
                                        dbConfig: DatabaseConfig
                                      ): Resource[F, PostgresWirings[F]] =
    for {
      xa <- transactor[F](dbConfig)
      wirings = new PostgresWirings[F](xa)
      _ <- Resource.liftF(Migration.run(dbConfig))
    } yield wirings

  def transactor[F[_]: Async: ContextShift](dbConfig: DatabaseConfig): Resource[F, HikariTransactor[F]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[F](dbConfig.connectionPoolSize) // our connect EC
      te <- ExecutionContexts.cachedThreadPool[F]         // our transaction EC
      xa <- HikariTransactor.newHikariTransactor[F](
        driverClassName = dbConfig.driver,
        url = dbConfig.url,
        user = dbConfig.user,
        pass = dbConfig.pass,
        connectEC = ce,                                   // await connection here
        transactEC = te                                   // execute JDBC operations here
      )
    } yield xa
}
