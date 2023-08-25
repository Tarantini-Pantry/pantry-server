package com.tarantini.pantry.datastore

interface Table {
   val tableName: String
   fun getColumns(): List<String>
}

fun selectAll(table: Table) = "SELECT * FROM ${table.tableName}"
fun selectAllWhere(table: Table, vararg params: Pair<String, String>) =
   "${selectAll(table)} WHERE ${params.map { "${it.first} = ${it.second}" }.joinToString(" AND ")}"

fun insertAllInto(table: Table) =
   "INSERT INTO ${table.tableName} (${table.getColumns().joinToString(",")}) values (${table.getColumns().joinToString(separator = ",") { "?" }})"
