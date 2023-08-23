package com.tarantini.pantry.domain

data class ItemName(val value: String)
enum class ItemType { Dry, Wet }
data class Weight(val value: Double, val measurement: Measurement)
enum class Measurement {
   GRAM, KILOGRAM,
}
data class Item(
   val name: ItemName,
   val type: ItemType,
   val weight: Weight,
)

data class CreateItemRequest(
   val name: String,
   val type: ItemType,
   val weight: Double,
   val measurement: Measurement
)
