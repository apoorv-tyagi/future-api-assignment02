package com.knoldus.parser

import org.scalatest._


class OperationsSpec extends AsyncFlatSpec with BeforeAndAfterAll {

    var max: Operations = _

    override def beforeAll(): Unit = {
      max = new Operations
    }
    "find Answers method " should " find User with Maximum number of posts " in {

      max.mostUserPost
    }

}