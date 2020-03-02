package com.knoldus.parser

import com.knoldus.parser.controller.{InformationParser, Operations}
import com.knoldus.parser.model._

import scala.concurrent.Future

object Driver {

  val usersList: Future[List[User]] = InformationParser.userParser("https://jsonplaceholder.typicode.com/users")
  val commentsList: Future[List[Comment]] = InformationParser.commentParser("https://jsonplaceholder.typicode.com/comments")
  val postsList: Future[List[Post]] = InformationParser.postParser("https://jsonplaceholder.typicode.com/posts")


  val userPostList: Future[List[UserPost]] = Operations.userPostsOperations(usersList, postsList)
  val postCommentsList: Future[List[PostAndComments]] = Operations.postsCommentsOperations(postsList, commentsList)

  val mostUserPostCount: Future[String] = Operations.mostUserPost(userPostList)
  val mostPostCommentCount: Future[String] = Operations.mostPostWithComment(postCommentsList, usersList)

  /**
   * @param key is the test input key
   * @return function result for test cases
   */
  def test(key: String): Future[String] = {
    key match {
      case "post" => Operations.mostUserPost(userPostList)
      case "comment" => Operations.mostPostWithComment(postCommentsList, usersList)
    }
  }

}
