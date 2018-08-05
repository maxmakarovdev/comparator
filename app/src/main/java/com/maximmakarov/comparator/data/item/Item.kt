package com.maximmakarov.comparator.data.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.maximmakarov.comparator.data.template.Template


@Entity(tableName = "items",
        foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["template_id"])])
class Item(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "template_id")
        val templateId: Int,

        val name: String,

        val score: Double? = null
)