package com.maximmakarov.comparator.data.model

import androidx.room.Embedded
import androidx.room.Relation


class GroupWithAttributes {
    @Embedded
    var group: AttributeGroup? = null

    @Relation(parentColumn = "id", entityColumn = "group_id")
    var attributes: List<Attribute> = arrayListOf()
}