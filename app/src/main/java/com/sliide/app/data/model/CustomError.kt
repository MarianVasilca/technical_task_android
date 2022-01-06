package com.sliide.app.data.model

class DeleteUserError(message: String, cause: Throwable?) : Throwable(message, cause)

class AddUserError(message: String, cause: Throwable?) : Throwable(message, cause)

class InvalidUserInputError(message: String) : Throwable(message)