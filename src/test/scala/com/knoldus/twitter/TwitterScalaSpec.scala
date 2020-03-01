package com.knoldus.twitter

import org.scalatest.flatspec.AsyncFlatSpec
import twitter4j.Status

import scala.concurrent.Future

class TwitterScalaSpec extends AsyncFlatSpec  {

  var resultTweetList: Future[List[Status]]= _

  var twitterScala : TwitterScala = _

  def beforeAll(): Unit = {
    resultTweetList = Driver.resultTweetList
    twitterScala = new TwitterScala
  }

}
