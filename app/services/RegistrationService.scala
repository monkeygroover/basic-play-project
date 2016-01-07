package services

import models.db.User
import models.rest.{UserId, UserRest}
import play.modules.reactivemongo.MongoController
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.Future

/**
  * Created by monkeygroover on 07/01/16.
  * Abstract base trait to allow easier unit testing of the controller without
  * requiring an actual mongodb connection
  */
trait RegistrationService {
  def insertUser(userData: UserRest): Future[UserId]
  def findUser(uuid: String): Future[Option[UserRest]]
}

trait MongoRegistrationService extends RegistrationService {
  self: MongoController =>

  import scala.concurrent.ExecutionContext.Implicits.global

  def collection: BSONCollection = db.collection[BSONCollection]("users")

  def insertUser(userData: UserRest): Future[UserId] = {

    val insertRec = User(_id = BSONObjectID.generate, firstName = userData.firstName, lastName = userData.lastName)

    collection.insert(insertRec).map { _ =>
      UserId(insertRec._id.stringify)
    }
  }

  def findUser(uuid: String): Future[Option[UserRest]] =
    for {
      bsonObjectId <- Future.fromTry(BSONObjectID.parse(uuid))
      query = BSONDocument("_id" -> bsonObjectId)
      result <- collection.find(query).one[User]
    } yield { result.map { user => UserRest(firstName = user.firstName, lastName = user.lastName)} }
}
