package com.maximmakarov.comparator.viewmodel

import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.Template

val testTemplates = listOf(Template(1, "first template"), Template(2, "second template"))
val testTemplate = testTemplates[0]
val testNewTemplate = Template(null, "new template")
val testAttributes = "test"

val testItems = listOf(Item(1, 1, "1st item"), Item(2, 1, "2nd item"))
