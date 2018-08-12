package com.maximmakarov.comparator.item

import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.item.ItemRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class ItemsViewModel : ViewModel(), KoinComponent {

    private val repository: ItemRepository by inject()

    fun getItems(templateId: Int) = repository.getItems(templateId)
}
