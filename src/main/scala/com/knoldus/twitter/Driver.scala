package com.knoldus.twitter

import twitter4j.{Query, _}

object Driver extends App {

  val hashTags: String = "#fcb"

  val query = new Query(hashTags)


  val objectTwitter = new TwitterScala
  val resultTweetList = objectTwitter.retrieveHashTagTweet(hashTags)
  val resultOfTweetCount = objectTwitter.numberOfTweets(query)
  val resultOfAverageTweets = objectTwitter.getAverageTweetsPerDay(query)
  val resultOfAverageLikes = objectTwitter.getAverageLikesPerTweet(query)
  val resultOfAverageReTweets = objectTwitter.getAverageReTweetsPerTweet(query)


}
