package com.maximmakarov.comparator.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.maximmakarov.comparator.data.dao.ItemAttrDataDao
import com.maximmakarov.comparator.data.dao.ItemDao
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.ItemAttrData
import com.maximmakarov.comparator.data.repository.ItemRepository
import com.maximmakarov.comparator.domain.boundary.IItemRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemRepositoryTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var itemRepository: IItemRepository

    private lateinit var itemDao: ItemDao
    private lateinit var attributeDataDao: ItemAttrDataDao

    @Before
    fun setUp() {
        itemDao = mock()
        attributeDataDao = mock()

        itemRepository = ItemRepository(itemDao, attributeDataDao)
    }

    @Test
    fun getItemsExecutesOnce() {
        whenever(itemDao.getItems(testTemplateId))
                .thenReturn(MutableLiveData<List<Item>>())

        itemRepository.getItems(testTemplateId)

        verify(itemDao, times(1)).getItems(testTemplateId)
        verifyNoMoreInteractions(itemDao)
    }

    @Test
    fun getItemsReturnsData() {
        whenever(itemDao.getItems(testTemplateId))
                .thenReturn(MutableLiveData<List<Item>>().apply { postValue(testItems) })

        assertTrue(itemRepository.getItems(testTemplateId).value == testItems)
    }

    @Test
    fun getItemsDetailsExecutesOnce() {
        whenever(attributeDataDao.getItemsDetails(testItemsIds))
                .thenReturn(MutableLiveData<List<ItemAttrData>>())

        itemRepository.getItemsDetails(testItemsIds)

        verify(attributeDataDao, times(1)).getItemsDetails(testItemsIds)
        verifyNoMoreInteractions(attributeDataDao)
    }

    @Test
    fun getItemsDetailsReturnsData() {
        whenever(attributeDataDao.getItemsDetails(testItemsIds))
                .thenReturn(MutableLiveData<List<ItemAttrData>>().apply { postValue(testAttributeData) })

        assertTrue(itemRepository.getItemsDetails(testItemsIds).value == testAttributeData)
    }

    @Test
    fun saveItemExecutesOnce() {
        whenever(itemDao.insert(testItems[0]))
                .thenReturn(listOf(testItems[0].id!!.toLong()))

        itemRepository.saveItem(testItems[0])

        verify(itemDao, times(1)).insert(testItems[0])
        verifyNoMoreInteractions(itemDao)
    }

    @Test
    fun saveItemDataExecutesOnce() {
        itemRepository.saveItemData(testAttributeData.toTypedArray())

        verify(attributeDataDao, times(1)).insert(*testAttributeData.toTypedArray())
        verifyNoMoreInteractions(attributeDataDao)
    }

    @Test
    fun updateItemExecutesOnce() {
        itemRepository.updateItem(testItems[0])

        verify(itemDao, times(1)).update(testItems[0])
        verifyNoMoreInteractions(itemDao)
    }

    @Test
    fun updateItemDataExecutesOnce() {
        itemRepository.updateItemData(testAttributeData.toTypedArray())

        verify(attributeDataDao, times(1)).update(*testAttributeData.toTypedArray())
        verifyNoMoreInteractions(attributeDataDao)
    }
}