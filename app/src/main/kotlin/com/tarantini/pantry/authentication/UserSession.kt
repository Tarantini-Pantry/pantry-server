package com.tarantini.pantry.authentication

import io.ktor.server.auth.jwt.*

data class UserSession(
   val id: Int,
   val name: String,
   val email: String,
   val imageUrl: String
) {
   companion object {
      const val NAME = "user_session"
      fun createSession(userId: Int, token: JWTPrincipal): UserSession {
         return UserSession(userId, token.getUserName(), token.getImageUrl(), token.getEmail())
      }
   }
}

fun JWTPrincipal.getUserName():String {
   return getClaim("name", String::class).orEmpty()
}

fun JWTPrincipal.getEmail():String {
   return getClaim("email", String::class).orEmpty()
}

fun JWTPrincipal.getImageUrl():String {
   return getClaim("picture", String::class).orEmpty()
}
