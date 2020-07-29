package tcc.bbshust.network.response

import com.squareup.moshi.Json
import tcc.bbshust.network.data.TokenResult

//Respective network api: GET /user/token?email=<邮箱>&password=<密码>
//Respective data type: TokenResult
data class TokenResponse(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "hint") val hint: String,
    @Json(name = "data") val token: TokenResult
)
