package com.tarantini.pantry.domain

data class User(
   val username: String,
   val hashedPassword: String,
   val email: String
)

data class CreateUserRequest(
   val username: String,
   val password: String,
   val email: String
)

data class UserResponse(
   val username: String,
   val email: String
)
