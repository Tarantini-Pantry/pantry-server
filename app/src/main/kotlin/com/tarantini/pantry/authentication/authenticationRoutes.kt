package com.tarantini.pantry.authentication

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.headers
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.authenticationRoutes(httpClient: HttpClient) {
   authenticate("auth-oauth-google") {
      get("/login") {
         // Redirects to 'authorizeUrl' automatically
      }
      get("/callback") {
         val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
         call.sessions.set(UserSession(principal!!.state!!, principal.accessToken))
         call.respondRedirect("/userInfo")
      }
   }
   get("/userInfo") {
      val userSession: UserSession? = call.sessions.get()
      if (userSession != null) {
         val userInfo: String = httpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
            headers {
               append(HttpHeaders.Authorization, "Bearer ${userSession.accessToken}")
            }
         }.body()
         call.respondText("Hello, ${userInfo}!")
      }
   }
}
