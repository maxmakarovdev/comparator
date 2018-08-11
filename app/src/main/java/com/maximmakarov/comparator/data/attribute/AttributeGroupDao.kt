package com.maximmakarov.comparator.data.attribute

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.*
import com.maximmakarov.comparator.data.BaseDao
import com.maximmakarov.comparator.data.item.Item
import com.maximmakarov.comparator.data.item.ItemAttributeDetail


@Dao
interface AttributeGroupDao : BaseDao<AttributeGroup> {

    @Transaction
    @Query("SELECT * FROM attribute_groups WHERE template_id = :templateId")
    fun getGroupsWithAttributes(templateId: Int): LiveData<List<GroupWithAttributes>>

    @Transaction
    @Query("SELECT * FROM attribute_groups WHERE template_id = :templateId")
    fun getGroupsWithAttributesWithDetails(templateId: Int): LiveData<List<GroupWithAttributesWithDetails>>

    fun getItemData(item: Item): LiveData<List<GroupWithAttributesWithDetails>> =
            Transformations.map(getGroupsWithAttributesWithDetails(item.templateId)) {
                it.filter { it.attributesWithDetails.filter { it.details[0].itemId != item.id }.isEmpty() }
            }
}


class GroupWithAttributes {
    @Embedded
    var group: AttributeGroup? = null

    @Relation(parentColumn = "id", entityColumn = "group_id")
    var attributes: List<Attribute> = arrayListOf()
}

class AttributesWithDetails {
    @Embedded
    var attribute: Attribute? = null

    @Relation(parentColumn = "id", entityColumn = "attribute_id")
    var details: List<ItemAttributeDetail> = arrayListOf()
}

class GroupWithAttributesWithDetails {
    @Embedded
    var group: AttributeGroup? = null

    @Relation(parentColumn = "id", entityColumn = "group_id")
    var attributesWithDetails: List<AttributesWithDetails> = arrayListOf()
}