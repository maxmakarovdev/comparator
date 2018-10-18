package com.maximmakarov.comparator.domain.interactor

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.model.Template


interface ITemplateInteractor {

    fun getTemplates(): LiveData<List<Template>>

    fun getTemplateById(templateId: Int): LiveData<Template>

    fun addTemplate(name: String, attributes: String)

    fun updateTemplate(templateId: Int, name: String)
}