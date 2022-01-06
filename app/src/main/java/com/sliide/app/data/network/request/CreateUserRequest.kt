package com.sliide.app.data.network.request

import com.google.gson.annotations.SerializedName
import com.sliide.app.data.model.Gender
import com.sliide.app.data.model.UserStatus

data class CreateUserRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: Gender,
    @SerializedName("status") val status: UserStatus
)
