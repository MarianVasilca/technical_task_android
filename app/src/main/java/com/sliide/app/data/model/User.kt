package com.sliide.app.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val gender: Gender?,
    val status: UserStatus?,
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}

enum class Gender {
    @SerializedName("male")
    MALE,

    @SerializedName("female")
    FEMALE,
}


enum class UserStatus {
    @SerializedName("active")
    ACTIVE,

    @SerializedName("inactive")
    INACTIVE,
}
