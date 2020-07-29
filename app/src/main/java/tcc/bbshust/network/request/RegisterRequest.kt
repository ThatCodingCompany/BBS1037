package tcc.bbshust.network.request

import com.squareup.moshi.Json

data class RegisterRequest(
    @Json(name = "password") var password: String,
    @Json(name = "email") var email: String
)