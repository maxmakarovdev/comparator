package com.maximmakarov.comparator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
class Template(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        val name: String
)