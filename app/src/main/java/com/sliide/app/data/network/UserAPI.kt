package com.sliide.app.data.network

import com.sliide.app.data.network.pagination.PaginationResponse
import com.sliide.app.data.network.request.CreateUserRequest
import com.sliide.app.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @GET("users")
    suspend fun getUsers(
        @Query(value = "page") pageNumber: Int? = null
    ): PaginationResponse<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") userID: Long
    ): Response<Unit>

    @POST("users")
    suspend fun addUser(
        @Body body: CreateUserRequest
    ): Response<Unit>

}