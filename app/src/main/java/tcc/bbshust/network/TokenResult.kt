package tcc.bbshust.network

import com.squareup.moshi.Json

//Respective network api: GET /user/token?email=<邮箱>&password=<密码>
data class TokenResult(
    @Json(name = "token") val token: String,
    @Json(name = "_id") val id: String,
    @Json(name = "expire_time") val expireTime: Long
)