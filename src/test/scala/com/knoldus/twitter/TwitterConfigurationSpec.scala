package com.knoldus.twitter

import org.scalatest.FlatSpec
import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}

class TwitterConfigurationSpec extends FlatSpec {

  behavior of "getTwitterInstance"
  it should "instance of type Twitter" in {

  val twitter: Twitter = new TwitterFactory().getInstance()
      twitter.setOAuthConsumer("G0x6p9CyXx5RVbK21ciHZvB2H",
    "NrAjcRC8FVYgwWM4qsBConn4HefS5IzUFHDoNDcs8dh2YBWcvL")
  twitter.setOAuthAccessToken(new AccessToken(
    "3103621308-FoEGRFM2kNYDmFdHVl38WpA10DzkXIxehx4Jit0",
    "Aah1pOyPT9noHAkEWOeLRAdQz1aZh9LglmJtA43np2jH8"))
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
