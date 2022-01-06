package com.sliide.app.data.repository

import com.google.common.truth.Truth
import com.sliide.app.fake.FakeUserAPI
import com.sliide.app.data.assembler.UserAssembler
import com.sliide.app.data.model.AddUserError
import com.sliide.app.data.model.DeleteUserError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class UsersRepositoryTest {

    private lateinit var fakeUserAPI: FakeUserAPI
    private lateinit var assembler: UserAssembler
    private lateinit var subject: UsersRepositoryImpl

    @Before
    fun setup() {
        assembler = UserAssembler()
        fakeUserAPI = FakeUserAPI(assembler)
        subject = UsersRepositoryImpl(fakeUserAPI, assembler)
    }

    @Test
    fun whenGetUsersPageCount_successful() = runBlockingTest {
        val pageCount = 1
        fakeUserAPI.setPageCount(1)


        val usersPageCount = subject.getUsersPageCount()

        Truth.assertThat(usersPageCount).isEqualTo(pageCount)
    }

    @Test
    fun whenGetUsers_successful() = runBlockingTest {
        val subject = UsersRepositoryImpl(fakeUserAPI, assembler)

        val users = subject.getUsers(1)

        Truth.assertThat(users).isEqualTo(fakeUserAPI.userItems)
    }

    @Test
    fun whenDeleteUser_successful() = runBlockingTest {
        val response = FakeUserAPI.SUCCESS_NO_CONTENT_RESPONSE
        fakeUserAPI.setDeleteUserResponse(response)

        val subject = UsersRepositoryImpl(fakeUserAPI, assembler)

        subject.deleteUser(1)
    }

    @Test(expected = DeleteUserError::class)
    fun whenDeleteUser_throws() = runBlockingTest {
        Response.error<Unit>(
            HttpURLConnection.HTTP_UNAUTHORIZED, "".toResponseBody()
        ).also { fakeUserAPI.setDeleteUserResponse(it) }

        val subject = UsersRepositoryImpl(fakeUserAPI, assembler)

        subject.deleteUser(1)
    }

    @Test
    fun whenAddUser_successful() = runBlockingTest {
        val response = FakeUserAPI.SUCCESS_CREATED_RESPONSE
        fakeUserAPI.setAddUserResponse(response)

        val subject = UsersRepositoryImpl(fakeUserAPI, assembler)

        subject.addUser("test", "name@example.com")
    }

    @Test(expected = AddUserError::class)
    fun whenAddUser_throws() = runBlockingTest {
        val response =
            Response.error<Unit>(HttpURLConnection.HTTP_UNAUTHORIZED, "".toResponseBody())
        fakeUserAPI.setAddUserResponse(response)

        val subject = UsersRepositoryImpl(fakeUserAPI, assembler)

        subject.addUser("test", "name@example.com")
    }

}