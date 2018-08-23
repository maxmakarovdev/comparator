package com.maximmakarov.comparator.item.form

import androidx.lifecycle.*
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.repository.ItemDataWithAttr
import com.maximmakarov.comparator.data.repository.ItemRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class FormViewModel : ViewModel(), KoinComponent {

    private val repository: ItemRepository by inject()
    private val inputData: MutableLiveData<Item> = MutableLiveData()
    private var item: Item? = null
    private var data: List<Pair<AttributeGroup, List<ItemDataWithAttr>>>? = null
    val itemData: LiveData<List<Pair<AttributeGroup, List<ItemDataWithAttr>>>> =
            Transformations.map(Transformations.switchMap(inputData) {
                repository.getItemData(it.templateId, it.id)
            }) { data = it; it }

    fun setArgs(itemArg: Item) {
        if (item == null) {
            item = itemArg
            inputData.value = item
        }
    }

    fun saveChanges(itemName: String) {
        if (data != null) {
            item!!.name = itemName
            repository.saveItemData(item!!, data!!)
        }
    }
}