package com.maximmakarov.comparator.presentation.items.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class ItemsViewModel : ViewModel(), KoinComponent {

    private val interactor: IItemInteractor by inject()
    private val templateInteractor: ITemplateInteractor by inject()
    private val inputData: MutableLiveData<Template> = MutableLiveData()
    private var template: Template? = null
    val itemsData: LiveData<List<Item>> =
            Transformations.switchMap(inputData) {
                interactor.getItems(it.id!!)
            }

    fun setArgs(templateArg: Template) {
        if (template == null) {
            template = templateArg
            inputData.value = template
        }
    }

    fun saveChanges(name: String) {
        templateInteractor.updateTemplate(template!!.id!!, name)
    }
}
