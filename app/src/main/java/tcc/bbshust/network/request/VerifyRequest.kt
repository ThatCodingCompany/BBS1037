package tcc.bbshust.network.request

import com.squareup.moshi.Json

//Related api: POST /user/verify
data class VerifyRequest(
    @Json(name = "_id") var id: String,
    @Json(name = "password") var password: String,
    @Json(name = "code") var verifyCode: String
)
