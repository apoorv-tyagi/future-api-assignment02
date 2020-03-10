package com.knoldus.twitter

import org.mockito.MockitoSugar
import org.scalatest.flatspec.AsyncFlatSpec
import scala.concurrent.Future


class TwitterScalaSpec extends AsyncFlatSpec with MockitoSugar  {

  val testUserTweet = List(UserTweet(1234,1,12), UserTweet(23534,4,45), UserTweet(5434,5,25), UserTweet(5434,3,23), UserTweet(8569,9,45))
  val mockTwitterObject : TweetExtract = mock[TweetExtract]
  val objectOfMock = new TwitterScala(mockTwitterObject)
  val hashTag = "#messi"

  "numberOfTweets" should "return count of tweets" in {

    when(mockTwitterObject.retrieveHashTagTweet(hashTag)).thenReturn(Future{testUserTweet})
    objectOfMock.numberOfTweets(hashTag).map(expected => assert(expected==5))
  }

  "averageReTweetsForReTweet" should "return average re-tweets for re-tweet" in {

    when(mockTwitterObject.retrieveHashTagTweet(hashTag)).thenReturn(Future{testUserTweet})
    objectOfMock.getAverageReTweetsPerTweet(hashTag).map(expected => assert(expected == 30))

  }

  "averageLikesForReTweets" should "return average like for re-tweet" in {

    when(mockTwitterObject.retrieveHashTagTweet(hashTag)).thenReturn(Future{testUserTweet})
    objectOfMock.getAverageLikesPerTweet(hashTag).map(expected => assert(expected == 4))
  }

}
