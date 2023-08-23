package com.tarantini.pantry.item

import com.tarantini.pantry.domain.CreateItemRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.itemEndpoints(service: ItemService) {
   get("/v1/item") {
      service.all().fold(
         { call.respond(HttpStatusCode.OK, it) },
         { call.respond(HttpStatusCode.InternalServerError, it) }
      )
   }

   post("/v1/item") {
      withCreateItemRequest {
         service.create(it.name, it.type, it.weight, it.measurement).fold(
            { call.respond(HttpStatusCode.OK, it) },
            { call.respond(HttpStatusCode.InternalServerError) }
         )
      }
   }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.withCreateItemRequest(f: suspend (CreateItemRequest) -> Unit) {
   runCatching { call.receive<CreateItemRequest>() }
      .fold(
         { f(it) },
         { call.respond(HttpStatusCode.BadRequest, it) }
      )
}
