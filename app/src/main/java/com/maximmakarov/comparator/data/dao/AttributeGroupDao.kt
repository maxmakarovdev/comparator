package com.maximmakarov.comparator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.GroupWithAttributes


@Dao
interface AttributeGroupDao : BaseDao<AttributeGroup> {

    @Query("SELECT * FROM attribute_groups")
    fun getGroups(): LiveData<List<AttributeGroup>>

    @Transaction
    @Query("SELECT * FROM attribute_groups WHERE template_id = :templateId")
    fun getGroupsWithAttributes(templateId: Int): LiveData<List<GroupWithAttributes>>
}