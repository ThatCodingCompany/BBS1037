package tcc.bbshust.network

import com.squareup.moshi.Json

//Respective network api: GET /user/token?email=<邮箱>&password=<密码>
//Respective data type: TokenResult
data class TokenResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "hint") val hint: String,
    @Json(name = "data") val token: TokenResult
)
