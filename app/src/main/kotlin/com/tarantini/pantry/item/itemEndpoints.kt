package com.tarantini.pantry.item

import com.tarantini.pantry.domain.CreateItemRequest
import com.tarantini.pantry.endpoints.withPathParam
import com.tarantini.pantry.endpoints.withRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemEndpoints(service: ItemService) {

   get("/v1/items") {
      service.findAll().fold(
         { call.respond(HttpStatusCode.OK, it) },
         { call.respond(HttpStatusCode.InternalServerError, it) }
      )
   }

   post("/v1/items") {
      withRequest<CreateItemRequest> { request ->
         service.create(request.name, request.tags).fold(
            { result -> call.respond(HttpStatusCode.Created, result) },
            { call.respond(HttpStatusCode.InternalServerError, it) }
         )
      }
   }

   delete("/v1/items/{itemId}") {
      withPathParam<Int>("itemId") { itemId ->
         service.deleteItem(itemId).fold(
            { call.respond(HttpStatusCode.NoContent) },
            { call.respond(HttpStatusCode.InternalServerError, it) }
         )
      }
   }

   delete("/v1/items") {
      service.deleteAllItems().fold(
         { call.respond(HttpStatusCode.NoContent) },
         { call.respond(HttpStatusCode.InternalServerError, it) }
      )
   }
}
