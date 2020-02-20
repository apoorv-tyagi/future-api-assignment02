package com.knoldus.parser

import org.scalatest._

import scala.concurrent.Future


class OperationsSpec extends AsyncFlatSpec with BeforeAndAfterAll {


    "Max Finder" should "eventually find user with maximum post" in {
    val futureFind: Future[String] = Driver.test("post")
    futureFind map { find => assert(find == "Clementina DuBuque") }
  }

  "Max Finder" should "eventually find user with whose post has maximum comment" in {
    val futureFind: Future[String] = Driver.test("comment")
    futureFind map { find => assert(find == "Clementina DuBuque") }
  }

}