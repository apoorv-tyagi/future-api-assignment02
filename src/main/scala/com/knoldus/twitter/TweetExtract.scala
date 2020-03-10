package com.knoldus.twitter

import twitter4j.Query
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

/**
 * This class contains methods to to retrieve tweets and get average tweet per day.
 */
class TweetExtract extends TwitterConfiguration {
  /**
   * This function is used to retrieve tweets.
   *
   * @param hashTag is the query for which tweets list is required
   * @return list of user tweets
   */
  def retrieveHashTagTweet(hashTag: String): Future[List[UserTweet]] = Future {
    val hashTagQuery = new Query(hashTag)
    val result = twitter.search(hashTagQuery).getTweets.asScala.toList
    result.map(element => UserTweet(element.getId, element.getFavoriteCount, element.getRetweetCount))
  }.fallbackTo {
    Future {
      List.empty[UserTweet]
    }
  }

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

}
