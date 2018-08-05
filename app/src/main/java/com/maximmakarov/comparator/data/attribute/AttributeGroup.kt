package com.maximmakarov.comparator.data.attribute

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.maximmakarov.comparator.data.template.Template


@Entity(tableName = "attribute_groups",
        foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["template_id"])])
class AttributeGroup(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "template_id")
        val templateId: Int,

        val name: String
)