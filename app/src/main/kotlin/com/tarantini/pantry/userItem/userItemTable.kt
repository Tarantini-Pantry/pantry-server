package com.tarantini.pantry.userItem

import com.tarantini.pantry.datastore.Table

object UserItemTable: Table {
   object Columns {
      const val ID = "id"
      const val USER_ID = "user_id"
      const val ITEM_ID = "item_id"
      const val VALUE = "value"
      const val MEASUREMENT = "measurement"
   }

   override val name: String
      get() = "user_item"
   override val columns: List<String>
      get() = listOf(Columns.USER_ID, Columns.ITEM_ID, Columns.VALUE, Columns.MEASUREMENT)
}
