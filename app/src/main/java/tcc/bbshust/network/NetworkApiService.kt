package tcc.bbshust.network

import kotlinx.coroutines.Deferred
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import tcc.bbshust.network.request.PostRequest
import tcc.bbshust.network.request.RegisterRequest
import tcc.bbshust.network.request.ReplyRequest
import tcc.bbshust.network.request.VerifyRequest
import tcc.bbshust.network.response.*

private const val BASE_URL = "https://s1.996404.xyz:3443/api/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface NetworkApiService {

    @GET("user/token")
    fun getTokenAsync(
        @Query("email") email: String,
        @Query("password") password: String
    ): Deferred<TokenResponse>

    @POST("user/info")
    fun registerAsync(
        @Body registerRequest: RegisterRequest
    ): Deferred<RegisterResponse>

    @POST("user/verify")
    fun verifyAsync(
        @Body verifyRequest: VerifyRequest
    ): Deferred<VerifyResponse>

    @GET("post")
    fun getAllPostsAsync(
        @Header("Authorization") authorization: String
    ): Deferred<GetAllPostsResponse>

    @GET("post/{id}")
    fun getPostByIdAsync(
        @Header("Authorization") authorization: String,
        @Path("id") postId: String
    ): Deferred<GetPostByIdResponse>

    @POST("post")
    fun newPostAsync(
        @Header("Authorization") authorization: String,
        @Body request: PostRequest
    ): Deferred<PostResponse>

    @POST("post/{id}")
    fun replyAsync(
        @Header("Authorization") authorization: String,
        @Path("id") postId: String,
        @Body request: ReplyRequest
    ): Deferred<ReplyResponse>
}

object NetworkApi {
    val retrofitService: NetworkApiService by lazy {
        retrofit.create(NetworkApiService::class.java)
    }
}
