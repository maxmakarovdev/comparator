package com.maximmakarov.comparator.repository

import com.maximmakarov.comparator.data.model.*

val testTemplates = listOf(Template(1, "first template"), Template(2, "second template"))
val testTemplateId = testTemplates[0].id!!

val testItemsIds = arrayOf(1, 2)
val testItems = listOf(Item(1, 1, "1st item"), Item(2, 1, "2nd item"))

val testAttributeData = listOf(ItemAttrData(1, 1, 1, "I"),
        ItemAttrData(2, 1, 2, "II"), ItemAttrData(3, 2, 1, "III"), ItemAttrData(4, 2, 2, "IV"))

val testGroup = AttributeGroup(1, 1, "group #1")
val testAttributes = arrayOf(Attribute(1, 1, "attribute one"), Attribute(2, 1, "attribute two"))
val testGroupWithAttributes = listOf(GroupWithAttributes())