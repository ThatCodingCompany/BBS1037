package tcc.bbshust.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import tcc.bbshust.network.request.PostRequest
import tcc.bbshust.network.request.RegisterRequest
import tcc.bbshust.network.request.ReplyRequest
import tcc.bbshust.network.request.VerifyRequest
import tcc.bbshust.network.response.*

private const val BASE_URL = "https://s1.996404.xyz:3443/api/v1/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NetworkApiService {

    @GET("user/token")
    suspend fun getTokenAsync(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<TokenResponse>

    @POST("user/info")
    suspend fun registerAsync(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("user/verify")
    suspend fun verifyAsync(
        @Body verifyRequest: VerifyRequest
    ): Response<VerifyResponse>

    @GET("post")
    suspend fun getAllPostsAsync(
        @Header("Authorization") authorization: String
    ): Response<GetAllPostsResponse>

    @GET("post/{id}")
    suspend fun getPostByIdAsync(
        @Header("Authorization") authorization: String,
        @Path("id") postId: String
    ): Response<GetPostByIdResponse>

    @POST("post")
    suspend fun newPostAsync(
        @Header("Authorization") authorization: String,
        @Body request: PostRequest
    ): Response<PostResponse>

    @POST("post/{id}")
    suspend fun replyAsync(
        @Header("Authorization") authorization: String,
        @Path("id") postId: String,
        @Body request: ReplyRequest
    ): Response<ReplyResponse>
}

object NetworkApi {
    val retrofitService: NetworkApiService by lazy {
        retrofit.create(NetworkApiService::class.java)
    }
}
