package com.maximmakarov.comparator.item.form

import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.item.ItemRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class FormViewModel : ViewModel(), KoinComponent {

    private val repository: ItemRepository by inject()

    fun getItemData(templateId: Int, itemId: Int) = repository.getItemData(templateId, itemId)
}