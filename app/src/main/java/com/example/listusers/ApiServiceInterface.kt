package com.example.listusers

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceInterface
  {
      @GET("users")
      fun getUsers(@Query("limit") limit: Int, @Query("skip") skip: Int): Call<UserResponse>

      @GET("users/{id}")
      fun getUserDetails(@Path("id") id: Int): Call<User>

  }
