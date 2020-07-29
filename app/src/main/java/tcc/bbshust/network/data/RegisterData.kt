package tcc.bbshust.network.data

import com.squareup.moshi.Json

//Related api: POST /user/info
data class RegisterData(
    @Json(name = "_id") var id: String
)