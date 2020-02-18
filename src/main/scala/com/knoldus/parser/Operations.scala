package com.knoldus.parser

case class UserPost(user: User, post: List[Post] )

case class PostAndComments(post: Post, comments: List[Comment])


object Operations{

  /**
   *
   * @param user is the list of type user
   * @param post takes the list of type post
   * @return list of user mapped with the post done by user
   */
  def userPostsOperations(user: List[User], post: List[Post]): List[UserPost] = {
    user.map(users => UserPost(users, post.filter(users.id == _.userId)))
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
   *
   * @param postComment is the list of post and comment by user
   * @return post with most comment
   */
  def mostPostComment(postComment: List[PostAndComments]): (Post, Int) = {
    val commentCount = for {eachPostComment <- postComment
                            } yield (eachPostComment.post, eachPostComment.comments.length)
    commentCount.reduceLeft((first, second) => if (first._2 > second._2) first else second)
  }

}

object Driver extends App{

  val users: List[User] = JsonDataParsingUsers.userData(JsonDataParsingUsers.jsonUserData)
  val comments: List[Comment] = JsonCommentParsing.commentsData(JsonCommentParsing.jsonCommentsData)
  val posts: List[Post] = JsonPostParsing.postsData(JsonPostParsing.jsonPostsData)

  val userPostList = Operations.userPostsOperations(users, posts)
  println(userPostList)
  val postCommentsList = Operations.postsCommentsOperations(posts,comments)

  val mostUserPostCount = Operations.mostUserPost(userPostList)
  val mostPostCommentCount = Operations.mostPostComment(postCommentsList)
  }