package com.knoldus.parser

import com.knoldus.parser.controller.{ DataExtractor, InformationParser, Operations, UserModel}
import com.knoldus.parser.model._

import scala.concurrent.Future

object Driver extends App{

  val objectOfDataExtractor = new DataExtractor
  val objectOfInformation = new InformationParser(objectOfDataExtractor)

  val a = new UserModel(objectOfInformation)
  val objectOfOperation = new Operations

  val userPostList: Future[List[UserPost]] = objectOfOperation.joinUserAndPost(a.usersList, a.postsList)
  val postCommentsList: Future[List[PostAndComments]] = objectOfOperation.mapPostToComment(a.postsList, a.commentsList)

  val mostUserPostUserName: Future[String] = objectOfOperation.userWithMostPost(userPostList)
  val mostPostCommentUserName: Future[String] = objectOfOperation.postWithMostComments(postCommentsList, a.usersList)


}
