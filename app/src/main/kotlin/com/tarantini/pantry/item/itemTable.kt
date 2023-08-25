package com.tarantini.pantry.item

import com.tarantini.pantry.datastore.Table

object ItemTable: Table {
   enum class Columns(val label: String) {
      NAME("name"),
      TYPE("type"),
   }

   override val tableName: String
      get() = "item"

   override fun getColumns(): List<String> {
      return Columns.values().map { it.label }.toList()
   }
}
