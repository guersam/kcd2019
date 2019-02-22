package com.guersam.kcd2019.interpreter.meme

import com.guersam.kcd2019.domain.Meme
import io.circe.Decoder
import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec}
import org.http4s.Uri
import org.http4s.circe.decodeUri

object GiphyProtocol {

  implicit private val jsonConf: Configuration = Configuration.default.withSnakeCaseMemberNames

  implicit private val stringAwareIntDecoder: Decoder[Int] =
    io.circe.Decoder.decodeInt or Decoder[String].emapTry(e => util.Try(e.toInt))

  implicit private val stringAwareLongDecoder: Decoder[Long] =
    io.circe.Decoder.decodeLong or Decoder[String].emapTry(e => util.Try(e.toLong))

  @ConfiguredJsonCodec(decodeOnly = true)
  case class Response[A](data: A)

  @ConfiguredJsonCodec(decodeOnly = true)
  case class Gif(
                  `type`: String,
                  id: String,
                  slug: String,
                  url: Uri,
                  bitlyUrl: Uri,
                  embedUrl: Uri,
                  username: Option[String],
                  source: String,
                  rating: Option[String],
                  contentUrl: String,
                  sourceTld: String,
                  updateDatetime: Option[String],
                  createDatetime: Option[String],
                  importDatetime: Option[String],
                  trendingDatetime: Option[String],
                  images: Images,
                  title: String,
                ) {
    def toMeme: Meme =
      Meme(images.fixedHeight.url)
  }

  @ConfiguredJsonCodec(decodeOnly = true)
  case class Images(
                     fixedHeight: Images.FixedHeight,
                     // 나머지는 생략
                   )
  object Images {

    @ConfiguredJsonCodec(decodeOnly = true)
    case class FixedHeight(
                            url: Uri,
                            width: Int,
                            height: Int,
                            size: Long,
                            mp4: String,
                            mp4Size: Long,
                            webp: String,
                            webpSize: Long
                          )
  }

}
