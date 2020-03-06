package com.knoldus.parser.controller

import com.knoldus.parser.model.{Address, Comment, Company, Geo, Post, User}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.mockito.MockitoSugar
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserModelSpec extends AnyFunSuite with MockitoSugar {

  implicit val formats: DefaultFormats.type = DefaultFormats

  val testGeo : Geo = Geo( "a", "b")
  val testCompany: Company = Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")
  val testAddress :Address = Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", testGeo)
  val user: User = User("1", "Upanshu", "upanshu101", "upanshu@gmail.com", testAddress , "8588964008", "upanshu.com", testCompany )
  val post: Post = Post("1", "1", "scala", "hello scala")
  val comment: Comment = Comment(  "1" , "1" , "Upanshu", "upanshu@gmail.com", "hello scala")

  val mockedJsonStringUser: String = write(user)
  val mockedJsonStringPost: String = write(post)
  val mockedJsonStringComment: String = write(comment)
  val mockedUserList = List(user)
  val mockedPostList = List(post)
  val mockedComment = List(comment)

  test ("should return user list") {
    val mockDataExtractor = mock[DataExtractor]
    val userModel = new UserModel(mockDataExtractor)
    when(mockDataExtractor.extractData[User]("https://jsonplaceholder.typicode.com/users")).thenReturn(Future(mockedUserList))
    userModel.usersList.map(list => assert(list == mockedUserList))
  }

  test ("should return post list") {

    val mockDataExtractor = mock[DataExtractor]
    val userModel = new UserModel(mockDataExtractor)
    when(mockDataExtractor.extractData[Post]("https://jsonplaceholder.typicode.com/posts")).thenReturn(Future(mockedPostList))
    userModel.postsList.map(list => assert(list == mockedPostList))

  }

  test ("should return comment list") {

    val mockDataExtractor = mock[DataExtractor]
    val userModel = new UserModel(mockDataExtractor)

    when(mockDataExtractor.extractData[Comment]("https://jsonplaceholder.typicode.com/comments")).thenReturn(Future(mockedComment))
    userModel.commentsList.map(list => assert(list == mockedComment))
  }



}
