package com.lihuisied.kotlinapp.HeroApp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/10229233666327556/search/{name}")
    suspend fun getSuperHeroes(@Path("name") superHeroName:String):Response<HeroDataResponse>

    @GET("/api/10229233666327556/{id}")
    suspend fun getHeroDetail(@Path("id") superHeroId: String):Response<HeroDetailResponse>

}