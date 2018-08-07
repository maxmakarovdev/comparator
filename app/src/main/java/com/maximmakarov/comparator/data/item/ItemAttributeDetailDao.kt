package com.maximmakarov.comparator.data.item

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ItemAttributeDetailDao {
    @Insert
    fun insert(itemAttributeDetail: ItemAttributeDetail): Long

    @Insert
    fun insertAll(itemAttributeDetails: List<ItemAttributeDetail>): Long

    @Update
    fun update(itemAttributeDetail: ItemAttributeDetail)

    @Delete
    fun delete(itemAttributeDetail: ItemAttributeDetail)

    @Query("SELECT * FROM attributes_details WHERE item_id = :itemId")
    fun getItemAttributeDetails(itemId: Int): LiveData<List<ItemAttributeDetail>>
}