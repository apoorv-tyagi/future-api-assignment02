package com.knoldus.parser.controller

import com.knoldus.parser.model.{Comment, Post, User}

import scala.concurrent.Future

class UserModel(informationParser: InformationParser) {

  val usersList: Future[List[User]] = informationParser.parseData[User]("https://jsonplaceholder.typicode.com/users")
  val postsList: Future[List[Post]] = informationParser.parseData[Post]("https://jsonplaceholder.typicode.com/posts")
  val commentsList: Future[List[Comment]] = informationParser.parseData[Comment]("https://jsonplaceholder.typicode.com/comments")

}
