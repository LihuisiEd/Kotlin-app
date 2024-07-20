package com.lihuisied.kotlinapp.HeroApp

import com.google.gson.annotations.SerializedName

data class HeroDetailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerstats: PowerStatsResponse,
    @SerializedName("image") val image: HeroImageDetailResponse,
    @SerializedName("biography") val biography: Biography,
    @SerializedName("appearance") val appearance: Appearance,
)

data class PowerStatsResponse(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String
)

data class HeroImageDetailResponse(@SerializedName("url") val url: String)

data class Biography(
    @SerializedName("full-name") val fullName: String,
    @SerializedName("publisher") val publisher: String
)

data class Appearance(
    @SerializedName("gender") val gender: String,
    @SerializedName("race") val race: String,
    @SerializedName("height") val height: List<String>
)
