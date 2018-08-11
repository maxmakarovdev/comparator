package com.maximmakarov.comparator.core.ext

import android.view.View

fun View.onClick(action: () -> Unit) {
    setOnClickListener { action() }
}

fun View.onLongCLick(action: () -> Unit) {
    setOnLongClickListener { action(); true }
}

fun View.clearOnClick() {
    setOnClickListener(null)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun Array<View>.visible() = forEach { it.visible() }
fun Array<View>.invisible() = forEach { it.invisible() }
fun Array<View>.gone() = forEach { it.gone() }
fun Array<View>.visibleOrGone(isVisible: Boolean) = forEach { it.visibleOrGone(isVisible) }
fun Array<View>.visibleOrInvisible(isVisible: Boolean) = forEach { it.visibleOrInvisible(isVisible) }
