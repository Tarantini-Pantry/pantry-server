package com.tarantini.pantry.domain

data class Weight(val value: Double, val measurement: Measurement)
enum class Measurement {
   GRAM, KILOGRAM,
}
data class UserItem(
   val item: Item,
   val weight: Weight
)
data class CreateUserItemRequest (
   val itemId: Int,
   val weight: Weight
)
