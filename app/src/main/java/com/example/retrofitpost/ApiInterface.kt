package com.example.retrofitpost

import com.example.retrofitpost.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    //Вариант 1
    @POST("/posts")
    suspend fun createPost(
        @Body user: User
    ): Response<User>

    //Вариант 1
    @FormUrlEncoded
    @POST("/posts")
    suspend fun createUrlPost(
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") body: String,
    ): Response<User>
}