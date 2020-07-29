package tcc.bbshust.network.request

import com.squareup.moshi.Json

//Related api: *POST /post/:id
data class ReplyRequest(
    @Json(name = "content") var content: String
)
