package com.knoldus.parser

import net.liftweb.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder


case class User(id: String, name: String, username: String, email: String, address: Address, phone: String, website: String, company: Company)

case class Company(name: String, catchPhrase: String, bs: String)

case class Address(street: String, suite: String, city: String, zipcode: String, geo: Geo)

case class Geo(lat: String, lng: String)


object JsonDataParsingUsers {
  implicit val formats: DefaultFormats.type = DefaultFormats

  def common(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)

  }

  def userParser(userUrl: String): Future[List[User]] = {
    val user = parse(JsonDataParsingUsers.userExtractor(userUrl))
    Future(user.children.map(_.extract[User]))
  }
  def userExtractor(userUrl: String): String = {
    common(userUrl)
  }

}

object UserDriver extends App{

  val users: Future[List[User]] = JsonDataParsingUsers.userParser("https://jsonplaceholder.typicode.com/users")
  println(users)

}


