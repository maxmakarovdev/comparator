package com.maximmakarov.comparator.data.repository

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.dao.ItemAttrDataDao
import com.maximmakarov.comparator.data.dao.ItemDao
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.ItemAttrData
import com.maximmakarov.comparator.domain.boundary.IItemRepository

class ItemRepository(private val itemDao: ItemDao,
                     private val itemAttrDataDao: ItemAttrDataDao) : IItemRepository {

    override fun getItems(templateId: Int): LiveData<List<Item>> {
        return itemDao.getItems(templateId)
    }

    override fun getItemsDetails(itemsIds: Array<Int>): LiveData<List<ItemAttrData>> {
        return itemAttrDataDao.getItemsDetails(itemsIds)
    }

    override fun saveItem(item: Item): Long {
        return itemDao.insert(item)[0]
    }

    override fun saveItemData(data: Array<ItemAttrData>): List<Long> {
        return itemAttrDataDao.insert(*data)
    }

    override fun updateItem(item: Item) {
        itemDao.update(item)
    }

    override fun updateItemData(data: Array<ItemAttrData>) {
        itemAttrDataDao.update(*data)
    }
}