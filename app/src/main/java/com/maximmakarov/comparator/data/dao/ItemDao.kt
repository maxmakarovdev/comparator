package com.maximmakarov.comparator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maximmakarov.comparator.data.model.Item


@Dao
interface ItemDao : BaseDao<Item> {

    @Query("SELECT * FROM items WHERE template_id = :templateId")
    fun getItems(templateId: Int): LiveData<List<Item>>
}