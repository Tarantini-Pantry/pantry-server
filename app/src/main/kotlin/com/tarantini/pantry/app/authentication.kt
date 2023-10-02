package com.tarantini.pantry.app

import com.tarantini.pantry.app.Config
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.createAuthentication(config: Config, dependencies: Dependencies) {
   authentication {
      oauth("auth-oauth-google") {
         urlProvider = { "http://localhost:8080/callback" }
         providerLookup = {
             OAuthServerSettings.OAuth2ServerSettings(
                 name = "google",
                 authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                 accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                 requestMethod = HttpMethod.Post,
                 clientId = config.oauth.clientId,
                 clientSecret = config.oauth.clientSecret,
                 defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
             )
         }
         client = dependencies.httpClient
      }
   }
}
