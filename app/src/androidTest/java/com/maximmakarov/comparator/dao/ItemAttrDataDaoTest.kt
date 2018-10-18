package com.maximmakarov.comparator.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.maximmakarov.comparator.core.extensions.contentDeepEquals
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.*
import com.maximmakarov.comparator.utils.getValue
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class ItemAttrDataDaoTest {
    private lateinit var database: AppDatabase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        database.templateDao().insert(*testTemplates)
        database.itemDao().insert(*testItems)
        database.attributeGroupDao().insert(*testGroups)
        database.attributeDao().insert(*testAttributes)
        database.itemAttrDataDao().insert(*testItemsData)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testItemsDataInserting() {
        val itemsData = getValue(database.itemAttrDataDao().getItemAttrData())

        assertTrue(itemsData.isNotEmpty())
    }

    @Test
    fun testItemsDataInsertingAssignedIds() {
        database.itemAttrDataDao().insert(ItemAttrData(itemId = testItems[0].id!!, attributeId = testAttributes[0].id!!, answer = "test"))
        val itemsData = getValue(database.itemAttrDataDao().getItemAttrData())

        assertTrue(itemsData.all { it.id != null })
    }

    @Test
    fun testItemsDataInsertingRetrieving() {
        val itemsData = getValue(database.itemAttrDataDao().getItemAttrData())

        assertTrue(itemsData.map { it.answer } contentDeepEquals testItemsData.map { it.answer })
    }

    @Test
    fun testGetDataForItems() {
        val itemsData = getValue(database.itemAttrDataDao().getItemsDetails(arrayOf(testItems[0].id!!, testItems[1].id!!)))

        assertTrue(itemsData.map { it.answer } contentDeepEquals testItemsData.take(4).map { it.answer })
    }

    @Test
    fun testItemsDataUpdating() {
        val itemData = getValue(database.itemAttrDataDao().getItemAttrData())[0]
        val editedItemData = ItemAttrData(itemData.id, itemData.itemId, itemData.attributeId, "new answer")
        database.itemAttrDataDao().update(editedItemData)
        val updatedItemData = getValue(database.itemAttrDataDao().getItemAttrData())[0]

        assertTrue(updatedItemData.answer == editedItemData.answer)
    }

    @Test
    fun testItemsDataDeleting() {
        var itemData = getValue(database.itemAttrDataDao().getItemAttrData())
        database.itemAttrDataDao().delete(itemData[0])
        itemData = getValue(database.itemAttrDataDao().getItemAttrData())

        assertTrue(itemData.map { it.answer } contentDeepEquals testItemsData.drop(1).map { it.answer })
    }
}