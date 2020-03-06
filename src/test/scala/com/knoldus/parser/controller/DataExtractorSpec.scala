package com.knoldus.parser.controller

import com.knoldus.parser.model.Post
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.mockito.MockitoSugar
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.ExecutionContext.Implicits.global


class DataExtractorSpec extends AnyFunSuite with MockitoSugar {

  implicit val formats: DefaultFormats.type = DefaultFormats

  val stringJson: String =
    """
      |{
      |userId: 1,
      |id: 1,
      |title: "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
      |body: "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto"
      |}
      |""".stripMargin

  val post: Post = Post("1", "1", "scala", "hello scala")

  val mockedJsonString : String =write(stringJson)
  val mockList = List(post)

  test  ("should return list of string") {

    val mockJsonParser = mock[JsonUrlParser]
    val jsonString = new DataExtractor(mockJsonParser)
    when(mockJsonParser.getData("https://jsonplaceholder.typicode.com/posts")).thenReturn(mockedJsonString)
    jsonString.extractData[Post](stringJson).map(list => list==mockList)

  }
}
