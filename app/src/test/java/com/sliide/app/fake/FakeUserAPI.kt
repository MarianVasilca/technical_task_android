package com.sliide.app.fake

import com.sliide.app.data.assembler.UserAssembler
import com.sliide.app.data.model.User
import com.sliide.app.data.network.UserAPI
import com.sliide.app.data.network.pagination.Pagination
import com.sliide.app.data.network.pagination.PaginationMeta
import com.sliide.app.data.network.pagination.PaginationResponse
import com.sliide.app.data.network.request.CreateUserRequest
import retrofit2.Response
import java.net.HttpURLConnection

class FakeUserAPI(
    private val userAssembler: UserAssembler
) : UserAPI {

    val userItems = mutableListOf<User>()
    private var pageCount = 0

    private var deleteUserResponse: Response<Unit> = SUCCESS_NO_CONTENT_RESPONSE
    private var addUserResponse: Response<Unit> = SUCCESS_CREATED_RESPONSE

    override suspend fun getUsers(pageNumber: Int?): PaginationResponse<User> {
        return PaginationResponse(
            meta = PaginationMeta(Pagination(pageCount)),
            data = userItems
        )
    }

    override suspend fun deleteUser(userID: Long): Response<Unit> {
        if (deleteUserResponse.isSuccessful) {
            userItems.removeAll { it.id == userID }
        }
        return deleteUserResponse
    }

    override suspend fun addUser(body: CreateUserRequest): Response<Unit> {
        if (addUserResponse.isSuccessful) {
            val user = userAssembler.toUser(body)
            userItems.add(user)
        }
        return addUserResponse
    }

    fun setPageCount(value: Int) {
        pageCount = value
    }

    fun setDeleteUserResponse(response: Response<Unit>) {
        deleteUserResponse = response
    }

    fun setAddUserResponse(response: Response<Unit>) {
        addUserResponse = response
    }

    companion object {
        val SUCCESS_NO_CONTENT_RESPONSE: Response<Unit> =
            Response.success(HttpURLConnection.HTTP_NO_CONTENT, Unit)

        val SUCCESS_CREATED_RESPONSE: Response<Unit> =
            Response.success(HttpURLConnection.HTTP_CREATED, Unit)
    }

}