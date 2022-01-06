package com.sliide.app.fake

import com.sliide.app.data.repository.UsersRepository
import com.sliide.app.data.model.Gender
import com.sliide.app.data.model.User
import com.sliide.app.data.model.UserStatus
import java.util.*
import kotlin.random.Random

class FakeUsersRepository : UsersRepository {

    private val userItems = mutableListOf<User>()
    private val pageCount = 0
    val lastPageUsers
        get() = userItems.takeLast(PAGE_SIZE)

    override suspend fun getUsersPageCount(): Int {
        return pageCount
    }

    override suspend fun getUsers(pageNumber: Int): List<User> {
        return lastPageUsers
    }

    override suspend fun deleteUser(userID: Long) {
        userItems.removeAll { it.id == userID }
    }

    override suspend fun addUser(name: String, emailAddress: String) {
        val item = User(
            id = Random.nextLong(),
            name = name,
            email = emailAddress,
            gender = Gender.values().random(),
            status = UserStatus.values().random()
        )
        userItems.add(item)
    }

    fun addUsers(items: MutableList<User>) {
        userItems.addAll(items)
    }

    fun createRandomUser(): User {
        return User(
            id = Random.nextLong(),
            name = UUID.randomUUID().toString(),
            email = UUID.randomUUID().toString(),
            gender = Gender.values().random(),
            status = UserStatus.values().random()
        )
    }

    companion object {
        private const val PAGE_SIZE = 1
    }
}