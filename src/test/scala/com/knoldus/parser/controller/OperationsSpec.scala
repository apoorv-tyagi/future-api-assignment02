package com.knoldus.parser.controller

import com.knoldus.parser.model.{Address, Comment, Company, Geo, Post, PostAndComments, User, UserPost}
import org.scalatest._
import scala.concurrent.Future


class OperationsSpec extends AsyncFlatSpec with BeforeAndAfterAll {

  val testGeo : Geo = Geo( "a", "b")
  val testCompany: Company = Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")
  val testAddress : Address = Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", testGeo)
  val testUserList: Future[List[User]] = Future{List(User("1", "Upanshu", "upanshu101", "upanshu@gmail.com", testAddress , "8588964008", "upanshu.com", testCompany )) }
  val testPostList: Future[List[Post]] = Future{ List(Post("1", "1", "scala", "hello scala")) }
  val testCommentList: Future[List[Comment]] = Future{ List(Comment(  "1" , "1" , "Upanshu", "upanshu@gmail.com", "hello scala"))}
  val testUserPost: Future[List[UserPost]] = Future{List(UserPost(User("1", "Upanshu", "upanshu101", "upanshu@gmail.com", testAddress , "8588964008", "upanshu.com", testCompany ),List(Post("1", "1", "scala", "hello scala"))))}
  val testListPostComment : Future[List[PostAndComments]] = Future{List(PostAndComments(Post("1", "1", "scala", "hello scala"),List(Comment(  "1" , "1" , "Upanshu", "upanshu@gmail.com", "hello scala"))))}
  var operations: Operations = new Operations

  override def beforeAll(): Unit = {
    operations = new Operations()
  }

 "joinUserAndPOst method" should "map user with post" in {

   val actualResult = operations.joinUserAndPost(testUserList, testPostList)

   val expectedResult: List[UserPost] = List(UserPost(User("1", "Upanshu", "upanshu101", "upanshu@gmail.com", testAddress , "8588964008", "upanshu.com", testCompany ),List(Post("1", "1", "scala", "hello scala"))))
   actualResult.map(result => assert(result == expectedResult ))

 }

  "map postToComment" should "map posts to comment" in {

    val actualResult = operations.mapPostToComment(testPostList,testCommentList)

    val expectedResult: List[PostAndComments] = List(PostAndComments(Post("1", "1", "scala", "hello scala"),List(Comment(  "1" , "1" , "Upanshu", "upanshu@gmail.com", "hello scala"))))
    actualResult.map(result => assert(result == expectedResult ))
  }

  "userWithMostPost method" should "give user name with most post" in {

    val actualResult = operations.userWithMostPost(testUserPost)

    val expectedResult: String = "Upanshu"
    actualResult.map(result => assert(result == expectedResult ))

  }

  "postWithMostComments" should "give user name whose post have most comment" in {

    val actualResult: Future[String] = operations.postWithMostComments(testListPostComment, testUserList)
    val expectedResult: String = "Upanshu"
    actualResult.map(result => {
      assert(result == expectedResult )
    })

  }

}
