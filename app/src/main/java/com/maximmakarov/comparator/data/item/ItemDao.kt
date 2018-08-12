package com.maximmakarov.comparator.data.item

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maximmakarov.comparator.data.BaseDao


@Dao
interface ItemDao : BaseDao<Item> {

    @Query("SELECT * FROM items WHERE template_id = :templateId")
    fun getItems(templateId: Int): LiveData<List<Item>>
}