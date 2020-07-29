package tcc.bbshust.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tcc.bbshust.network.NetworkApi
import tcc.bbshust.network.data.Post
import tcc.bbshust.utils.makeToken

private const val TAG = "LoginViewModel"

class LoginViewModel : ViewModel() {

    private var userId = ""
    private var token = ""
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onLoginButtonClick() {
        Log.d(TAG, "onLoginButtonClick: invoked")
        uiScope.launch {
            if (username.value != null && username.value != "" && password.value != null && password.value != "") {
                val tokenDeferred = NetworkApi.retrofitService.getTokenAsync(
                    "${username.value}@hust.edu.cn",
                    password.value!!
                )

                try {
                    val tokenResult = tokenDeferred.await()
                    token = tokenResult.data.token
                    userId = tokenResult.data.id
                    Log.d(TAG, token)
                } catch (e: Exception) {
                    Log.d(TAG, "onLoginButtonClick: failure: ${e.message}")
                }

                //Log.d(TAG, "onLoginButtonClick: token: $token")
                val allPostsResponseDeferred =
                    NetworkApi.retrofitService.getAllPostsAsync(makeToken(token))

                var posts: List<Post>? = null
                try {
                    //Log.d(TAG, "onLoginButtonClick: token: $token")
                    posts = allPostsResponseDeferred.await().data.postsInfo
                    Log.d(TAG, "onLoginButtonClick: success: $posts")
                } catch (e: Exception) {
                    Log.d(TAG, "onLoginButtonClick: failure: ${e.message}")
                }
                if (posts != null) {
                    val specificPostResponseDeferred =
                        NetworkApi.retrofitService.getPostByIdAsync(
                            makeToken(token),
                            posts.get(0).postId
                        )

                    try {
                        val post = specificPostResponseDeferred.await().data
                        Log.d(TAG, "onLoginButtonClick: success: $post")
                    } catch (e: Exception) {
                        Log.d(TAG, "onLoginButtonClick: failure: ${e.message}")
                    }
                }

            }
        }
    }
}