package com.tarantini.pantry.client

import com.tarantini.pantry.domain.Item
import com.tarantini.pantry.domain.JacksonSupport.fromJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes

class PantryClient(
   private val config: ClientConfig,
) {

   private val client = HttpClient(Apache) {
      install(ContentEncoding)
      install(HttpTimeout) {
         requestTimeoutMillis = config.requestTimeoutMillis
         connectTimeoutMillis = config.connectTimeoutMillis
         socketTimeoutMillis = config.socketTimeoutMillis
      }
      install(HttpRequestRetry) {
         retryOnServerErrors(maxRetries = config.maxRetries)
         exponentialDelay()
      }
      expectSuccess = false
      followRedirects = true
      developmentMode = true
   }

   /**
    * Returns all items in the system.
    */
   suspend fun getItems(): Result<List<Item>> = runCatching {
      val resp = client.get("/item")
      resp.readBytes().fromJson<List<Item>>()
   }
}
