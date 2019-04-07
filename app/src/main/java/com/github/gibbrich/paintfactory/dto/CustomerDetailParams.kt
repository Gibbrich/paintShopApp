package com.github.gibbrich.paintfactory.dto

import android.os.Parcel
import android.os.Parcelable

data class CustomerDetailParams(
    val customerId: Int
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(customerId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CustomerDetailParams> {
        override fun createFromParcel(parcel: Parcel): CustomerDetailParams {
            return CustomerDetailParams(parcel)
        }

        override fun newArray(size: Int): Array<CustomerDetailParams?> {
            return arrayOfNulls(size)
        }
    }
}