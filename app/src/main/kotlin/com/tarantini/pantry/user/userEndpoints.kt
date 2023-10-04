package com.tarantini.pantry.user

import com.tarantini.pantry.domain.CreateUserItemRequest
import com.tarantini.pantry.endpoints.withPathParam
import com.tarantini.pantry.endpoints.withRequest
import com.tarantini.pantry.endpoints.withUserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userEndpoints(userService: UserService) {
   get("/v1/users") {
      userService.all().fold(
         { call.respond(HttpStatusCode.OK, it) },
         { call.respond(HttpStatusCode.InternalServerError, it) }
      )
   }

   get("/v1/users/{userId}/items") {
      withPathParam<Int>("userId") { userId ->
         userService.findAllByUser(userId).fold(
            { call.respond(HttpStatusCode.OK, it) },
            { call.respond(HttpStatusCode.InternalServerError, it) }
         )
      }
   }

   post("/v1/users/{userId}/items") {
      withUserSession { session ->
         withPathParam<Int>("userId") { userId ->
            withRequest<CreateUserItemRequest> { request ->
               userService.addItemToUser(userId, request.itemId, request.weight).fold(
                  { call.respond(HttpStatusCode.OK, it) },
                  { call.respond(HttpStatusCode.InternalServerError, it) }
               )
            }
         }
      }
   }
}
