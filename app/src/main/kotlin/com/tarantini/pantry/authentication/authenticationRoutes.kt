package com.tarantini.pantry.authentication

import com.sksamuel.tabby.results.then
import com.tarantini.pantry.domain.User
import com.tarantini.pantry.user.UserService
import com.tarantini.pantry.utils.Constants.GOOGLE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.authenticationRoutes(userService: UserService) {
   route("users/authenticate") {
      authenticate(GOOGLE) {
         get {
            val principal = call.principal<JWTPrincipal>()!!
            val user = User(principal.getUserName(), principal.getImageUrl(), principal.getEmail())
            userService.exists(user).fold(
               { userId ->
                  if (userId == null) {
                     userService.create(user).fold(
                        {
                           call.sessions.set(UserSession.createSession(it, principal))
                           call.respond(HttpStatusCode.OK, user)
                        },
                        { call.respond(HttpStatusCode.InternalServerError, it) }
                     )
                  } else {
                     call.sessions.set(UserSession.createSession(userId, principal))
                     call.respond(HttpStatusCode.OK, user)
                  }
               },
               { call.respond(HttpStatusCode.InternalServerError, it) }
            )
         }
      }
   }

   route("logout") {
      get {
         call.sessions.clear<UserSession>()
         call.respondText("Logged Out!")
      }
   }
}
