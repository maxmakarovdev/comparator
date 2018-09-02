package com.maximmakarov.comparator.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.maximmakarov.comparator.core.ext.contentDeepEquals
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.util.getValue
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class AttributeDaoTest {
    private lateinit var database: AppDatabase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        database.templateDao().insert(*testTemplates)
        database.attributeGroupDao().insert(*testGroups)
        database.attributeDao().insert(*testAttributes)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testAttributesInserting() {
        val attributes = getValue(database.attributeDao().getAttributes())

        assertTrue(attributes.isNotEmpty())
    }

    @Test
    fun testAttributesInsertingAssignedIds() {
        database.attributeDao().insert(Attribute(groupId = testGroups[0].id!!, name = "test"))
        val attributes = getValue(database.attributeDao().getAttributes())

        assertTrue(attributes.all { it.id != null })
    }

    @Test
    fun testAttributesInsertingRetrievingData() {
        val attributes = getValue(database.attributeDao().getAttributes())

        assertTrue(attributes.map { it.name } contentDeepEquals testAttributes.map { it.name })
    }

    @Test
    fun testAttributesUpdating() {
        val attribute = getValue(database.attributeDao().getAttributes())[0]
        val editedAttribute = Attribute(attribute.id, attribute.groupId, "new name", !attribute.isImportant)
        database.attributeDao().update(editedAttribute)
        val updatedAttribute = getValue(database.attributeDao().getAttributes())[0]

        assertTrue(updatedAttribute.name == editedAttribute.name)
    }

    @Test
    fun testAttributesDeleting() {
        var attributes = getValue(database.attributeDao().getAttributes())
        database.attributeDao().delete(attributes[0])
        attributes = getValue(database.attributeDao().getAttributes())

        assertTrue(attributes.map { it.name } contentDeepEquals testAttributes.drop(1).map { it.name })
    }
}