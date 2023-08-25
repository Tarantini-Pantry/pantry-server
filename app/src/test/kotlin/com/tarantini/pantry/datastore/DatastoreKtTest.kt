package com.tarantini.pantry.datastore

import com.tarantini.pantry.item.ItemTable
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class DatastoreKtTest: DescribeSpec({

   describe("select all where") {

      it("should create proper string") {
         selectAllWhere(ItemTable, Pair("column", "value")) shouldBe
            "SELECT * FROM item WHERE column = value"
      }
   }
})
