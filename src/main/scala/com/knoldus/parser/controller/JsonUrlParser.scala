package com.knoldus.parser.controller

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

/**
 * This Trait contain defined method to get string from json url.
 */
trait JsonUrlParser {

  /**
   * This method is used to get string from url.
   * @param url of json file
   * @return string of data
   */
  def getData(url: String): String = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    val response = client.execute(request)
    IOUtils.toString(response.getEntity.getContent)
  }

}
