package com.knoldus.parser.controller

import com.knoldus.parser.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Operations {
  /**
   * This method is used to model user with their corresponding posts.
   *
   * @param users is the list of type user
   * @param posts takes the list of type post
   * @return list of user mapped with the post done by user
   */
  def joinUserAndPost(users: Future[List[User]], posts: Future[List[Post]]): Future[List[UserPost]] = {
    users.map(users => posts.map(posts => users
      .map(user => UserPost(user, posts
        .filter(user.id == _.userId))))).flatten

  }

  /**
   * This method is used to combine posts and comments on the corresponding posts.
   *
   * @param posts    is the list of type post
   * @param comments takes the list of type comment
   * @return list of post and comment done by same user
   */
  def mapPostToComment(posts: Future[List[Post]], comments: Future[List[Comment]]): Future[List[PostAndComments]] = {
    val listOfPostAndComment = for {listOfPosts <- posts
                                    listOfComments <- comments
                                    } yield listOfPosts.map(post => PostAndComments(post, listOfComments
      .filter(_.postId == post.id)))
    listOfPostAndComment
  }

  /**
   * This method is used to find user who have done most post.
   *
   * @param userPosts is the list of user and posts done by user
   * @return the user with most posts
   */
  def userWithMostPost(userPosts: Future[List[UserPost]]): Future[String] = {
    val userWithMostPost = for {
      listOfUserPost <- userPosts
    } yield listOfUserPost.map(element => (element.user, element.post.length))
      .reduceLeft((first, second) => if (first._2 > second._2) first else second)
    userWithMostPost.map(_._1.name)
  }

  /**
   * This method is used to extract name of the user whose posts has most comments.
   *
   * @param listOfPostAndComments is the list of posts mapped to comments
   * @param user is the future list of user details
   * @return name of the user
   */
  def postWithMostComments(listOfPostAndComments: Future[List[PostAndComments]], user: Future[List[User]]): Future[String] = {
    listOfPostAndComments.map {
      case Nil => Future {""}
      case _ => val sortedPostAndComments = listOfPostAndComments
        .map(_.sortWith((comparePointer, comparedToPointer) => comparePointer.comment.length <= comparedToPointer.comment.length))
        val resultPost = sortedPostAndComments.map(pointer => pointer.last.post.userId)
        user.map(pointer => resultPost.map(j => pointer.filter(_.id == j).map(_.name).head)).flatten
    }.flatten
  }

}
