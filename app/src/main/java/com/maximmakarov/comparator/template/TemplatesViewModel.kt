package com.maximmakarov.comparator.template

import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.template.TemplateDao
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TemplatesViewModel : ViewModel(), KoinComponent {

    private val dao: TemplateDao by inject()

    val templates = dao.getTemplates()
}
