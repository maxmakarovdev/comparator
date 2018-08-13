package com.maximmakarov.comparator.data.model

import androidx.room.*


@Entity(tableName = "attribute_groups",
        indices = [Index("template_id")],
        foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["template_id"], onDelete = ForeignKey.CASCADE)])
class AttributeGroup(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "template_id")
        val templateId: Int,

        val name: String
)