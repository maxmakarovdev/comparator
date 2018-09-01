package com.maximmakarov.comparator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.maximmakarov.comparator.data.model.Item


@Dao
interface ItemDao : BaseDao<Item> {

    @Query("SELECT * FROM items")
    fun getItems(): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE template_id = :templateId")
    fun getItems(templateId: Int): LiveData<List<Item>>
}