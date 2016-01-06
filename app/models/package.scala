import play.api.libs.json.Json

/**
  * Created by monkeygroover on 06/01/16.
  */
package object models {

  case class User(firstName: String,
                  lastName: String)

  object User {
    implicit val userFormat = Json.format[User]
  }

}
