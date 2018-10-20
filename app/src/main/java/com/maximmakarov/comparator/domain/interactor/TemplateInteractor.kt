package com.maximmakarov.comparator.domain.interactor

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository


class TemplateInteractor(private val repository: ITemplateRepository) : ITemplateInteractor {

    override fun getTemplates(): LiveData<List<Template>> {
        return repository.getTemplates()
    }

    override fun getTemplateById(templateId: Int): LiveData<Template> {
        return repository.getTemplateById(templateId)
    }

    override fun addTemplate(name: String, attributes: String) {
        val templateId = repository.saveTemplate(Template(name = name)).toInt()

        val strAttr = if (attributes.contains("##")) attributes else "##$name\n$attributes"

        strAttr.split("##").filter { it.isNotBlank() }.forEach { strGroup ->
            var groupId = 0
            strGroup.split("\n").filter { it.isNotBlank() }.withIndex().map {
                if (it.index == 0) {
                    groupId = repository.saveGroup(AttributeGroup(templateId = templateId, name = it.value)).toInt()
                }
                Attribute(groupId = groupId, name = it.value, isImportant = it.value.startsWith("*"))
            }.drop(1).let {
                repository.saveAttributes(it.toTypedArray())
            }
        }
    }

    override fun updateTemplate(templateId: Int, name: String) {
        repository.saveTemplate(Template(templateId, name))
    }
}