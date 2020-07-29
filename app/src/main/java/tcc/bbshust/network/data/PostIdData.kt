package tcc.bbshust.network.data

import com.squareup.moshi.Json

//Related api: *POST /post
data class PostIdData(
    @Json(name = "_id") var postId: String
)
