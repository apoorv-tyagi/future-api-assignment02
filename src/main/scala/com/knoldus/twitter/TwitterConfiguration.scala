package com.knoldus.twitter

import twitter4j.auth.AccessToken
import twitter4j.{Twitter, TwitterFactory}


trait TwitterConfiguration {

  val twitter: Twitter = new TwitterFactory().getInstance()
  // Authorising with your Twitter Application credentials
  twitter.setOAuthConsumer("G0x6p9CyXx5RVbK21ciHZvB2H",
    "NrAjcRC8FVYgwWM4qsBConn4HefS5IzUFHDoNDcs8dh2YBWcvL")
  twitter.setOAuthAccessToken(new AccessToken(
    "3103621308-FoEGRFM2kNYDmFdHVl38WpA10DzkXIxehx4Jit0",
    "Aah1pOyPT9noHAkEWOeLRAdQz1aZh9LglmJtA43np2jH8"))

}

//model