package com.maximmakarov.comparator.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.maximmakarov.comparator.data.model.GroupWithAttributes
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.ItemAttrData
import com.maximmakarov.comparator.domain.boundary.IItemRepository
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.interactor.ItemInteractor
import com.maximmakarov.comparator.utils.getLiveDataValue
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ItemInteractorTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var itemInteractor: IItemInteractor

    private lateinit var repository: IItemRepository
    private lateinit var templateRepository: ITemplateRepository

    @Before
    fun setUp() {
        repository = mock()
        templateRepository = mock()

        itemInteractor = ItemInteractor(repository, templateRepository)
    }

    @Test
    fun getItemsExecutesOnce() {
        whenever(repository.getItems(testTemplateId))
                .thenReturn(MutableLiveData<List<Item>>())

        itemInteractor.getItems(testTemplateId)

        verify(repository, times(1)).getItems(testTemplateId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun getItemsReturnsData() {
        whenever(repository.getItems(testTemplateId))
                .thenReturn(MutableLiveData<List<Item>>().apply { postValue(testItems) })

        assertTrue(getLiveDataValue(itemInteractor.getItems(testTemplateId)) == testItems)
    }

    @Test
    fun getEmptyItemDataExecutesOnce() {
        whenever(templateRepository.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData())

        itemInteractor.getItemData(testTemplateId, null)

        verify(templateRepository, times(1)).getGroupsWithAttributes(testTemplateId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun getEmptyItemDataReturnsData() {
        whenever(templateRepository.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData<List<GroupWithAttributes>>().apply { postValue(testGroupWithAttributes) })

        assertTrue(getLiveDataValue(itemInteractor.getItemData(testTemplateId, null))
                [0].second[0].attribute.name == testAttributes[0].name)
    }

    @Test
    fun getExistingItemDataExecutesOnce() {
        whenever(templateRepository.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData())
        whenever(repository.getItemsDetails(arrayOf(testItems[0].id!!)))
                .thenReturn(MutableLiveData())

        itemInteractor.getItemData(testTemplateId, testItems[0].id!!)

        verify(templateRepository, times(1)).getGroupsWithAttributes(testTemplateId)
        verify(repository, times(1)).getItemsDetails(arrayOf(testItems[0].id!!))
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun getExistingItemDataReturnsData() {
        whenever(templateRepository.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData<List<GroupWithAttributes>>().apply { postValue(testGroupWithAttributes) })
        whenever(repository.getItemsDetails(arrayOf(testItems[0].id!!)))
                .thenReturn(MutableLiveData<List<ItemAttrData>>().apply { postValue(testAttributesData.filter { it.itemId == testItems[0].id!! }) })

        assertTrue(getLiveDataValue(itemInteractor.getItemData(testTemplateId, testItems[0].id!!))
                [0].second[0].data.answer == testItemsData[0].second[0].second[0].data.answer)
    }

    @Test
    fun getItemsTransformedDataExecutesOnce() {
        whenever(templateRepository.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData())
        whenever(repository.getItemsDetails(testItems.map { it.id!! }.toTypedArray()))
                .thenReturn(MutableLiveData())

        itemInteractor.getItemsTransformedData(testTemplateId, testItems)

        verify(templateRepository, times(1)).getGroupsWithAttributes(testTemplateId)
        verify(repository, times(1)).getItemsDetails(testItems.map { it.id!! }.toTypedArray())
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun getItemsTransformedDataReturnsValidData() {
        whenever(templateRepository.getGroupsWithAttributes(testTemplateId))
                .thenReturn(MutableLiveData<List<GroupWithAttributes>>().apply { postValue(testGroupWithAttributes) })
        whenever(repository.getItemsDetails(testItems.map { it.id!! }.toTypedArray()))
                .thenReturn(MutableLiveData<List<ItemAttrData>>().apply { postValue(testAttributesData) })

        assertTrue(getLiveDataValue(itemInteractor.getItemsTransformedData(testTemplateId, testItems))
                [0].cells[0].text.isEmpty()) //(row,cell)
        assertTrue(getLiveDataValue(itemInteractor.getItemsTransformedData(testTemplateId, testItems))
                [0].cells[1].text == testItems[0].name)
        assertTrue(getLiveDataValue(itemInteractor.getItemsTransformedData(testTemplateId, testItems))
                [1].cells[0].text == testGroup.name)
        assertTrue(getLiveDataValue(itemInteractor.getItemsTransformedData(testTemplateId, testItems))
                [2].cells[0].text == testItemsData[0].second[0].second[0].attribute.name)
        assertTrue(getLiveDataValue(itemInteractor.getItemsTransformedData(testTemplateId, testItems))
                [2].cells[1].text == testItemsData[0].second[0].second[0].data.answer)
    }

    @Test
    fun saveNewItemDataExecutesOnce() {
        itemInteractor.saveItemData(testNewItem, testNewItemData)

        verify(repository, times(1)).saveItem(any())
        verify(repository, times(1)).saveItemData(any())
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun saveExistedItemDataExecutesOnce() {
        itemInteractor.saveItemData(testItems[0], testItemsData[0].second)

        verify(repository, times(1)).updateItem(any())
        verify(repository, times(1)).updateItemData(any())
        verifyNoMoreInteractions(repository)
    }
}