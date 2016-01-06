package controllers

import javax.inject.Inject

import models.User
import play.api.mvc._
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{ReactiveMongoApi, MongoController, ReactiveMongoComponents}

import scala.concurrent.Future

class Registration @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents
{

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  import scala.concurrent.ExecutionContext.Implicits.global

  def registerUser = Action.async(parse.json) {
    request =>
      request.body.validate[User].map {
        user =>
          collection.insert(user).map {
            lastError =>
              //logger.debug(s"Successfully inserted with LastError: $lastError")
              Created(s"User Created")
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }


  //def getUser(uuid: String) = Action.a

}
