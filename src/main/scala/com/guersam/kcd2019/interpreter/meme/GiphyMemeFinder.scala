package com.guersam.kcd2019.interpreter.meme

import cats.effect.{ConcurrentEffect, Resource, Sync}
import com.guersam.kcd2019.config.GiphyConfig
import com.guersam.kcd2019.domain.{Meme, MemeFinder}
import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s.circe._
import cats.implicits._
import io.circe.Decoder
import org.http4s.EntityDecoder

import scala.concurrent.ExecutionContext

class GiphyMemeFinder[F[_]: Sync](
                                   httpClient: Client[F],
                                   config: GiphyConfig
                                 ) extends MemeFinder[F] {
  import GiphyProtocol._

  implicit def responseDecoder[A: Decoder]: EntityDecoder[F, Response[A]] = jsonOf[F, Response[A]]

  def findRandomMemeByKeyword(keyword: String): F[Meme] = {
    val uri = (config.apiRoot / "gifs" / "random")
      .withQueryParam("api_key", config.apiKey)
      .withOptionQueryParam("rating", config.rating)

    httpClient.expect[Response[Gif]](uri).map(_.data.toMeme)
      .onError {
        case e => Sync[F].delay(e.printStackTrace())
      }
  }
}

object GiphyMemeFinder {

  def resource[F[_]: ConcurrentEffect](config: GiphyConfig): Resource[F, MemeFinder[F]] =
    BlazeClientBuilder[F](ExecutionContext.global)
      .resource
      .map(client => new GiphyMemeFinder[F](client, config))
}
