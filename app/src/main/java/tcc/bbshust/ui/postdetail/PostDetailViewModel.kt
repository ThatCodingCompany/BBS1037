package tcc.bbshust.ui.postdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import tcc.bbshust.network.NetworkApi
import tcc.bbshust.network.data.Post
import tcc.bbshust.network.data.TokenData
import tcc.bbshust.network.moshi
import tcc.bbshust.network.request.ReplyRequest
import tcc.bbshust.network.response.GetPostByIdResponse
import tcc.bbshust.network.response.ReplyResponse
import tcc.bbshust.utils.makeToken
import java.lang.Exception

class PostDetailViewModel(
    private val token: TokenData,
    private val postId: String
) : ViewModel() {

    private val TAG = "PostDetailViewModel"

    val replyList: MutableLiveData<List<Post>> = MutableLiveData()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _isReplied = MutableLiveData<Boolean>()
    val isReplied: LiveData<Boolean>
        get() = _isReplied

    private val _makeToast = MutableLiveData<String>()
    val makeToast: LiveData<String>
        get() = _makeToast

    init {
        getPostDetail()
    }

    fun getPostDetail() {

        uiScope.launch {
            try {
                val res =
                    NetworkApi.retrofitService.getPostByIdAsync(makeToken(token.token), postId)
                if (res.isSuccessful) {
                    val header = Post(
                        res.body()!!.data!!.postId,
                        res.body()!!.data!!.createTime,
                        res.body()!!.data!!.updateTime,
                        res.body()!!.data!!.title,
                        res.body()!!.data!!.author,
                        res.body()!!.data!!.content,
                        null
                    )
                    replyList.value = listOf(header) + res.body()!!.data!!.replyList!!
                } else {
                    try {
                        val jsonConverter =
                            moshi.adapter<GetPostByIdResponse>(GetPostByIdResponse::class.java)
                        val errorBody = jsonConverter.fromJson(res.errorBody()!!.string())
                        _makeToast.value = errorBody!!.hint
                    } catch (e: Exception) {
                        _makeToast.value = e.message
                    }
                }
            } catch (e: Exception) {
                _makeToast.value = e.message
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }

    fun addReply(content: String) {
        uiScope.launch {
            try {
                val request = ReplyRequest(content)
                val res =
                    NetworkApi.retrofitService.replyAsync(makeToken(token.token), postId, request)
                if (res.isSuccessful) {
                    _isReplied.value = true
                } else {
                    try {
                        val jsonConverter =
                            moshi.adapter<ReplyResponse>(ReplyResponse::class.java)
                        val errorBody = jsonConverter.fromJson(res.errorBody()!!.string())
                        _makeToast.value = errorBody!!.hint
                        Log.d(TAG, "addReply: ${errorBody.hint}")
                    } catch (e: Exception) {
                        Log.d(TAG, "addReply: ${e.message}")
                        _makeToast.value = e.message
                    }
                }

            } catch (e: Exception) {
                _makeToast.value = e.message
            }
        }
    }


}