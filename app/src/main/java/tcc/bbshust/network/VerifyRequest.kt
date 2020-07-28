package tcc.bbshust.network

import com.squareup.moshi.Json

data class VerifyRequest(
    @Json(name = "_id") var id: String,
    @Json(name = "code") var verifyCode: String
)
