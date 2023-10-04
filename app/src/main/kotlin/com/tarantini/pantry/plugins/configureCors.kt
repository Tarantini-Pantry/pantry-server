package com.tarantini.pantry.plugins

import com.tarantini.pantry.utils.CustomHeaders
import io.ktor.http.*
import io.ktor.server.plugins.cors.*

fun CORSConfig.configureCors() {
   anyHost()

   allowMethod(HttpMethod.Options)
   allowMethod(HttpMethod.Put)
   allowMethod(HttpMethod.Patch)
   allowMethod(HttpMethod.Delete)

   allowHeader(HttpHeaders.Authorization)
   allowHeader(HttpHeaders.ContentType)
   allowHeader(HttpHeaders.SetCookie)
   allowHeader(HttpHeaders.UserAgent)
   allowHeader(HttpHeaders.Referrer)
   allowHeader(CustomHeaders.Nonce)
   allowHeader(HttpHeaders.Accept)
   allowHeader(HttpHeaders.Cookie)

   exposeHeader(HttpHeaders.SetCookie)
   allowCredentials = true
}
