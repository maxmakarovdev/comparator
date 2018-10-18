package com.maximmakarov.comparator.domain.boundary

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Template

interface ITemplateRepository {

    fun getTemplates(): LiveData<List<Template>>

    fun getTemplateById(templateId: Int): LiveData<Template>

    fun saveTemplate(template: Template): Long

    fun saveGroup(group: AttributeGroup): Long

    fun saveAttributes(attributes: Array<Attribute>)

    fun updateTemplate(template: Template)
}