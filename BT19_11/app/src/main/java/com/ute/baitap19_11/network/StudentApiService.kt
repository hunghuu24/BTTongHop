package com.ute.baitap19_11.network

import retrofit2.http.GET

interface StudentApiService {
    @GET("users") suspend fun getStudents(): List<StudentDto>
}
