package com.maximmakarov.comparator.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.maximmakarov.comparator.data.dao.GroupWithAttributes
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.ItemAttrData

class ItemRepository(private val db: AppDatabase) {

    fun getItems(templateId: Int) = db.itemDao().getItems(templateId)

    fun getItemData(templateId: Int, itemId: Int?): LiveData<List<Pair<AttributeGroup, List<ItemDataWithAttr>>>> =
            if (itemId == null) Transformations.map(getItemsData(templateId)) { it[0].second }
            else Transformations.map(getItemsData(templateId, arrayOf(itemId))) { it[0].second }

    fun getItemsData(templateId: Int, itemsIds: Array<Int> = arrayOf()) =  //todo simplify to map
            MediatorLiveData<List<Pair<Int, List<Pair<AttributeGroup, List<ItemDataWithAttr>>>>>>().apply {
                var groupsWithAttributes: List<GroupWithAttributes>? = null
                var itemAttrData: List<ItemAttrData>? = null

                fun update() {
                    if (groupsWithAttributes != null && itemsIds.isEmpty()) { //new item
                        this.value = listOf(Pair(0, groupsWithAttributes!!.map {
                            Pair(it.group!!, it.attributes.map {
                                ItemDataWithAttr(it, ItemAttrData(attributeId = it.id))
                            })
                        }))
                    }
                    if (groupsWithAttributes != null && itemAttrData != null) { //existed item
                        val data: MutableList<Pair<Int, List<Pair<AttributeGroup, List<ItemDataWithAttr>>>>> = mutableListOf()
                        itemsIds.forEach { itemId ->
                            data.add(Pair(itemId, groupsWithAttributes!!.map {
                                Pair(it.group!!, it.attributes.map { attr ->
                                    ItemDataWithAttr(attr, itemAttrData!!.find { it.itemId == itemId && it.attributeId == attr.id }
                                            ?: ItemAttrData(itemId = itemId, attributeId = attr.id))
                                })
                            }))
                        }
                        this.value = data
                    }
                }

                addSource(db.attributeGroupDao().getGroupsWithAttributes(templateId)) {
                    groupsWithAttributes = it
                    update()
                }
                if (itemsIds.isNotEmpty()) {
                    addSource(db.itemAttrDataDao().getItemsDetails(itemsIds)) {
                        itemAttrData = it
                        update()
                    }
                }
            }

    fun saveItemData(item: Item, data: List<Pair<AttributeGroup, List<ItemDataWithAttr>>>) {
        val dataArray = data.map { it.second }.flatten().map { it.data }.toTypedArray()
        if (item.id == null) {
            val itemId = db.itemDao().insert(item)[0].toInt()
            dataArray.forEach { it.itemId = itemId }

            db.itemAttrDataDao().insert(*dataArray)
        } else {
            db.itemDao().update(item)

            db.itemAttrDataDao().update(*dataArray)
        }
    }
}

data class ItemDataWithAttr(val attribute: Attribute, val data: ItemAttrData)