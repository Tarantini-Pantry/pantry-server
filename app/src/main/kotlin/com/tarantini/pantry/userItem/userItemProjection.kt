package com.tarantini.pantry.userItem

import com.tarantini.pantry.datastore.Projection
import com.tarantini.pantry.datastore.qualifiedColumn
import com.tarantini.pantry.item.ItemTable as IT
import com.tarantini.pantry.userItem.UserItemTable as UIT

class UserItemProjection(private val userId: Int): Projection {
   object Values {
      const val NAME = "name"
      const val MEASUREMENT = "measurement"
      const val VALUE = "value"
   }

   override val queryString: String
      get() = "SELECT " +
         "${IT.qualifiedColumn(IT.Columns.NAME)} as ${Values.NAME}, " +
         "${UIT.qualifiedColumn(UIT.Columns.VALUE)} as ${Values.VALUE}, " +
         "${UIT.qualifiedColumn(UIT.Columns.MEASUREMENT)} as ${Values.MEASUREMENT} " +
         "FROM " +
         "${IT.name} " +
         "LEFT JOIN " +
         "${UIT.name} " +
         "ON " +
         "${UIT.qualifiedColumn(UIT.Columns.ITEM_ID)} = ${IT.qualifiedColumn(IT.Columns.ID)} " +
         "WHERE " +
         "${UIT.qualifiedColumn(UIT.Columns.USER_ID)} = $userId "

   override val values: List<String>
      get() = listOf(Values.NAME, Values.MEASUREMENT, Values.VALUE)
}
