package com.tarantini.pantry.item

import com.tarantini.pantry.domain.Item
import com.tarantini.pantry.domain.ItemName
import com.tarantini.pantry.domain.ItemType
import com.tarantini.pantry.domain.Measurement
import com.tarantini.pantry.domain.Weight

class ItemService(private val datastore: ItemDatastore) {

   suspend fun create(name: String, type: ItemType, value: Double, measurement: Measurement): Result<Item> {
      val item = Item(ItemName(name), type, Weight(value, measurement))
      return datastore.insert(item).map { item }
   }

   suspend fun all(): Result<List<Item>> {
      return datastore.findAll()
   }
}
