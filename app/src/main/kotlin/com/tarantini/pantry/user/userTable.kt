package com.tarantini.pantry.user

import com.tarantini.pantry.datastore.Table

object UserTable: Table {
   enum class Columns(val label: String) {
      USERNAME("username"),
      HASHED_PASSWORD("hashed_password"),
      EMAIL("email");
   }

   override val tableName: String
      get() = "pantry_user"

   override fun getColumns(): List<String> {
      return Columns.values().map { it.label }.toList()
   }
}
