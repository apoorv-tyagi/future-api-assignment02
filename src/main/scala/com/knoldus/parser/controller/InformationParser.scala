package com.knoldus.parser.controller

import com.knoldus.parser.model.{Comment, Post, User}

import scala.concurrent.Future

object InformationParser extends DataExtractor {
  /**
   * This function is used to parse user data.
   * @param userUrl takes the url containing user data
   * @return future list of type user
   */
  def userParser(userUrl: String): Future[List[User]] = {
    extractData[User](userUrl)
  }
  /**
   * This function is used to parse comments done by users.
   * @param commentUrl takes the url containing comment data
   * @return future list of type comment
   */
  def commentParser(commentUrl: String): Future[List[Comment]] = {
    extractData[Comment](commentUrl)
  }
  /**
   * This function is used to parse post done by users.
   * @param postUrl takes the url containing user data
   * @return extracts post data
   */
  def postParser(postUrl: String): Future[List[Post]] = {
    extractData[Post](postUrl)
  }

}
