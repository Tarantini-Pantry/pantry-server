package com.tarantini.pantry.datastore

interface Table {
   val name: String
   val idColumn: String
   val columns: List<String>
}

interface Projection {
   val queryString: String
   val values: List<String>
}

fun Table.qualifiedColumn(column: String): String = "$name.$column"

fun selectAll(table: Table) = "SELECT * FROM ${table.name}"
fun selectAllWhere(table: Table, vararg params: Pair<String, String>) =
   "${selectAll(table)} WHERE ${params.joinToString(" AND ") { "${it.first} = ${it.second}" }}"
fun insertAllInto(table: Table) =
   "INSERT INTO ${table.name} (${table.columns.joinToString(",")}) values (${table.columns.joinToString(separator = ",") { "?" }})"
