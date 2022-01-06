package com.sliide.app.ui.add_user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.app.data.model.AddUserError
import com.sliide.app.data.model.InvalidUserInputError
import com.sliide.app.domain.usecase.AddUserUseCase
import com.sliide.app.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {

    val isLoading = MutableLiveData(false)
    val isInvalidInputError = SingleLiveEvent<Unit>()
    val isRequestError = SingleLiveEvent<Unit>()
    val successEvent = SingleLiveEvent<Unit>()

    fun addUser(name: String?, emailAddress: String?) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                addUserUseCase.addUser(name, emailAddress)
                successEvent.call()
            } catch (error: InvalidUserInputError) {
                isInvalidInputError.call()
            } catch (error: AddUserError) {
                isRequestError.call()
            } finally {
                isLoading.value = false
            }
        }
    }

}