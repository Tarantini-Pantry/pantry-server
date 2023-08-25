package com.tarantini.pantry.user

import com.tarantini.pantry.domain.CreateUserRequest
import com.tarantini.pantry.endpoints.withPathParam
import com.tarantini.pantry.item.ItemService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.userEndpoints(userService: UserService, itemService: ItemService) {
   get("/v1/users") {
      userService.all().fold(
         { call.respond(HttpStatusCode.OK, it) },
         { call.respond(HttpStatusCode.InternalServerError, it) }
      )
   }

   post("/v1/users") {
      withCreateUserRequest {
         userService.create(it.username, it.password, it.email).fold(
            { call.respond(HttpStatusCode.OK, it) },
            { call.respond(HttpStatusCode.InternalServerError) }
         )
      }
   }

//   get("/v1/users/{userId}/items") {
//      withPathParam<Long>("userId") { userId ->
//         itemService.findAllByUser(userId).fold(
//            { call.respond(HttpStatusCode.OK, it) },
//            { call.respond(HttpStatusCode.InternalServerError, it) }
//         )
//      }
//   }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.withCreateUserRequest(f: suspend (CreateUserRequest) -> Unit) {
   runCatching { call.receive<CreateUserRequest>() }
      .fold(
         { f(it) },
         { call.respond(HttpStatusCode.BadRequest, it) }
      )
}
