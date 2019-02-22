package com.guersam.kcd2019.interpreter.infra.database

import cats.effect.IO
import doobie.{HC, Transactor}

import scala.concurrent.ExecutionContext

object TestTransactor {

  implicit val cs = IO.contextShift(ExecutionContext.global)

  val default: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/ihavedoneit",
    "ihavedoneit",
    "kcd2019"
  )

  val autoRollback: Transactor[IO] =
    Transactor.after.set(default, HC.rollback)

}
