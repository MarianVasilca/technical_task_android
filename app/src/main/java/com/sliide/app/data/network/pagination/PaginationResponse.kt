package com.sliide.app.data.network.pagination

import com.google.gson.annotations.SerializedName

data class PaginationResponse<out T>(
    @SerializedName("meta") val meta: PaginationMeta,
    @SerializedName("data") val data: List<T>
)
