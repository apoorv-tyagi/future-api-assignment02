package com.knoldus.parser.controller

import org.scalatest.flatspec.AsyncFlatSpec
import scala.io.Source

class JsonUrlParserSpec extends AsyncFlatSpec {

  val obj: JsonUrlParser = new JsonUrlParser {}

  behavior of "getData"
  it should "fetch the json data from the specified url" in {
    val actualString = obj.getData("https://jsonplaceholder.typicode.com/posts")
    val expectedString = Source.fromFile("./src/test/resources/jsonData").mkString
    assert(expectedString.trim == actualString.trim)
  }

}
