package tcc.bbshust.network.response

import com.squareup.moshi.Json
import tcc.bbshust.network.data.TokenData

//Respective network api: GET /user/token?email=<邮箱>&password=<密码>
//Respective data type: TokenResult
data class TokenResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String,
    @Json(name = "data") var token: TokenData
)
