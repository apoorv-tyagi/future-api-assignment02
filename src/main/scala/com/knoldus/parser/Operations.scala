package com.knoldus.parser

case class UserPost(user: User, post: List[Post])

case class PostAndComments(post: Post, comments: List[Comment])

object Operations{
  /**
   *
   * @param users is the list of type user
   * @param posts takes the list of type post
   * @return list of user mapped with the post done by user
   */
  def userPostsOperations(users: List[User], posts: List[Post]): List[UserPost] = {
    users.map(user=>UserPost(user,posts.filter(user.id==_.userId)))
   }
  /**
   *
   * @param post is the list of type post
   * @param comment takes the list of type comment
   * @return list of post and comment done by same user
   */
  def postsCommentsOperations(post: List[Post], comment: List[Comment]): List[PostAndComments] = {
    post.map(posts => PostAndComments(posts, comment.filter(posts.id == _.postId)))
  }
  /**
   *
   * @param userPosts is the list of user and posts done by user
   * @return the user with most posts
   */
  def mostUserPost(userPosts: List[UserPost]): (User, Int) = {
    val countPost = for {eachPost <- userPosts
                         } yield (eachPost.user, eachPost.post.length)
    countPost.reduceLeft((first, second) => if (first._2 > second._2) first else second)
  }
  /**
   * @param postComment is the list of post and comments
   * @param user is the list of all users
   * @return user whose post have most comments
   */
  def mostPostComment(postComment:List[PostAndComments],user:List[User]): (String, String, Int) ={
    @scala.annotation.tailrec
    def innerTest(innerList:List[PostAndComments], result:(String,Int)):(String,Int)= innerList match {
      case Nil=>result
      case head::Nil=>if(head.comments.length>=result._2) (head.post.userId,head.comments.length) else result
      case head::tail=> if(head.comments.length>=result._2) innerTest(tail,(head.post.userId,head.comments.length)) else  innerTest(tail,result)
    }
    val result= innerTest(postComment,("",0))
    val tupleList=user.filter(_.id==result._1)
    (tupleList.head.name,result._1,result._2)
  }

}


object Driver extends App{

  val users: List[User] = JsonDataParsingUsers.userData(JsonDataParsingUsers.jsonUserData)
  val comments: List[Comment] = JsonCommentParsing.commentsData(JsonCommentParsing.jsonCommentsData)
  val posts: List[Post] = JsonPostParsing.postsData(JsonPostParsing.jsonPostsData)


  val userPostList = Operations.userPostsOperations(users, posts)
  val postCommentsList = Operations.postsCommentsOperations(posts,comments)

  val mostUserPostCount = Operations.mostUserPost(userPostList)
  val mostPostCommentCount = Operations.mostPostComment(postCommentsList,users)

  }