package tcc.bbshust.network.response

import com.squareup.moshi.Json
import tcc.bbshust.network.data.Post

//Related api: *GET /post/:id
data class GetPostByIdResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String,
    @Json(name = "data") var data: Post?
)
