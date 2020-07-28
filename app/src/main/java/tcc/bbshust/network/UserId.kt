package tcc.bbshust.network

import com.squareup.moshi.Json

data class UserId(
    @Json(name = "_id") var id: String
)