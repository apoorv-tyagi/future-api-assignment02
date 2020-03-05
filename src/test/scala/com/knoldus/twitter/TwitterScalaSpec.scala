package com.knoldus.twitter


import org.scalatest.flatspec.AsyncFlatSpec

import twitter4j.{Query, _}


class TwitterScalaSpec extends AsyncFlatSpec  {

  var twitterObj: TwitterScala = _

  val twitter = new TwitterScala

  val hashTags: String = "#fcb"
  val query = new Query(hashTags)



  "getTweets" should "retrieve tweets" in {

    val actualResult = twitter.retrieveHashTagTweet(hashTags)

    val actualBool = actualResult.map(x => x.nonEmpty)

    val expected = true

    actualBool.map(result => assert(result==expected))
  }


  "getLikesPerTweet" should "give number of average likes" in{

    val actualResult = twitter.getLikesPerTweet(query)

    val expectedCount = 0

    actualResult.map(result => assert(result==expectedCount))
  }

  "numberOfTweet" should "give number of tweet" in {

    val actualResult = twitter.numberOfTweets(query)

    val expectedResult = 15

    actualResult.map(actual => assert(actual == expectedResult))

  }

  "getAverageTweetsPerDay" should( "give average number of tweets in a day") in{

    val actualResult = twitter.getAverageTweetsPerDay(query)

    val expected = 0

    actualResult.map(result => assert(result >= expected))

  }

  "getReTweetsPerTweet" should "give number of average re-tweets" in{

    val actualResult = twitter.getReTweetsPerTweet(query)

    val expectedCount = 0

    actualResult.map(result => assert(result >= expectedCount))

  }


}
