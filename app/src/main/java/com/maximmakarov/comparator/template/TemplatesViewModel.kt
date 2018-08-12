package com.maximmakarov.comparator.template

import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.template.TemplateRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TemplatesViewModel : ViewModel(), KoinComponent {

    private val repository: TemplateRepository by inject()

    fun getTemplates() = repository.getTemplates()
}
