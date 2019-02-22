package com.guersam.kcd2019

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

// https://github.com/vpavkin/ticket-booking-aecor
object App extends IOApp {

  val app = new AppF[IO]

  def run(args: List[String]): IO[ExitCode] = app.run.use(_ => IO.never).as(ExitCode.Success)
}
