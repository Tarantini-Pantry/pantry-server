package com.tarantini.pantry.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.util.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import io.ktor.util.reflect.*
import java.util.*

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.withRequest(f: (T) -> Unit) {
   runCatching { call.receive<T>() }
      .fold(
         { f(it) },
         { call.respond(HttpStatusCode.BadRequest, it) }
      )
}

suspend inline fun <reified T: Any> PipelineContext<Unit, ApplicationCall>.withPathParam(name: String, f: PipelineContext<Unit, ApplicationCall>.(T) -> Unit) {
   runCatching {
      call.parameters.getOrFail(name).coerce<T>(typeInfo<T>()).fold(
         { f(it) },
         {
            call.respond(HttpStatusCode.BadRequest, "Invalid path parameter: $name")
         }
      )
   }
}

suspend inline fun <reified T: Any> PipelineContext<Unit, ApplicationCall>.withQueryParam(name: String, f: PipelineContext<Unit, ApplicationCall>.(T) -> Unit) {
   runCatching {
      call.request.queryParameters.getOrFail(name).coerce<T>(typeInfo<T>()).fold(
         { f(it) },
         {
            call.respond(HttpStatusCode.BadRequest, "Invalid value for parameter: $name")
         }
      )
   }
}
@Suppress("UNCHECKED_CAST")
fun <T : Any> String.coerce(typeInfo: TypeInfo): Result<T> = runCatching {
   when (typeInfo.reifiedType) {
      String::class.java -> this as T
      Int::class.java -> this.toInt() as T
      Long::class.java -> this.toLong() as T
      Double::class.java -> this.toDouble() as T
      Boolean::class.java -> this.toBoolean() as T
      else -> throw UnknownFormatConversionException(this)
   }
}
