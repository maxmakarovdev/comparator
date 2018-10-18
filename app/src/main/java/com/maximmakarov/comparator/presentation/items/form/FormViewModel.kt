package com.maximmakarov.comparator.presentation.items.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.model.AttributeData
import org.koin.standalone.KoinComponent


class FormViewModel(private val interactor: IItemInteractor) : ViewModel(), KoinComponent {

    private val inputData: MutableLiveData<Item> = MutableLiveData()
    private var item: Item? = null
    private var data: List<Pair<AttributeGroup, List<AttributeData>>>? = null
    val itemData: LiveData<List<Pair<AttributeGroup, List<AttributeData>>>> =
            Transformations.map(Transformations.switchMap(inputData) {
                interactor.getItemData(it.templateId, it.id)
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
            interactor.saveItemData(item!!, data!!)
        }
    }
}