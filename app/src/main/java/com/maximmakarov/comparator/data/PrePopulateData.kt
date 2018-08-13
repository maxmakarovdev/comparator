package com.maximmakarov.comparator.data

import com.maximmakarov.comparator.data.attribute.Attribute
import com.maximmakarov.comparator.data.attribute.AttributeGroup
import com.maximmakarov.comparator.data.item.Item
import com.maximmakarov.comparator.data.item.ItemAttrData
import com.maximmakarov.comparator.data.template.Template


val PREPOPULATE_TEMPLATE = Template(1, "Smartphones")

val PREPOPULATE_GROUPS = arrayOf(
        AttributeGroup(1, 1, "Performance"),
        AttributeGroup(2, 1, "Screen"),
        AttributeGroup(3, 1, "Battery"),
        AttributeGroup(4, 1, "Camera")
)
val PREPOPULATE_ATTRIBUTES = arrayOf(
        Attribute(1, 1, "CPU", true),
        Attribute(2, 1, "RAM", false),
        Attribute(3, 1, "Storage", false),
        Attribute(4, 2, "Resolution", false),
        Attribute(5, 2, "Screen size", false),
        Attribute(6, 3, "Battery life", false),
        Attribute(7, 3, "Battery capacity", true),
        Attribute(8, 4, "Rear camera", false),
        Attribute(9, 4, "Front camera", false)
)
val PREPOPULATE_ITEMS = arrayOf(
        Item(1, 1, "Samsung Galaxy S9 Plus"),
        Item(2, 1, "Huawei P20 Pro"),
        Item(3, 1, "iPhone X")
)
val PREPOPULATE_ITEMS_DATA = arrayOf(
        ItemAttrData(1, 1, 1, "Snapdragon 845 / Exynos 9810", 9),
        ItemAttrData(2, 1, 2, "6GB", 9),
        ItemAttrData(3, 1, 3, "64GB/128GB", 8),
        ItemAttrData(4, 1, 4, "1440 x 2960", 7),
        ItemAttrData(5, 1, 5, "6.2-inch", 6),
        ItemAttrData(6, 1, 6, "7h", 6),
        ItemAttrData(7, 1, 7, "3,500mAh", 6),
        ItemAttrData(8, 1, 8, "Dual 12MP", 7),
        ItemAttrData(9, 1, 9, "8MP", 7),

        ItemAttrData(10, 2, 1, "Kirin 970", 7),
        ItemAttrData(11, 2, 2, "6GB", 9),
        ItemAttrData(12, 2, 3, "128GB", 8),
        ItemAttrData(13, 2, 4, "1080 x 2240", 5),
        ItemAttrData(14, 2, 5, "6.1-inch", 7),
        ItemAttrData(15, 2, 6, "10h", 8),
        ItemAttrData(16, 2, 7, "4,000mAh", 8),
        ItemAttrData(17, 2, 8, "40MP + 20MP + 8MP", 10),
        ItemAttrData(18, 2, 9, "24MP", 10),

        ItemAttrData(19, 3, 1, "A11 Bionic", 7),
        ItemAttrData(20, 3, 2, "3GB", 6),
        ItemAttrData(21, 3, 3, "64/256GB", 9),
        ItemAttrData(22, 3, 4, "1125x2436", 7),
        ItemAttrData(23, 3, 5, "5.8-inch", 7),
        ItemAttrData(24, 3, 6, "8h", 7),
        ItemAttrData(25, 3, 7, "2716mAh", 6),
        ItemAttrData(26, 3, 8, "12MP+12MP", 7),
        ItemAttrData(27, 3, 9, "7MP", 6)
)