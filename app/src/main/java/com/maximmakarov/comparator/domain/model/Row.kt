package com.maximmakarov.comparator.domain.model


class Row(val cells: MutableList<Cell> = mutableListOf()) {
    fun add(text: String = "", isGroup: Boolean = false, score: Int = 0) {
        cells.add(Cell(text, isGroup, score))
    }
}