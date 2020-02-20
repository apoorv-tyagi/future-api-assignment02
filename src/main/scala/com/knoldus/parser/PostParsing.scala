package com.knoldus.parser

import net.liftweb.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

case class Post(userId: String, id: String, title: String, body: String)

object JsonPostParsing {

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * @param url contains json post data
   * @return string of post data
   */
  def getData(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  /**
   * @param postUrl takes the url containing user data
   * @return extracts post data
   */
  def postExtractor(postUrl: String): String = {
    getData(postUrl)
  }

  /**
   * @param postUrl takes the url containing user data
   * @return extracts post data
   */
  def postParser(postUrl: String): Future[List[Post]] = {
    val post = parse(JsonPostParsing.postExtractor(postUrl))
    Future(post.children.map(_.extract[Post]))
  }

}


