package com.maximmakarov.comparator.domain.interactor

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.domain.model.AttributeData
import com.maximmakarov.comparator.domain.model.Row


interface IItemInteractor {

    fun getItems(templateId: Int): LiveData<List<Item>>

    fun getItemData(templateId: Int, itemId: Int?): LiveData<List<Pair<AttributeGroup, List<AttributeData>>>>

    fun getItemsTransformedData(templateId: Int, items: List<Item> = listOf()): LiveData<List<Row>>

    fun saveItemData(item: Item, data: List<Pair<AttributeGroup, List<AttributeData>>>)
}