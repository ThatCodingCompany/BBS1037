package tcc.bbshust.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessageViewModel : ViewModel() {
    private val _hint= MutableLiveData<String>().apply {
        value = "呜呜，此功能尚未开放"
    }

    val hint: LiveData<String>
        get() = _hint
}