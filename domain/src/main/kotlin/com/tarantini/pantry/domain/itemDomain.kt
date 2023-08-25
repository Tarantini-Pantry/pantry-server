package com.tarantini.pantry.domain

data class Item(
   val name: String,
   val tags: List<String>
)

data class CreateItemRequest(
   val name: String,
   val tags: List<String>
)
