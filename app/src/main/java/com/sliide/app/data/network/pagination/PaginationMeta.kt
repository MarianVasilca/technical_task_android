package com.sliide.app.data.network.pagination

import com.google.gson.annotations.SerializedName

data class PaginationMeta(
    @SerializedName("pagination") val pagination: Pagination
)
