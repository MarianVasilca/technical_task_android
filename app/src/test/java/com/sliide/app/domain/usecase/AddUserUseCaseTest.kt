package com.sliide.app.domain.usecase

import com.sliide.app.fake.FakeUsersRepository
import com.sliide.app.data.model.InvalidUserInputError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddUserUseCaseTest {

    private lateinit var subject: AddUserUseCase
    private lateinit var fakeRepository: FakeUsersRepository

    @Before
    fun setup() {
        fakeRepository = FakeUsersRepository()
        subject = AddUserUseCase(fakeRepository)
    }

    @Test(expected = InvalidUserInputError::class)
    fun whenAddUser_emptyName_throws() = runBlockingTest {
        subject.addUser("", "name@example.com")
    }

    @Test(expected = InvalidUserInputError::class)
    fun whenAddUser_nullName_throws() = runBlockingTest {
        subject.addUser(null, "name@example.com")
    }

    @Test(expected = InvalidUserInputError::class)
    fun whenAddUser_nullEmail_throws() = runBlockingTest {
        subject.addUser("John", null)
    }

    @Test(expected = InvalidUserInputError::class)
    fun whenAddUser_invalidEmail_throws() = runBlockingTest {
        subject.addUser("John", "name@example.")
    }

    @Test
    fun whenAddUser_successful() = runBlockingTest {
        subject.addUser("John", "name@example.com")
    }

}