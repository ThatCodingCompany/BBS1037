package tcc.bbshust.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessageViewModel : ViewModel() {
    private val _hint= MutableLiveData<String>().apply {
        value = "This is Message Fragment"
    }

    val hint: LiveData<String>
        get() = _hint
}