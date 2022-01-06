package com.sliide.app.ui.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.app.data.model.User
import com.sliide.app.domain.usecase.DeleteUserUseCase
import com.sliide.app.domain.usecase.GetLastPageUsersUseCase
import com.sliide.app.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getLastPageUsersUseCase: GetLastPageUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    val users = MutableLiveData<List<User>>()
    val isLoading = MutableLiveData(false)
    val isError = SingleLiveEvent<Unit>()
    val refreshDataEvent = SingleLiveEvent<Unit>()
    val addUserClickEvent = SingleLiveEvent<Unit>()

    init {
        refreshData()
    }

    fun onAddUserClicked() {
        addUserClickEvent.call()
    }

    fun deleteUser(user: User) {
        launchDataLoad {
            deleteUserUseCase.deleteUser(userID = user.id)
            refreshDataEvent.call()
        }
    }

    fun refreshData() {
        launchDataLoad {
            users.value = getLastPageUsersUseCase.getLastPageUsers()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                isLoading.value = true
                block()
            } catch (error: Throwable) {
                isError.call()
            } finally {
                isLoading.value = false
            }
        }
    }

}