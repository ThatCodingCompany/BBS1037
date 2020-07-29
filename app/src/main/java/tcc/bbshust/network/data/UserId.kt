package tcc.bbshust.network.data

import com.squareup.moshi.Json

data class UserId(
    @Json(name = "_id") var id: String
)