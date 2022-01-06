package com.sliide.app.domain.usecase

import com.sliide.app.data.model.InvalidUserInputError
import com.sliide.app.data.repository.UsersRepository
import com.sliide.app.util.extension.isValidEmail
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UsersRepository
) {

    suspend fun addUser(name: String?, emailAddress: String?) {
        if (areFieldsValid(name, emailAddress)) {
            repository.addUser(name!!, emailAddress!!)
        } else {
            throw InvalidUserInputError("Please check the e-mail and name again")
        }
    }

    private fun areFieldsValid(name: String?, emailAddress: String?) =
        name?.isNotBlank() == true && emailAddress.isValidEmail()

}