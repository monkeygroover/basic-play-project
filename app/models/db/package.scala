package models

import reactivemongo.bson._

/**
  * Created by monkeygroover on 07/01/16.
  * Model classes used by the data layer
  */
package object db {

  case class User(_id: BSONObjectID,
                  firstName: String,
                  lastName: String)

  object User {
    implicit val reader: BSONDocumentReader[User] = Macros.reader[User]
    implicit val writer: BSONDocumentWriter[User] = Macros.writer[User]

    implicit val userFormat: BSONHandler[BSONDocument, User] = Macros.handler[User]
  }

}
