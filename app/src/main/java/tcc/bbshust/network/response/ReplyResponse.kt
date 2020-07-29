package tcc.bbshust.network.response

import com.squareup.moshi.Json

//Related api: *POST /post/:id
data class ReplyResponse(
    @Json(name = "success") var isSuccess: Boolean,
    @Json(name = "hint") var hint: String
)
