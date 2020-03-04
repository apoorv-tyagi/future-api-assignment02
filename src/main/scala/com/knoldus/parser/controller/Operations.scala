package com.knoldus.parser.controller

import com.knoldus.parser.model._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Operations {
  /**
   * This function is used to model user with their corresponding posts.
   * @param users is the list of type user
   * @param posts takes 0the list of type post
   * @return list of user mapped with the post done by user
   */
  def joinUserAndPost(users: Future[List[User]], posts: Future[List[Post]]): Future[List[UserPost]] = {
    users.map(users => posts.map(posts => users.map(user => UserPost(user, posts.filter(user.id == _.userId))))).flatten

  }
  /**
   * This function is used to combine posts and comments on the corresponding posts.
   * @param posts is the list of type post
   * @param comments takes the list of type comment
   * @return list of post and comment done by same user
   */
  def mapPostToComment(posts: Future[List[Post]], comments: Future[List[Comment]]): Future[List[PostAndComments]] = {
    val listOfPostAndComment = for {listOfPosts <- posts
                                    listOfComments <- comments
                                    } yield listOfPosts.map(post => PostAndComments(post, listOfComments.filter(_.postId == post.id)))
    listOfPostAndComment
  }
  /**
   * This function is used to find user who have done most post.
   * @param userPosts is the list of user and posts done by user
   * @return the user with most posts
   */
  def userWithMostPost(userPosts: Future[List[UserPost]]): Future[String] = {
    val userWithMostPost = for {
      listOfUserPost <- userPosts
    } yield listOfUserPost.map(element => (element.user, element.post.length)).reduceLeft((first, second) => if (first._2 > second._2) first else second)
    userWithMostPost.map(_._1.name)
  }

  /**
   * This funstion is used to find user whose post have most comments.
   * @param futureList is the future list of post and comment.
   * @param users      is the future list of users
   * @return name of the user whose post has most comment
   */
  def postWithMostComments(futureList: Future[List[PostAndComments]], users: Future[List[User]]): Future[String] = {
    /**
     * This fuction is used to extract names of user with most comments on post
     * @param futureLists is the future list of post and comment.
     * @param result is empty list container which will store user name
     * @return
     */
    def retrieveUserName(futureLists: Future[List[PostAndComments]], result: Future[String]): Future[String] = {

      futureLists.map {
        case Nil => result
        case _ :: Nil => result
        case head :: tail :: rest => if (head.comment.length > tail.comment.length) {
          retrieveUserName(Future {head :: rest}, Future {head.post.userId})
        } else { retrieveUserName(Future { tail :: rest}, Future {
            tail.post.userId
          })
        }
      }.flatten

    }

    val maxPost = retrieveUserName(futureList, Future {
      "0"
    })
    users.map(allUser => maxPost.map(mostPostComment => allUser.filter(eachUser => eachUser.id == mostPostComment).head.name)).flatten
  }


}
