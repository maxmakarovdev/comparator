package com.maximmakarov.comparator.data.item

import androidx.room.*
import com.maximmakarov.comparator.data.template.Template


@Entity(tableName = "items",
        indices = [Index("template_id")],
        foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["template_id"], onDelete = ForeignKey.CASCADE)])
class Item(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "template_id")
        val templateId: Int,

        val name: String,

        val score: Double? = null
)