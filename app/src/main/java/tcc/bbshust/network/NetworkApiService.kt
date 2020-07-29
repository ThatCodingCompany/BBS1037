package tcc.bbshust.network

import kotlinx.coroutines.Deferred
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import tcc.bbshust.network.request.RegisterRequest
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
    fun register(
        @Body registerRequest: RegisterRequest
    ): Deferred<RegisterResponse>

    @POST("user/verify")
    fun verify(
        @Body verifyRequest: VerifyRequest
    ): Deferred<VerifyResponse>

    @GET("post")
    fun getAllPosts(
        @Header("Authorization") authorization: String
    ): Deferred<GetAllPostsResponse>

    @GET("post/{id}")
    fun getPostById(
        @Header("Authorization") authorization: String,
        @Path("id") postId: String
    ): Deferred<GetPostByIdResponse>
}

object NetworkApi {
    val retrofitService: NetworkApiService by lazy {
        retrofit.create(NetworkApiService::class.java)
    }
}
