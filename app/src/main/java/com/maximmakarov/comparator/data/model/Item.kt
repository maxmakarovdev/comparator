package com.maximmakarov.comparator.data.model

import androidx.room.*


@Entity(tableName = "items",
        indices = [Index("template_id")],
        foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["template_id"], onDelete = ForeignKey.CASCADE)])
class Item(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "template_id")
        val templateId: Int,

        var name: String = "",

        val score: Double? = null
)