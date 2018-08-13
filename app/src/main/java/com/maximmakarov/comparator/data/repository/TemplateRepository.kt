package com.maximmakarov.comparator.data.repository

import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Template


class TemplateRepository(private val db: AppDatabase) {

    fun getTemplates() = db.templateDao().getTemplates()

    fun getTemplateById(templateId: Int) = db.templateDao().getTemplateById(templateId)

    fun updateTemplate(templateId: Int, name: String) = db.templateDao().update(Template(templateId, name))

    fun addTemplate(name: String, attributes: String) {
        val templateId = db.templateDao().insert(Template(name = name))[0].toInt()

        val strAttr = if (attributes.contains("##")) attributes else "##$name\n$attributes"

        strAttr.split("##").forEach { strGroup ->
            var groupId = 0
            strGroup.split("\n").filter { it.isNotEmpty() }.withIndex().map {
                if (it.index == 0) {
                    groupId = db.attributeGroupDao().insert(AttributeGroup(templateId = templateId, name = it.value))[0].toInt()
                }
                Attribute(groupId = groupId, name = it.value, isImportant = it.value.startsWith("*"))
            }.drop(1).let {
                db.attributeDao().insert(*it.toTypedArray())
            }
        }
    }
}