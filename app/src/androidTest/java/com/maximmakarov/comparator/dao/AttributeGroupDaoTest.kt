package com.maximmakarov.comparator.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.maximmakarov.comparator.core.ext.contentDeepEquals
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Template
import com.maximmakarov.comparator.util.getValue
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class AttributeGroupDaoTest {
    private lateinit var database: AppDatabase

    private val testTemplate = Template(id = 1, name = "first template")
    private val testGroup = arrayOf(AttributeGroup(id = 1, templateId = 1, name = "group"))
    private val testAttributes = arrayOf(Attribute(id = 1, groupId = 1, name = "first"), Attribute(id = 2, groupId = 1, name = "second"))

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java).build()

        database.templateDao().insert(testTemplate)
        database.attributeGroupDao().insert(*testGroup)
        database.attributeDao().insert(*testAttributes)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGroupInserting() {
        val groups = getValue(database.attributeGroupDao().getGroups())

        assertTrue(groups.isNotEmpty())
    }

    @Test
    fun testGroupInsertingAssignedIds() {
        val groups = getValue(database.attributeGroupDao().getGroups())

        assertTrue(groups.all { it.id != null })
    }

    @Test
    fun testGroupInsertingRetrieving() {
        val groupNames = getValue(database.attributeGroupDao().getGroups()).map { it.name }

        assertTrue(groupNames contentDeepEquals testGroup.map { it.name })
    }

    @Test
    fun testGetGroupWithAttributes() {
        val groupWithAttributes = getValue(database.attributeGroupDao().getGroupsWithAttributes(testTemplate.id!!))[0]

        assertTrue(groupWithAttributes.group == testGroup[0])
        assertTrue(groupWithAttributes.attributes contentDeepEquals testAttributes)
    }

    @Test
    fun testGroupUpdating() {
        val group = getValue(database.attributeGroupDao().getGroups())[0]
        val editedGroup = AttributeGroup(group.id, group.templateId, "new group name")
        database.attributeGroupDao().update(editedGroup)
        val updatedGroup = getValue(database.attributeGroupDao().getGroups())[0]

        assertTrue(updatedGroup.name == editedGroup.name)
    }

    @Test
    fun testGroupDeleting() {
        var groups = getValue(database.attributeGroupDao().getGroups())
        database.attributeGroupDao().delete(groups[0])
        groups = getValue(database.attributeGroupDao().getGroups())

        assertTrue(groups.isEmpty())
    }
}