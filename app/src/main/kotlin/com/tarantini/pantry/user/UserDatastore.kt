package com.tarantini.pantry.user

import com.tarantini.pantry.datastore.JdbcCoroutineTemplate
import com.tarantini.pantry.datastore.insertAllInto
import com.tarantini.pantry.datastore.selectAll
import com.tarantini.pantry.domain.User
import com.tarantini.pantry.user.UserTable.Columns
import org.springframework.jdbc.core.RowMapper
import javax.sql.DataSource

class UserDatastore(ds: DataSource) {

   private val template = JdbcCoroutineTemplate(ds)

   private val mapper = RowMapper { rs, _ ->
      User(
         username = rs.getString(Columns.USERNAME),
         userImageUrl = rs.getString(Columns.USER_IMAGE_URL),
         email = rs.getString(Columns.EMAIL),
      )
   }

   suspend fun insert(user: User): Result<Int> {
      return template.update(
         insertAllInto(UserTable),
         listOf(user.username, user.userImageUrl, user.email)
      )
   }

   suspend fun findAll(): Result<List<User>> {
      return template.queryForList(selectAll(UserTable), mapper)
   }

   suspend fun existsByEmail(email: String): Result<Int?> {
      return template.query(
         "SELECT id FROM ${UserTable.name} WHERE email = '$email'",
         RowMapper { rs, _ -> rs.getInt(Columns.ID) })
   }
}
