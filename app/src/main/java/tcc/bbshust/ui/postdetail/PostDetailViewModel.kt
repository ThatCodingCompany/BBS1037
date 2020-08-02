package tcc.bbshust.ui.postdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import tcc.bbshust.network.NetworkApi
import tcc.bbshust.network.data.Post
import tcc.bbshust.network.data.TokenData
import tcc.bbshust.network.moshi
import tcc.bbshust.network.response.GetPostByIdResponse
import tcc.bbshust.utils.makeToken
import java.lang.Exception

class PostDetailViewModel(
    private val token: TokenData,
    private val postId: String
) : ViewModel() {

    private val TAG="PostDetailViewModel"

    val replyList: MutableLiveData<List<Post>> = MutableLiveData()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getPostDetail()
    }

    private fun getPostDetail() {

        uiScope.launch {
            val res = NetworkApi.retrofitService.getPostByIdAsync(makeToken(token.token), postId)
            if (res.isSuccessful) {
                replyList.value = res.body()!!.data!!.replyList
            } else {
                val jsonConverter =
                    moshi.adapter<GetPostByIdResponse>(GetPostByIdResponse::class.java)
                val errorBody=jsonConverter.fromJson(res.errorBody()!!.string())
                try {
                    Log.d(TAG, "getPostDetail: ${errorBody!!.hint}")
                }catch (e:Exception){
                    Log.d(TAG, "getPostDetail: ${e.message}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}