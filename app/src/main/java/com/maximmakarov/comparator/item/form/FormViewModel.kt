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
    private var data: List<Pair<AttributeGroup, List<ItemDataWithAttr>>>? = null
    val itemData: LiveData<List<Pair<AttributeGroup, List<ItemDataWithAttr>>>> =
            Transformations.map(Transformations.switchMap(inputData) {
                repository.getItemData(it.templateId, it.itemId)
            }) { data = it; it }

    fun setArgs(templateId: Int, itemId: Int) {
        if (args == null) { //set args only once
            args = Args(templateId, itemId)
            inputData.value = args
        }
    }

    fun saveChanges() {
        data?.let { repository.saveItemData(it) }
    }

    class Args(val templateId: Int, val itemId: Int)
}