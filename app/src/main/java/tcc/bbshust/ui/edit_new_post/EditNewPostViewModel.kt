package tcc.bbshust.ui.edit_new_post

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tcc.bbshust.network.NetworkApi
import tcc.bbshust.network.request.PostRequest
import tcc.bbshust.utils.makeToken

class EditNewPostViewModel(
    application: Application,
    private val token: String
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "EditNewPostViewModel"
    }

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    val title = MutableLiveData<String>("")
    val content = MutableLiveData<String>("")

    private val _navigateToHome = MutableLiveData<Boolean>(false)
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private val _makeToast = MutableLiveData<String>()
    val makeToast: LiveData<String>
        get() = _makeToast

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun onButtonCancelClick() {
        _navigateToHome.value = true
    }

    fun onButtonConfirmClick() {

        uiScope.launch {
            val mTitle = title.value
            val mContent = content.value
            if (mTitle == null || mContent == null || mTitle == "" || mContent == "") {
                _makeToast.value = "标题或内容为空！"
                return@launch
            }
            val request = PostRequest(mTitle, mContent)
            try {
                val res = NetworkApi.retrofitService.newPostAsync(makeToken(token), request)
                if (res.isSuccessful) {
                    _makeToast.value = "发帖成功！"
                    _navigateToHome.value = true
                } else {
                    _makeToast.value = "嘤嘤嘤，没发出去"
                }
            } catch (e: Exception) {
                _makeToast.value = e.message
                Log.e(TAG, "onButtonConfirmClick: EXCEPTION RAISED", e)
            }
        }
    }

    fun doneMakingToast() {
        _makeToast.value = null
    }

    fun doneNavigatingToHome() {
        _navigateToHome.value = false
    }
}
