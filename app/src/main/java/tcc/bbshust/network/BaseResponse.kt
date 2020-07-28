package tcc.bbshust.network

import com.squareup.moshi.Json

data class BaseResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "hint") val hint: String
)
