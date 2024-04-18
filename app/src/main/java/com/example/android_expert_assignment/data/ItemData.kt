package com.example.android_expert_assignment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

    @Parcelize
    data class ItemData(
        val image: Int,
        val itemName: Int,
        val itemIntroduction: Int,
        val seller: Int,
        val price: Int,
        val address: Int,
        val like: Int,
        val chatting: Int
    ) : Parcelable