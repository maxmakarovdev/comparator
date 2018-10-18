package com.maximmakarov.comparator.presentation.templates.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TemplatesViewModel : ViewModel(), KoinComponent {

    private val interactor: ITemplateInteractor by inject()

    val templatesData: LiveData<List<Template>> = interactor.getTemplates()
}
