package com.lihuisied.kotlinapp.HeroApp

import com.google.gson.annotations.SerializedName

data class HeroDataResponse(@SerializedName("response") val response: String,
                            @SerializedName("results") val superheroes: List<HeroItemResponse>)

data class HeroItemResponse(@SerializedName("id") val hero_id: String,
                            @SerializedName("name") val hero_name: String,
                            @SerializedName("image") val hero_image:HeroImageResponse
)

data class HeroImageResponse(@SerializedName("url") val url:String)