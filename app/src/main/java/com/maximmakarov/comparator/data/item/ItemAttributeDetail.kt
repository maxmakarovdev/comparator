package com.maximmakarov.comparator.data.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.maximmakarov.comparator.data.attribute.Attribute


@Entity(tableName = "attributes_details",
        foreignKeys = [
            ForeignKey(entity = Item::class, parentColumns = ["id"], childColumns = ["item_id"]),
            ForeignKey(entity = Attribute::class, parentColumns = ["id"], childColumns = ["attribute_id"])
        ])
class ItemAttributeDetail(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "item_id")
        val itemId: Int,

        @ColumnInfo(name = "attribute_id")
        val attributeId: Int,

        val answer: String,

        val score: Int? = null
)