package com.maximmakarov.comparator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.maximmakarov.comparator.data.model.Template

@Dao
interface TemplateDao : BaseDao<Template> {

    @Query("SELECT * FROM templates")
    fun getTemplates(): LiveData<List<Template>>

    @Query("SELECT * FROM templates where id = :id")
    fun getTemplateById(id: Int): LiveData<Template>
}