package com.maximmakarov.comparator.data.repository

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.GroupWithAttributes
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.ItemAttrData
import com.maximmakarov.comparator.domain.boundary.IItemRepository

class ItemRepository(private val db: AppDatabase) : IItemRepository {

    override fun getItems(templateId: Int): LiveData<List<Item>> {
        return db.itemDao().getItems(templateId)
    }

    override fun getGroupsWithAttributes(templateId: Int): LiveData<List<GroupWithAttributes>> {
        return db.attributeGroupDao().getGroupsWithAttributes(templateId)
    }

    override fun getItemsDetails(itemsIds: Array<Int>): LiveData<List<ItemAttrData>> {
        return db.itemAttrDataDao().getItemsDetails(itemsIds)
    }

    override fun saveItem(item: Item): Long {
        return db.itemDao().insert(item)[0]
    }

    override fun saveItemData(data: Array<ItemAttrData>): List<Long> {
        return db.itemAttrDataDao().insert(*data)
    }

    override fun updateItem(item: Item) {
        db.itemDao().update(item)
    }

    override fun updateItemData(data: Array<ItemAttrData>) {
        db.itemAttrDataDao().update(*data)
    }
}