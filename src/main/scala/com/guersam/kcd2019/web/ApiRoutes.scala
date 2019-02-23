package com.guersam.kcd2019.web

import cats.effect.Effect
import cats.implicits._
import com.guersam.kcd2019.domain.{Achievement, AchievementRegistry, CongratulationService}
import com.guersam.kcd2019.web.slack.{SlackPresenter, SlackProtocol}
import org.http4s.{EntityDecoder, HttpRoutes, UrlForm}
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class ApiRoutes[F[_]: Effect](
                               congratulationService: CongratulationService[F],
                               achievementRegistry: AchievementRegistry[F],
                             ) extends Http4sDsl[F] {

  implicit val slashCommandEncoder = jsonEncoderOf[F, SlackProtocol.SlashCommandResponse]

  implicit val AchievementDecoder: EntityDecoder[F, Achievement] =
    EntityDecoder[F, UrlForm].map { frm =>
      val userId = frm.getFirstOrElse("user_id", "")
      val teamId = frm.getFirstOrElse("team_id", "")
      val text = frm.getFirstOrElse("text", "")
      Achievement(teamId, userId, text)
    }

  private val registerAchievement = HttpRoutes.of[F] {
    case req @ POST -> Root / "done" =>
      req.decode[Achievement] { achievement =>
        for {
          congrats <- congratulationService.congratulate(achievement)
          slackResp = SlackPresenter.renderCongratulation(congrats)
          resp <- Ok(slackResp)
        } yield resp
      }
  }

  private val listTeamAchievements = HttpRoutes.of[F] {
    case req @ POST -> Root / "done-list" =>
      req.decode[UrlForm] { cmd =>
        val teamId = cmd.getFirstOrElse("team_id", "")

        for {
          achievements <- achievementRegistry.findAllAchievementsByTeam(teamId)
          slackResp = SlackPresenter.renderAchievements(achievements)
          resp <- Ok(slackResp)
        } yield resp
      }
  }

  val routes: HttpRoutes[F] =
    registerAchievement <+> listTeamAchievements

}

