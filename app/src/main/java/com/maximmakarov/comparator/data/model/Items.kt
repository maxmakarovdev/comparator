package com.maximmakarov.comparator.data.model

import android.os.Parcel
import android.os.Parcelable


class Items(val items: List<Item>) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Item)!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(items)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Items> {
        override fun createFromParcel(parcel: Parcel) = Items(parcel)
        override fun newArray(size: Int): Array<Items?> = arrayOfNulls(size)
    }
}