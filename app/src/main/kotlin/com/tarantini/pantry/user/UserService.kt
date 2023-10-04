package com.tarantini.pantry.user

import com.tarantini.pantry.domain.User
import com.tarantini.pantry.domain.UserItem
import com.tarantini.pantry.domain.Weight
import com.tarantini.pantry.userItem.UserItemDatastore

class UserService(private val datastore: UserDatastore, private val userItemDatastore: UserItemDatastore) {

   suspend fun create(user: User): Result<Int> {
      return datastore.insert(user)
   }

   suspend fun exists(user: User): Result<Int?> {
      return datastore.existsByEmail(user.email)
   }

   suspend fun all(): Result<List<User>> {
      return datastore.findAll()
   }

   suspend fun findAllByUser(userId: Int): Result<List<UserItem>> {
      return userItemDatastore.getItemsForUser(userId)
   }

   suspend fun addItemToUser(userId: Int, itemId: Int, weight: Weight): Result<Int> {
      return userItemDatastore.insert(userId, itemId, weight)
   }
}
