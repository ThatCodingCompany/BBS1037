package tcc.bbshust.ui.home

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
import tcc.bbshust.network.response.GetAllPostsResponse
import tcc.bbshust.utils.makeToken

class HomeViewModel(
    private val token: TokenData?,
    private val email: String?,
    private val password: String?
) : ViewModel() {
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
        if (token == null || email == null || password == null) {
            _navigateToLogin.value = true
            return
        }
        uiScope.launch {
            val res = NetworkApi.retrofitService.getAllPostsAsync(makeToken(token.token))
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

    private fun reLogin() {
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}