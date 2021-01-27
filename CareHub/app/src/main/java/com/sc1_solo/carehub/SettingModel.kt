package com.sc1_solo.carehub

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SettingModel (
    var isDarkTheme: Boolean = false
): Parcelable