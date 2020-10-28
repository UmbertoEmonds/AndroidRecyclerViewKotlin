package com.umbertoemonds.myapplication.api

import com.umbertoemonds.myapplication.model.Gare
import com.umbertoemonds.myapplication.model.ResponseAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

const val BASE_URL = "https://ressources.data.sncf.com/" // top level property

interface Api {

    @GET("api/records/1.0/search/?dataset=gares-pianos&q=&rows=108&sort=gare&facet=gare")
    fun getResponse(): Call<ResponseAPI>

}