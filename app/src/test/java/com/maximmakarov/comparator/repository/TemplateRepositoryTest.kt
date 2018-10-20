package com.maximmakarov.comparator.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.maximmakarov.comparator.data.dao.AttributeDao
import com.maximmakarov.comparator.data.dao.AttributeGroupDao
import com.maximmakarov.comparator.data.dao.TemplateDao
import com.maximmakarov.comparator.data.model.GroupWithAttributes
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.data.repository.TemplateRepository
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class TemplateRepositoryTest {
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var templateRepository: ITemplateRepository

    private lateinit var templateDao: TemplateDao
    private lateinit var attributeGroupDao: AttributeGroupDao
    private lateinit var attributeDao: AttributeDao

    @Before
    fun setUp() {
        templateDao = mock()
        attributeGroupDao = mock()
        attributeDao = mock()

        templateRepository = TemplateRepository(templateDao, attributeGroupDao, attributeDao)
    }

    @Test
    fun getTemplatesExecutesOnce() {
        whenever(templateDao.getTemplates())
                .thenReturn(MutableLiveData<List<Template>>())

        templateRepository.getTemplates()

        verify(templateDao, times(1)).getTemplates()
        verifyNoMoreInteractions(templateDao)
    }

    @Test
    fun getTemplatesReturnsData() {
        whenever(templateDao.getTemplates())
                .thenReturn(MutableLiveData<List<Template>>().apply { postValue(testTemplates) })

        assertTrue(templateRepository.getTemplates().value == testTemplates)
    }

    @Test
    fun getTemplateByIdExecutesOnce() {
        whenever(templateDao.getTemplateById(testTemplateId))
                .thenReturn(MutableLiveData<Template>())

        templateRepository.getTemplateById(testTemplateId)

        verify(templateDao, times(1)).getTemplateById(testTemplateId)
        verifyNoMoreInteractions(templateDao)
    }

    @Test
    fun getTemplateByIdReturnsData() {
        whenever(templateDao.getTemplateById(testTemplateId))
                .thenReturn(MutableLiveData<Template>().apply { postValue(testTemplates[0]) })

        assertTrue(templateRepository.getTemplateById(testTemplateId).value == testTemplates[0])
    }

    @Test
    fun getGroupsWithAttributesExecutesOnce() {
        whenever(attributeGroupDao.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData<List<GroupWithAttributes>>())

        templateRepository.getGroupsWithAttributes(testTemplateId)

        verify(attributeGroupDao, times(1)).getGroupsWithAttributes(testTemplateId)
        verifyNoMoreInteractions(attributeGroupDao)
    }

    @Test
    fun getGroupsWithAttributesReturnsData() {
        whenever(attributeGroupDao.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData<List<GroupWithAttributes>>().apply { postValue(testGroupWithAttributes) })

        assertTrue(templateRepository.getGroupsWithAttributes(testTemplateId).value == testGroupWithAttributes)
    }

    @Test
    fun saveTemplateExecutesOnce() {
        whenever(templateDao.insert(testTemplates[0]))
                .thenReturn(listOf(testTemplates[0].id!!.toLong()))

        templateRepository.saveTemplate(testTemplates[0])

        verify(templateDao, times(1)).insert(testTemplates[0])
        verifyNoMoreInteractions(templateDao)
    }

    @Test
    fun saveGroupExecutesOnce() {
        whenever(attributeGroupDao.insert(testGroup))
                .thenReturn(listOf(testGroup.id!!.toLong()))

        templateRepository.saveGroup(testGroup)

        verify(attributeGroupDao, times(1)).insert(testGroup)
        verifyNoMoreInteractions(attributeGroupDao)
    }

    @Test
    fun saveAttributesExecutesOnce() {
        whenever(attributeDao.insert(*testAttributes))
                .thenReturn(testAttributes.map { it.id!!.toLong() })

        templateRepository.saveAttributes(testAttributes)

        verify(attributeDao, times(1)).insert(*testAttributes)
        verifyNoMoreInteractions(attributeDao)
    }

    @Test
    fun updateTemplateExecutesOnce() {
        templateRepository.updateTemplate(testTemplates[0])

        verify(templateDao, times(1)).update(testTemplates[0])
        verifyNoMoreInteractions(templateDao)
    }
}