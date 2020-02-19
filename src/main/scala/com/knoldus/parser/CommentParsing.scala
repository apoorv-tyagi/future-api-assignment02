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

  def common(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

  def commentExtractor(commentUrl: String): String = {
    common(commentUrl)
  }

  def commentParser(commentUrl: String): Future[List[Comment]] = {
    val comment = parse(JsonCommentParsing.commentExtractor(commentUrl))
    Future(comment.children.map(_.extract[Comment]))
  }

}

object CommentDriver extends App {
  val comments: Future[List[Comment]] = JsonCommentParsing.commentParser("https://jsonplaceholder.typicode.com/comments")
  println(comments)
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


