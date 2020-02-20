package com.knoldus.parser

import net.liftweb.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder


case class Comment(postId: String, id: String, name: String, email: String, body: String)

object JsonCommentParsing {

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * @param url contains json comment data
   * @return string of comment data
   */
  def getData(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  /**
   * @param commentUrl takes the url containing user data
   * @return extracts comment data
   */
  def commentExtractor(commentUrl: String): String = {
    getData(commentUrl)
  }
  /**
   * @param commentUrl takes the url containing comment data
   * @return future list of type comment
   */
  def commentParser(commentUrl: String): Future[List[Comment]] = {
    val comment = parse(JsonCommentParsing.commentExtractor(commentUrl))
    Future(comment.children.map(_.extract[Comment]))
  }

}




