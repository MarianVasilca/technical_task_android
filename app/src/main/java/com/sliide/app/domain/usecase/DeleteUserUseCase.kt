package com.sliide.app.domain.usecase

import com.sliide.app.data.repository.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend fun deleteUser(userID: Long) {
        repository.deleteUser(userID)
    }
}