package com.sliide.app.data.repository

import com.sliide.app.data.assembler.UserAssembler
import com.sliide.app.data.model.AddUserError
import com.sliide.app.data.model.DeleteUserError
import com.sliide.app.data.model.User
import com.sliide.app.data.network.UserAPI
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userAPI: UserAPI,
    private val userAssembler: UserAssembler
) : UsersRepository {

    override suspend fun getUsersPageCount(): Int {
        return userAPI.getUsers(pageNumber = null).meta.pagination.pages
    }

    override suspend fun getUsers(pageNumber: Int): List<User> {
        return userAPI.getUsers(pageNumber).data
    }

    override suspend fun deleteUser(userID: Long) {
        val errorMessage = "Unable to delete user"
        try {
            val result = userAPI.deleteUser(userID)
            if (!isDeleteCallSuccessful(result)) {
                throw DeleteUserError(errorMessage, null)
            }
        } catch (error: Throwable) {
            throw DeleteUserError(errorMessage, error)
        }
    }

    override suspend fun addUser(name: String, emailAddress: String) {
        val errorMessage = "Unable to add user"
        try {
            val request = userAssembler.toCreateUserRequest(name, emailAddress)
            val result = userAPI.addUser(request)
            if (!isCreateCallSuccessful(result)) {
                throw AddUserError(errorMessage, null)
            }
        } catch (error: Throwable) {
            throw AddUserError(errorMessage, error)
        }
    }

    private fun isDeleteCallSuccessful(result: Response<Unit>): Boolean {
        return result.code() == HttpURLConnection.HTTP_NO_CONTENT
    }

    private fun isCreateCallSuccessful(result: Response<Unit>): Boolean {
        return result.code() == HttpURLConnection.HTTP_CREATED
    }
}