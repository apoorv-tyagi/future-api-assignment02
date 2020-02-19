package com.knoldus.parser

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


case class UserPost(user: User, post: List[Post])

case class PostAndComments(post: Post, comment: List[Comment])

object Operations{
  /**
   *
   * @param users is the list of type user
   * @param posts takes the list of type post
   * @return list of user mapped with the post done by user
   */
  def userPostsOperations(users: Future[List[User]], posts: Future[List[Post]]): Future[List[UserPost]] = {
    users.map(users => posts.map(posts => (users.map(user=>UserPost(user,posts.filter(user.id==_.userId)))))).flatten

   }
  /**
   *
   * @param posts is the list of type post
   * @param comments takes the list of type comment
   * @return list of post and comment done by same user
   */
  def postsCommentsOperations(posts: Future[List[Post]], comments: Future[List[Comment]]): Future[List[PostAndComments]] = {
    //posts.map(posts=>comments.map(comments => posts.map(post => PostAndComments(post, comments.filter(post.id == _.postId)))))

    val listOfPostAndComment = for {listOfPosts <- posts
                                    listOfComments <- comments
                                    } yield listOfPosts.map(post => PostAndComments(post, listOfComments.filter(_.postId == post.id)))
    listOfPostAndComment
  }
  /**
   *
   * @param userPosts is the list of user and posts done by user
   * @return the user with most posts
   */
  def mostUserPost(userPosts: Future[List[UserPost]]): Future[String] = {
    val userWithMostPost = for{
      listOfUserPost <- userPosts
    } yield listOfUserPost.map(x => (x.user,x.post.length)).reduceLeft((first, second) => if (first._2 > second._2) first else second)
    userWithMostPost.map(_._1.name)
  }

//
//  def postWithMaxComment(futureList: Future[List[PostAndComments]], users: List[User]): Future[String] = {
//    @scala.annotation.tailrec
//    def innerTest(futureLists: Future[List[PostAndComments]], result: Future[String]): Future[String] = {
//
//      futureLists.map{
//        case Nil => result
//        case _ :: Nil => result
//        case head :: tail :: rest  => if (head.comment.length > tail.comment.length) {
//          innerTest(Future{head::tail}, Future{head.post.userId})
//        } else {innerTest(Future{tail:: rest},Future{tail.post.userId})}}.flatten
//
//  }
//    val maxPost = innerTest(futureList, Future{"0"})
//
//    users.map(users => maxPost.map(mostPostComment =>
//}

}


object Driver extends App{

  val usersList: Future[List[User]] = JsonDataParsingUsers.userParser("https://jsonplaceholder.typicode.com/users")
  val commentsList: Future[List[Comment]] = JsonCommentParsing.commentParser("https://jsonplaceholder.typicode.com/comments")
  val postsList: Future[List[Post]] = JsonPostParsing.postParser("https://jsonplaceholder.typicode.com/posts")


  val userPostList = Operations.userPostsOperations(usersList, postsList)
  val postCommentsList = Operations.postsCommentsOperations(postsList,commentsList)

  val mostUserPostCount = Operations.mostUserPost(userPostList)
//  val mostPostCommentCount = Operations.mostPostComment(postCommentsList,usersList)
  Thread.sleep(30000)
  println(mostUserPostCount)

  }