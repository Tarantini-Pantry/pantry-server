package com.tarantini.pantry.plugins

import com.auth0.jwk.JwkProviderBuilder
import com.tarantini.pantry.app.Config
import com.tarantini.pantry.utils.Constants.GOOGLE
import com.tarantini.pantry.utils.Constants.NONCE
import com.tarantini.pantry.utils.CustomHeaders
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.net.URL
import java.util.concurrent.TimeUnit

fun AuthenticationConfig.configureGoogleJwt(config: Config) {
   val jwkProvider = JwkProviderBuilder(URL(config.googleAuth.certificateUrl))
      .cached(10, 24, TimeUnit.HOURS)
      .rateLimited(10, 1, TimeUnit.MINUTES)
      .build()

   jwt(GOOGLE) {
      verifier(jwkProvider) {
         withIssuer(config.googleAuth.issuer)
         withAudience(config.googleAuth.audience)
      }
      validate {jwtCredential ->
         val nonceFromHeader = request.headers[CustomHeaders.Nonce]
         if (nonceFromHeader.isNullOrBlank()) {
            return@validate null
         }

         val nonceFromJWT = jwtCredential.payload.getClaim(NONCE).asString()

         if (nonceFromHeader != nonceFromJWT)
            return@validate null

         JWTPrincipal(jwtCredential.payload)
      }
   }
}
