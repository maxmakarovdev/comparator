package com.maximmakarov.comparator.data.attribute

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "attributes",
        foreignKeys = [ForeignKey(entity = AttributeGroup::class, parentColumns = ["id"], childColumns = ["group_id"])])
class Attribute(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "group_id")
        val groupId: Int,

        val name: String,

        val isImportant: Boolean = false
)