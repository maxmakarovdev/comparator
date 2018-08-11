package com.maximmakarov.comparator.data.item

import androidx.room.*
import com.maximmakarov.comparator.data.attribute.Attribute


@Entity(tableName = "attributes_details",
        indices = [Index("item_id"), Index("attribute_id")],
        foreignKeys = [
            ForeignKey(entity = Item::class, parentColumns = ["id"], childColumns = ["item_id"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = Attribute::class, parentColumns = ["id"], childColumns = ["attribute_id"], onDelete = ForeignKey.SET_NULL)
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