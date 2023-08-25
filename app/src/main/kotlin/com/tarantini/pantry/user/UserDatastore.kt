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
         username = rs.getString(Columns.USERNAME.label),
         hashedPassword = rs.getString(Columns.HASHED_PASSWORD.label),
         email = rs.getString(Columns.EMAIL.label),
      )
   }

   suspend fun insert(user: User): Result<Int> {
      return template.update(
         insertAllInto(UserTable),
         listOf(user.username, user.hashedPassword, user.email)
      )
   }

   suspend fun findAll(): Result<List<User>> {
      return template.queryForList(selectAll(UserTable), mapper)
   }
}
