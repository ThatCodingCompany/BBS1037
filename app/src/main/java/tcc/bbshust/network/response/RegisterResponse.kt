package tcc.bbshust.network.response

import com.squareup.moshi.Json
import tcc.bbshust.network.data.RegisterData

//Related api: POST /user/info
data class RegisterResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String,
    @Json(name = "data") var data: RegisterData
)