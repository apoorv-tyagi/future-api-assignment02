package com.knoldus.twitter

import twitter4j.{Query, _}

object Driver extends App {

  val hashTags: String = "#fcb"

  val query = new Query(hashTags)


  val obj = new TwitterScala
  val resultTweetList = obj.retrieveHashTagTweet(hashTags)
  val resultOfCount = obj.numberOfTweets(resultTweetList)
  val resultOfAverageTweets = obj.getAverageTweetsPerDay(resultTweetList)
  val resultOfAverageLikes = obj.getLikesPerTweet(query)
  val resultOfAverageReTweets = obj.getReTweetsPerTweet(query)

}
