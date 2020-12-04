package com.sc1_solo.carehub

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HospitalData(
        var hospitalName: String,
        var photo: String,
        var avgopenhour: String,
        var address: String,
        var openhour: String,
        var phone: String,
        var website: String,
        val lang: Double,
        val lat: Double
) : Parcelable