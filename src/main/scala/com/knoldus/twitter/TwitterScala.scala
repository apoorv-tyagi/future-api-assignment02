package com.knoldus.twitter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

/**
 * This class methods for twitter api operations.
 */
class TwitterScala(tweetExtract: TweetExtract) {
  /**
   * This function is used to count the number of tweets.
   *
   * @param hashTag which is query for which number of tweets are needed.
   * @return number of tweets
   */
  def numberOfTweets(hashTag: String): Future[Int] = {

    val tweetList = tweetExtract.retrieveHashTagTweet(hashTag)
    val countOfTweets = tweetList.map(element => element.length)
    countOfTweets
    }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate average likes per tweet.
   *
   * @param hashTag is the query for which average likes per tweets is required
   * @return average likes per tweet
   */
  def getAverageLikesPerTweet(hashTag: String): Future[Int] = {

    val tweets = tweetExtract.retrieveHashTagTweet(hashTag)
    val sizeOfTweet = tweets.map(element => element.size)

    val likesCount = tweets.map(element => element.map(_.getFavouriteCount))
    val sumOfTweet = likesCount.map(element => element.sum)
    sumOfTweet.map(sum => sizeOfTweet.map(size => sum / size)).flatten
    }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate re-tweets per tweet
   *
   * @param hashTag is the query for which average re-tweet per tweet is required
   * @return average number of re-tweets per tweet
   */
  def getAverageReTweetsPerTweet(hashTag: String): Future[Int] = {

    val tweets = tweetExtract.retrieveHashTagTweet(hashTag)
    val sizeOfTweet = tweets.map(element => element.size)
    val reTweetCount = tweets.map(element => element.map(_.getReTweetCountInt))
    val sumOfReTweet = reTweetCount.map(element => element.sum)
    sumOfReTweet.map(sum => sizeOfTweet.map(size => sum / size)).flatten
    }.fallbackTo(Future {
    -1
  })


}
