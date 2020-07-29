package tcc.bbshust.network.response

import com.squareup.moshi.Json

data class ReplyResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String
)
