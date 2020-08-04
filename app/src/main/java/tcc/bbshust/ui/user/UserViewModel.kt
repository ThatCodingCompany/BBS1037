package tcc.bbshust.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import tcc.bbshust.database.Account
import tcc.bbshust.database.AccountDatabaseDao

class UserViewModel(
    application: Application,
    private val database: AccountDatabaseDao
) : AndroidViewModel(application) {

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    val userEmail = MutableLiveData<String>()

    private suspend fun getUserEmail(): String {
        return withContext(Dispatchers.IO) {
            database.getAccount()!!.email
        }
    }

    init {
        uiScope.launch {
            userEmail.value = getUserEmail()
        }
    }

}