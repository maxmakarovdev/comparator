package com.maximmakarov.comparator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maximmakarov.comparator.data.model.Attribute
import com.maximmakarov.comparator.data.model.AttributeGroup


@Dao
interface AttributeGroupDao : BaseDao<AttributeGroup> {

    @Transaction
    @Query("SELECT * FROM attribute_groups WHERE template_id = :templateId")
    fun getGroupsWithAttributes(templateId: Int): LiveData<List<GroupWithAttributes>>
}


class GroupWithAttributes {
    @Embedded
    var group: AttributeGroup? = null

    @Relation(parentColumn = "id", entityColumn = "group_id")
    var attributes: List<Attribute> = arrayListOf()
}