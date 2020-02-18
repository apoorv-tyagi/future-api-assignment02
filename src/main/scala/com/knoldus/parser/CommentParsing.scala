package com.knoldus.parser

import net.liftweb.json.DefaultFormats
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


case class Comment(postId: String, id: String, name: String, email: String, body: String)

object JsonCommentParsing {

  /**
   *
   * @param url which is the url which contains json comment data
   * @return string of json comment data
   */
  def getData(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  val jsonCommentsData: Future[String] = Future {
    JsonCommentParsing.getData("https://jsonplaceholder.typicode.com/comments")
  }

  /**
   *
   * @param jsonData is json comment data in form of string
   * @return is the list of type comment
   */
  def parse(jsonData: String): List[Comment] = {
    val parsedJsonData = net.liftweb.json.parse(jsonData)
    parsedJsonData.children map { comment =>

      val postId = (comment \ "postId").extract[String]
      val id = (comment \ "id").extract[String]
      val name = (comment \ "name").extract[String]
      val email = (comment \ "email").extract[String]
      val body = (comment \ "body").extract[String]

      Comment(postId, id, name, email, body)
    }
  }
  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   *
   * @param futureOfString which is the placeholder for future comment data string
   * @return parsed list of comment data of future string
   */
  def commentsData(futureOfString: Future[String]): List[Comment] = {
    val parsedJsonComments: Future[List[Comment]] = for {
      commentsData <- jsonCommentsData
      parsedJsonData1 <- Future(JsonCommentParsing.parse(commentsData))
    } yield parsedJsonData1
    Await.result(parsedJsonComments, atMost = 8.seconds)
  }

}

object CommentDriver extends App {
  val comments: List[Comment] = JsonCommentParsing.commentsData(JsonCommentParsing.jsonCommentsData)
}

//object PostDriver{
//
//  def po : List[Post]={
//
//    val resultPost = JsonPostParsing.getData("https://jsonplaceholder.typicode.com/posts")
//    val posts = for {eachPost <- JsonPostParsing.parse(resultPost)} yield {
//      eachPost
//    }
//    posts
//  }
//}


