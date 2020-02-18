package com.knoldus.parser

import net.liftweb.json.DefaultFormats
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

case class Post(userId: String, id: String, title: String, body: String)

object JsonPostParsing {
  /**
   *
   * @param url which is the url which contain json user data
   * @return string of the json data
   */
  def getData(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }
  val jsonPostsData: Future[String] = Future {
    JsonPostParsing.getData("https://jsonplaceholder.typicode.com/posts")
  }

  /**
   *
   * @param futureOfString which is the placeholder for future comment data string
   * @return parsed list of post data of future string
   */
  def postsData(futureOfString: Future[String]): List[Post] = {
    val parsedJsonPosts = for {
      postsData <- jsonPostsData
      parsedJsonData <- Future(JsonPostParsing.parse(postsData))
    } yield parsedJsonData
    Await.result(parsedJsonPosts, atMost = 8.seconds)
  }

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   *
   * @param jsonData is json post data in form of string
   * @return is the list of type post
   */
  def parse(jsonData: String): List[Post] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { comment =>

      val userId = (comment \ "userId").extract[String]
      val id = (comment \ "id").extract[String]
      val title = (comment \ "title").extract[String]
      val body = (comment \ "body").extract[String]

      Post(userId, id, title, body)
    }
  }

}

object PostDriver extends App {
  val posts: List[Post] = JsonPostParsing.postsData(JsonPostParsing.jsonPostsData)
}
