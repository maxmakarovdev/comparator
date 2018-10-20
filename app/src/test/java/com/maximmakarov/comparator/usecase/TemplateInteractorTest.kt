package com.maximmakarov.comparator.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import com.maximmakarov.comparator.domain.interactor.TemplateInteractor
import com.maximmakarov.comparator.utils.getValue
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class TemplateInteractorTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var templateInteractor: ITemplateInteractor

    private lateinit var repository: ITemplateRepository

    @Before
    fun setUp() {
        repository = mock()

        templateInteractor = TemplateInteractor(repository)
    }

    @Test
    fun getTemplatesExecutesOnce() {
        whenever(repository.getTemplates())
                .thenReturn(MutableLiveData())

        templateInteractor.getTemplates()

        verify(repository, times(1)).getTemplates()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun getTemplatesReturnsData() {
        whenever(repository.getTemplates())
                .thenReturn(MutableLiveData<List<Template>>().apply { postValue(testTemplates) })

        assertTrue(getValue(templateInteractor.getTemplates()) == testTemplates)
    }

    @Test
    fun getTemplateByIdExecutesOnce() {
        whenever(repository.getTemplateById(testTemplateId))
                .thenReturn(MutableLiveData())

        templateInteractor.getTemplateById(testTemplateId)

        verify(repository, times(1)).getTemplateById(testTemplateId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun getTemplateByIdReturnsData() {
        whenever(repository.getTemplateById(testTemplateId))
                .thenReturn(MutableLiveData<Template>().apply { postValue(testTemplates[0]) })

        assertTrue(getValue(templateInteractor.getTemplateById(testTemplateId)) == testTemplates[0])
    }

    @Test
    fun testAddingTemplate() {
        templateInteractor.addTemplate(testNewTemplateName, testTemplateData)

        verify(repository, times(1)).saveTemplate(any())
        verify(repository, times(2)).saveGroup(any())
        verify(repository, times(2)).saveAttributes(any())
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun testUpdatingTemplate() {
        templateInteractor.updateTemplate(testTemplateId, testNewTemplateName)

        verify(repository, times(1)).saveTemplate(any())
        verifyNoMoreInteractions(repository)
    }
}