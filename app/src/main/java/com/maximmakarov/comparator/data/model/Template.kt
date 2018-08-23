package com.maximmakarov.comparator.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
class Template(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,

        val name: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Template> {
        override fun createFromParcel(parcel: Parcel) = Template(parcel)
        override fun newArray(size: Int): Array<Template?> = arrayOfNulls(size)
    }
}