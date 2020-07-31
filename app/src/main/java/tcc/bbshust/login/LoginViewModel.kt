package tcc.bbshust.login

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tcc.bbshust.network.NetworkApi
import tcc.bbshust.network.data.Post
import tcc.bbshust.network.data.TokenData
import tcc.bbshust.network.moshi
import tcc.bbshust.network.response.TokenResponse
import tcc.bbshust.utils.makeToken
import java.lang.Exception
import kotlin.math.log

private const val TAG = "LoginViewModel"

class LoginViewModel : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()


    private val _bottomNavState = MutableLiveData<Int>()

    val bottomNavState: LiveData<Int>
        get() = _bottomNavState

    private val _navigateToHome = MutableLiveData<TokenResponse>()

    val navigateToHome: LiveData<TokenResponse>
        get() = _navigateToHome

    init {
        _bottomNavState.value = View.GONE
        Log.d(TAG, "init:success ${bottomNavState.value}")
    }

    fun loginForToken() {
        uiScope.launch {

            try {
                val res = NetworkApi.retrofitService.getTokenAsync(
                    "${username.value}",
                    password.value!!
                )
                if (res.isSuccessful) {
                    _navigateToHome.value = res.body()
                } else {
                    val jasonConverter = moshi.adapter<TokenResponse>(TokenResponse::class.java)
                    if (res.errorBody() != null) {
                        val errorRes = jasonConverter.fromJson(res.errorBody()!!.string())
                        _navigateToHome.value = errorRes
                    }
                    Log.d(TAG, "loginForToken: error")
                }

            } catch (e: Exception) {
                Log.d(TAG, "loginForToken: failure: ${e.message}")
            }


        }
    }


    fun navigateToHomeDone() {
        _navigateToHome.value = null
        _bottomNavState.value = View.VISIBLE
        Log.d(TAG, "navigateToHomeDone: ${bottomNavState.value}")
    }
}