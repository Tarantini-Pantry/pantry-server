package com.tarantini.pantry.user

import com.tarantini.pantry.datastore.Table

object UserTable: Table {
   object Columns {
      const val ID = "id"
      const val USERNAME = "username"
      const val USER_IMAGE_URL = "user_image_url"
      const val EMAIL = "email"
   }

   override val name: String
      get() = "pantry_user"
   override val idColumn: String
      get() = Columns.ID
   override val columns: List<String>
      get() = listOf(Columns.USERNAME, Columns.USER_IMAGE_URL, Columns.EMAIL)
}
