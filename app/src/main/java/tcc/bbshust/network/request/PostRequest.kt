package tcc.bbshust.network.request

import com.squareup.moshi.Json

//Related api: *POST /post
data class PostRequest(
    @Json(name = "title") var title: String,
    @Json(name = "content") var content: String
)
