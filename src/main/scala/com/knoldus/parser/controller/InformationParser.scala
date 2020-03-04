package com.knoldus.parser.controller

import net.liftweb.json.DefaultFormats
import scala.concurrent.Future

class InformationParser(dataExtractor: DataExtractor)  {

  implicit val formats: DefaultFormats.type = DefaultFormats

  def parseData[T](jsonData: String)(implicit m: Manifest[T]): Future[List[T]] = {
    dataExtractor.extractData[T](jsonData)
  }

}
