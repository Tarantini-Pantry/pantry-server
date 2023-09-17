package com.tarantini.pantry.app

import com.sksamuel.hoplite.env.Environment
import com.tarantini.pantry.datastore.createDataSource
import com.tarantini.pantry.item.ItemDatastore
import com.tarantini.pantry.item.ItemService
import com.tarantini.pantry.user.UserDatastore
import com.tarantini.pantry.user.UserService
import com.tarantini.pantry.userItem.UserItemDatastore
import com.zaxxer.hikari.HikariDataSource
import io.micrometer.core.instrument.MeterRegistry

/**
 * Creates all the dependencies used by this service wrapped in a [Dependencies] object.
 *
 *  @param env the variable for the environment eg STAGING or PROD.
 * @param serviceName a unique name for this service used in logs and metrics
 * @param config the loaded configuration values.
 */
fun createDependencies(env: Environment, serviceName: String, config: Config): Dependencies {

   val registry = createDatadogMeterRegistry(config.datadog, env, serviceName)
   val ds = createDataSource(config.db, registry)

   val itemDatastore = ItemDatastore(ds)
   val itemService = ItemService(itemDatastore)

   val userDatastore = UserDatastore(ds)
   val userItemDatastore = UserItemDatastore(ds)
   val userService = UserService(userDatastore, userItemDatastore)

   return Dependencies(
      registry,
      ds,
      itemDatastore,
      itemService,
      userDatastore,
      userService,
      userItemDatastore
   )
}

/**
 * The [Dependencies] object is a god object that contains all the dependencies of the project.
 *
 * In an dependency injection framework like Spring, this is created automagically for you and is
 * called ApplicationContext.
 */
data class Dependencies(
   val registry: MeterRegistry,
   val ds: HikariDataSource,
   val itemDatastore: ItemDatastore,
   val itemService: ItemService,
   val userDatastore: UserDatastore,
   val userService: UserService,
   val userItemDatastore: UserItemDatastore
)
