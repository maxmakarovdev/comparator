package com.maximmakarov.comparator.domain.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.GroupWithAttributes
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.ItemAttrData
import com.maximmakarov.comparator.domain.boundary.IItemRepository
import com.maximmakarov.comparator.domain.model.AttributeData
import com.maximmakarov.comparator.domain.model.Row


class ItemInteractor(private val repository: IItemRepository) : IItemInteractor {

    override fun getItems(templateId: Int): LiveData<List<Item>> {
        return repository.getItems(templateId)
    }

    override fun getItemData(templateId: Int, itemId: Int?): LiveData<List<Pair<AttributeGroup, List<AttributeData>>>> {
        return if (itemId == null) Transformations.map(getItemsData(templateId)) { it[0].second }
        else Transformations.map(getItemsData(templateId, listOf(itemId))) { it[0].second }
    }


    override fun getItemsTransformedData(templateId: Int, items: List<Item>): LiveData<List<Row>> {
        return Transformations.map(getItemsData(templateId, items.map { it.id!! })) { transformItemData(items, it) }
    }

    private fun transformItemData(items: List<Item>, data: List<Pair<Int, List<Pair<AttributeGroup, List<AttributeData>>>>>): List<Row> {
        val tableData = mutableListOf<Row>()

        tableData.add(Row().apply {
            add()
            items.forEach { add(text = it.name) }
        })

        val firstItemGroups = data[0].second
        var row: Row
        var attrs: List<AttributeData>
        for (groupIndex in 0 until firstItemGroups.size) {

            tableData.add(Row().apply { add(text = firstItemGroups[groupIndex].first.name, isGroup = true) })

            attrs = firstItemGroups[groupIndex].second
            for (attrIndex in 0 until attrs.size) {
                row = Row().apply { add(text = attrs[attrIndex].attribute.name) }

                for (i in 0 until data.size) {
                    val d = data[i].second[groupIndex].second[attrIndex].data
                    row.add(text = d.answer, score = d.score ?: 0)
                }

                tableData.add(row)
            }
        }
        return tableData
    }

    private fun getItemsData(templateId: Int, itemsIds: List<Int> = listOf()): LiveData<List<Pair<Int, List<Pair<AttributeGroup, List<AttributeData>>>>>> { //todo simplify to map
        return MediatorLiveData<List<Pair<Int, List<Pair<AttributeGroup, List<AttributeData>>>>>>().apply {
            var groupsWithAttributes: List<GroupWithAttributes>? = null
            var itemAttrData: List<ItemAttrData>? = null

            fun update() {
                if (groupsWithAttributes != null && itemsIds.isEmpty()) { //new item
                    this.value = listOf(Pair(0, groupsWithAttributes!!.map {
                        Pair(it.group!!, it.attributes.map {
                            AttributeData(it, ItemAttrData(attributeId = it.id))
                        })
                    }))
                }
                if (groupsWithAttributes != null && itemAttrData != null) { //existed item
                    val data: MutableList<Pair<Int, List<Pair<AttributeGroup, List<AttributeData>>>>> = mutableListOf()
                    itemsIds.forEach { itemId ->
                        data.add(Pair(itemId, groupsWithAttributes!!.map {
                            Pair(it.group!!, it.attributes.map { attr ->
                                AttributeData(attr, itemAttrData!!.find { it.itemId == itemId && it.attributeId == attr.id }
                                        ?: ItemAttrData(itemId = itemId, attributeId = attr.id))
                            })
                        }))
                    }
                    this.value = data
                }
            }

            addSource(repository.getGroupsWithAttributes(templateId)) {
                groupsWithAttributes = it
                update()
            }
            if (itemsIds.isNotEmpty()) {
                addSource(repository.getItemsDetails(itemsIds.toTypedArray())) {
                    itemAttrData = it
                    update()
                }
            }
        }
    }

    override fun saveItemData(item: Item, data: List<Pair<AttributeGroup, List<AttributeData>>>) {
        val dataArray = data.map { it.second }.flatten().map { it.data }.toTypedArray()
        if (item.id == null) {
            val itemId = repository.saveItem(item).toInt()
            dataArray.forEach { it.itemId = itemId }
            repository.saveItemData(dataArray)
        } else {
            repository.updateItem(item)
            repository.updateItemData(dataArray)
        }
    }
}