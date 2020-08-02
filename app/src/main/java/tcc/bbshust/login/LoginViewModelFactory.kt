package tcc.bbshust.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.database.AccountDatabaseDao
import tcc.bbshust.network.data.TokenData

class LoginViewModelFactory(
    private val database: AccountDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}