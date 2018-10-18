package com.maximmakarov.comparator.presentation.items.comparison

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.model.Row
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class ComparisonViewModel : ViewModel(), KoinComponent {

    private val interactor: IItemInteractor by inject()
    private val inputData: MutableLiveData<List<Item>> = MutableLiveData()
    private var items: List<Item>? = null
    val tableData: LiveData<List<Row>> =
            Transformations.switchMap(inputData) {
                interactor.getItemsTransformedData(it[0].templateId, it)
            }

    fun setArgs(itemsArg: List<Item>) {
        if (items == null) { //set args once
            items = itemsArg
            inputData.value = items
        }
    }
}