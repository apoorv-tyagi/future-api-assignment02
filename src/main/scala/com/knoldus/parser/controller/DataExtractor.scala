package com.knoldus.parser.controller

import net.liftweb.json.{DefaultFormats, parse}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
 * This class contains method to extract jason data.
 * @param jsonUrlParser is the object of JsonUrlParser.
 */
class DataExtractor (jsonUrlParser: JsonUrlParser) {

  /**
   * This method is used to convert string of json data in the list of defined type.
   * @param url contains url for json data
   * @return List of defined type
   */
  def extractData[A](url :String)(implicit m:Manifest[A]): Future[List[A]] = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    val data = parse(jsonUrlParser.getData(url))
    Future(data.children.map(_.extract[A]))
  }
}
