package com.knoldus.twitter


import org.scalatest.flatspec.AsyncFlatSpec

import twitter4j.{Query, _}


class TwitterScalaSpec extends AsyncFlatSpec  {

  var twitterObj: TwitterScala = _

  val twitter = new TwitterScala

  case class Status(createdAt: String, id :Int, id_Str : Int, text : String, user: String)
  val list: List[Status] = List(Status("noon", 1, 2 , "messi is best" , "upanshu"))
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

  "getReTweetsPerTweet" should "give number of average re-tweets" in{

    val actualResult = twitter.getReTweetsPerTweet(query)
    val expectedCount = 0

    actualResult.map(result => assert(result >= expectedCount))

  }


}
