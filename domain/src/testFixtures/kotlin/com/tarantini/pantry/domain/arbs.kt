package com.tarantini.pantry.domain

import io.kotest.property.Arb
import io.kotest.property.arbitrary.map
import io.kotest.property.arbitrary.string

fun Arb.Companion.itemName() = Arb.string(7).map { ItemName(it) }
