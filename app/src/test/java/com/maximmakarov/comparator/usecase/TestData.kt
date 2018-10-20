package com.maximmakarov.comparator.usecase

import com.maximmakarov.comparator.data.model.*
import com.maximmakarov.comparator.domain.model.AttributeData
import com.maximmakarov.comparator.repository.testTemplates

// === Templates data ===
val testTemplates = listOf(Template(1, "first template"), Template(2, "second template"))
val testNewTemplateName = "new template"
val testTemplateData = "##First group\nFirst attribute\nSecond attribute\nThird attribute\n##Second group\n\n   \n \nFirst attribute\nSecond attribute\n##  "


// === Items data ===
val testTemplateId = testTemplates[0].id!!
val testItems = listOf(Item(1, 1, "1st item"), Item(2, 1, "2nd item"))

val testGroup = AttributeGroup(1, 1, "group #1")
val testAttributes = listOf(Attribute(1, 1, "attribute one"), Attribute(2, 1, "attribute two"))
val testGroupWithAttributes = listOf(GroupWithAttributes().apply { group = testGroup; attributes = testAttributes })
val testAttributesData = listOf(ItemAttrData(1, 1, 1, "answer 1-1"),
        ItemAttrData(2, 1, 2, "answer 1-2"), ItemAttrData(3, 2, 1, "answer 2-1"), ItemAttrData(4, 2, 2, "answer 2-2"))

val testItemsData = listOf(
        Pair(testItems[0].id!!, listOf(Pair(testGroup,
                listOf(AttributeData(testAttributes[0], testAttributesData[0]),
                        AttributeData(testAttributes[1], testAttributesData[1]))))),
        Pair(testItems[1].id!!, listOf(Pair(testGroup,
                listOf(AttributeData(testAttributes[0], testAttributesData[2]),
                        AttributeData(testAttributes[1], testAttributesData[3]))))))

val testNewItem = Item(null, 1, "new item")
val testNewAttributesData = listOf(ItemAttrData(null, null, 1, "answer 1-1"), ItemAttrData(null, null, 2, "answer 1-2"))
val testNewItemData = listOf(Pair(testGroup,
                listOf(AttributeData(testAttributes[0], testNewAttributesData[0]),
                        AttributeData(testAttributes[1], testNewAttributesData[1]))))