package controllers


import javax.inject.Inject

import models.rest.UserRest
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.{MongoRegistrationService, RegistrationService}

import scala.concurrent.Future

/**
  * Real controller
  * @param reactiveMongoApi injected by play-reactivemongo
  */
class Registration @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends AbstractRegistration
  with MongoRegistrationService
  with MongoController
  with ReactiveMongoComponents

/**
  * The controller functionality has been placed in a trait to allow
  * the mongodb calls to be mocked out when testing
  */
trait AbstractRegistration extends Controller {
  self: RegistrationService =>

  import scala.concurrent.ExecutionContext.Implicits.global

  val registerUser = Action.async(parse.json) { request =>
      request.body.validate[UserRest].map { user =>
        insertUser(user).map { userId => Created(Json.toJson(userId)) }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def getUser(uuid: String) = Action.async {
    findUser(uuid).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound(Json.obj("message" -> s"User with id $uuid not registered"))
    }
  }
}
