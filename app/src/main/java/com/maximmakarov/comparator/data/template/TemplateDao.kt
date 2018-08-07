package com.maximmakarov.comparator.data.template

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maximmakarov.comparator.data.attribute.Attribute
import com.maximmakarov.comparator.data.attribute.AttributeGroup

@Dao
interface TemplateDao {
    @Insert
    fun insert(template: Template): Long

    @Update
    fun update(template: Template)

    @Delete
    fun delete(template: Template)

    @Query("SELECT * FROM templates")
    fun getTemplates(): LiveData<List<Template>>
}