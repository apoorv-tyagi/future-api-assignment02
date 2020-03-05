package com.knoldus.twitter

import twitter4j.{Query, _}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

class TwitterScala extends TwitterConfiguration {

  /**
   * This function is used to retrieve tweets.
   *
   * @param hashTag is the string of wanted hashTag
   * @return list of status
   */
  def retrieveHashTagTweet(hashTag: String): Future[List[Status]] = Future {
    val hashTagQuery = new Query(hashTag)
    val result = twitter.search(hashTagQuery).getTweets.asScala.toList
    result
  }.fallbackTo {
    Future {
      List.empty[Status]
    }
  }

  /**
   * This function is used to count the number of tweets.
   *
   * @param tweets which is query of required hashTag
   * @return number of tweets
   */
  def numberOfTweets(tweets: Query): Future[Int] = Future {

    val tweetList = twitter.search(tweets).getTweets.asScala.toList
    val countOfTweets = tweetList.length
    countOfTweets

  }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate average tweets per day.
   *
   * @param tweet is query of required hashTag
   * @return average tweets done per day
   */
  def getAverageTweetsPerDay(tweet: Query): Future[Int] = Future {

    val tweetList = twitter.search(tweet).getTweets.asScala.toList
    val sortedPosts = tweetList.sortWith((a, b) => a.getCreatedAt.before(b.getCreatedAt))
    sortedPosts.reverse.head.getCreatedAt.compareTo(sortedPosts.head.getCreatedAt)

  }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate average likes per tweet.
   *
   * @param tweet is the query of required hashTag
   * @return average likes per tweet
   */
  def getAverageLikesPerTweet(tweet: Query): Future[Int] = Future {

    val list = twitter.search(tweet)
    val tweets = list.getTweets.asScala.toList
    val likesCount = tweets.map(_.getFavoriteCount)
    likesCount.sum / tweets.size
  }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate re-tweets per tweet
   *
   * @param tweet is query of required hashTag
   * @return average number of re-tweets per tweet
   */
  def getAverageReTweetsPerTweet(tweet: Query): Future[Int] = Future {

    val list = twitter.search(tweet)
    val tweets = list.getTweets.asScala.toList
    val reTweetCount = tweets.map(_.getRetweetCount)
    reTweetCount.sum / tweets.size
  }.fallbackTo(Future {
    -1
  })


}




