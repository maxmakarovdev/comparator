package com.maximmakarov.comparator.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*


@Entity(tableName = "items",
        indices = [Index("template_id")],
        foreignKeys = [ForeignKey(entity = Template::class, parentColumns = ["id"], childColumns = ["template_id"], onDelete = ForeignKey.CASCADE)])
class Item(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        @ColumnInfo(name = "template_id")
        val templateId: Int,

        var name: String = "",

        val score: Double? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readInt(),
            parcel.readString() ?: "",
            parcel.readValue(Double::class.java.classLoader) as? Double)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeInt(templateId)
        parcel.writeString(name)
        parcel.writeValue(score)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel) = Item(parcel)
        override fun newArray(size: Int): Array<Item?> = arrayOfNulls(size)
    }
}


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