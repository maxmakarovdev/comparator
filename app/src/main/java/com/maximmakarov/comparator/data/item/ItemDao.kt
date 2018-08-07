package com.maximmakarov.comparator.data.item

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ItemDao {
    @Insert
    fun insert(item: Item): Long

    @Insert
    fun insertAll(items: List<Item>): Long

    @Update
    fun update(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("SELECT * FROM items WHERE template_id = :templateId")
    fun getTemplateItems(templateId: Int): LiveData<List<Item>>
}