package com.sliide.app.data.assembler

import com.sliide.app.data.network.request.CreateUserRequest
import com.sliide.app.data.model.Gender
import com.sliide.app.data.model.User
import com.sliide.app.data.model.UserStatus
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class UserAssembler @Inject constructor() {

    fun toUser(
        request: CreateUserRequest
    ): User {
        return User(
            id = Random.nextLong(),
            name = request.name,
            email = request.email,
            gender = request.gender,
            status = request.status
        )
    }

    fun toCreateUserRequest(
        name: String,
        emailAddress: String
    ): CreateUserRequest {
        return CreateUserRequest(
            name = name,
            email = emailAddress,
            gender = Gender.values().random(),
            status = UserStatus.values().random()
        )
    }
}