package com.guersam.kcd2019.interpreter.meme

import cats.effect.{Concurrent, Sync}
import cats.implicits._
import com.guersam.kcd2019.domain.{Meme, MemeFinder}

import scala.util.Random

class InMemoryMemeFinder[F[_]: Sync](memes: Vector[Meme]) extends MemeFinder[F] {

  def findRandomMemeByKeyword(keyword: String): F[Meme] =
    randomIndex.map(memes)

  private def randomIndex: F[Int] =
    Sync[F].delay(Random.nextInt(memes.length))
}

object InMemoryMemeFinder {

  import GiphyProtocol._
  def fromCachedGiphyResource[F[_]: Sync: Concurrent]: F[MemeFinder[F]] = {
    for {
      src <- Sync[F].delay {
        scala.io.Source.fromResource("meme/giphy_congratulations_1000.json").mkString
      }
      memes <-
        io.circe.parser.decode[Response[Vector[Gif]]](src)
          .map(_.data.map(_.toMeme))
          .liftTo[F]
    } yield new InMemoryMemeFinder[F](memes)
  }

}
