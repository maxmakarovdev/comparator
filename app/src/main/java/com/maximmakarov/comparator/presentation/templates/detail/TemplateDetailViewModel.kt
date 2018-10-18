package com.maximmakarov.comparator.presentation.templates.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class TemplateDetailViewModel : ViewModel(), KoinComponent {

    private val interactor: ITemplateInteractor by inject()
    private val inputData: MutableLiveData<Template> = MutableLiveData()
    private var template: Template? = null
    val templateData: LiveData<Template> =
            Transformations.switchMap(inputData) {
                interactor.getTemplateById(it.id!!)
            }

    fun setArgs(templateArg: Template) {
        if (template == null) {
            template = templateArg
            if (template?.id != null) {
                inputData.value = template
            }
        }
    }

    fun saveChanges(name: String, attributes: String) {
        if (template!!.id != null) interactor.updateTemplate(template!!.id!!, name)
        else interactor.addTemplate(name, attributes)
    }
}