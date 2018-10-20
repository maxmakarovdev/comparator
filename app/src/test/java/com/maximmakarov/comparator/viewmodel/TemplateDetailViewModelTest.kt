package com.maximmakarov.comparator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import com.maximmakarov.comparator.presentation.templates.detail.TemplateDetailViewModel
import com.maximmakarov.comparator.utils.getLiveDataValue
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class TemplateDetailViewModelTest {
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TemplateDetailViewModel

    private lateinit var interactor: ITemplateInteractor

    @Before
    fun setUp() {
        interactor = mock()

        viewModel = TemplateDetailViewModel(interactor)
    }

    @Test
    fun testGetTemplateById() {
        viewModel.setArgs(testTemplate)
        getLiveDataValue(viewModel.templateData)

        verify(interactor, times(1)).getTemplateById(testTemplate.id!!)
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun addNewTemplate(){
        viewModel.setArgs(testNewTemplate)
        getLiveDataValue(viewModel.templateData)
        viewModel.saveChanges(testNewTemplate.name, testAttributes)

        verify(interactor, times(1)).addTemplate(testNewTemplate.name, testAttributes)
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun testSaveChanges(){
        whenever(interactor.getTemplateById(testTemplate.id!!))
                .thenReturn(MutableLiveData<Template>().apply { postValue(testTemplate) })

        viewModel.setArgs(testTemplate)
        getLiveDataValue(viewModel.templateData)
        viewModel.saveChanges(testTemplate.name, testAttributes)

        verify(interactor, times(1)).getTemplateById(testTemplate.id!!)
        verify(interactor, times(1)).updateTemplate(testTemplate.id!!, testTemplate.name)
        verifyNoMoreInteractions(interactor)
    }
}