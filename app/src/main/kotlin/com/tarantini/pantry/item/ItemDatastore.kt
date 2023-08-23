package com.tarantini.pantry.item

import com.tarantini.pantry.datastore.JdbcCoroutineTemplate
import com.tarantini.pantry.domain.Item
import com.tarantini.pantry.domain.ItemName
import com.tarantini.pantry.domain.ItemType
import com.tarantini.pantry.domain.Measurement
import com.tarantini.pantry.domain.Weight
import org.springframework.jdbc.core.RowMapper
import javax.sql.DataSource

class ItemDatastore(ds: DataSource) {

   private val template = JdbcCoroutineTemplate(ds)

   private val mapper = RowMapper { rs, _ ->
      Item(
         name = ItemName(rs.getString("name")),
         type = ItemType.valueOf(rs.getString("type")),
         weight = Weight(rs.getDouble("value"), Measurement.valueOf(rs.getString("measurement"))),
      )
   }

   suspend fun insert(item: Item): Result<Int> {
      return template.update(
         "INSERT INTO items (name, type, value, measurement) VALUES (?,?,?,?)",
         listOf(item.name.value, item.type.name, item.weight.value, item.weight.measurement.name)
      )
   }

   suspend fun findAll(): Result<List<Item>> {
      return template.queryForList("SELECT * FROM items", mapper)
   }
}
