package com.knoldus.twitter

import com.typesafe.config.ConfigFactory
import org.scalatest.FlatSpec
import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

class TwitterConfigurationSpec extends FlatSpec {

  behavior of "getTwitterInstance"
  it should "instance of type Twitter" in {
  val config = ConfigFactory.load()
  val twitter: Twitter = new TwitterFactory().getInstance()

  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer(config.getString("consumer.key"),
    config.getString("consumer.secret"))
  twitter.setOAuthAccessToken(new AccessToken(
    config.getString("token.key"),
    config.getString("token.secret")))

   assert(twitter.getScreenName == "upanshu101")
  }

  it should "throw exception as credentials are invalid" in {
    try{
      val twitter: Twitter = new TwitterFactory().getInstance()
    }catch{
      case exception: Exception => assert(exception.getMessage == "failed to get twitter instance")
    }
  }

}
