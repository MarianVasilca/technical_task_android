package com.sliide.app.domain.usecase

import com.google.common.truth.Truth
import com.sliide.app.fake.FakeUsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetLastPageUsersUseCaseTest {

    private lateinit var subject: GetLastPageUsersUseCase
    private lateinit var fakeRepository: FakeUsersRepository

    @Before
    fun setup() {
        fakeRepository = FakeUsersRepository()
        subject = GetLastPageUsersUseCase(fakeRepository)
    }

    @Test
    fun whenGetLastPageUsers_successful() = runBlockingTest {
        val user = fakeRepository.createRandomUser()
        fakeRepository.addUsers(mutableListOf(user))

        val result = subject.getLastPageUsers()

        Truth.assertThat(result).isEqualTo(fakeRepository.lastPageUsers)
    }

}