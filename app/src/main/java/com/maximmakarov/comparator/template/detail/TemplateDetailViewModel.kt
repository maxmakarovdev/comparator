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
    private val inputData: MutableLiveData<Args> = MutableLiveData()
    private var args: Args? = null
    val templateData: LiveData<Template> =
            Transformations.switchMap(inputData) {
                repository.getTemplateById(it.templateId)
            }

    fun setArgs(templateId: Int) {
        if (args == null) { //set args only once
            args = Args(templateId)
            inputData.value = args
        }
    }

    class Args(val templateId: Int)

    fun addTemplate(name: String, attributes: String) = repository.addTemplate(name, attributes)

    fun editTemplateName(templateId: Int, name: String) = repository.updateTemplate(templateId, name)
}