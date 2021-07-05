package com.inhouse.catastrophic.ui.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(val id: String, val url: String) : Parcelable