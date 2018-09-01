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

    //todo set ids
    //todo move this to a separate place
    private val testTemplates = arrayOf(Template(id = 1, name = "first template"), Template(id = 2, name = "second template"))
    private val testItems = arrayOf(Item(templateId = 1, name = "first"), Item(templateId = 1, name = "second"), Item(templateId = 2, name = "third"))

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
        val items = getValue(database.itemDao().getItems())

        assertTrue(items[0].id != null)
    }

    @Test
    fun testGetItemsByTemplateId() {
        val items = getValue(database.itemDao().getItems(testTemplates[0].id!!))

        assertTrue(items.map { it.name } contentDeepEquals testItems.take(2).map { it.name })
    }

    @Test
    fun testItemsInsertingRetrievingData() {
        val itemNames = getValue(database.itemDao().getItems()).map { it.name }

        assertTrue(itemNames contentDeepEquals testItems.map { it.name })
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
        val template = getValue(database.templateDao().getTemplates())[0]
        val item = getValue(database.itemDao().getItems(template.id!!))[0]

        val groupId = database.attributeGroupDao().insert(AttributeGroup(templateId = template.id!!, name = "group"))[0].toInt()
        val attributeId = database.attributeDao().insert(Attribute(groupId = groupId, name = "attribute"))[0].toInt()
        database.itemAttrDataDao().insert(ItemAttrData(itemId = item.id, attributeId = attributeId, answer = "answer"))

        database.itemDao().delete(item)

        assertTrue(getValue(database.templateDao().getTemplates()).isNotEmpty())
        assertTrue(getValue(database.attributeGroupDao().getGroups()).isNotEmpty())
        assertTrue(getValue(database.attributeDao().getAttributes()).isNotEmpty())
        assertTrue(getValue(database.itemAttrDataDao().getItemAttrData()).isEmpty())
    }
}