package com.maximmakarov.comparator.item

import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.item.ItemDao
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class ItemsViewModel : ViewModel(), KoinComponent {

    private val dao: ItemDao by inject()

    fun getItems(templateId: Int) = dao.getTemplateItems(templateId)
}
