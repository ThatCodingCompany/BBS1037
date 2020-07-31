package tcc.bbshust.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tcc.bbshust.network.NetworkApi
import tcc.bbshust.network.moshi
import tcc.bbshust.network.request.RegisterRequest
import tcc.bbshust.network.request.VerifyRequest
import tcc.bbshust.network.response.RegisterResponse
import tcc.bbshust.network.response.VerifyResponse
import java.lang.Exception


class RegisterViewModel : ViewModel() {

    private val TAG = "RegisterViewModel :"

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val email = MutableLiveData<String>()
    val verifyCode = MutableLiveData<String>()
    val passWord = MutableLiveData<String>()
    val verifyPass = MutableLiveData<String>()

    private val _registerRes = MutableLiveData<RegisterResponse>()
    val registerRes: LiveData<RegisterResponse>
        get() = _registerRes

    private val _navigateToLogin = MutableLiveData<VerifyResponse>()
    val navigateToLogin: LiveData<VerifyResponse>
        get() = _navigateToLogin

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    var toastInfo = MutableLiveData<String>()

    fun onClickGetRegisterRes() {
        if (email.value != null && email.value != "") {
            uiScope.launch {
                try {
                    val registerRequest = RegisterRequest(email.value!!)
                    val res = NetworkApi.retrofitService.registerAsync(
                        registerRequest
                    )
                    if (res.isSuccessful) {
                        _registerRes.value = res.body()
                    } else {
                        val jsonConverter =
                            moshi.adapter<RegisterResponse>(RegisterResponse::class.java)
                        if (res.errorBody() != null) {
                            _registerRes.value = jsonConverter.fromJson(res.errorBody()!!.string())
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "getUserId: ${e.message}")
                }
            }
        } else {
            toastInfo.value = "邮箱不能为空！"
        }

    }


    fun onClickRegisterVerify() {
        if (verifyCode.value != null && verifyCode.value != "" &&
            passWord.value != null && passWord.value != "" &&
            verifyPass.value != null && verifyPass.value != ""
        ) {
            if(passWord.value==verifyPass.value){
                val registerRes=registerRes.value
                if (registerRes!=null){
                    if(registerRes.isSuccess){
                        registerVerify()
                    }else{
                        toastInfo.value="嘤嘤嘤，发生了未知错误，试试重新获取验证码？"
                        toastInfo.value="error:${registerRes.hint}"
                    }
                }else{
                    toastInfo.value="呜呜呜，您还没获取验证码T-T"
                }
            }else{
                toastInfo.value="呜呜呜，两次密码输入不一致"
            }
        } else {
            toastInfo.value="呜呜呜，您还没输入完全"
        }
    }

    private fun registerVerify() {
        //data!=null password and verifyCode
        uiScope.launch {
            try {
                val verifyRequest = VerifyRequest(
                    registerRes.value!!.data!!.id,
                    passWord.value!!,
                    verifyCode.value!!
                )
                val res = NetworkApi.retrofitService.verifyAsync(verifyRequest)
                if (res.isSuccessful) {
                    _navigateToLogin.value = res.body()
                } else {
                    val jasonConverter =
                        moshi.adapter<VerifyResponse>(VerifyResponse::class.java)
                    if (res.errorBody() != null) {
                        _navigateToLogin.value = jasonConverter.fromJson(res.errorBody()!!.string())
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "registerVerify: ${e.message}")
            }
        }
    }

    fun navigateToHomeDone() {
        _navigateToLogin.value=null
    }
}