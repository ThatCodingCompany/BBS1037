package tcc.bbshust.ui.reply

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReplyViewModel : ViewModel() {
    val content = MutableLiveData<String>()
}