package com.tarantini.pantry.user

import com.tarantini.pantry.datastore.Table

object UserTable: Table {
   object Columns {
      const val ID = "id"
      const val USERNAME = "username"
      const val HASHED_PASSWORD = "hashed_password"
      const val EMAIL = "email"
   }

   override val name: String
      get() = "pantry_user"
   override val columns: List<String>
      get() = listOf(Columns.USERNAME, Columns.HASHED_PASSWORD, Columns.EMAIL)
}
