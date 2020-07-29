package tcc.bbshust.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _hint= MutableLiveData<String>().apply {
        value = "This is User Fragment"
    }

    val hint: LiveData<String>
        get() = _hint
}