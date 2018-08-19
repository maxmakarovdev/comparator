package com.maximmakarov.comparator.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.maximmakarov.comparator.data.dao.GroupWithAttributes
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.ItemAttrData

class ItemRepository(private val db: AppDatabase) {

    fun getItems(templateId: Int) = db.itemDao().getItems(templateId)

    fun getItemData(templateId: Int, itemId: Int = 0): LiveData<List<Pair<AttributeGroup, List<ItemDataWithAttr>>>> {
        return MediatorLiveData<List<Pair<AttributeGroup, List<ItemDataWithAttr>>>>().apply {
            var groupsWithAttributes: List<GroupWithAttributes>? = null
            var itemAttrData: List<ItemAttrData>? = null

            fun update() {
                if (groupsWithAttributes != null && itemId == 0) {
                    this.value = groupsWithAttributes!!.map {
                        Pair(it.group!!, it.attributes.map { ItemDataWithAttr(it) })
                    }
                }
                if (groupsWithAttributes != null && itemAttrData != null) {
                    this.value = groupsWithAttributes!!.map {
                        Pair(it.group!!, it.attributes.map { attr ->
                            ItemDataWithAttr(attr, itemAttrData!!.find { it.attributeId == attr.id }
                                    ?: ItemAttrData())
                        })
                    }
                }
            }

            addSource(db.attributeGroupDao().getGroupsWithAttributes(templateId)) {
                groupsWithAttributes = it
                update()
            }
            if (itemId != 0) {
                addSource(db.itemAttrDataDao().getItemAttributeDetails(itemId)) {
                    itemAttrData = it
                    update()
                }
            }
        }
    }

    fun saveItemData(data: List<Pair<AttributeGroup, List<ItemDataWithAttr>>>) {

    }
}

data class ItemDataWithAttr(val attribute: Attribute, val data: ItemAttrData = ItemAttrData())