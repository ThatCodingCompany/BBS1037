package tcc.bbshust.network.request

import com.squareup.moshi.Json

data class ReplyRequest(
    @Json(name = "content") var content: String
)
