package tcc.bbshust.network.response

import com.squareup.moshi.Json
import tcc.bbshust.network.data.PostIdData

//Related api: *POST /post
data class PostResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String,
    @Json(name = "data") var data: PostIdData?
)
