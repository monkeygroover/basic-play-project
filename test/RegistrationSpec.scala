import controllers.AbstractRegistration
import models.rest.{UserId, UserRest}
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.RegistrationService

import scala.concurrent.Future


class RegistrationSpec extends PlaySpec with Results {

  "Registration#registerUser" should {
    "return Created with a body containing a uuid when a user is registered" in {

      val controller = new TestableRegistration()

      val request = FakeRequest("POST", "/user").withJsonBody(Json.parse(
        s"""{
            |  "firstName": "John",
            |  "lastName": "Smith"
            |}""".stripMargin))

      val apiResult = call(controller.registerUser, request)

      status(apiResult) mustEqual CREATED

      val resultBody = contentAsJson(apiResult)

      resultBody mustEqual Json.parse(
        s"""{
            |  "uuid": "TEST UUID"
            |}""".stripMargin)
    }
  }
}

// A quick version to show testing, in reality this would probably be done in a more flexible way, test more cases
// and assert that the correct values were sent
class TestableRegistration extends RegistrationService with AbstractRegistration  {
  def insertUser(userData: UserRest): Future[UserId] = Future.successful(UserId("TEST UUID"))

  def findUser(uuid: String): Future[Option[UserRest]] = Future.successful(Some(UserRest("John", "Smith")))
}
