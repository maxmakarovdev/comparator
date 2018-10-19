package com.maximmakarov.comparator.domain.boundary

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.model.GroupWithAttributes
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.ItemAttrData


interface IItemRepository {

    fun getItems(templateId: Int): LiveData<List<Item>>

    fun getItemsDetails(itemsIds: Array<Int>): LiveData<List<ItemAttrData>>

    fun saveItem(item: Item): Long

    fun saveItemData(data: Array<ItemAttrData>): List<Long>

    fun updateItem(item: Item)

    fun updateItemData(data: Array<ItemAttrData>)
}