package com.tarantini.pantry.plugins

import com.tarantini.pantry.app.Config
import com.tarantini.pantry.authentication.UserSession
import io.ktor.server.sessions.*
import io.ktor.util.*

fun SessionsConfig.configureSessions(config: Config) {
   val secretSignKey = hex(config.sessionConfig.signingKey)
   val encryptionKey = hex(config.sessionConfig.encryptionKey)
   cookie<UserSession>(UserSession.NAME, SessionStorageMemory()) {
      transform(SessionTransportTransformerEncrypt(encryptionKey, secretSignKey))
      cookie.httpOnly = false
   }
}
