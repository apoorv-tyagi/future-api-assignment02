package com.knoldus.parser.model

case class Company(name: String, catchPhrase: String, bs: String)

case class Address(street: String, suite: String, city: String, zipcode: String, geo: Geo)

case class Geo(lat: String, lng: String)

case class Post(userId: String, id: String, title: String, body: String)

case class Comment(postId: String, id: String, name: String, email: String, body: String)

case class User(id: String, name: String, username: String, email: String, address: Address, phone: String, website: String, company: Company)

case class UserPost(user: User, post: List[Post])

case class PostAndComments(post: Post, comment: List[Comment])
