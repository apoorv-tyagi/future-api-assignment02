package com.knoldus.parser.controller

import com.knoldus.parser.model.{Comment, Post, User}
import scala.concurrent.Future

/**
 * This class contains method for which contains required list.
 * @param dataExtractor is the object of data extractor class
 */
class UserModel(dataExtractor: DataExtractor) {

  /**
   * This method is used to retrieve list of user information.
   * @return list of type User
   */
  def usersList: Future[List[User]] = dataExtractor.extractData[User]("https://jsonplaceholder.typicode.com/users")

  /**
   * This method is used to retrieve list of post information.
   * @return list of type Post
   */
  def postsList: Future[List[Post]] = dataExtractor.extractData[Post]("https://jsonplaceholder.typicode.com/posts")

  /**
   * This method is used to retrieve list of comment information.
   * @return list of type Comment
   */
  def commentsList: Future[List[Comment]] = dataExtractor.extractData[Comment]("https://jsonplaceholder.typicode.com/comments")

}
