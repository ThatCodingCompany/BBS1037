package tcc.bbshust.login

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import tcc.bbshust.database.Account
import tcc.bbshust.database.AccountDatabaseDao
import tcc.bbshust.network.NetworkApi
import tcc.bbshust.network.data.Post
import tcc.bbshust.network.data.TokenData
import tcc.bbshust.network.moshi
import tcc.bbshust.network.response.TokenResponse
import tcc.bbshust.utils.makeToken
import java.lang.Exception
import kotlin.math.log

private const val TAG = "LoginViewModel"

class LoginViewModel(
    private val database: AccountDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()


    private val _navigateToHome = MutableLiveData<TokenResponse>()

    val navigateToHome: LiveData<TokenResponse>
        get() = _navigateToHome


    fun loginForToken() {
        uiScope.launch {

            val email = "${username.value}@hust.edu.cn"

            try {
                val res = NetworkApi.retrofitService.getTokenAsync(
                    email,
                    password.value!!
                )
                if (res.isSuccessful) {
                    val account = Account(
                        email = email,
                        password = password.value!!,
                        userId = res.body()!!.data!!.id
                    )
                    updateAccountToDatabase(account)
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
                Log.e(TAG, "loginForToken: failure: ${e.message}")
            }


        }
    }

    private suspend fun updateAccountToDatabase(account: Account) {
        withContext(Dispatchers.IO) {
            //TODO: CHECK HERE
            synchronized(this@LoginViewModel) {
                database.clear()
                database.insert(account)
            }
        }
    }

    fun navigateToHomeDone() {
        _navigateToHome.value = null
    }
}