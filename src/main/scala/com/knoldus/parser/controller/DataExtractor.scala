package com.knoldus.parser.controller

import net.liftweb.json.{DefaultFormats, parse}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class DataExtractor extends JsonUrlParser {

  def extractData[A](url :String)(implicit m:Manifest[A]): Future[List[A]] = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    val data = parse(getData(url))
    Future(data.children.map(_.extract[A]))
  }
}
