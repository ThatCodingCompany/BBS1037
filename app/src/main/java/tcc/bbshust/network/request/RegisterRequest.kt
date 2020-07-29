package tcc.bbshust.network.request

import com.squareup.moshi.Json

//Related api: POST /user/info
data class RegisterRequest(
    @Json(name = "email") var email: String
)