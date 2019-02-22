package com.guersam.kcd2019.web.slack

import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec}

object SlackProtocol {

  implicit private val jsonConf: Configuration = Configuration.default.withSnakeCaseMemberNames

  @ConfiguredJsonCodec
  case class AttachmentField(title: String, value: String, short: Boolean)

  @ConfiguredJsonCodec
  case class Attachment(
                        fallback: Option[String] = None,
                        color: Option[String] = None,
                        pretext: Option[String] = None,
                        authorName: Option[String] = None,
                        authorLink: Option[String] = None,
                        authorIcon: Option[String] = None,
                        title: Option[String] = None,
                        titleLink: Option[String] = None,
                        text: Option[String] = None,
                        fields: List[AttachmentField] = Nil,
                        imageUrl: Option[String] = None,
                        thumbUrl: Option[String] = None,
                        footer: Option[String] = None,
                        footerIcon: Option[String] = None,
                        ts: Option[Int] = None
                       )

  @ConfiguredJsonCodec
  case class SlashCommandResponse(
                                   responseType: Option[String] = None,
                                   text: String,
                                   attachments: List[Attachment]
                                 )
}

