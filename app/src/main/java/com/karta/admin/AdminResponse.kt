package com.karta.admin

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AdminResponse(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name_admin")
    val nameAdmin: String,
    @SerializedName("email_admin")
    val emailAdmin: String

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        if (id != null) {
            parcel.writeLong(id)
        }
        parcel.writeString(nameAdmin)
        parcel.writeString(emailAdmin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdminResponse> {
        override fun createFromParcel(parcel: Parcel): AdminResponse {
            return AdminResponse(parcel)
        }

        override fun newArray(size: Int): Array<AdminResponse?> {
            return arrayOfNulls(size)
        }
    }

}