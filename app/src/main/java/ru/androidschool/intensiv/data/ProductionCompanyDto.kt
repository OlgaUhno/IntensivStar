package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class ProductionCompanyDto(
    @SerializedName("name")
    val name: String?
)
