package com.knoldus.parser.controller

import com.knoldus.parser.model.{Comment, Post, User}
import scala.concurrent.Future

class UserModel(dataExtractor: DataExtractor) {

  def usersList: Future[List[User]] = dataExtractor.extractData[User]("https://jsonplaceholder.typicode.com/users")
  def postsList: Future[List[Post]] = dataExtractor.extractData[Post]("https://jsonplaceholder.typicode.com/posts")
  def commentsList: Future[List[Comment]] = dataExtractor.extractData[Comment]("https://jsonplaceholder.typicode.com/comments")

}
