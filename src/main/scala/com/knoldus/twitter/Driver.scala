package com.knoldus.twitter

import twitter4j.{Query, _}

object Driver extends App {

  val hashTags: String = "#fcb"

  val query = new Query(hashTags)

}
