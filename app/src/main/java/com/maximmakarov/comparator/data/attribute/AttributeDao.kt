package com.maximmakarov.comparator.data.attribute

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface AttributeDao {
    @Insert
    fun insert(attribute: Attribute): Long

    @Update
    fun update(attribute: Attribute)

    @Delete
    fun delete(attribute: Attribute)
}