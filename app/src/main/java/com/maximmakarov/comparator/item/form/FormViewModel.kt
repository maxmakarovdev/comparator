package com.maximmakarov.comparator.item.form

import androidx.lifecycle.*
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.repository.ItemDataWithAttr
import com.maximmakarov.comparator.data.repository.ItemRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class FormViewModel : ViewModel(), KoinComponent {

    private val repository: ItemRepository by inject()
    private val inputData: MutableLiveData<Args> = MutableLiveData()
    private var args: Args? = null
    val itemData: LiveData<List<Pair<AttributeGroup, List<ItemDataWithAttr>>>> =
            Transformations.switchMap(inputData) {
                repository.getItemData(it.templateId, it.itemId)
            }

    fun setArgs(templateId: Int, itemId: Int) {
        if (args == null) { //set args only once
            args = Args(templateId, itemId)
            inputData.value = args
        }
    }

    class Args(val templateId: Int, val itemId: Int)
}