package com.maximmakarov.comparator.data.item

import com.maximmakarov.comparator.data.AppDatabase


class ItemRepository(private val db: AppDatabase) {

    fun getItems(templateId: Int) = db.itemDao().getItems(templateId)
}