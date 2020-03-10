package com.knoldus.twitter

/**
 * This case class is used to store required user information from twitter Status.
 * @param id is the tweet id of the user
 * @param getFavouriteCount is the likes on post.
 * @param getReTweetCountInt is the re-tweet count of the user
 */
case class UserTweet(id : Long, getFavouriteCount : Int, getReTweetCountInt: Int)
