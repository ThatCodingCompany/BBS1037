package tcc.bbshust.network.data

import com.squareup.moshi.Json

//Related api: *GET /post
//Related api: *GET /post/:id
data class Post(
    @Json(name = "_id") var postId: String,
    @Json(name = "created_at") var createTime: Long,
    @Json(name = "updated_at") var updateTime: Long,
    @Json(name = "title") var title: String?,
    @Json(name = "user_id") var author: String,
    @Json(name = "content") var content: String,
    @Json(name = "reply") var replyList: List<Post>?
)
