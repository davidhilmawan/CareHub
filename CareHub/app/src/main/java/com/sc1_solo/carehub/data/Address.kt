package com.sc1_solo.carehub.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var id: String? = null,
    var title: String? = null,
    var address: String? = null,
) : Parcelable