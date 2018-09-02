package com.maximmakarov.comparator.dao

import com.maximmakarov.comparator.data.model.*


val testTemplates = arrayOf(
        Template(id = 1, name = "first template"),
        Template(id = 2, name = "second template"))
val testItems = arrayOf(
        Item(id = 1, templateId = 1, name = "1st item"),
        Item(id = 2, templateId = 1, name = "2nd item"),
        Item(id = 3, templateId = 2, name = "3rd item"))
val testGroups = arrayOf(
        AttributeGroup(id = 1, templateId = 1, name = "group #1"),
        AttributeGroup(id = 2, templateId = 2, name = "group #2"))
val testAttributes = arrayOf(
        Attribute(id = 1, groupId = 1, name = "attribute one"),
        Attribute(id = 2, groupId = 1, name = "attribute two"))
val testItemsData = arrayOf(
        ItemAttrData(id = 1, itemId = 1, attributeId = 1, answer = "I"),
        ItemAttrData(id = 2, itemId = 1, attributeId = 2, answer = "II"),
        ItemAttrData(id = 3, itemId = 2, attributeId = 1, answer = "III"),
        ItemAttrData(id = 4, itemId = 2, attributeId = 2, answer = "IV"),
        ItemAttrData(id = 5, itemId = 3, attributeId = 1, answer = "V"),
        ItemAttrData(id = 6, itemId = 3, attributeId = 2, answer = "VI"))