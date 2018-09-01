package com.maximmakarov.comparator.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.maximmakarov.comparator.core.ext.contentDeepEquals
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.*
import com.maximmakarov.comparator.util.getValue
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

    private val testTemplate = Template(id = 1, name = "first template")
    private val testItems = arrayOf(
            Item(id = 1, templateId = 1, name = "first"),
            Item(id = 2, templateId = 1, name = "second"),
            Item(id = 3, templateId = 1, name = "third"))
    private val testGroup = AttributeGroup(id = 1, templateId = 1, name = "group")
    private val testAttributes = arrayOf(Attribute(id = 1, groupId = 1, name = "first"), Attribute(id = 2, groupId = 1, name = "second"))
    private val testItemsData = arrayOf(
            ItemAttrData(itemId = 1, attributeId = 1, answer = "one"),
            ItemAttrData(itemId = 1, attributeId = 2, answer = "two"),
            ItemAttrData(itemId = 2, attributeId = 1, answer = "three"),
            ItemAttrData(itemId = 2, attributeId = 2, answer = "four"),
            ItemAttrData(itemId = 3, attributeId = 1, answer = "five"),
            ItemAttrData(itemId = 3, attributeId = 2, answer = "six"))

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        database.templateDao().insert(testTemplate)
        database.itemDao().insert(*testItems)
        database.attributeGroupDao().insert(testGroup)
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
        val itemsData = getValue(database.itemAttrDataDao().getItemAttrData())

        assertTrue(itemsData[0].id != null)
    }

    @Test
    fun testItemsDataInsertingRetrieving() {
        val itemsDataAnswers = getValue(database.itemAttrDataDao().getItemAttrData()).map { it.answer }

        assertTrue(itemsDataAnswers contentDeepEquals testItemsData.map { it.answer })
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