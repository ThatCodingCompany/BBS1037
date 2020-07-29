package tcc.bbshust.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _hint=MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val hint:LiveData<String>
        get() = _hint
}