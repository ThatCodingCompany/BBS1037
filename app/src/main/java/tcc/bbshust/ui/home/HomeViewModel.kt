package tcc.bbshust.ui.home

import android.app.Application
import android.util.Log
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
import tcc.bbshust.network.response.GetAllPostsResponse
import tcc.bbshust.network.response.TokenResponse
import tcc.bbshust.utils.makeToken

class HomeViewModel(
    private val dataSource: AccountDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private var token: TokenData? = null
    private var account: Account? = null

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val postList: MutableLiveData<List<Post>> = MutableLiveData()

    private val _makeToast = MutableLiveData<String>()
    val makeToast: LiveData<String>
        get() = _makeToast

    private val _navigateToLogin = MutableLiveData<Boolean>(false)
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    init {
        fillPostList()
    }

    private fun fillPostList() {
        uiScope.launch {
            val mToken = token
            if (account == null) {
                account = getAccount()
                if (account == null) {
                    _navigateToLogin.value = true
                    return@launch
                }
            }
            if (mToken == null || mToken.expireTime <= System.currentTimeMillis() + 1800000) {
                reLogin()
            }

            val res = NetworkApi.retrofitService.getAllPostsAsync(makeToken(token!!.token))
            if (res.isSuccessful) {
                postList.value = res.body()!!.data!!.postsInfo
            } else {
                val jsonConverter =
                    moshi.adapter<GetAllPostsResponse>(GetAllPostsResponse::class.java)
                val errorBody = jsonConverter.fromJson(res.errorBody()!!.string())
                try {
                    _makeToast.value = errorBody!!.hint
                } catch (e: Exception) {
                    _makeToast.value = e.message
                }
            }
        }
    }

    fun doneMakingToast() {
        _makeToast.value = null
    }

    fun doneNavigating() {
        _navigateToLogin.value = false
    }

    private suspend fun reLogin() {

        try {
            val res =
                NetworkApi.retrofitService.getTokenAsync(account!!.email, account!!.password)
            if (res.isSuccessful) {
                token = res.body()!!.data!!
            } else {
                val converter = moshi.adapter<TokenResponse>(TokenResponse::class.java)
                _makeToast.value = converter.fromJson(res.errorBody()!!.string())!!.hint
            }
        } catch (e: Exception) {
            Log.e(TAG, "reLogin: ", e)
        }
    }

    private suspend fun getAccount(): Account? {
        return withContext(Dispatchers.IO) {
            dataSource.getAccount()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}