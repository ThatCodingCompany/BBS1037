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

class HomeViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val postList = MutableLiveData<MutableList<Post>>()

    init {
//        uiScope.launch {
//            val res = NetworkApi.retrofitService.getAllPostsAsync()
//        }
    }
}