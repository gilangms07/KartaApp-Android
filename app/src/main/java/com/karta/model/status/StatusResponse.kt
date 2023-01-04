package com.karta.model.status

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class StatusResponse (
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name_ketua")
    val nameKetua: String,
    @SerializedName("name_wakil")
    val nameWakil: String,
    @SerializedName("rt")
    val rt: String,
    @SerializedName("status")
    val status: String

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "") {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        if (id != null) {
            parcel.writeLong(id)
        }
        parcel.writeString(nameKetua)
        parcel.writeString(nameWakil)
        parcel.writeString(rt)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StatusResponse> {
        override fun createFromParcel(parcel: Parcel): StatusResponse {
            return StatusResponse(parcel)
        }

        override fun newArray(size: Int): Array<StatusResponse?> {
            return arrayOfNulls(size)
        }
    }

}