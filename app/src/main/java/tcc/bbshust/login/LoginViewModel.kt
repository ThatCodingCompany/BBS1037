package tcc.bbshust.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tcc.bbshust.network.NetworkApi

private const val TAG = "LoginViewModel"

class LoginViewModel : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onLoginButtonClick() {
        Log.d(TAG, "onLoginButtonClick: invoked")
        uiScope.launch {
            if (username.value != null && username.value != "" && password.value != null && password.value != "") {
                val tokenDeferred = NetworkApi.retrofitService.getToken(
                    "${username.value}@hust.edu.cn",
                    password.value!!
                )

                try {
                    val tokenResult = tokenDeferred.await()
                    Log.d(TAG, tokenResult.token.token)
                } catch (e: Exception) {
                    Log.d(TAG, "onLoginButtonClick: failure: ${e.message}")
                }
            }
        }
    }
}