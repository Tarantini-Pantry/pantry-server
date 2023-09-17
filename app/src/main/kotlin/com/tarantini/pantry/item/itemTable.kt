package com.tarantini.pantry.item

import com.tarantini.pantry.datastore.Table

object ItemTable: Table {
   object Columns {
      const val ID = "id"
      const val NAME = "name"
   }
   override val name: String
      get() = "item"
   override val columns: List<String>
      get() = listOf(
         Columns.NAME
      )

}
