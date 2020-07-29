package tcc.bbshust.network.response

import com.squareup.moshi.Json

data class BaseResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "hint") val hint: String
)
