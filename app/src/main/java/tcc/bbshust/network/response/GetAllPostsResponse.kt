package tcc.bbshust.network.response

import com.squareup.moshi.Json
import tcc.bbshust.network.data.GetAllPostsData

//Related api: *GET /post
data class GetAllPostsResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String,
    @Json(name = "data") var data: GetAllPostsData?
)
