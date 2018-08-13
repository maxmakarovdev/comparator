package com.maximmakarov.comparator.data.item

import androidx.room.*
import com.maximmakarov.comparator.data.attribute.Attribute


@Entity(tableName = "attributes_details",
        indices = [Index("item_id"), Index("attribute_id")],
        foreignKeys = [
            ForeignKey(entity = Item::class, parentColumns = ["id"], childColumns = ["item_id"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = Attribute::class, parentColumns = ["id"], childColumns = ["attribute_id"], onDelete = ForeignKey.SET_NULL)
        ])
class ItemAttrData(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        @ColumnInfo(name = "item_id")
        var itemId: Int = 0,

        @ColumnInfo(name = "attribute_id")
        var attributeId: Int = 0,

        var answer: String = "",

        var score: Int? = null
)