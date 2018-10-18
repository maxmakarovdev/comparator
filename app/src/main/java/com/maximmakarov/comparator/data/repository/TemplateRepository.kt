package com.maximmakarov.comparator.data.repository

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository


class TemplateRepository(private val db: AppDatabase) : ITemplateRepository {

    override fun getTemplates(): LiveData<List<Template>> {
        return db.templateDao().getTemplates()
    }

    override fun getTemplateById(templateId: Int): LiveData<Template> {
        return db.templateDao().getTemplateById(templateId)
    }

    override fun saveTemplate(template: Template): Long {
        return db.templateDao().insert(template)[0]
    }

    override fun saveGroup(group: AttributeGroup): Long {
        return db.attributeGroupDao().insert(group)[0]
    }

    override fun saveAttributes(attributes: Array<Attribute>){
        db.attributeDao().insert(*attributes)
    }

    override fun updateTemplate(template: Template) {
        db.templateDao().update(template)
    }
}