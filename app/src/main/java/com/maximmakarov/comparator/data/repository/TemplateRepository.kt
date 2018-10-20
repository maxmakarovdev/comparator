package com.maximmakarov.comparator.data.repository

import androidx.lifecycle.LiveData
import com.maximmakarov.comparator.data.dao.AttributeDao
import com.maximmakarov.comparator.data.dao.AttributeGroupDao
import com.maximmakarov.comparator.data.dao.TemplateDao
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.GroupWithAttributes
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository


class TemplateRepository(private val templateDao: TemplateDao,
                         private val attributeGroupDao: AttributeGroupDao,
                         private val attributeDao: AttributeDao) : ITemplateRepository {

    override fun getTemplates(): LiveData<List<Template>> {
        return templateDao.getTemplates()
    }

    override fun getTemplateById(templateId: Int): LiveData<Template> {
        return templateDao.getTemplateById(templateId)
    }

    override fun getGroupsWithAttributes(templateId: Int): LiveData<List<GroupWithAttributes>> {
        return attributeGroupDao.getGroupsWithAttributes(templateId)
    }

    override fun saveTemplate(template: Template): Long {
        return templateDao.insert(template)[0]
    }

    override fun saveGroup(group: AttributeGroup): Long {
        return attributeGroupDao.insert(group)[0]
    }

    override fun saveAttributes(attributes: Array<Attribute>): List<Long> {
        return attributeDao.insert(*attributes)
    }

    override fun updateTemplate(template: Template) {
        templateDao.update(template)
    }
}