package com.maximmakarov.comparator.data.template

import com.maximmakarov.comparator.data.AppDatabase


class TemplateRepository(private val db: AppDatabase) {

    fun getTemplates() = db.templateDao().getTemplates()
}