package models

import play.api.libs.json.Json

/**
  * Created by monkeygroover on 06/01/16.
  * Model classes used by REST operations
  */
package object rest {

  case class UserId(uuid: String)

  object UserId {
    implicit val writes = Json.writes[UserId]
  }

  case class UserRest(firstName: String,
                  lastName: String)

  object UserRest {
    implicit val format = Json.format[UserRest]
    implicit val writes = Json.writes[UserRest]
  }
}
