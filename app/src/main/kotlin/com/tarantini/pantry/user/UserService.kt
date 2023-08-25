package com.tarantini.pantry.user

import com.tarantini.pantry.domain.User
import org.mindrot.jbcrypt.BCrypt

class UserService(private val datastore: UserDatastore) {

   suspend fun create(name: String, password: String, email: String): Result<Int> {
      val user = User(name, BCrypt.hashpw(password, BCrypt.gensalt()), email)
      return datastore.insert(user)
   }

   suspend fun all(): Result<List<User>> {
      return datastore.findAll()
   }
}
