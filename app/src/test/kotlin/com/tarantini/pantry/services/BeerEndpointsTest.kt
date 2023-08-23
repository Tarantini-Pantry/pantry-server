package com.tarantini.pantry.services

import com.tarantini.pantry.item.ItemDatastore
import com.tarantini.pantry.item.ItemService
import com.tarantini.pantry.item.itemEndpoints
import io.kotest.core.extensions.install
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.testing.testApplication

class ItemEndpointsTest : FunSpec() {
   init {

      val ds = install(postgres)
      val service = ItemService(ItemDatastore(ds))

      test("Missing route should return 404") {
         testApplication {
            application {
               install(ContentNegotiation) { jackson() }
            }
            routing {
               itemEndpoints(service)
            }
            val resp = client.get("/v1/unkn")
            resp.status shouldBe HttpStatusCode.NotFound
         }
      }

      test("GET /v1/item should return all items") {
         testApplication {
            application {
               install(ContentNegotiation) { jackson() }
            }
            routing {
               itemEndpoints(service)
            }
            val resp = client.get("/v1/item")
            resp.status shouldBe HttpStatusCode.OK
         }
      }
   }
}
