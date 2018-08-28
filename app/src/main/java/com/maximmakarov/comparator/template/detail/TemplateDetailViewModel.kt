package com.maximmakarov.comparator.template.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.data.repository.TemplateRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class TemplateDetailViewModel : ViewModel(), KoinComponent {

    private val repository: TemplateRepository by inject()
    private val inputData: MutableLiveData<Template> = MutableLiveData()
    private var template: Template? = null
    val templateData: LiveData<Template> =
            Transformations.switchMap(inputData) {
                repository.getTemplateById(it.id!!)
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
        if (template!!.id != null) repository.updateTemplate(template!!.id!!, name)
        else repository.addTemplate(name, attributes)
    }
}