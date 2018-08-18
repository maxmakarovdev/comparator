package com.maximmakarov.comparator.data.model

import androidx.room.*


@Entity(tableName = "attributes",
        indices = [Index("group_id")],
        foreignKeys = [ForeignKey(entity = AttributeGroup::class, parentColumns = ["id"], childColumns = ["group_id"], onDelete = ForeignKey.CASCADE)])
data class Attribute(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "group_id")
        val groupId: Int,

        val name: String,

        val isImportant: Boolean = false
)