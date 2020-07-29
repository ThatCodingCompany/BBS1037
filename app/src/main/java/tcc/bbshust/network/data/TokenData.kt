package tcc.bbshust.network.data

import com.squareup.moshi.Json

//Respective network api: GET /user/token?email=<邮箱>&password=<密码>
data class TokenData(
    @Json(name = "token") var token: String,
    @Json(name = "_id") var id: String,
    @Json(name = "expire_time") var expireTime: Long
)