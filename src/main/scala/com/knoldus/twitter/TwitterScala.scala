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
   * @param hashTag is the string of wanted hashtag
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
   * @param tweets which is future of list of status
   * @return number of tweets
   */
  def numberOfTweets(tweets: Future[List[Status]]): Future[Int] = {

    val countOfTweets = tweets.map(tweet => tweet.length)
    countOfTweets

    }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate average tweets per day.
   *
   * @param tweetList is the future list of status of tweet
   * @return average tweets done per day
   */
  def getAverageTweetsPerDay(tweetList: Future[List[Status]]): Future[Int] = {

    val sortedPosts = tweetList.map(tweet => tweet.sortWith((a, b) => a.getCreatedAt.before(b.getCreatedAt)))
    sortedPosts.map(post => post.reverse.head.getCreatedAt.compareTo(post.head.getCreatedAt))

    }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate average likes per tweet.
   *
   * @param tweet is the string of required hash tag
   * @return average likes per tweet
   */
  def getLikesPerTweet(tweet: Query): Future[Int] = Future {
    val list = twitter.search(tweet)
    val tweets = list.getTweets.asScala.toList
    val likesCount = tweets.map(_.getFavoriteCount)
    likesCount.size / tweets.size
  }.fallbackTo(Future {
    -1
  })

  /**
   * This function is used to calculate re-tweets per tweet
   *
   * @param tweet is the string of required hash tag
   * @return average number of re-tweets per tweet
   */
  def getReTweetsPerTweet(tweet: Query): Future[Int] = Future {

    val list = twitter.search(tweet)
    val tweets = list.getTweets.asScala.toList
    val reTweetCount = tweets.map(_.getRetweetCount)
    reTweetCount.sum / tweets.size
  }.fallbackTo(Future {
    -1
  })


}




