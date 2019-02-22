package com.guersam.kcd2019.interpreter.infra.database

import cats.{Eq, Monad}
import doobie.{ConnectionIO, Transactor}
import doobie.implicits._

trait ConnectionIOInstances[F[_]] {

  def transactor: Transactor[F]

  implicit def eqConnectionIO[A](implicit ev: Eq[F[A]], F: Monad[F]): Eq[ConnectionIO[A]] =
    new Eq[ConnectionIO[A]] {
      override def eqv(x: doobie.ConnectionIO[A], y: doobie.ConnectionIO[A]): Boolean =
        ev.eqv(x.transact(transactor), y.transact(transactor))
    }
}
