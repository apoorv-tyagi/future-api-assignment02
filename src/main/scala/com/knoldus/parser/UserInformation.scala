package com.knoldus.parser

import net.liftweb.json.DefaultFormats
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

case class User(id: String, name: String, username: String, email: String, address: Address, phone: String, website: String, company: Company)

case class Company(name: String, catchPhrase: String, bs: String)

case class Address(street: String, suite: String, city: String, zipcode: String, geo: Geo)

case class Geo(lat: String, lng: String)


object JsonDataParsingUsers {
  /**
   *
   * @param url which is the url which contain json user data
   * @return string of the json data
   */
  def getData(url :String) : String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  val jsonUserData: Future[String] = Future {
    JsonDataParsingUsers.getData("https://jsonplaceholder.typicode.com/users")
  }
  /**
   *
   * @param jsonData is the json data in string
   * @return List of user type
   */
  def parse(jsonData: String): List[User] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { user =>

      val id = (user \ "id").extract[String]
      val name = (user \ "name").extract[String]
      val username = (user \ "username").extract[String]
      val email = (user \ "email").extract[String]
      val street = (user \ "address" \ "street").extract[String]
      val suite = (user \ "address" \ "suite").extract[String]
      val city = (user \ "address" \ "city").extract[String]
      val zipcode = (user \ "address" \ "zipcode").extract[String]
      val lat = (user \ "address" \ "geo" \ "lat").extract[String]
      val lng = (user \ "address" \ "geo" \ "lng").extract[String]
      val phone = (user \ "phone").extract[String]
      val website = (user \ "website").extract[String]
      val companyName = (user \ "company" \ "name").extract[String]
      val catchPhrase = (user \ "company" \ "catchPhrase").extract[String]
      val bs = (user \ "company" \ "bs").extract[String]
      User(id, name, username, email, Address(street, suite, city, zipcode, Geo(lat, lng)), phone, website, Company(companyName, catchPhrase, bs))
    }
  }
  implicit val formats: DefaultFormats.type = DefaultFormats
  /**
   *
   * @param futureOfString which is the placeholder for future user data string
   * @return list of type user parsed
   */
  def userData(futureOfString: Future[String]): List[User] = {
    val parsedJsonUserData: Future[List[User]] = for {
      userData <- jsonUserData
      parsedJsonData <- Future(JsonDataParsingUsers.parse(userData))
    } yield parsedJsonData
    Await.result(parsedJsonUserData, atMost = 8.seconds)
  }

}

object UserDriver extends App{
  val users: List[User] = JsonDataParsingUsers.userData(JsonDataParsingUsers.jsonUserData)
}

