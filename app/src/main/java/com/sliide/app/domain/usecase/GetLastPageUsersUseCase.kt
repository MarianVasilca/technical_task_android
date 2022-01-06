package com.sliide.app.domain.usecase

import com.sliide.app.data.repository.UsersRepository
import com.sliide.app.data.model.User
import javax.inject.Inject

class GetLastPageUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend fun getLastPageUsers(): List<User> {
        val usersPageCount = repository.getUsersPageCount()
        return repository.getUsers(usersPageCount)
    }
}