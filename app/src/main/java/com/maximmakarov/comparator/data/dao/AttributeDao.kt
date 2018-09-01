package com.maximmakarov.comparator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.maximmakarov.comparator.data.model.Attribute

@Dao
interface AttributeDao : BaseDao<Attribute> {
    @Query("SELECT * FROM attributes")
    fun getAttributes(): LiveData<List<Attribute>>
}