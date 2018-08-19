package com.maximmakarov.comparator.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.repository.ItemRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class ItemsViewModel : ViewModel(), KoinComponent {

    private val repository: ItemRepository by inject()
    private val inputData: MutableLiveData<Args> = MutableLiveData()
    private var args: Args? = null
    val itemsData: LiveData<List<Item>> =
            Transformations.switchMap(inputData) {
                repository.getItems(it.templateId)
            }

    fun setArgs(templateId: Int) {
        if (args == null) { //set args only once
            args = Args(templateId)
            inputData.value = args
        }
    }

    class Args(val templateId: Int)
}
