package com.knoldus.twitter

import twitter4j.{Query, _}

object Driver extends App {

  val hashTags: String = "#fcb"

  val query = new Query(hashTags)


  val obj = new TwitterScala
  val resultTweetList = obj.retrieveHashTagTweet(hashTags)
  val resultOfTweetCount = obj.numberOfTweets(query)
  val resultOfAverageTweets = obj.getAverageTweetsPerDay(query)
  val resultOfAverageLikes = obj.getAverageLikesPerTweet(query)
  val resultOfAverageReTweets = obj.getAverageReTweetsPerTweet(query)


}
