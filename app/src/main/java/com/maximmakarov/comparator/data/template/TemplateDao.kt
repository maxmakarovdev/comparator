package com.maximmakarov.comparator.data.template

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.maximmakarov.comparator.data.BaseDao

@Dao
interface TemplateDao : BaseDao<Template> {

    @Query("SELECT * FROM templates")
    fun getTemplates(): LiveData<List<Template>>

    @Query("SELECT * FROM templates where id = :id")
    fun getTemplateById(id: Int): LiveData<Template>
}