package com.maximmakarov.comparator.core.ext

inline infix fun <reified T> List<T>.contentEquals(other: List<T>) = toTypedArray() contentEquals other.toTypedArray()

inline infix fun <reified T> List<T>.contentEquals(other: Array<T>) = toTypedArray() contentEquals other

inline infix fun <reified T> Array<T>.contentEquals(other: List<T>) = this contentEquals other.toTypedArray()

inline infix fun <reified T> List<T>.contentDeepEquals(other: List<T>) = toTypedArray() contentDeepEquals other.toTypedArray()

inline infix fun <reified T> Array<T>.contentDeepEquals(other: List<T>) = this contentDeepEquals other.toTypedArray()

inline infix fun <reified T> List<T>.contentDeepEquals(other: Array<T>) = toTypedArray() contentDeepEquals other