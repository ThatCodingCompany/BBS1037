package tcc.bbshust.network.response

import com.squareup.moshi.Json

//Related api: POST /user/verify
data class VerifyResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String
)
