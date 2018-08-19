package com.maximmakarov.comparator.template

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.data.repository.TemplateRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TemplatesViewModel : ViewModel(), KoinComponent {

    private val repository: TemplateRepository by inject()

    val templatesData: LiveData<List<Template>> = repository.getTemplates()
}
