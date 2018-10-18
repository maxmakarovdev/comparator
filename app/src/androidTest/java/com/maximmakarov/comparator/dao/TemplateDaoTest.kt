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
open class TemplateDaoTest {
    private lateinit var database: AppDatabase

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        database.templateDao().insert(*testTemplates)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testTemplatesInserting() {
        val templates = getValue(database.templateDao().getTemplates())

        assertTrue(templates.isNotEmpty())
    }

    @Test
    fun testTemplatesInsertingAssignedIds() {
        database.templateDao().insert(Template(name = "test"))
        val templates = getValue(database.templateDao().getTemplates())

        assertTrue(templates.all { it.id != null })
    }

    @Test
    fun testGetFirstTemplateById() {
        val templateId = getValue(database.templateDao().getTemplates())[0].id!!
        val template = getValue(database.templateDao().getTemplateById(templateId))

        assertTrue(template.name == testTemplates[0].name)
    }

    @Test
    fun testTemplatesInsertingRetrievingData() {
        val templates = getValue(database.templateDao().getTemplates())

        assertTrue(templates.map { it.name } contentDeepEquals testTemplates.map { it.name })
    }

    @Test
    fun testTemplatesUpdating() {
        val template = getValue(database.templateDao().getTemplates())[0]
        val editedTemplate = Template(template.id, "new name")
        database.templateDao().update(editedTemplate)
        val updatedTemplate = getValue(database.templateDao().getTemplates())[0]

        assertTrue(updatedTemplate.name == editedTemplate.name)
    }

    @Test
    fun testTemplatesDeleting() {
        var templates = getValue(database.templateDao().getTemplates())
        database.templateDao().delete(templates[0])
        templates = getValue(database.templateDao().getTemplates())

        assertTrue(templates.map { it.name } contentDeepEquals testTemplates.drop(1).map { it.name })
    }

    @Test
    fun testTemplateCascadeDeleting() {
        database.itemDao().insert(*testItems)
        database.attributeGroupDao().insert(*testGroups)
        database.attributeDao().insert(*testAttributes)
        database.itemAttrDataDao().insert(*testItemsData)

        database.templateDao().delete(*testTemplates)

        assertTrue(getValue(database.attributeGroupDao().getGroups()).isEmpty())
        assertTrue(getValue(database.attributeDao().getAttributes()).isEmpty())
        assertTrue(getValue(database.itemDao().getItems()).isEmpty())
        assertTrue(getValue(database.itemAttrDataDao().getItemAttrData()).isEmpty())
    }
}