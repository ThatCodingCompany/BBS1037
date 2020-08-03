package tcc.bbshust.ui.edit_new_post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditNewPostViewModelFactory(
    private val application: Application,
    private val token: String
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNewPostViewModel::class.java)) {
            return EditNewPostViewModel(application, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
