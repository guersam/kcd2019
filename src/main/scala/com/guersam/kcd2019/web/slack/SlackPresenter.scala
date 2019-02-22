package com.guersam.kcd2019.web.slack

import SlackProtocol._
import com.guersam.kcd2019.domain.{Achievement, Congratulation}
import fs2.text

object SlackPresenter {

  def renderCongratulation(congrats: Congratulation): SlashCommandResponse =
    SlashCommandResponse(
      text = congrats.text,
      attachments =
        congrats.meme.toList.map { m =>
          Attachment(imageUrl = Some(m.uri.renderString))
        }
    )

  def renderAchievements(achievements: List[Achievement]): SlashCommandResponse =
    SlashCommandResponse(
      text = "업적 목록",
      attachments = achievements.map { a =>
        val text = s"<@${a.userId}>: ${a.text}"
        Attachment(text = Some(text))
      }
    )

}
