package com.sliide.app.data.repository

import com.sliide.app.data.model.User

interface UsersRepository {

    suspend fun getUsersPageCount(): Int
    suspend fun getUsers(pageNumber: Int): List<User>
    suspend fun deleteUser(userID: Long)
    suspend fun addUser(name: String, emailAddress: String)

}