package tcc.bbshust.network.data

import com.squareup.moshi.Json

//Related api: *GET /post
data class GetAllPostsData(
    @Json(name = "posts_info") val postsInfo: List<Post>
)
