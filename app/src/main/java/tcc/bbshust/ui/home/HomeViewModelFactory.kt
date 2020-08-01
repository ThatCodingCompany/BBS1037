package tcc.bbshust.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.network.data.TokenData

class HomeViewModelFactory(
    private val token: TokenData?,
    private val email: String?,
    private val password: String?
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(token, email, password) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
