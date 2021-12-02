package com.karta.model.tampil

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TampilResponse (
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("rt")
        val rt: String,
        @SerializedName("rw")
        val rw: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("phone_number")
        val phoneNumber: String

        ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "") {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeLong(id)
                parcel.writeString(name)
                parcel.writeString(address)
                parcel.writeString(rt)
                parcel.writeString(rw)
                parcel.writeString(email)
                parcel.writeString(phoneNumber)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<TampilResponse> {
                override fun createFromParcel(parcel: Parcel): TampilResponse {
                        return TampilResponse(parcel)
                }

                override fun newArray(size: Int): Array<TampilResponse?> {
                        return arrayOfNulls(size)
                }
        }
}