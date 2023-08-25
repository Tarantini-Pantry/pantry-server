package com.tarantini.pantry.domain

data class Weight(val value: Double, val measurement: Measurement)
enum class Measurement {
   GRAM, KILOGRAM,
}

data class UserItem(
   val user: User,
   val item: Item,
   val weight: Weight,
)
