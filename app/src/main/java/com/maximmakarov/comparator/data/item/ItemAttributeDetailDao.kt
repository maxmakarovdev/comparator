package com.maximmakarov.comparator.data.item

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maximmakarov.comparator.data.BaseDao


@Dao
interface ItemAttributeDetailDao : BaseDao<ItemAttributeDetail> {

    @Query("SELECT * FROM attributes_details WHERE item_id = :itemId")
    fun getItemAttributeDetails(itemId: Int): LiveData<List<ItemAttributeDetail>>
}