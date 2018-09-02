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
open class ItemDaoTest {
    private lateinit var database: AppDatabase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        database.templateDao().insert(*testTemplates)
        database.itemDao().insert(*testItems)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testItemsInserting() {
        val items = getValue(database.itemDao().getItems())

        assertTrue(items.isNotEmpty())
    }

    @Test
    fun testItemsInsertingAssignedIds() {
        database.itemDao().insert(Item(templateId = testTemplates[0].id!!, name = "test"))
        val items = getValue(database.itemDao().getItems())

        assertTrue(items.all { it.id != null })
    }

    @Test
    fun testGetItemsByTemplateId() {
        val items = getValue(database.itemDao().getItems(testTemplates[0].id!!))

        assertTrue(items.map { it.name } contentDeepEquals testItems.take(2).map { it.name })
    }

    @Test
    fun testItemsInsertingRetrievingData() {
        val items = getValue(database.itemDao().getItems())

        assertTrue(items.map { it.name } contentDeepEquals testItems.map { it.name })
    }

    @Test
    fun testItemsUpdating() {
        val item = getValue(database.itemDao().getItems())[0]
        val editedItem = Item(item.id, item.templateId, "new name")
        database.itemDao().update(editedItem)
        val updatedItem = getValue(database.itemDao().getItems())[0]

        assertTrue(updatedItem.name == editedItem.name)
    }

    @Test
    fun testItemsDeleting() {
        var items = getValue(database.itemDao().getItems())
        database.itemDao().delete(items[0])
        items = getValue(database.itemDao().getItems())

        assertTrue(items.map { it.name } contentDeepEquals testItems.drop(1).map { it.name })
    }

    @Test
    fun testItemCascadeDeleting() {
        database.attributeGroupDao().insert(*testGroups)
        database.attributeDao().insert(*testAttributes)
        database.itemAttrDataDao().insert(*testItemsData)

        database.itemDao().delete(*testItems)

        assertTrue(getValue(database.templateDao().getTemplates()).isNotEmpty())
        assertTrue(getValue(database.attributeGroupDao().getGroups()).isNotEmpty())
        assertTrue(getValue(database.attributeDao().getAttributes()).isNotEmpty())
        assertTrue(getValue(database.itemAttrDataDao().getItemAttrData()).isEmpty())
    }
}