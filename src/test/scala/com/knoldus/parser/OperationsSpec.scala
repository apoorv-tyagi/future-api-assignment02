package com.knoldus.parser

import org.scalatest._

class OperationsSpec extends FlatSpec with BeforeAndAfterAll {
  var arithmeticOperations: OperationsSpec = new OperationsSpec

  override def beforeAll(): Unit = {
    arithmeticOperations = new OperationsSpec()
  }

  override def afterAll(): Unit = {
    if (arithmeticOperations != null) {
    }
  }

  "user post " should "return list of post" in {
    val actualResult = Operations.userPostsOperations(List)
    val expectedResult = 10
    assert(expectedResult == actualResult)
  }
}

