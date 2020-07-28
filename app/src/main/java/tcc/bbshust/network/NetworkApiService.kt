package tcc.bbshust.network

import kotlinx.coroutines.Deferred
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
    fun getToken(
        @Query("email") email: String,
        @Query("password") password: String
    ): Deferred<TokenResponse>
}

object NetworkApi {
    val retrofitService: NetworkApiService by lazy {
        retrofit.create(NetworkApiService::class.java)
    }
}
