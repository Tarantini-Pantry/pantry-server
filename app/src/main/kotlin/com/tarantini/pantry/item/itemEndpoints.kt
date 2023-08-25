package com.tarantini.pantry.item

import com.tarantini.pantry.domain.CreateItemRequest
import com.tarantini.pantry.endpoints.withRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemEndpoints(service: ItemService) {

   post("/v1/items") {
      withRequest<CreateItemRequest> {request ->
         service.create(request.name, request.tags).fold(
            { result -> call.respond(HttpStatusCode.OK, result) },
            { call.respond(HttpStatusCode.InternalServerError) }
         )
      }
   }

   get("/v1/items") {
      service.findAll().fold(
         { call.respond(HttpStatusCode.OK, it) },
         { call.respond(HttpStatusCode.InternalServerError, it) }
      )
   }
}
