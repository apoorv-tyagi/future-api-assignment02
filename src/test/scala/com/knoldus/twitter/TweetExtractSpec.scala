package com.knoldus.twitter


import org.scalatest.flatspec.AsyncFlatSpec

import twitter4j.{Query, _}


class TweetExtractSpec extends AsyncFlatSpec {

  val twitter = new TweetExtract

  val hashTags: String = "#fcb"
  val query = new Query(hashTags)

  "getTweets" should "retrieve tweets" in {

    val actualResult = twitter.retrieveHashTagTweet(hashTags)
    val actualBool = actualResult.map(list => list.nonEmpty)

    val expected = true

    actualBool.map(result => assert(result == expected))
  }

  "getAverageTweetsPerDay" should "give average number of tweets in a day" in {

    val actualResult = twitter.getAverageTweetsPerDay(query)
    val expected = 0

    actualResult.map(result => assert(result >= expected))

  }
}
